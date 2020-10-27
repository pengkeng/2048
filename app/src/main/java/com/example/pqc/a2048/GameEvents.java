package com.example.pqc.a2048;

public class GameEvents {
    private boolean isWin = false;
    private boolean isFail = false;


    public GameEvents(boolean isWin) {
        this.isWin = isWin;
    }

    public boolean isWin() {
        return isWin;
    }

    public void setWin(boolean win) {
        isWin = win;
    }

    public boolean isFail() {
        return isFail;
    }

    public void setFail(boolean fail) {
        isFail = fail;
    }
}
