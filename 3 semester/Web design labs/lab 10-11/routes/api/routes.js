const user = require("./news");
const comments = require("./comment");

module.exports = function(app) {
    app.use("/api/news", user);
    app.use("/api/comments", comments);
}