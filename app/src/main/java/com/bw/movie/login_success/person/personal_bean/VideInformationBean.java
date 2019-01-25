package com.bw.movie.login_success.person.personal_bean;

import java.util.List;

public class VideInformationBean {

    /**
     * result : [{"id":1,"imageUrl":"http://172.17.8.100/images/movie/stills/wbsys/wbsys1.jpg","name":"我不是药神","releaseTime":1530720000000,"summary":"一位不速之客的意外到访，打破了神油店老板程勇（徐峥 饰）的平凡人生，他从一个交不起房租的男性保健品商贩，一跃成为印度仿制药\u201c格列宁\u201d的独家代理商。收获巨额利润的他，生活剧烈变化，被病患们冠以\u201c药神\u201d的称号。但是，一场关于救赎的拉锯战也在波涛暗涌中慢慢展开......"},{"id":2,"imageUrl":"http://172.17.8.100/images/movie/stills/mtyj/mtyj1.jpg","name":"摩天营救","releaseTime":1532016000000,"summary":"在香港市中心，世界上最高、结构最复杂的摩天大楼遭到破坏，危机一触即发。威尔·索耶（道恩·强森 饰）的妻子萨拉（内芙·坎贝尔 饰）和孩子们在98层被劫为人质，直接暴露在火线上。威尔，这位战争英雄、前联邦调查局救援队员，作为大楼的安保顾问，却被诬陷纵火和谋杀。他必须奋力营救家人，为自己洗脱罪名，关乎生死存亡的高空任务就此展开。"}]
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

    public static class ResultBean {
        /**
         * id : 1
         * imageUrl : http://172.17.8.100/images/movie/stills/wbsys/wbsys1.jpg
         * name : 我不是药神
         * releaseTime : 1530720000000
         * summary : 一位不速之客的意外到访，打破了神油店老板程勇（徐峥 饰）的平凡人生，他从一个交不起房租的男性保健品商贩，一跃成为印度仿制药“格列宁”的独家代理商。收获巨额利润的他，生活剧烈变化，被病患们冠以“药神”的称号。但是，一场关于救赎的拉锯战也在波涛暗涌中慢慢展开......
         */

        private int id;
        private String imageUrl;
        private String name;
        private long releaseTime;
        private String summary;

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

        public long getReleaseTime() {
            return releaseTime;
        }

        public void setReleaseTime(long releaseTime) {
            this.releaseTime = releaseTime;
        }

        public String getSummary() {
            return summary;
        }

        public void setSummary(String summary) {
            this.summary = summary;
        }
    }
}
