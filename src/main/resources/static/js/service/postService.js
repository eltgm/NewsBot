'use strict'

let postServiceFn = function ($http) {
    this.sendNews = function (news) {
        let data = new FormData();
        data.append("text", news.text)
        for (let i in news.photos) {
            data.append("uploadFile[" + i + "]", news.photos[i]);
        }

        $http.post("http://localhost:8080/api/news", data, {
            headers: {'Content-Type': undefined}
        });
    };
}

postModule.service("postService", postServiceFn);