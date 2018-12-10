package com.ja.mk.baseballex.commons;

public class RecodeData {
    private String bbr_idx;
    private String bbr_nick;
    private String bbr_content;
    private String bbr_trycount;
    private String bbr_trytime;
    private String bbr_joindate;

    public RecodeData(){

    }

    public RecodeData(String bbr_idx, String bbr_nick, String bbr_content, String bbr_trycount, String bbr_trytime, String bbr_joindate) {
        this.bbr_idx = bbr_idx;
        this.bbr_nick = bbr_nick;
        this.bbr_content = bbr_content;
        this.bbr_trycount = bbr_trycount;
        this.bbr_trytime = bbr_trytime;
        this.bbr_joindate = bbr_joindate;
    }

    public String getBbr_idx() {
        return bbr_idx;
    }

    public void setBbr_idx(String bbr_idx) {
        this.bbr_idx = bbr_idx;
    }

    public String getBbr_nick() {
        return bbr_nick;
    }

    public void setBbr_nick(String bbr_nick) {
        this.bbr_nick = bbr_nick;
    }

    public String getBbr_content() {
        return bbr_content;
    }

    public void setBbr_content(String bbr_content) {
        this.bbr_content = bbr_content;
    }

    public String getBbr_trycount() {
        return bbr_trycount;
    }

    public void setBbr_trycount(String bbr_trycount) {
        this.bbr_trycount = bbr_trycount;
    }

    public String getBbr_trytime() {
        return bbr_trytime;
    }

    public void setBbr_trytime(String bbr_trytime) {
        this.bbr_trytime = bbr_trytime;
    }

    public String getBbr_joindate() {
        return bbr_joindate;
    }

    public void setBbr_joindate(String bbr_joindate) {
        this.bbr_joindate = bbr_joindate;
    }
}
