<#import "parts/common.ftlh" as c>

<@c.page>
    <h5>
        User editor
    </h5>
    <form action="/user" method="post">
        <div class="form-group row">
            <input type="text" name="username" class="form-control"value="${user.username}">
        </div>

        <#list roles as role>
            <div class="form-group row">
                <label><input type="checkbox" name="${role}" ${user.roles?seq_contains(role)?string("checked", "")}>${role}</label>
            </div>
        </#list>
        <input type="hidden" value="${user.id}" name="userId">
        <input type="hidden" value="${_csrf.token}" name="_csrf">
        <button type="submit" class="btn btn-primary">Save</button>
    </form>
</@c.page>