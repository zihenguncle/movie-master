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
    public static final String URL_NEARBY_CINEAMS="cinema/v1/findNearbyCinemas?page=%d&count=%d&longitude=%s&latitude=%s";

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

    //根据影院ID查询该影院当前排期的电影列表
    public static final String URL_MOVIE_AT_TIME="movie/v1/findMovieListByCinemaId?cinemaId=%d";

    //根据电影ID和影院ID查询电影排期列表
    public static final String URL_SCHEDULE_CINEMA="movie/v1/findMovieScheduleList?cinemasId=%d&movieId=%d";

    //查看影片评论
    public static final String URL_SELECT_TAKE = "movie/v1/findAllMovieComment?movieId=%d&page=%d&count=%d";

    //查看影片评论的回复
    public static final String URL_TAKE_TAKE = "movie/v1/findCommentReply?commentId=%d&page=%d&count=%d";

    //URL_TICKET_LIST 购买
    public static final String URL_TICKET_LIST ="movie/v1/findCinemasListByMovieId?movieId=%d";
    //URL_TICKET_RECORD  购票记录  user/v1/verify/findUserBuyTicketRecordList
    public static final String URL_TICKET_RECORD ="user/v1/verify/findUserBuyTicketRecordList?page=%d&count=%d&status=%d";

    //上传头像  user/v1/verify/uploadHeadPic
    public static final String TYPR_INMAGE_XINXI="user/v1/verify/uploadHeadPic";

    //取消关注
    public static final String URLNOLOVEMOVIE= "movie/v1/verify/cancelFollowMovie?movieId=%d";

    //关注电影
    public static final String URL_LOVEMOVIE = "movie/v1/verify/followMovie?movieId=%d";

    //查询电影信息明细
    //http://172.17.8.100/movieApi/cinema/v1/findCinemaInfo
    public static final String URL_FIND_CINEMA_INFO = "cinema/v1/findCinemaInfo?cinemaId=%d";


    //查询影院用户评论列表
    //http://172.17.8.100/movieApi/cinema/v1/findAllCinemaComment
    public static final String URL_FIND_CINEMA_COMMENT = "cinema/v1/findAllCinemaComment?cinemaId=%d&page=%d&count=%d";

    //添加用户对影片的评论  ---入参movieId，commentContent
    public static final String URL_WRITE_TAKE = "movie/v1/verify/movieComment";

    //根据电影ID和影院ID查询电影排期列表--get
    public static final String URL_SCHEDULE = "movie/v1/findMovieScheduleList?cinemasId=%d&movieId=%d";


    //影院评论点赞
    public static final String URL_CINEMA_COMMENT_GRENT = "cinema/v1/verify/cinemaCommentGreat";
    //修改用户信息
    public static final String URL_UPDATE_INFORMMATION = "user/v1/verify/modifyUserInfo";

    //评论点赞
    public static final String URL_TAKE_LOVE = "movie/v1/verify/movieCommentGreat";
    //更新版本
    public static final String URL_UPDATE_CODE="tool/v1/findNewVersion";
    //查询消息
    public static final String URL_SELECT_INFORMATION="tool/v1/verify/findAllSysMsgList?page=%d&count=%d";
    //未读取的消息数量
    public static final String URL_READ_COUNT="tool/v1/verify/findUnreadMessageCount";
    //修改读取的状态
    public static final String URL_UPDATE_READ="tool/v1/verify/changeSysMsgStatus?id=%s";

    //购票下单----POST
    public static final String URL_GOTO_PAY = "movie/v1/verify/buyMovieTicket";

    //支付------POST
    public static final String URL_PAY = "movie/v1/verify/pay";
    //上传
    public static final String URL_TOKEN="tool/v1/verify/uploadPushToken";
}
