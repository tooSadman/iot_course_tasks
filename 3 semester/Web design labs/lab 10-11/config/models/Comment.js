const mongoose = require("mongoose");

const  commentSchema = mongoose.Schema({
    message: String,
    date: String,
})

const CommentModel = mongoose.model("Comment", commentSchema);

module.exports = CommentModel;