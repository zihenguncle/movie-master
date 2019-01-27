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

    //推荐影院  get
    public static final String URL_RECOMMEND_CINEAMS="cinema/v1/findRecommendCinemas?page=%d&count=%d";

    //附近影院  get
    public static final String URL_NEARBY_CINEAMS="cinema/v1/findNearbyCinemas?page=%d&count=%d";

    //电影详情
    public static final String URL_MOVE_DATEILS ="movie/v1/findMoviesDetail?movieId=%d";

    //根据电影名称模糊查询电影院
    public static final String URL_FIND_CINEMA="cinema/v1/findAllCinemas?page=%d&count=%d&cinemaName=%s";

    //关注影院
    public static final String URL_FOLLOW_CINEMA="cinema/v1/verify/followCinema?cinemaId=%d";

    //取消关注影院
    public static final String URL_CANCEL_FOLLOW_CINEMA="cinema/v1/verify/cancelFollowCinema?cinemaId=%d";


    //user/v1/verify/userSignIn   签到
    public static final String URL_SIGN_IN="user/v1/verify/userSignIn";
    //user/v1/verify/getUserInfoByUserId  个人用户的信息
    public static final String URL_PERSONAL_MESSAGE="user/v1/verify/getUserInfoByUserId";
    //user/v1/verify/modifyUserPwd  修改密码
    public static final String URL_UPDATE_PASSWORD="user/v1/verify/modifyUserPwd";







}
