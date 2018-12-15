'use strict'


Storage.prototype.setObj = function(key, obj) {
  return this.setItem(key, JSON.stringify(obj))
}
Storage.prototype.getObj = function(key) {
  return JSON.parse(this.getItem(key))
}

let useLocalStorage = false;

let storage = window.localStorage;
let buttonAddComment = document.getElementById("addComment");
let commentsRow = document.getElementById("comments");
buttonAddComment.onclick = onClick;

let requestDB = self.indexedDB.open('LAB_DB', 4);
let db = null;
let productsStore = null;

useIndexedDb();

function isOnline() {
  return window.navigator.onLine;
}



function onClick() {
  let commentInput = document.getElementById("commentFormControlTextarea");

  if (commentInput.value == "" || commentInput.value == " " || commentInput.value.length == 0) {
    alert("Чистий комментар")
  } else {

    if (isOnline()) {
      console.log("Is online");
      drawCommentsForIndexDB(comment);
    } else {

      let commentHTML = document.createElement('div');
      let date = new Date();
        let comment = {
        commentInput: commentInput.value,
        date: date
      };
      let commentJSON = JSON.stringify(comment);

      if (useLocalStorage) {
        let comments = storage.getObj("comments");
        comments.push(commentJSON)

        storage.setObj("comments", comments);
        getData();
      } else {
        addData(comment);

      }

    }
    commentInput.value = "";

  }
}

function useIndexedDb() {

  requestDB.onsuccess = function(event) {
    // get database from event
    db = event.target.result;
    checkStorage();
  };

  requestDB.onerror = function(event) {
    console.log('[onerror]', requestDB.error);
  };

  requestDB.onupgradeneeded = function(event) {
    var db = event.target.result;
    db.createObjectStore('fans', {
      keyPath: 'id',
      autoIncrement: true
    });
    db.createObjectStore('news', {
      keyPath: 'id',
      autoIncrement: true
    });
  };


}

function addData(data) {
  // create transaction from database
  let transaction = db.transaction('fans', 'readwrite');

  // add success event handleer for transaction
  // you should also add onerror, onabort event handlers
  transaction.onsuccess = function(event) {
    console.log('[Transaction] ALL DONE!');
  };

  // get store from transaction
  productsStore = transaction.objectStore('fans');

  // put products data in productsStore

  var db_op_req = productsStore.add(data);

  db_op_req.onsuccess = function(event) {
    console.log("ADDED"); // true
  }
}

function getData(processData) {

  // create transaction from database
  let transaction = db.transaction('fans', 'readwrite');
  let data = [];

  // add success event handleer for transaction
  // you should also add onerror, onabort event handlers
  transaction.onsuccess = function(event) {
    console.log('[Transaction] ALL DONE!');
  };

  // get store from transaction
  productsStore = transaction.objectStore('fans');

  // put products data in productsStore

  productsStore.getAll().onsuccess = function(event) {
    data = event.target.result;
    processData(data);
  };


}

function checkStorage() {


  if (useLocalStorage) {
    let comments = storage.getObj("comments");

    drawComments(comments);

    if (comments == null) {
      storage.setObj("comments", new Array());
    }
  } else {
    getData(drawCommentsForIndexDB);
  }


}

function drawComments(comments) {
  if (comments.length > 0) {
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
    //storage.setObj("comments", new Array());
  }
}

function drawCommentsForIndexDB(comments) {
  if (comments.length > 0) {
    comments.forEach(commentValue => {

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
    //storage.setObj("comments", new Array());
  }
}
