from flask import Flask, abort
from flask_restful import Api, Resource, reqparse, fields, marshal

app = Flask(__name__, static_url_path="")
api = Api(app)

spares = [
    {
        'Id': 0,
        'serial_number': 0,
        'price': 0.0,
        'amount': 0,
        'category': None
    }
]

spares_fields = {
    'id': fields.Integer,
    'serial_number': fields.Integer,
    'price': fields.Integer,
    'amount': fields.Integer,
    'category': fields.String
}


class SparesList(Resource):
    def __init__(self):
        self.reqparse = reqparse.RequestParser()
        self.reqparse.add_argument('id', type=int, required=True, help='No Id provided', location='json')
        self.reqparse.add_argument('serial_number', type=int, default=0, location='json')
        self.reqparse.add_argument('price', type=int, default=0.0, location='json')
        self.reqparse.add_argument('amount', type=int, default=0, location='json')
        self.reqparse.add_argument('category', type=str, default="", location='json')
        super(SparesList, self).__init__()

    @staticmethod
    def get():
        return {'All spares available': [marshal(spare, spares_fields) for spare in spares]}

    def put(self):
        args = self.reqparse.parse_args()
        spare = {
            'Id': spares[-1]['Id'] + 1,
            'id': args['id'],
            'serial_number': args['serial_number'],
            'price': args['price'],
            'amount': args['amount'],
            'category': args['category']
        }
        spares.append(spare)
        return {'spare': marshal(spare, spares_fields)}, 201

    def post(self):
        args = self.reqparse.parse_args()
        spare = [spare for spare in spares if spare.get('id') == args['id']]
        if len(spare) == 0:
            abort(404)
        spare = spare[0]
        args = self.reqparse.parse_args()
        for k, v in args.items():
            if v is not None:
                spare[k] = v
        return {'spare': marshal(spare, spares_fields)}


class Spare(Resource):
    def __init__(self):
        self.reqparse = reqparse.RequestParser()
        self.reqparse.add_argument('id', type=int, location='json')
        self.reqparse.add_argument('serial_number', type=int, location='json')
        self.reqparse.add_argument('price', type=int, location='json')
        self.reqparse.add_argument('amount', type=int, location='json')
        self.reqparse.add_argument('category', type=str, location='json')
        super(Spare, self).__init__()  # super().__init__() / spare.__init__(self)

    def get(self, id):
        spare = [spare for spare in spares if spare.get('id') == id]
        if len(spare) == 0:
            abort(404)
        return {'spare': marshal(spare[0], spares_fields)}

    def delete(self, id):
        spare = [spare for spare in spares if spare.get('id') == id]
        if len(spare) == 0:
            abort(404)
        spares.remove(spare[0])
        return {'result': True}


api.add_resource(SparesList, '/spares', endpoint='spares')
api.add_resource(Spare, '/spares/<int:id>', endpoint='spare')

if __name__ == '__main__':
    app.run(debug=True)
