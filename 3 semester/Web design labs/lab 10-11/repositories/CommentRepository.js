
const Repository = require("./GeneralRepository");
const MessageModel = require("../models/Comment");

class CommentRepository extends Repository {
    constructor(){
        super();
        this.model = MessageModel;
    }
}

module.exports = new CommentRepository();
