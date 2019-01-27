package com.bw.movie.login_success.person.personal_bean;

import java.util.List;

public class TicketInformationBean {

    /**
     * result : [{"amount":3,"beginTime":40800000,"cinemaName":"青春光线电影院","createTime":1533805513000,"endTime":47880000,"id":52,"movieName":"西虹市首富","orderId":"20180809170513183","price":66.5,"screeningHall":"2号厅","status":1,"userId":5},{"amount":3,"beginTime":40800000,"cinemaName":"青春光线电影院","createTime":1533805499000,"endTime":47880000,"id":51,"movieName":"西虹市首富","orderId":"20180809170459908","price":66.5,"screeningHall":"2号厅","status":1,"userId":5}]
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
         * amount : 3
         * beginTime : 40800000
         * cinemaName : 青春光线电影院
         * createTime : 1533805513000
         * endTime : 47880000
         * id : 52
         * movieName : 西虹市首富
         * orderId : 20180809170513183
         * price : 66.5
         * screeningHall : 2号厅
         * status : 1
         * userId : 5
         */

        private int amount;
        private int beginTime;
        private String cinemaName;
        private long createTime;
        private int endTime;
        private int id;
        private String movieName;
        private String orderId;
        private double price;
        private String screeningHall;
        private int status;
        private int userId;

        public int getAmount() {
            return amount;
        }

        public void setAmount(int amount) {
            this.amount = amount;
        }

        public int getBeginTime() {
            return beginTime;
        }

        public void setBeginTime(int beginTime) {
            this.beginTime = beginTime;
        }

        public String getCinemaName() {
            return cinemaName;
        }

        public void setCinemaName(String cinemaName) {
            this.cinemaName = cinemaName;
        }

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public int getEndTime() {
            return endTime;
        }

        public void setEndTime(int endTime) {
            this.endTime = endTime;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getMovieName() {
            return movieName;
        }

        public void setMovieName(String movieName) {
            this.movieName = movieName;
        }

        public String getOrderId() {
            return orderId;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public String getScreeningHall() {
            return screeningHall;
        }

        public void setScreeningHall(String screeningHall) {
            this.screeningHall = screeningHall;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }
    }
}
