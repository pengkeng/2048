package com.example.pqc.a2048;

import android.content.SharedPreferences;
import android.os.Handler;
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
import java.util.logging.LogRecord;

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.MyHolder> {

    private ArrayList<CardBean> mList = new ArrayList<>();
    private SlideListener mSlideListener;

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
                        int myIndex = j * level + i;
                        if (mList.get(myIndex).getNumber() == 0) {
                            continue;
                        }
                        int target = myIndex;
                        int toY = 0;
                        for (int k = j - 1; k >= 0; k--) {
                            target = k * level + i;
                            toY = k;
                            if (mList.get(target).getNumber() != 0) {
                                break;
                            }
                        }
                        if (mList.get(target).getNumber() == 0) {
                            mSlideListener.translate(i, i, j, toY, mList.get(myIndex).getNumber() + "");
                            mList.get(target).setNumber(mList.get(myIndex).getNumber());
                            mList.get(myIndex).setNumber(0);
                            notifyItemChanged(myIndex);
                        } else if (mList.get(myIndex).getNumber() == mList.get(target).getNumber()) {
                            mSlideListener.translate(i, i, j, toY, mList.get(myIndex).getNumber() + "");
                            mList.get(target).setNumber(mList.get(myIndex).getNumber() * 2);
                            mList.get(target).setAnim(true);
                            mList.get(myIndex).setNumber(0);
                            notifyItemChanged(myIndex);
                        } else {
                            if (j == toY + 1) {
                                continue;
                            }
                            mSlideListener.translate(i, i, j, toY + 1, mList.get(myIndex).getNumber() + "");
                            mList.get(target + level).setNumber(mList.get(myIndex).getNumber());
                            mList.get(myIndex).setNumber(0);
                            notifyItemChanged(myIndex);
                        }
                    }
                }
                break;
            case 2:

                break;
            case 3:

                break;
            case 4:

                break;
        }
        sendScore();
        addCard();
        judgeGame();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                notifyDataSetChanged();
            }
        },200);
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
                    notifyItemChanged(index);
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

    public void setSlideListener(SlideListener listener) {
        mSlideListener = listener;
    }
}
