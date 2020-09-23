<a class="btn btn-primary" data-toggle="collapse" href="#collapseExample" role="button" aria-expanded="false" aria-controls="collapseExample">
    <#if post??>Post Edit<#else>Post Now</#if>
</a>
<div class="collapse <#if post??>show</#if>" id="collapseExample">
    <div class="form-group mt-3">
        <form method="post" enctype="multipart/form-data">
            <div class="form-group">
                <input type="text" class="form-control ${(textError??)?string('is-invalid','')}"
                       value="<#if post??>${post.text}</#if>" name="text" placeholder="What new has happened?"/>
                <#if textError??>
                    <div class="invalid-feedback">
                        ${textError}
                    </div>
                </#if>
            </div>
            <div class="form-group">
                <div class="custom-file">
                    <input type="file"  name="file" id="customFile"/>
                    <label class="custom-file-label" for="customFile">Choose photo</label>
                </div>
            </div>
            <input type="hidden" name="_csrf" value="${_csrf.token}"/>
            <input type="hidden" name="id" value="<#if post??>${post.id}</#if>"/>
            <table>
                <tr>
                    <td>
                        <button type="submit" class="btn btn-primary">Post</button>
                    </td>
                    <td>
                        <#if post??>
                            <button type="button" class="btn btn-danger" data-toggle="modal" data-target="#modalPostDelete">Delete</button>
                        </#if>
                    </td>
                </tr>
            </table>

        </form>
    </div>
</div>