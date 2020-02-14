<#include "security.ftlh">
<#import "pager.ftlh" as p>


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

            <div class="card-footer text-muted">
                <a href = "/user-posts/${post.author.id}">${post.authorName}</a>
                <#if post.author.id == currentUserId>
                    <a class="btn btn-primary" href = "/user-posts/${post.author.id}?post=${post.id}">
                        Edit
                    </a>
                </#if>
            </div>

        </div>
    <#else>
        No posts
    </#list>
</div>
<@p.pager url page />