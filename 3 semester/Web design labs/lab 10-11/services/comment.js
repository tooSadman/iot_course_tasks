const CommentRepository = require("../repositories/CommentRepository");

module.exports = {
    findAll: callback => {
        CommentRepository.getAll((err, data) => {
            callback(err, data);
        })
    },

    findOne: (id, callback) => {
        CommentRepository.getById(id, (err, data) => {
            callback(err, data);
        })
    },

    putComment: (data, callback) => {
        let { message, date } = data;
        let newMessage = { message: message, date: date};

        CommentRepository.put(newMessage, (err) => {
            callback(err);
        })
    },

    deleteData: (id, callback) => {
        CommentRepository.deleteData(id, (err) => {
            callback(err);
        })
    },

    updateData: (id, data, callback) => {
        CommentRepository.updateData(id, data, (err, data) => {
            callback(err, data);
        })
    },
}