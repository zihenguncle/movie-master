package com.bw.movie.login_success.home_fragment.bean;

/**
 * Author: 郭淄恒
 * Date: 2019/2/13 10:33
 * Description: 选座
 */
public class CinemaSeatTableDetailBean {

    private int cinemasId;
    private String MovieName;
    private String beginTime;
    private String endTime;
    private String hall;
    private int seatsTotal;

    public CinemaSeatTableDetailBean(int cinemasId, String movieName, String beginTime, String endTime, String hall, int seatsTotal) {
        this.cinemasId = cinemasId;
        MovieName = movieName;
        this.beginTime = beginTime;
        this.endTime = endTime;
        this.hall = hall;
        this.seatsTotal = seatsTotal;
    }

    public int getCinemasId() {
        return cinemasId;
    }

    public String getMovieName() {
        return MovieName;
    }

    public String getBeginTime() {
        return beginTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public String getHall() {
        return hall;
    }

    public int getSeatsTotal() {
        return seatsTotal;
    }
}
