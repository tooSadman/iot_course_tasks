const mongoose = require("mongoose");

const  newsSchema = mongoose.Schema({
    newsTitle: String,
    newsInput: String
})

const NewsModel = mongoose.model("News", newsSchema);

module.exports = NewsModel;