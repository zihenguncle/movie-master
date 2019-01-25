package com.bw.movie.login_success.home_fragment.bean;

import java.util.List;

/**
 * @author 郭淄恒
 *    banner   Bean
 * @date 2019.1.24     19.20
 */
public class HomeBannerBeantwo {


    private String message;
    private String status;
    private List<ResultBean> result;

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

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public static class ResultBean {


        //private boolean followMovie;
        private int id;
        private String imageUrl;
        private String name;
        private int rank;
        private String summary;

        /*public boolean isFollowMovie() {
            return followMovie;
        }

        public void setFollowMovie(boolean followMovie) {
            this.followMovie = followMovie;
        }
*/
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

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getRank() {
            return rank;
        }

        public void setRank(int rank) {
            this.rank = rank;
        }

        public String getSummary() {
            return summary;
        }

        public void setSummary(String summary) {
            this.summary = summary;
        }
    }
}
