$(document).ready(function() {

    $("#askComment").click(function(event){
        event.preventDefault();
        askComment();
    });

    function askComment() {
        var formData = {
            text : $("#commentText").val(),
            postId : currentPost
        }
        var token = $("meta[name='_csrf']").attr("content");
        var header = $("meta[name='_csrf_header']").attr("content");

        $.ajax({
            type: 'POST',
            contentType : "application/json",
            url: '/post/'+currentPost+'/askComment',
            dataType: "json",
            data : JSON.stringify(formData),
            beforeSend: function(xhr) {
                xhr.setRequestHeader(header, token);
            },
            success: function (result) {
                if (result.status == "success") {
                    alert("You asked us to leave a comment! Please check out the comments later!")

                } else {
                    alert("Unfortunately you cant ask for a comment now. Please try a  bit later!")
                    console.log("ERROR: Unable to post a comment", e);
                }
            },
            error: function (е) {
                alert("Unfortunately you cant ask for a comment now. Please try a  bit later!")
                console.log("ERROR: in ask for a comment", e);
            }
        });
    }
})