package com.example.pqc.a2048;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.MyHolder> {

    private ArrayList<CardBean> mList = new ArrayList<>();

    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        MyHolder myHolder = new MyHolder(new CardView(viewGroup.getContext()));
        return myHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder myHolder, int i) {
        MyHolder holder = (MyHolder) myHolder;
        holder.setData();
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void setList(ArrayList<CardBean> list) {
        mList.clear();
        mList.addAll(list);
    }

    public void move(int state) {

        int level = (int) Math.sqrt(mList.size());
        switch (state) {
            case 1:
                for (int i = 0; i < level; i++) {
                    for (int j = 1; j < level; j++) {
                        int target = j * level + i;
                        for (int k = 0; k < j; k++) {
                            int index = k * level + i;
                            if (mList.get(index).getNumber() == 0) {
                                mList.get(index).setNumber(mList.get(target).getNumber());
                                mList.get(target).setNumber(0);
                            }
                        }
                    }
                    for (int j = 1; j < level; j++) {
                        int target = (j - 1) * level + i;
                        int index = j * level + i;
                        if (mList.get(target).getNumber() == mList.get(index).getNumber()) {
                            mList.get(target).setNumber(mList.get(target).getNumber() * 2);
                            if (mList.get(target).getNumber() != 0) {
                                mList.get(target).setAnim(true);
                                mList.get(index).setNumber(0);
                            }
                        }
                    }
                    for (int j = 1; j < level; j++) {
                        int target = j * level + i;
                        for (int k = 0; k < j; k++) {
                            int index = k * level + i;
                            if (mList.get(index).getNumber() == 0) {
                                mList.get(index).setNumber(mList.get(target).getNumber());
                                mList.get(target).setNumber(0);
                                if (mList.get(target).isAnim()) {
                                    mList.get(target).setAnim(false);
                                    mList.get(index).setAnim(true);
                                }
                            }
                        }
                    }
                }
                break;
            case 2:
                for (int i = 0; i < level; i++) {
                    for (int j = level - 2; j >= 0; j--) {
                        int target = j * level + i;
                        for (int k = level - 1; k > j; k--) {
                            int index = k * level + i;
                            if (mList.get(index).getNumber() == 0) {
                                mList.get(index).setNumber(mList.get(target).getNumber());
                                mList.get(target).setNumber(0);
                            }
                        }
                    }
                    for (int j = level - 2; j >= 0; j--) {
                        int target = (j + 1) * level + i;
                        int index = j * level + i;
                        if (mList.get(target).getNumber() == mList.get(index).getNumber()) {
                            mList.get(target).setNumber(mList.get(target).getNumber() * 2);
                            mList.get(index).setNumber(0);
                            if (mList.get(target).getNumber() != 0) {
                                mList.get(target).setAnim(true);
                                mList.get(index).setNumber(0);
                            }
                        }
                    }
                    for (int j = level - 2; j >= 0; j--) {
                        int target = j * level + i;
                        for (int k = level - 1; k > j; k--) {
                            int index = k * level + i;
                            if (mList.get(index).getNumber() == 0) {
                                mList.get(index).setNumber(mList.get(target).getNumber());
                                mList.get(target).setNumber(0);
                                if (mList.get(target).isAnim()) {
                                    mList.get(target).setAnim(false);
                                    mList.get(index).setAnim(true);
                                }
                            }
                        }
                    }
                }
                break;
            case 3:
                for (int i = 0; i < mList.size(); i += level) {
                    for (int j = i + 1; j < i + level; j++) {
                        int target = j;
                        for (int k = i; k < j; k++) {
                            int index = k;
                            if (mList.get(index).getNumber() == 0) {
                                mList.get(index).setNumber(mList.get(target).getNumber());
                                mList.get(target).setNumber(0);
                            }
                        }
                    }
                    for (int j = i + 1; j < i + level; j++) {
                        int target = j - 1;
                        int index = j;
                        if (mList.get(target).getNumber() == mList.get(index).getNumber()) {
                            mList.get(target).setNumber(mList.get(target).getNumber() * 2);
                            mList.get(index).setNumber(0);
                            if (mList.get(target).getNumber() != 0) {
                                mList.get(target).setAnim(true);
                                mList.get(index).setNumber(0);
                            }
                        }
                    }
                    for (int j = i + 1; j < i + level; j++) {
                        int target = j;
                        for (int k = i; k < j; k++) {
                            int index = k;
                            if (mList.get(index).getNumber() == 0) {
                                mList.get(index).setNumber(mList.get(target).getNumber());
                                mList.get(target).setNumber(0);
                                if (mList.get(target).isAnim()) {
                                    mList.get(target).setAnim(false);
                                    mList.get(index).setAnim(true);
                                }
                            }
                        }
                    }
                }
                break;
            case 4:
                for (int i = 0; i < mList.size(); i += level) {
                    for (int j = i + level - 2; j >= i; j--) {
                        int target = j;
                        for (int k = i + level - 1; k > j; k--) {
                            int index = k;
                            if (mList.get(index).getNumber() == 0) {
                                mList.get(index).setNumber(mList.get(target).getNumber());
                                mList.get(target).setNumber(0);
                            }
                        }
                    }
                    for (int j = i + level - 2; j >= i; j--) {
                        int target = j + 1;
                        int index = j;
                        if (mList.get(target).getNumber() == mList.get(index).getNumber()) {
                            mList.get(target).setNumber(mList.get(target).getNumber() * 2);
                            mList.get(index).setNumber(0);
                            if (mList.get(target).getNumber() != 0) {
                                mList.get(target).setAnim(true);
                                mList.get(index).setNumber(0);
                            }
                        }
                    }
                    for (int j = i + level - 2; j >= i; j--) {
                        int target = j;
                        for (int k = i + level - 1; k > j; k--) {
                            int index = k;
                            if (mList.get(index).getNumber() == 0) {
                                mList.get(index).setNumber(mList.get(target).getNumber());
                                mList.get(target).setNumber(0);
                                if (mList.get(target).isAnim()) {
                                    mList.get(target).setAnim(false);
                                    mList.get(index).setAnim(true);
                                }
                            }
                        }
                    }
                }
                break;
        }
        sendScore();
        addCard();
        judgeGame();
        notifyDataSetChanged();
    }

    private void sendScore() {
        int score = 0;
        for (int i = 0; i < mList.size(); i++) {
            score += mList.get(i).getNumber();
        }
        EventBus.getDefault().post(new ScoreEvents(score));
    }

    private void judgeGame() {
        for (int i = 0; i < mList.size(); i++) {
            if (mList.get(i).getNumber() == 2048) {
                EventBus.getDefault().post(new GameEvents(true));
                return;
            }
        }
    }


    class MyHolder extends RecyclerView.ViewHolder {

        private CardView cardView;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            cardView = (CardView) itemView;
        }

        public void setData() {
            if (mList.get(getAdapterPosition()).isAdd()) {
                cardView.addCard();
            } else {
                cardView.setNum(mList.get(getAdapterPosition()).getNumber(), mList.get(getAdapterPosition()).isAnim());
            }
            mList.get(getAdapterPosition()).setAdd(false);
            mList.get(getAdapterPosition()).setAnim(false);
        }
    }

    public ArrayList<CardBean> getList() {
        return mList;
    }

    private void addCard() {
        boolean couldAdd = false;
        for (int i = 0; i < mList.size(); i++) {
            if (mList.get(i).getNumber() == 0) {
                couldAdd = true;
                break;
            }
        }
        if (couldAdd) {
            while (true) {
                double d = Math.random();
                int index = (int) (d * 100);
                if (index < mList.size() && mList.get(index).getNumber() == 0) {
                    mList.get(index).setNumber(2);
                    mList.get(index).setAdd(true);
                    break;
                }
            }
        } else {
            boolean isFail = true;
            for (int i = 0; i < mList.size(); i++) {
                int level = (int) Math.sqrt(mList.size());
                int top = i - level;
                int bottom = i + level;
                int left = i - 1;
                int right = i + 1;

                if (top >= 0 && mList.get(i).getNumber() == mList.get(top).getNumber()) {
                    isFail = false;
                }
                if (bottom < mList.size() && mList.get(i).getNumber() == mList.get(bottom).getNumber()) {
                    isFail = false;
                }

                if (left >= 0 && left % level != level - 1 && mList.get(i).getNumber() == mList.get(left).getNumber()) {
                    isFail = false;
                }
                if (right < mList.size() && right % level != level && mList.get(i).getNumber() == mList.get(right).getNumber()) {
                    isFail = false;
                }
            }
            if (isFail) {
                GameEvents gameEvents = new GameEvents(false);
                gameEvents.setFail(true);
                EventBus.getDefault().post(gameEvents);
            }

        }
    }
}
