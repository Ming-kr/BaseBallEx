package com.ja.mk.baseballex.commons;

public class ResultData{
    private boolean isFinish;
    private int strike;
    private int ball;

    public boolean isFinish() {
        return isFinish;
    }

    public ResultData(int strike , int ball , boolean isFinish){
        this.strike = strike;
        this.ball = ball;
        this.isFinish = isFinish;
    }

    public int getStrike(){
        return this.strike;
    }
    public int getBall(){
        return this.ball;
    }
}
