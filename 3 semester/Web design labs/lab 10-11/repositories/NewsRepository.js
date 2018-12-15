
const Repository = require("./GeneralRepository");
const NewsModel = require("../models/News");

class NewsRepository extends Repository {
    constructor() {
        super();
        this.model = NewsModel;
    }

}

module.exports = new NewsRepository();
