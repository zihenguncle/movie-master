package com.bw.movie.login_success.home_fragment.bean;

import java.util.List;

public class DetailsBean {

    /**
     * result : {"director":"文牧野","duration":"117分钟","followMovie":true,"id":1,"imageUrl":"http://172.17.8.100/images/movie/stills/wbsys/wbsys1.jpg","movieTypes":"剧情 / 喜剧","name":"我不是药神","placeOrigin":"中国","posterList":["http://172.17.8.100/images/movie/stills/wbsys/wbsys1.jpg","http://172.17.8.100/images/movie/stills/wbsys/wbsys2.jpg","http://172.17.8.100/images/movie/stills/wbsys/wbsys3.jpg","http://172.17.8.100/images/movie/stills/wbsys/wbsys4.jpg","http://172.17.8.100/images/movie/stills/wbsys/wbsys5.jpg","http://172.17.8.100/images/movie/stills/wbsys/wbsys6.jpg"],"rank":0,"shortFilmList":[{"imageUrl":"http://172.17.8.100/images/movie/stills/wbsys/wbsys2.jpg","videoUrl":"http://172.17.8.100/video/movie/wbsys/wbsysygp1.ts"},{"imageUrl":"http://172.17.8.100/images/movie/stills/wbsys/wbsys1.jpg","videoUrl":"http://172.17.8.100/video/movie/wbsys/wbsysygp2.ts"},{"imageUrl":"http://172.17.8.100/images/movie/stills/wbsys/wbsys5.jpg","videoUrl":"http://172.17.8.100/video/movie/wbsys/wbsysygp3.mp4"}],"starring":"徐峥,周一围,王传君,谭卓","summary":"一位不速之客的意外到访，打破了神油店老板程勇（徐峥 饰）的平凡人生，他从一个交不起房租的男性保健品商贩，一跃成为印度仿制药\u201c格列宁\u201d的独家代理商。收获巨额利润的他，生活剧烈变化，被病患们冠以\u201c药神\u201d的称号。但是，一场关于救赎的拉锯战也在波涛暗涌中慢慢展开......"}
     * message : 查询成功
     * status : 0000
     */

    private ResultBean result;
    private String message;
    private String status;

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public static class ResultBean {
        /**
         * director : 文牧野
         * duration : 117分钟
         * followMovie : true
         * id : 1
         * imageUrl : http://172.17.8.100/images/movie/stills/wbsys/wbsys1.jpg
         * movieTypes : 剧情 / 喜剧
         * name : 我不是药神
         * placeOrigin : 中国
         * posterList : ["http://172.17.8.100/images/movie/stills/wbsys/wbsys1.jpg","http://172.17.8.100/images/movie/stills/wbsys/wbsys2.jpg","http://172.17.8.100/images/movie/stills/wbsys/wbsys3.jpg","http://172.17.8.100/images/movie/stills/wbsys/wbsys4.jpg","http://172.17.8.100/images/movie/stills/wbsys/wbsys5.jpg","http://172.17.8.100/images/movie/stills/wbsys/wbsys6.jpg"]
         * rank : 0
         * shortFilmList : [{"imageUrl":"http://172.17.8.100/images/movie/stills/wbsys/wbsys2.jpg","videoUrl":"http://172.17.8.100/video/movie/wbsys/wbsysygp1.ts"},{"imageUrl":"http://172.17.8.100/images/movie/stills/wbsys/wbsys1.jpg","videoUrl":"http://172.17.8.100/video/movie/wbsys/wbsysygp2.ts"},{"imageUrl":"http://172.17.8.100/images/movie/stills/wbsys/wbsys5.jpg","videoUrl":"http://172.17.8.100/video/movie/wbsys/wbsysygp3.mp4"}]
         * starring : 徐峥,周一围,王传君,谭卓
         * summary : 一位不速之客的意外到访，打破了神油店老板程勇（徐峥 饰）的平凡人生，他从一个交不起房租的男性保健品商贩，一跃成为印度仿制药“格列宁”的独家代理商。收获巨额利润的他，生活剧烈变化，被病患们冠以“药神”的称号。但是，一场关于救赎的拉锯战也在波涛暗涌中慢慢展开......
         */

        private String director;
        private String duration;
        private int followMovie;
        private int id;
        private String imageUrl;
        private String movieTypes;
        private String name;
        private String placeOrigin;
        private int rank;
        private String starring;
        private String summary;
        private List<String> posterList;
        private List<ShortFilmListBean> shortFilmList;

        public String getDirector() {
            return director;
        }

        public void setDirector(String director) {
            this.director = director;
        }

        public String getDuration() {
            return duration;
        }

        public void setDuration(String duration) {
            this.duration = duration;
        }

        public int getFollowMovie() {
            return followMovie;
        }

        public void setFollowMovie(int followMovie) {
            this.followMovie = followMovie;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getImageUrl() {
            return imageUrl;
        }

        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
        }

        public String getMovieTypes() {
            return movieTypes;
        }

        public void setMovieTypes(String movieTypes) {
            this.movieTypes = movieTypes;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPlaceOrigin() {
            return placeOrigin;
        }

        public void setPlaceOrigin(String placeOrigin) {
            this.placeOrigin = placeOrigin;
        }

        public int getRank() {
            return rank;
        }

        public void setRank(int rank) {
            this.rank = rank;
        }

        public String getStarring() {
            return starring;
        }

        public void setStarring(String starring) {
            this.starring = starring;
        }

        public String getSummary() {
            return summary;
        }

        public void setSummary(String summary) {
            this.summary = summary;
        }

        public List<String> getPosterList() {
            return posterList;
        }

        public void setPosterList(List<String> posterList) {
            this.posterList = posterList;
        }

        public List<ShortFilmListBean> getShortFilmList() {
            return shortFilmList;
        }

        public void setShortFilmList(List<ShortFilmListBean> shortFilmList) {
            this.shortFilmList = shortFilmList;
        }

        public static class ShortFilmListBean {
            /**
             * imageUrl : http://172.17.8.100/images/movie/stills/wbsys/wbsys2.jpg
             * videoUrl : http://172.17.8.100/video/movie/wbsys/wbsysygp1.ts
             */

            private String imageUrl;
            private String videoUrl;

            public String getImageUrl() {
                return imageUrl;
            }

            public void setImageUrl(String imageUrl) {
                this.imageUrl = imageUrl;
            }

            public String getVideoUrl() {
                return videoUrl;
            }

            public void setVideoUrl(String videoUrl) {
                this.videoUrl = videoUrl;
            }
        }
    }
}
