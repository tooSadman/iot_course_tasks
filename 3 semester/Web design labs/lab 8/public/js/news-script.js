'use strict'

Storage.prototype.setObj = function(key, obj) {
  return this.setItem(key, JSON.stringify(obj))
}
Storage.prototype.getObj = function(key) {
  return JSON.parse(this.getItem(key))
}

let storage = window.localStorage;
let newsList = document.getElementById("newsList");
window.addEventListener('online', checkLocalStorage);

loadData()
checkLocalStorage();

function loadData() {
  //loadDataFromServer
}

function isOnline() {
  return window.navigator.onLine;
}

function checkLocalStorage() {

  if (isOnline()) {
    let localNews = storage.getObj("news");

    if (localNews == null) {
      storage.setObj("news", new Array());
    }

    if (localNews.length > 0) {
      localNews.forEach(newsValue => {


        newsValue = JSON.parse(newsValue);
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
        if (newsList != null) {
          newsList.appendChild(news);
        }


      });

      storage.setObj("news", new Array());
    }

  }
}
