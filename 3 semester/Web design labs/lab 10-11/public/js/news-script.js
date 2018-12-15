'use strict'

Storage.prototype.setObj = function (key, obj) {
    return this.setItem(key, JSON.stringify(obj))
}
Storage.prototype.getObj = function (key) {
    return JSON.parse(this.getItem(key))
}

let storage = window.localStorage;
let newsList = document.getElementById("newsList");
window.addEventListener('online', loadData);

loadData();
checkLocalStorage();

function loadData() {
    if (isOnline()) {

        let initConfig = {
            method: "GET",
            mode: 'cors',
        };

        fetch('http://localhost:1428/api/news', initConfig)
            .then(function (response) {
                return response.json();
            })
            .then(function (resp) {
                resp.forEach((news) => {


                    let newsElement = document.createElement('div');

                    newsElement.className = "col-xl-3 col-md-6 col-sm-12";
                    newsElement.innerHTML = `
                    <div class="card">
                        <img src="img/slider.jpeg" alt="">
                        <div class="card-content">
                            <h5>${news.newsTitle}</h5>
                            ${news.newsInput}
                        </div>
                    </div>`;
                    newsList.appendChild(newsElement);
                })
            })
            .catch(alert);


        let localNews = storage.getObj("news");

        if (localNews == null) {
            storage.setObj("news", new Array());
        }

        if (localNews.length > 0) {
            localNews.forEach(newsValue => {


                newsValue = JSON.parse(newsValue);
                let news = document.createElement('div');

                news.className = "col-xl-3 col-md-6 col-sm-12";
                news.innerHTML = `
                <div class="card">
                    <img src="https://svirtus.cdnvideo.ru/D74Box_RBQEgQ3j4UUcI2KVMQNA=/0x0:1280x720/355x200/filters:quality(90)/https://s3.eu-central-1.amazonaws.com/esforce-media/85/854438f0521464fcdad70a93d36a8e61.jpg?m=33feb0c11dcb0ad4d31b0f217f19cce9" alt="">
                    <div class="card-content">
                        <h5>${newsValue.newsTitle}</h5>
                        ${newsValue.newsInput}
                    </div>
                </div>`;
                newsList.appendChild(news);


            });

            storage.setObj("news", new Array());
        }

    }
}

function isOnline() {
    return window.navigator.onLine;
}

function checkLocalStorage() {

    if (!isOnline()) {
        let localNews = storage.getObj("news");

        if (localNews == null) {
            storage.setObj("news", new Array());
        }

        if (localNews.length > 0) {
            localNews.forEach(newsValue => {


                newsValue = JSON.parse(newsValue);
                let news = document.createElement('div');

                news.className = "col-xl-3 col-md-6 col-sm-12";
                news.innerHTML = `
                <div class="card">
                    <img src="https://svirtus.cdnvideo.ru/D74Box_RBQEgQ3j4UUcI2KVMQNA=/0x0:1280x720/355x200/filters:quality(90)/https://s3.eu-central-1.amazonaws.com/esforce-media/85/854438f0521464fcdad70a93d36a8e61.jpg?m=33feb0c11dcb0ad4d31b0f217f19cce9" alt="">
                    <div class="card-content">
                        <h5>${newsValue.titleInput}</h5>
                        ${newsValue.newsInput}
                    </div>
                </div>`;
                newsList.appendChild(news);


            });

            storage.setObj("news", new Array());
        }

    }
}
