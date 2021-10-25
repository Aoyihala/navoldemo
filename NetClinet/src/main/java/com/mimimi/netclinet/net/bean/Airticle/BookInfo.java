package com.mimimi.netclinet.net.bean.Airticle;

import android.content.Context;

import com.blankj.utilcode.util.FileUtils;
import com.google.gson.annotations.SerializedName;

import java.io.File;
import java.util.List;

public class BookInfo
{

    @SerializedName("status")
    private int status;
    @SerializedName("data")
    private DataDTO data;
    @SerializedName("mtime")
    private int mtime;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public DataDTO getData() {
        return data;
    }

    public void setData(DataDTO data) {
        this.data = data;
    }

    public int getMtime() {
        return mtime;
    }

    public void setMtime(int mtime) {
        this.mtime = mtime;
    }

    public static class DataDTO {
        @SerializedName("detail")
        private DetailDTO detail;
        @SerializedName("volumes")
        private List<VolumesDTO> volumes;

        public DetailDTO getDetail() {
            return detail;
        }

        public void setDetail(DetailDTO detail) {
            this.detail = detail;
        }

        public List<VolumesDTO> getVolumes() {
            return volumes;
        }

        public void setVolumes(List<VolumesDTO> volumes) {
            this.volumes = volumes;
        }

        public static class DetailDTO {
            @SerializedName("articleid")
            private int articleid;
            @SerializedName("subid")
            private int subid;
            @SerializedName("articlename")
            private String articlename;
            @SerializedName("cover")
            private String cover;
            @SerializedName("size")
            private SizeDTO size;
            @SerializedName("intro")
            private String intro;
            @SerializedName("flag")
            private int flag;
            @SerializedName("fullflag")
            private int fullflag;
            @SerializedName("display")
            private int display;
            @SerializedName("sortid")
            private int sortid;
            @SerializedName("sort")
            private SortDTO sort;
            @SerializedName("lastupdate")
            private String lastupdate;
            @SerializedName("lasttimestamp")
            private int lasttimestamp;
            @SerializedName("author")
            private String author;
            @SerializedName("illustrator")
            private String illustrator;
            @SerializedName("goodnum")
            private int goodnum;
            @SerializedName("allvote")
            private int allvote;
            @SerializedName("allvisit")
            private int allvisit;
            @SerializedName("weekvisit")
            private int weekvisit;
            @SerializedName("score")
            private int score;
            @SerializedName("pfnum")
            private int pfnum;
            @SerializedName("average")
            private String average;

            public int getArticleid() {
                return articleid;
            }

            public void setArticleid(int articleid) {
                this.articleid = articleid;
            }

            public int getSubid() {
                return subid;
            }

            public void setSubid(int subid) {
                this.subid = subid;
            }

            public String getArticlename() {
                return articlename;
            }

            public void setArticlename(String articlename) {
                this.articlename = articlename;
            }

            public String getCover() {
                return cover;
            }

            public void setCover(String cover) {
                this.cover = cover;
            }

            public SizeDTO getSize() {
                return size;
            }

            public void setSize(SizeDTO size) {
                this.size = size;
            }

            public String getIntro() {
                return intro;
            }

            public void setIntro(String intro) {
                this.intro = intro;
            }

            public int getFlag() {
                return flag;
            }

            public void setFlag(int flag) {
                this.flag = flag;
            }

            public int getFullflag() {
                return fullflag;
            }

            public void setFullflag(int fullflag) {
                this.fullflag = fullflag;
            }

            public int getDisplay() {
                return display;
            }

            public void setDisplay(int display) {
                this.display = display;
            }

            public int getSortid() {
                return sortid;
            }

            public void setSortid(int sortid) {
                this.sortid = sortid;
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

            public int getLasttimestamp() {
                return lasttimestamp;
            }

            public void setLasttimestamp(int lasttimestamp) {
                this.lasttimestamp = lasttimestamp;
            }

            public String getAuthor() {
                return author;
            }

            public void setAuthor(String author) {
                this.author = author;
            }

            public String getIllustrator() {
                return illustrator;
            }

            public void setIllustrator(String illustrator) {
                this.illustrator = illustrator;
            }

            public int getGoodnum() {
                return goodnum;
            }

            public void setGoodnum(int goodnum) {
                this.goodnum = goodnum;
            }

            public int getAllvote() {
                return allvote;
            }

            public void setAllvote(int allvote) {
                this.allvote = allvote;
            }

            public int getAllvisit() {
                return allvisit;
            }

            public void setAllvisit(int allvisit) {
                this.allvisit = allvisit;
            }

            public int getWeekvisit() {
                return weekvisit;
            }

            public void setWeekvisit(int weekvisit) {
                this.weekvisit = weekvisit;
            }

            public int getScore() {
                return score;
            }

            public void setScore(int score) {
                this.score = score;
            }

            public int getPfnum() {
                return pfnum;
            }

            public void setPfnum(int pfnum) {
                this.pfnum = pfnum;
            }

            public String getAverage() {
                return average;
            }

            public void setAverage(String average) {
                this.average = average;
            }

            public static class SizeDTO {
                @SerializedName("small")
                private String small;
                @SerializedName("large")
                private String large;

                public String getSmall() {
                    return small;
                }

                public void setSmall(String small) {
                    this.small = small;
                }

                public String getLarge() {
                    return large;
                }

                public void setLarge(String large) {
                    this.large = large;
                }
            }

            public static class SortDTO {
                @SerializedName("caption")
                private String caption;
                @SerializedName("shortname")
                private String shortname;

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
        }

        public static class VolumesDTO {
            @SerializedName("vid")
            private String vid;
            @SerializedName("volume")
            private String volume;
            @SerializedName("chapters")
            private List<ChaptersDTO> chapters;
            private long totoal;
            private int percent;
            private String faildReason;
            private boolean success=false;
            private String path;

            public boolean isGzip(Context context, String aid)
            {

                if (FileUtils.isDir(context.getExternalCacheDir().getPath()+"/"+aid+"/"+vid))
                {
                    //大于0
                    return FileUtils.getLength(new File(context.getExternalCacheDir().getPath() + "/" + aid + "/" + vid)) > 0;

                }
                else
                {
                    return false;
                }

            }


            public boolean isSuccess() {
                return success;
            }

            public void setSuccess(boolean success) {
                this.success = success;
            }

            public void setPath(String path) {
                this.path = path;
            }

            public String getPath() {
                return path;
            }


            public void setFaildReason(String faildReason) {
                this.faildReason = faildReason;
            }

            public String getFaildReason() {
                return faildReason;
            }

            public void setPercent(int percent) {
                this.percent = percent;
            }

            public int getPercent() {
                return percent;
            }

            public void setTotoal(long totoal) {
                this.totoal = totoal;
            }

            public long getTotoal() {
                return totoal;
            }


            public String getVid() {
                return vid;
            }

            public void setVid(String vid) {
                this.vid = vid;
            }

            public String getVolume() {
                return volume;
            }

            public void setVolume(String volume) {
                this.volume = volume;
            }

            public List<ChaptersDTO> getChapters() {
                return chapters;
            }

            public void setChapters(List<ChaptersDTO> chapters) {
                this.chapters = chapters;
            }

            public static class ChaptersDTO {
                @SerializedName("cid")
                private String cid;
                @SerializedName("chapter")
                private String chapter;

                public String getCid() {
                    return cid;
                }

                public void setCid(String cid) {
                    this.cid = cid;
                }

                public String getChapter() {
                    return chapter;
                }

                public void setChapter(String chapter) {
                    this.chapter = chapter;
                }
            }
        }
    }
}
