package com.bw.movie.mvp.eventbus;

public class MessageList {
    String flag;
    Object str;

    public MessageList(String flag, Object str) {
        this.flag = flag;
        this.str = str;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public Object getStr() {
        return str;
    }

    public void setStr(Object str) {
        this.str = str;
    }
}
