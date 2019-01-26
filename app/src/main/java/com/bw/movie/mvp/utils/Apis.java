package com.bw.movie.mvp.utils;

public class Apis {
    //登录
    public static final String URL_LOGIN="user/v1/login";
    //注册
    public static final String URL_REGISTER="user/v1/registerUser";

    //主页面轮播----GET请求：-------即将上映
    public static final String URL_BANNER = "movie/v1/findComingSoonMovieList?page=%d&count=%d";

    //主页面
    public static final String URL_HOTMOVIE = "movie/v1/findHotMovieList?page=%d&count=%d";

    //主页面---正在上映---get
    public static final String URL_DOINGMOVIE = "movie/v1/findReleaseMovieList?page=%d&count=%d";

    //public static final String
    //微信登录
    public static final String URL_WEIXIN_LOGIN="user/v1/weChatBindingLogin";

    // 影院   cinema/v1/verify/findCinemaPageList  get
    public static final String URL_CIMEAMA="cinema/v1/verify/findCinemaPageList?page=%d&count=%d";

    //  URL_VIDE_INFORMATION    影片  get
    public static final String URL_VIDE_INFORMATION="movie/v1/verify/findMoviePageList?page=%d&count=%d";

    //tool/v1/verify/recordFeedBack  post  意见反馈
    public static final String URL_FEED_BACK="tool/v1/verify/recordFeedBack";

    //电影详情的接口
    public static final String URL_MOVE_DATEILS ="movie/v1/findMoviesDetail?movieId=%d";


}
