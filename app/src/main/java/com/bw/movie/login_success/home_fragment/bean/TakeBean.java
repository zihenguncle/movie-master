package com.bw.movie.login_success.home_fragment.bean;

import java.util.List;

public class TakeBean {

    /**
     * message : 请求成功
     * result : [{"commentHeadPic":"http://172.17.8.100/images/head_pic/bwjy.jpg","commentId":3,"commentTime":1533117674000,"commentUserId":6,"commentUserName":"谁的益达","greatNum":0,"hotComment":0,"isGreat":1,"movieComment":"恐龙界的霸主！","replyNum":0},{"commentHeadPic":"http://172.17.8.100/images/movie/head_pic/2018-07-21/20180721120945.jpg","commentId":1,"commentTime":1532681329000,"commentUserId":5,"commentUserName":"你的益达","greatNum":0,"hotComment":0,"isGreat":0,"movieComment":"电影很好看","replyNum":0}]
     * status : 0000
     */

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
        /**
         * commentHeadPic : http://172.17.8.100/images/head_pic/bwjy.jpg
         * commentId : 3
         * commentTime : 1533117674000
         * commentUserId : 6
         * commentUserName : 谁的益达
         * greatNum : 0
         * hotComment : 0
         * isGreat : 1
         * movieComment : 恐龙界的霸主！
         * replyNum : 0
         */

        private String commentHeadPic;
        private int commentId;
        private long commentTime;
        private int commentUserId;
        private String commentUserName;
        private int greatNum;
        private int hotComment;
        private int isGreat;
        private String movieComment;
        private int replyNum;

        public String getCommentHeadPic() {
            return commentHeadPic;
        }

        public void setCommentHeadPic(String commentHeadPic) {
            this.commentHeadPic = commentHeadPic;
        }

        public int getCommentId() {
            return commentId;
        }

        public void setCommentId(int commentId) {
            this.commentId = commentId;
        }

        public long getCommentTime() {
            return commentTime;
        }

        public void setCommentTime(long commentTime) {
            this.commentTime = commentTime;
        }

        public int getCommentUserId() {
            return commentUserId;
        }

        public void setCommentUserId(int commentUserId) {
            this.commentUserId = commentUserId;
        }

        public String getCommentUserName() {
            return commentUserName;
        }

        public void setCommentUserName(String commentUserName) {
            this.commentUserName = commentUserName;
        }

        public int getGreatNum() {
            return greatNum;
        }

        public void setGreatNum(int greatNum) {
            this.greatNum = greatNum;
        }

        public int getHotComment() {
            return hotComment;
        }

        public void setHotComment(int hotComment) {
            this.hotComment = hotComment;
        }

        public int getIsGreat() {
            return isGreat;
        }

        public void setIsGreat(int isGreat) {
            this.isGreat = isGreat;
        }

        public String getMovieComment() {
            return movieComment;
        }

        public void setMovieComment(String movieComment) {
            this.movieComment = movieComment;
        }

        public int getReplyNum() {
            return replyNum;
        }

        public void setReplyNum(int replyNum) {
            this.replyNum = replyNum;
        }
    }
}
