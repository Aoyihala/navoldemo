package com.mimimi.lib_booksinfo.bean;

public class ContentBean
{
    public static String IMG_TYPE="img";
    public static String WORDS_TYPE="words";
    private String content;
    private int page;
    private String type;
    private String imgPath;

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

}
