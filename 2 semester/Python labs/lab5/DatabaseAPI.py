import configparser
from flask_sqlalchemy import SQLAlchemy
from flask import Flask, jsonify, request

application = Flask(__name__)

config = configparser.ConfigParser()
config.read('/home/sadman/Github/iot_course_tasks/2 semester/Python labs/lab5/spares_db.conf')

application.config['SQLALCHEMY_DATABASE_URI'] = 'mysql+pymysql://' + config.get('DB', 'user') + \
                                                ':' + config.get('DB', 'password') + '@' + \
                                                config.get('DB', 'host') + '/' + config.get('DB', 'db')

application.config['SQLALCHEMY_TRACK_MODIFICATIONS'] = True

mysql = SQLAlchemy()
mysql.init_app(application)


class Spares(mysql.Model):
    __tableserial_number__ = 'spares'
    id = mysql.Column(mysql.Integer, primary_key=True)
    serial_number = mysql.Column(mysql.Integer, nullable=False)
    price = mysql.Column(mysql.Integer, nullable=False)
    amount = mysql.Column(mysql.Integer, nullable=False)

    def __repr__(self):
        return '<Spares (%s, %s, %s, %s) >' % (self.serial_number, self.price, self.amount)


@application.route("/")
def hello():
    return "Hello World!"


@application.route('/spares', methods=['POST'])
def create_spare():
    id = request.get_json()["id"]
    serial_number = request.get_json()["serial_number"]
    price = request.get_json()["price"]
    amount = request.get_json()["amount"]
    curr_session = mysql.session

    spare = Spares(id=id, serial_number=serial_number, price=price, amount=amount)

    try:
        curr_session.add(spare)
        curr_session.commit()
    except:
        curr_session.rollback()
        curr_session.flush()

    spare_id = spare.id
    data = Spares.query.filter_by(id=spare_id).first()

    config.read('/home/sadman/Github/iot_course_tasks/2 semester/Python labs/lab5/spares_db.conf')

    result = [data.serial_number, data.price, data.amount]

    return jsonify(session=result)


@application.route('/spares', methods=['GET'])
def get_spare():
    data = Spares.query.all()

    data_all = []

    for spare in data:
        data_all.append([spare.id, spare.serial_number, spare.price, spare.amount])

    return jsonify(spares=data_all)


@application.route('/spares', methods=['PATCH'])
def update_spare():
    global spare
    spare_id = request.get_json()["id"]
    serial_number = request.get_json()["serial_number"]
    price = request.get_json()["price"]
    amount = request.get_json()["amount"]
    curr_session = mysql.session

    try:
        spare = Spares.query.filter_by(id=spare_id).first()
        spare.serial_number = serial_number
        spare.price = price
        spare.amount = amount
        curr_session.commit()
    except:
        curr_session.rollback()
        curr_session.flush()

    spare_id = spare.id
    data = Spares.query.filter_by(id=spare_id).first()

    config.read('/home/sadman/Github/iot_course_tasks/2 semester/Python labs/lab5/spares_db.conf')

    result = [data.serial_numberdata.price, data.amount]

    return jsonify(session=result)


@application.route('/spares/<int:product_id>', methods=['DELETE'])
def delete_spare(spare_id):
    curr_session = mysql.session

    Spares.query.filter_by(id=spare_id).delete()
    curr_session.commit()

    return get_spare()


if __name__ == "__main__":
    application.run()
