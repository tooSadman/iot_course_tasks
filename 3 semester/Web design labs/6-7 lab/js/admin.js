'use strict'

let buttonAddNews = document.getElementById("addNews");
buttonAddNews.onclick = onClick;


function onClick() {

    let newsInput = document.getElementById("newsInput");
    let titleInput = document.getElementById("titleInput");

    if (newsInput.value == "" || titleInput.value == "") {
        alert("Щоб додати статтю - потрібно заповнити усі поля форми.")
    } else {
        newsInput.value = "";
        titleInput.value = "";
    }
}
