const router = require("express").Router();
const commentService = require("../../services/comment");


router.get("/", (req, res, next) => {
    commentService.findAll((err, data) => {
        if (!err) {
            res.data = data;
            res.json(res.data);
        } else {
            res.status(400);
            res.end();
        };
    });
});

router.get("/:id", (req, res, next) => {
    commentService.findOne(req.params.id, (err, data) => {
        if (!err) {
            res.data = data;
            res.json(res.data);
        } else {
            res.status(400);
            res.end();
        }
    });
});

router.post("/", (req, res, next) => {
    commentService.putComment(req.body, (err) => {
        if (!err) {
            res.send("Saved!");
        } else {
            res.status(400);
            res.end();
        }
    })
});

router.put("/:id", (req, res, next) => {
    commentService.updateData(req.params.id, req.body, (err, data) => {
        if (!err) {
            res.data = data;
            res.json(res.data);
        } else {
            res.status(400);
            res.end();
        }
    })
});

router.delete("/:id", (req, res, next) => {
    commentService.deleteData(req.params.id, (err) => {
        if (!err) {
            res.send("Deleted!");
        } else {
            res.status(400);
            res.end();
        }
    })
});



module.exports = router;