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
            <form method="post" action="/post/<#if post??>${post.id}</#if>/delete">
                <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                <input type="hidden" name="id" value="<#if post??>${post.id}</#if>"/>
                <div class="modal-footer">
                    <input class="input-group-text" id="commentText" type="text" name="text">
                    <button type="button" class="btn btn-primary" id="publishComment" >Comment</button>
                </div>
            </form>
        </div>

    </div>
</div>