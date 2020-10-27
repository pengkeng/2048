package com.example.pqc.a2048.beans;

public class CardBean {

    private int index;
    private int number = 0;
    private boolean isAnim = false;
    private boolean isAdd = false;
    private boolean changeTwice = false;

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public boolean isAnim() {
        return isAnim;
    }

    public void setAnim(boolean anim) {
        isAnim = anim;
    }

    public boolean isAdd() {
        return isAdd;
    }

    public void setAdd(boolean add) {
        isAdd = add;
    }

    public boolean isChangeTwice() {
        return changeTwice;
    }

    public void setChangeTwice(boolean changeTwice) {
        this.changeTwice = changeTwice;
    }
}
