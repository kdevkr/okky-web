<h3>사는얘기</h3>
<ul class="list-group">
    <#list life.content as article>
        <#if article.voteCount?? && article.voteCount < 0 >
            <#assign voteIcon = "fa-thumbs-down">
            <#assign articleLevel = "list-group-item-danger">
        <#else>
            <#assign voteIcon = "fa-thumbs-up">
            <#if article.voteCount?? && article.voteCount == 0 >
                <#assign articleLevel = "">
            <#else>
                <#assign articleLevel = "list-group-item-success">
            </#if>
        </#if>

        <a class="list-group-item list-group-item-action ${articleLevel!""} flex-column align-items-start align-middle" href="https://okky.kr/articles/${article.id?c}">
            <div class="d-flex w-100 justify-content-between">
                <#if article.displayAuthor.pictureType == "ATTACHED">
                    <#assign pictureUrl = "https://okky.kr/profile/${article.displayAuthor.picture}">
                <#elseif article.displayAuthor.pictureType == "GRAVATAR">
                    <#assign pictureUrl = "https://www.gravatar.com/avatar/${article.displayAuthor.picture}?d=identicon&s=96">
                <#elseif article.displayAuthor.pictureType == "FACEBOOK">
                    <#assign pictureUrl = "//graph.facebook.com/${article.displayAuthor.picture}/picture?width=32&height=32">
                <#else>
                    <#assign pictureUrl = "${article.displayAuthor.picture}">
                </#if>
                <h6 class="mb-1">
                    ${article.title}
                </h6>
                <small>${article.dateCreated}</small>
            </div>
            <small>
                <img style="width: 1.5rem; height: 1.5rem; border-radius: 1rem" src="${pictureUrl}">
                <span class="align-middle">${article.displayAuthor.nickname}</span>
                <span class="align-middle text-muted ml-2"><i class="fa-solid fa-eye"></i> ${article.viewCount}</span>
                <span class="align-middle text-muted ml-2"><i class="fa-solid ${voteIcon}"></i> ${article.voteCount}</span>
                <span class="align-middle text-muted ml-2"><i class="fa-solid fa-comment"></i> ${article.noteCount}</span>
            </small>
        </a>
    </#list>
</ul>