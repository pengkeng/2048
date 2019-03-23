package com.example.pqc.a2048.beans;

public class ScoreEvents {

    private int Score;
    private int money;

    public int getScore() {
        return Score;
    }

    public void setScore(int score) {
        Score = score;
    }

    public ScoreEvents(int score){
        this.Score = score;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }
}
