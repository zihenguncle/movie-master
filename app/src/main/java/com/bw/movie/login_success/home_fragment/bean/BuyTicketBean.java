package com.bw.movie.login_success.home_fragment.bean;

import java.io.Serializable;
import java.util.List;

public class BuyTicketBean implements Serializable {

    /**
     * result : [{"address":"北京市崇文区崇文门外大街18号国瑞城首层、地下二层","commentTotal":0,"distance":0,"followCinema":0,"id":9,"logo":"http://mobile.bwstudent.com/images/movie/logo/blh.jpg","name":"北京百老汇影城国瑞购物中心店"},{"address":"北京市海淀区远大路1号B座5层魔影国际影城","commentTotal":0,"distance":0,"followCinema":0,"id":4,"logo":"http://mobile.bwstudent.com/images/movie/logo/mygj.jpg","name":"魔影国际影城"},{"address":"东城区滨河路乙1号雍和航星园74-76号楼","commentTotal":0,"distance":0,"followCinema":0,"id":1,"logo":"http://mobile.bwstudent.com/images/movie/logo/qcgx.jpg","name":"青春光线电影院"}]
     * message : 查询成功
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

    public static class ResultBean implements Serializable{
        /**
         * address : 北京市崇文区崇文门外大街18号国瑞城首层、地下二层
         * commentTotal : 0
         * distance : 0
         * followCinema : 0
         * id : 9
         * logo : http://mobile.bwstudent.com/images/movie/logo/blh.jpg
         * name : 北京百老汇影城国瑞购物中心店
         */

        private String address;
        private int commentTotal;
        private int distance;
        private int followCinema;
        private int id;
        private String logo;
        private String name;

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public int getCommentTotal() {
            return commentTotal;
        }

        public void setCommentTotal(int commentTotal) {
            this.commentTotal = commentTotal;
        }

        public int getDistance() {
            return distance;
        }

        public void setDistance(int distance) {
            this.distance = distance;
        }

        public int getFollowCinema() {
            return followCinema;
        }

        public void setFollowCinema(int followCinema) {
            this.followCinema = followCinema;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getLogo() {
            return logo;
        }

        public void setLogo(String logo) {
            this.logo = logo;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
