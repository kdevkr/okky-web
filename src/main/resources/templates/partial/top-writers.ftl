<section class="container">
    <h3>Top Writers</h3>
    <ul class="list-group">
        <#list topWriters as topWriter>
            <#if topWriter.pictureType == "ATTACHED">
                <#assign pictureUrl = "https://okky.kr/profile/${topWriter.picture}">
            <#elseif topWriter.pictureType == "GRAVATAR">
                <#assign pictureUrl = "https://www.gravatar.com/avatar/${topWriter.picture}?d=identicon&s=96">
            <#elseif topWriter.pictureType == "FACEBOOK">
                <#assign pictureUrl = "//graph.facebook.com/${topWriter.picture}/picture?width=32&height=32">
            <#else>
                <#assign pictureUrl = "${topWriter.picture}">
            </#if>

            <a class="list-group-item border-0" href="https://okky.kr/users/${topWriter.id?c}">
                <span class="align-middle">
                    <#if topWriter_index == 0>
                        <i class="fa-solid fa-crown text-warning"></i>
                    <#elseif topWriter_index <= 4>
                        <i class="fa-solid fa-medal text-success"></i>
                    <#else>
                        <i class="fa-solid fa-bolt text-danger"></i>
                    </#if>
                </span>
                <img style="width: 2rem; height: 2rem; border-radius: 1rem" src="${pictureUrl}">
                <span class="align-middle">
                    ${topWriter.nickname}
                    <small class="text-muted">${topWriter.gotPoints}</small>
                </span>
            </a>
        </#list>
    </ul>
</section>