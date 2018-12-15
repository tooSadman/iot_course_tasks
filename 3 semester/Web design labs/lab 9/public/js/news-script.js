'use strict'

Storage.prototype.setObj = function (key, obj) {
    return this.setItem(key, JSON.stringify(obj))
}
Storage.prototype.getObj = function (key) {
    return JSON.parse(this.getItem(key))
}

let useLocalStorage = false;

let storage = window.localStorage;
let newsList = document.getElementById("newsList");

let requestDB = self.indexedDB.open('LAB_DB', 4);
let db = null;
let productsStore = null;


//useIndexedDb();
//checkStorage();

class Newsmaker {

     useIndexedDb() {

        requestDB.onsuccess = function (event) {
            // get database from event
            db = event.target.result;
            checkStorage();
        };

        requestDB.onerror = function (event) {
            console.log('[onerror]', requestDB.error);
        };

        requestDB.onupgradeneeded = function (event) {
            var db = event.target.result;
            db.createObjectStore('fans', { keyPath: 'id', autoIncrement: true });
            db.createObjectStore('news', { keyPath: 'id', autoIncrement: true });
        };


    }

    constructor() {
        this.localStoragy = false;
        this.useIndexedDb();
    }
}


function checkStorage() {


    if (useLocalStorage) {
        let news = storage.getObj("news");

        drawNews(news);

        if (news == null) {
            storage.setObj("news", new Array());
        }
    } else {
        getData(drawNews);
    }


}

function getData(processData) {

    // create transaction from database
    let transaction = db.transaction('news', 'readwrite');
    let data = [];

    // add success event handleer for transaction
    // you should also add onerror, onabort event handlers
    transaction.onsuccess = function (event) {
        console.log('[Transaction] ALL DONE!');
    };

    // get store from transaction
    productsStore = transaction.objectStore('news');

    // put products data in productsStore

    productsStore.getAll().onsuccess = function (event) {
        data = event.target.result;
        processData(data);
    };


}



function drawNews(news) {
    if (news.length > 0) {
        news.forEach(newsValue => {

                if(useLocalStorage) {
                    newsValue = JSON.parse(newsValue);
                }
                //newsValue = JSON.parse(newsValue);
                let news = document.createElement('div');

                news.className = "col-xl-4 col-md-4 col-sm-12";
                news.innerHTML = `
                <div class="card">
                    <img src="img/slider.jpeg" alt="">
                    <div class="card-content">
                        <h5>${newsValue.titleInput}</h5>
                        ${newsValue.newsInput}
                    </div>
                </div>`;
                newsList.appendChild(news);
        });
        storage.setObj("comments", new Array());
    }
}

let newsmaker = new Newsmaker();
