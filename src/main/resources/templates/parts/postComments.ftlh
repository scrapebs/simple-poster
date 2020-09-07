<div class="modal fade" id="collapsePostComments" tabindex="-1" role="dialog" aria-labelledby="collapsePostComments" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="exampleModalLabel">Comments</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <form method="post" action="/post/<#if post??>${post.id}</#if>/delete">
                <div class="modal-body">
                    <p>Comments</p>
                </div>
                <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                <input type="hidden" name="id" value="<#if post??>${post.id}</#if>"/>
                <div class="modal-footer">
                    <input class="input-group-text" type="text" name="text">
                    <button type="button" class="btn btn-primary" data-dismiss="modal">Comment</button>
                </div>
            </form>
        </div>

    </div>
</div>