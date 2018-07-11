"use strict"
const AjaxUtils = {
    contextPath: /*[[@{/}]]*/ '',
    post: (url, params, success, fail) => {
        $.ajax({
            url: contextPath + 'rest/' + url || {},
            data: params,
            async: true,
            type: "POST",
            success: function (response) {
                if (success)
                    success(response)
                else
                    console.log(response)
            },
            error: function (response) {
                if (fail)
                    fail(response)
                else
                    console.error(response)
            },
            complete: function (response) {
                console.log(response)
            }
        })
    },
    // get
    get: (url) => {
        $.get(contextPath + 'view/' + url)
    }
}