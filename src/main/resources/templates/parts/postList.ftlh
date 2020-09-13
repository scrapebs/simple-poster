<#include "security.ftlh">
<#import "pager.ftlh" as p>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script src="/static/js/postComment.js"></script>
<script src="/static/js/getComments.js"></script>

<script>
    var currentPost;
    function setCurrentPost(postId){
        currentPost = postId;
        ajaxGetComments();
    }
</script>


<div class="card-columns" id="posts-list">
    <#list page.content as post>
        <div class="card my-3" style="width: 18rem;" data-id="${post.id}">
            <div class="card-img-top">
                <#if post.filename??>
                    <img class="post-image" src="/img/${post.filename}"/>
                </#if>
            </div>
            <div class="m-2">
                <span>${post.text}</span><br/>
            </div>

            <div class="card-footer text-muted container">
                <div class="row">
                    <a class="col align-self-center" href = "/user-posts/${post.author.id}">${post.authorName}</a>
                    <a class="col align-self-center" href ="/posts/${post.id}/like">
                        <#if post.meLiked>
                            <i class="fas fa-heart"></i>
                        <#else>
                            <i class="far fa-heart"></i>
                        </#if>
                        ${post.likes}
                    </a>
                    <a class="col align-self-center" data-toggle="modal" href="#collapsePostComments" id="comments"
                       data-target="#collapsePostComments" aria-expanded="false" aria-controls="collapsePostComments"
                       onclick="setCurrentPost(${post.id})">
                        <i class="fa fa-comments"></i>
                    </a>
                    <#if post.author.id == currentUserId>
                        <a class="col btn btn-primary" href = "/user-posts/${post.author.id}?post=${post.id}">
                            Edit
                        </a>
                    </#if>
                </div>
            </div>

        </div>
    <#else>
        No posts
    </#list>
</div>
<#include "comments.ftlh">
<@p.pager url page />