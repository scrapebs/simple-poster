$(document).ready(function() {

        $("#publishComment").click(function(event){
            event.preventDefault();
            if($("#commentText").val() != "") {
                ajaxPostComment();
            }
        });

        function ajaxPostComment() {
            var formData = {
                text : $("#commentText").val(),
                postId : currentPost
            }
            var token = $("meta[name='_csrf']").attr("content");
            var header = $("meta[name='_csrf_header']").attr("content");

            $.ajax({
                type: 'POST',
                contentType : "application/json",
                url: '/post/save',
                dataType: "json",
                data : JSON.stringify(formData),
                beforeSend: function(xhr) {
                    xhr.setRequestHeader(header, token);
                },
                success: function (result) {
                    if (result.status == "success") {
                        $("#commentText").val("");
                        ajaxGetComments();
                    } else {
                        $("#getCommentsDiv").html("<strong>Error loading comments</strong>");
                        console.log("Failed loading comments: ", result);
                    }
                },
                error: function (е) {
                    $("#getResultDiv").html("<strong>Error loading comments</strong>");
                    console.log("ERROR: Unable to post a comment", e);
                }
            });
        }
    })