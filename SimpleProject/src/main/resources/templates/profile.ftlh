<#import "parts/common.ftlh" as c>

<@c.page>
    ${message?ifExists}
    <form method="post">
        <h5>
            <span class="text-decoration-underline">
                ${username}
            </span>
        </h5>
        <div class="form-group ">
            <label class="col-sm-2 col-form-label">Password</label>
            <div class="col-sm-6">
                <input type="password" name="password" class="form-control" placeholder="Password" />
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-2 col-form-label">Email</label>
            <div class="col-sm-6">
                <input type="email" name="email" class="form-control" placeholder="some@email.com" value="${email!''}"/>
            </div>
        </div>
        <input type="hidden" name="_csrf" value="${_csrf.token}" />
        <button class="btn btn-primary mt-3" type="submit">Save</button>
    </form>
</@c.page>