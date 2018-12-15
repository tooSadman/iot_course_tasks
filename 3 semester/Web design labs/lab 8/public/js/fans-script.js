'use strict'

Storage.prototype.setObj = function(key, obj) {
    return this.setItem(key, JSON.stringify(obj))
}
Storage.prototype.getObj = function(key) {
    return JSON.parse(this.getItem(key))
}

window.addEventListener('online', checkStorage);

let storage = window.localStorage;
let buttonAddComment = document.getElementById("addComment");
let commentsRow = document.getElementById("comments");
buttonAddComment.onclick = onClick;


checkStorage();



function isOnline() {
    return window.navigator.onLine;
}


function onClick() {
    let commentInput = document.getElementById("commentFormControlTextarea");

    if (commentInput.value == "" || commentInput.value == " " || commentInput.value.length == 0) {
        alert("Чистий комментар")
    } else {

        if (isOnline()) {
            console.log("IS online");
            unloadData();
        } else {

            let date = new Date();

            let commentJSON = JSON.stringify({commentInput: commentInput.value, date: date});
            let comments = storage.getObj("comments");
            comments.push(commentJSON)

            storage.setObj("comments", comments);


        }
        commentInput.value = "";
    }
}

function sendDataToServer(comment) {
    storage.setObj("comments", new Array());
}

function checkStorage() {
    let comments = storage.getObj("comments");

    if (comments == null) {
        storage.setObj("comments", new Array());
    }

    if (comments.length > 0 && isOnline()) {
        comments.forEach(commentValue => {
            commentValue = JSON.parse(commentValue);
            let comment = document.createElement('div');

            comment.className = "col-12 fan-comment";
            comment.innerHTML = `${commentValue.commentInput}
                    <br>
                    <div class="info">
                        <span class="date">
                            ${commentValue.date}
                        </span>
                        <span class="nickname">
                            user
                        </span>
                    </div>
                    <hr>`;

            commentsRow.appendChild(comment);
        });
        storage.setObj("comments", new Array());
    }
}

function unloadData() {
    console.log("Online");
    let comments = storage.getObj("comments");

    if (comments.length > 0) {
        sendDataToServer(comments);
        storage.setObj("comments", new Array());
    }
}

