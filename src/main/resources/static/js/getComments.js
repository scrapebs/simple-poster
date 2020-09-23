
    function ajaxGetComments() {
        $.ajax({
            type: 'GET',
            url: '/post/'+currentPost+'/comments',
            success: function (result) {
                $("#getCommentsDiv ul").empty();
                if (result.status == "success") {
                    $.each(result.data, function (i, comment) {
                        var comment = comment.author.username + ": " + comment.text + "<br>";
                        $('#getCommentsDiv .list-group').append(comment)
                    });
                } else {
                    $('#getCommentsDiv .list-group').append("No comments yet. Make the first one!")
                }
            },
            error: function (е) {
                $("#getCommentsDiv ul").empty();
                $('#getCommentsDiv .list-group').append("Error loading comments!!")
                console.log("ERROR: Unable to load comments", e);
            }
        });
    };
