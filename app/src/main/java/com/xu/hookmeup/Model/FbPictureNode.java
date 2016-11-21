package com.xu.hookmeup.Model;

/**
 * Created by marcin on 20.11.16.
 */

public class FbPictureNode {
    private int width,height;
    private boolean issil;
    private String url;

    public void setWidth(int width) {
        this.width = width;
    }

    public int getWidth() {
        return width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getHeight() {
        return height;
    }

    public void setIsSilhouette(boolean is) {
        this.issil = is;
    }

    public boolean getIsSilhouette() {
        return issil;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }
}
