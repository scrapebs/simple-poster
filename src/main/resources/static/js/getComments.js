
    function ajaxGetComments() {
        $.ajax({
            type: 'GET',
            url: '/post/'+currentPost+'/comments',
            success: function (result) {
                if (result.status == "success") {
                    $('#getCommentsDiv ul').empty();
                    $.each(result.data, function (i, comment) {
                        var comment = comment.author.username + ": " + comment.text + "<br>";
                        $('#getCommentsDiv .list-group').append(comment)
                    });
                } else {
                    $("#getCommentsDiv").html("<strong>Error loading comments</strong>");
                    console.log("Failed loading comments: ", result);
                }
            },
            error: function (е) {
                $("#getResultDiv").html("<strong>Error loading comments</strong>");
                console.log("ERROR: Unable to load comments", e);
            }
        });
    };
