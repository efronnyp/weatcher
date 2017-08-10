package com.levarech.weatcher.data.city.repository.source.network.response;

/**
 * Created by EFR on 19/03/2017.
 * Image info date of current_observation instance.
 */

class ImageInfo {

    private String url;

    private String title;

    private String link;

    public String getUrl() {
        return url;
    }

    public ImageInfo setUrl(String url) {
        this.url = url;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public ImageInfo setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getLink() {
        return link;
    }

    public ImageInfo setLink(String link) {
        this.link = link;
        return this;
    }
}
