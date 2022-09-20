package com.ha0l.sstiseccode.model;

/**
 * @author ha0
 * @date 2022-0920
 */
public class PageTemplate {

    private String title;
    private String templateCode;

    public PageTemplate() {}

    public PageTemplate(String title, String templateCode) {
        this.title = title;
        this.templateCode = templateCode;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTemplateCode() {
        return templateCode;
    }

    public void setTemplateCode(String templateCode) {
        this.templateCode = templateCode;
    }
}
