<#include "security.ftlh">
<meta name="_csrf" content="${_csrf.token}"/>
<meta name="_csrf_header" content="${_csrf.headerName}"/>

<div class="modal fade" id="collapsePostComments" tabindex="-1" role="dialog" aria-labelledby="collapsePostComments" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="exampleModalLabel">Comments</h5>
                <button type="button" class="close" data-dismiss="modal" id="close" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <div id="getCommentsDiv">
                    <ul class="list-group">
                    </ul>
                </div>
            </div>
               <div class="modal-footer">
                    <input class="input-group-text" id="commentText" type="text" name="text">
                    <button type="button" class="btn btn-primary" id="publishComment" >Comment</button>
                    <#if currentUserId == post.author.id>
                        <button type="button" class="btn btn-info" id="askComment" >Ask for a comment</button>
                    </#if>
               </div>
        </div>

    </div>
</div>