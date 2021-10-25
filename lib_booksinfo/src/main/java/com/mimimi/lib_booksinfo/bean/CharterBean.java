package com.mimimi.lib_booksinfo.bean;

import android.content.Context;

import com.google.gson.annotations.SerializedName;
import com.mimimi.lib_booksinfo.utils.SpUtils;

import java.io.File;
import java.util.List;
import java.util.Map;

public class CharterBean
{

    @SerializedName("ver")
    private String ver;
    @SerializedName("makeTime")
    private Integer makeTime;
    @SerializedName("chapters")
    private List<ChaptersDTO> chapters;

    @SerializedName("articleName")
    private String articleName;
    @SerializedName("volumeName")
    private String volumeName;
    @SerializedName("creator")
    private String creator;
    @SerializedName("aid")
    private Integer aid;
    @SerializedName("vid")
    private Integer vid;
    @SerializedName("total")
    private Integer total;
    @SerializedName("sort")
    private SortDTO sort;
    @SerializedName("lastupdate")
    private String lastupdate;
    @SerializedName("coverName")
    private String coverName;

    public String getVer() {
        return ver;
    }

    public void setVer(String ver) {
        this.ver = ver;
    }

    public Integer getMakeTime() {
        return makeTime;
    }

    public void setMakeTime(Integer makeTime) {
        this.makeTime = makeTime;
    }

    public List<ChaptersDTO> getChapters() {
        return chapters;
    }

    public void setChapters(List<ChaptersDTO> chapters) {
        this.chapters = chapters;
    }


    public String getArticleName() {
        return articleName;
    }

    public void setArticleName(String articleName) {
        this.articleName = articleName;
    }

    public String getVolumeName() {
        return volumeName;
    }

    public void setVolumeName(String volumeName) {
        this.volumeName = volumeName;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public Integer getAid() {
        return aid;
    }

    public void setAid(Integer aid) {
        this.aid = aid;
    }

    public Integer getVid() {
        return vid;
    }

    public void setVid(Integer vid) {
        this.vid = vid;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public SortDTO getSort() {
        return sort;
    }

    public void setSort(SortDTO sort) {
        this.sort = sort;
    }

    public String getLastupdate() {
        return lastupdate;
    }

    public void setLastupdate(String lastupdate) {
        this.lastupdate = lastupdate;
    }

    public String getCoverName() {
        return coverName;
    }

    public void setCoverName(String coverName) {
        this.coverName = coverName;
    }



    public static class SortDTO {
        @SerializedName("sortid")
        private Integer sortid;
        @SerializedName("caption")
        private String caption;
        @SerializedName("shortname")
        private String shortname;

        public Integer getSortid() {
            return sortid;
        }

        public void setSortid(Integer sortid) {
            this.sortid = sortid;
        }

        public String getCaption() {
            return caption;
        }

        public void setCaption(String caption) {
            this.caption = caption;
        }

        public String getShortname() {
            return shortname;
        }

        public void setShortname(String shortname) {
            this.shortname = shortname;
        }
    }

    public static class ChaptersDTO {
        @SerializedName("cid")
        private Integer cid;
        @SerializedName("cname")
        private String cname;
        @SerializedName("cmtime")
        private Integer cmtime;
        private boolean localLoadSuccess;

        public List<PageBean> getLocalCache(Context context, String aid, String vid,String cid)
        {

            return SpUtils.INSTANCE.getCharatorData(context,aid,vid).get(cid);

        }

        public void setLocalLoadSuccess(boolean localLoadSuccess) {
            this.localLoadSuccess = localLoadSuccess;
        }

        public boolean isLocalLoadSuccess() {
            return localLoadSuccess;
        }

        public Integer getCid() {
            return cid;
        }

        public void setCid(Integer cid) {
            this.cid = cid;
        }

        public String getCname() {
            return cname;
        }

        public void setCname(String cname) {
            this.cname = cname;
        }

        public Integer getCmtime() {
            return cmtime;
        }

        public void setCmtime(Integer cmtime) {
            this.cmtime = cmtime;
        }
    }
}
