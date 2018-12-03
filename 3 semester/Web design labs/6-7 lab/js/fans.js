'use strict'

let buttonAddComment = document.getElementById("addComment");
buttonAddComment.onclick = onClick;


function onClick() {
    let commentsRow = document.getElementById("comments");
    let commentInput = document.getElementById("commentFormControlTextarea");

    if (commentInput.value == "" || commentInput.value == " " || commentInput.value.length == 0) {
        alert("Ви нічого не ввели, спробуйте ще раз!")
    } else {
        let comment = document.createElement('div');
        let date = new Date();

        comment.className = "col-12 fan-comment";
        comment.innerHTML = `${commentInput.value}
                    <br>
                    <div class="info">
                        <span class="date">
                            ${date}
                        </span>
                        <span class="nickname">
                            guest
                        </span>
                    </div>
                    <hr>`;

        commentsRow.appendChild(comment);
        commentInput.value = "";
    }
}
