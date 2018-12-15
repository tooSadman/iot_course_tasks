const express = require("express");
const bodyParser = require("body-parser");
const path = require("path");
const session = require("express-session");
const MongoStore = require("connect-mongo")(session);
const mongooseConnection = require("./db/dbconnect").connection;
const routes = require("./routes/api/routes");

const staticPath = path.normalize(__dirname + "/public");

const app = express();


app.use(
    session({
        secret: "verysomesecretKeY",
        resave: true,
        saveUninitialized: true,
        store: new MongoStore({
            mongooseConnection: mongooseConnection
        })
    })
);

app.use(express.static(staticPath));

app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: true }));
routes(app);

const server = app.listen(1428);
