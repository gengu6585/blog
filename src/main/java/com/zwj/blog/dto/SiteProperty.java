package com.zwj.blog.dto;

public enum SiteProperty {
    TAG("tag"),
    CATEGORY("category"),
    ARTICLE("post"),
    PUBLISH("publish"),
    PAGE("page"),
    DRAFT("draft"),
    LINK("link"),
    IMAGE("image"),
    FILE("file"),
    TOKEN("csrf_token"),
    IsRecentCommented("is_recent_commented"),
    IsRecentRead("is_recent_read"),

    /**
     * 附件存放的URL，默认为网站地址，如集成第三方则为第三方CDN域名
     */
    ATTACH_URL("attach_url"),

    /**
     * 网站要过滤，禁止访问的ip列表
     */
    BLOCK_IPS("site_block_ips");


    private String property;

    SiteProperty(java.lang.String Property) {
        this.property = Property;
    }

    public java.lang.String getProperty() {
        return property;
    }

    public void setType(java.lang.String Property) {
        this.property = Property;
    }
}
