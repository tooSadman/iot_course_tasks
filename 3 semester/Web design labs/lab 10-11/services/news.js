const NewsRepository = require("../repositories/NewsRepository");

module.exports = {
    findAll: callback => {
        NewsRepository.getAll((err, data) => {
            callback(err, data);
        })
    },

    findOne: (id, callback) => {
        NewsRepository.getById(id, (err, data) => {
            callback(err, data);
        })
    },

    putUser: (data, callback) => {
        let { newsTitle, newsInput } = data;
        let newUser = { newsTitle: newsTitle, newsInput: newsInput };

        NewsRepository.put(newUser, (err) => {
            callback(err);
        })
    },

    deleteData: (id, callback) => {
        NewsRepository.deleteData(id, (err) => {
            callback(err);
        })
    },

    updateData: (id, data, callback) => {
        NewsRepository.updateData(id, data, (err, data) => {
            callback(err, data);
        })
    },
}