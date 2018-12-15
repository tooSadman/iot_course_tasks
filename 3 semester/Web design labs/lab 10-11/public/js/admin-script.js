'use strict'

Storage.prototype.setObj = function (key, obj) {
    return this.setItem(key, JSON.stringify(obj))
}
Storage.prototype.getObj = function (key) {
    return JSON.parse(this.getItem(key))
}

let storage = window.localStorage;
let buttonAddNews = document.getElementById("addNews");
buttonAddNews.onclick = onClick;


checkLocalStorage();
window.addEventListener('online', sendDataToServer);

function isOnline() {
    return window.navigator.onLine;
}


function onClick() {

    let newsInput = document.getElementById("newsInput");
    let titleInput = document.getElementById("titleInput");

    if (newsInput.value == "" || titleInput.value == "") {
        alert("Наявні пусті поля!")
    } else if (titleInput.value.length > 50) {
        alert("Занадто довгий заголовок!");
    } else {

        let newsJSON = JSON.stringify({ newsTitle: titleInput.value, newsInput: newsInput.value });

        if (isOnline()) {
            sendDataToServer(newsJSON);
        } else {

            let newsList = storage.getObj("news");
            

            newsList.push(newsJSON);

            storage.setObj("news", newsList);

        }

        newsInput.value = "";
        titleInput.value = "";
    }
}




function sendDataToServer(news) {



        let myHeaders = new Headers();
        myHeaders.append("Content-Type", "application/json");

        let initConfig = {
            method: "POST",
            mode: 'cors',
            headers: myHeaders,
            body: news
        };

        fetch('http://localhost:1428/api/news', initConfig)
            .then(function(response) {
                alert(response.headers.get('Content-Type')); // application/json; charset=utf-8
                alert(response.status); // 200

                return response.json();
            })
            .then(function(resp) {
                alert(resp);
            })
            .catch( alert );


    storage.setObj("news", new Array());
}

function checkLocalStorage() {
    let news = storage.getObj("news");

    if (news == null) {
        storage.setObj("news", new Array());
    }
}
