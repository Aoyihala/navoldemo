package com.mimimi.lib_booksinfo.bean;

public class PageBean
{


    private int backgroundColor;
    private boolean eveningMode;
    private ContentBean contentBean;

    public void setEveningMode(boolean eveningMode) {
        this.eveningMode = eveningMode;
    }

    public int getBackgroundColor() {
        return backgroundColor;
    }

    public boolean isEveningMode() {
        return eveningMode;
    }

    public void setBackgroundColor(int backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public void setContentBean(ContentBean contentBean) {
        this.contentBean = contentBean;
    }

    public ContentBean getContentBean() {
        return contentBean;
    }
}
