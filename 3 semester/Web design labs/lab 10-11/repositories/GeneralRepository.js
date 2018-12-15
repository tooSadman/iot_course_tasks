class Repository {

    getAll(callback) {
        let model = this.model;
        let query = model.find();
        query.exec(callback);
    }

    getById(id, callback) {
        let model = this.model;
        let query = model.findOne({
            _id: id
        });
        query.exec(callback);
    }

    put(data, callback) {
        let model = this.model;

        model.create(data, callback);
    }

    deleteData(id, callback) {
        let model = this.model;
        let data = model.findOne({
            _id: id
        });

        data.remove().exec(callback);
    }

    updateData(id, data, callback) {
        let model = this.model;
        let conditions = { _id: id };

        model.update(conditions, data, callback);
    }
}

module.exports = Repository;
