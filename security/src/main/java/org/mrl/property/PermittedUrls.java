package org.mrl.property;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "security.permit-urls")
public class PermittedUrls {

    private String[] get = {};

    private String[] post = {};

    private String[] put = {};

    private String[] delete = {};

    public String[] getGet() {
        return get;
    }

    public void setGet(String[] get) {
        this.get = get;
    }

    public String[] getPost() {
        return post;
    }

    public void setPost(String[] post) {
        this.post = post;
    }

    public String[] getPut() {
        return put;
    }

    public void setPut(String[] put) {
        this.put = put;
    }

    public String[] getDelete() {
        return delete;
    }

    public void setDelete(String[] delete) {
        this.delete = delete;
    }
}
