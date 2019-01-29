package com.bw.movie.login_success.home_fragment.bean;

import java.util.List;

public class Take_Take_Bean {

    /**
     * result : [{"commentTime":1532590303000,"replyHeadPic":"http://172.17.8.100/images/head_pic/bwjy.jpg","replyContent":"我还没看","replyUserId":6,"replyUserName":"谁的益达"},{"commentTime":1533002791000,"replyHeadPic":"http://172.17.8.100/images/movie/head_pic/2018-07-21/20180721120945.jpg","replyContent":"赞赞啊","replyUserId":5,"replyUserName":"你的益达"}]
     * message : 请求成功
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
         * commentTime : 1532590303000
         * replyHeadPic : http://172.17.8.100/images/head_pic/bwjy.jpg
         * replyContent : 我还没看
         * replyUserId : 6
         * replyUserName : 谁的益达
         */

        private long commentTime;
        private String replyHeadPic;
        private String replyContent;
        private int replyUserId;
        private String replyUserName;

        public long getCommentTime() {
            return commentTime;
        }

        public void setCommentTime(long commentTime) {
            this.commentTime = commentTime;
        }

        public String getReplyHeadPic() {
            return replyHeadPic;
        }

        public void setReplyHeadPic(String replyHeadPic) {
            this.replyHeadPic = replyHeadPic;
        }

        public String getReplyContent() {
            return replyContent;
        }

        public void setReplyContent(String replyContent) {
            this.replyContent = replyContent;
        }

        public int getReplyUserId() {
            return replyUserId;
        }

        public void setReplyUserId(int replyUserId) {
            this.replyUserId = replyUserId;
        }

        public String getReplyUserName() {
            return replyUserName;
        }

        public void setReplyUserName(String replyUserName) {
            this.replyUserName = replyUserName;
        }
    }
}
