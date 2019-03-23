package com.example.pqc.a2048.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.example.pqc.a2048.GameEvents;
import com.example.pqc.a2048.SlideListener;
import com.example.pqc.a2048.beans.CardBean;
import com.example.pqc.a2048.beans.ScoreEvents;
import com.example.pqc.a2048.view.CardView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

public class CardAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<CardBean> mList = new ArrayList<>();
    private SlideListener mSlideListener;
    private ArrayList<Integer> preList = new ArrayList<>();
    private long preTime = 0;

    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        MyHolder myHolder = new MyHolder(new CardView(viewGroup.getContext()));
        return myHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        MyHolder holder = (MyHolder) viewHolder;
        Log.e("111111--->>>", "onBindViewHolder: i:" + i);
        holder.setData();
    }

//    @Override
//    public void onBindViewHolder(@NonNull MyHolder myHolder, int i) {
//        MyHolder holder = (MyHolder) myHolder;
//        Log.e("111111--->>>", "onBindViewHolder: i:" + i);
//        holder.setData();
//    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position, @NonNull List<Object> payloads) {
        onBindViewHolder(viewHolder, position);
        Log.e("111111--->>>", "onBindViewHolder: payloads" + payloads.size());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void setList(ArrayList<CardBean> list) {
        mList.clear();
        mList.addAll(list);
        for (int i = 0; i < mList.size(); i++) {
            preList.add(0);
        }
    }

    public void move(int state) {

        //防止快速点击
        if (System.currentTimeMillis() - preTime < 500) {
            return;
        }
        preTime = System.currentTimeMillis();
        int level = (int) Math.sqrt(mList.size());
        preList.clear();
        for (int i = 0; i < mList.size(); i++) {
            preList.add(mList.get(i).getNumber());
        }
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
                            int number = mList.get(myIndex).getNumber();
                            mList.get(myIndex).setNumber(0);
                            mList.get(myIndex).setChangeTwice(true);
                            mSlideListener.translate(i, i, j, toY, number + "");
                            mList.get(target).setNumber(number);
                        } else if (mList.get(myIndex).getNumber() == mList.get(target).getNumber()) {
                            int number = mList.get(myIndex).getNumber();
                            mList.get(myIndex).setNumber(0);
                            mList.get(myIndex).setChangeTwice(true);
                            mSlideListener.translate(i, i, j, toY, number + "");
                            mList.get(target).setNumber(number * 2);
                            mList.get(target).setAnim(true);
                        } else {
                            if (j == toY + 1) {
                                continue;
                            }
                            int number = mList.get(myIndex).getNumber();
                            mList.get(myIndex).setNumber(0);
                            mList.get(myIndex).setChangeTwice(true);
                            mSlideListener.translate(i, i, j, toY + 1, number + "");
                            mList.get(target + level).setNumber(number);
                        }
                    }
                }
                break;
            case 2:
                for (int i = 0; i < level; i++) {
                    for (int j = level - 2; j >= 0; j--) {
                        int myIndex = j * level + i;
                        if (mList.get(myIndex).getNumber() == 0) {
                            continue;
                        }
                        int target = myIndex;
                        int toY = level - 1;
                        for (int k = j + 1; k <= level - 1; k++) {
                            target = k * level + i;
                            toY = k;
                            if (mList.get(target).getNumber() != 0) {
                                break;
                            }
                        }
                        if (mList.get(target).getNumber() == 0) {
                            int number = mList.get(myIndex).getNumber();
                            mList.get(myIndex).setNumber(0);
                            mList.get(myIndex).setChangeTwice(true);
                            mSlideListener.translate(i, i, j, toY, number + "");
                            mList.get(target).setNumber(number);
                        } else if (mList.get(myIndex).getNumber() == mList.get(target).getNumber()) {
                            int number = mList.get(myIndex).getNumber();
                            mList.get(myIndex).setNumber(0);
                            mList.get(myIndex).setChangeTwice(true);
                            mSlideListener.translate(i, i, j, toY, number + "");
                            mList.get(target).setNumber(number * 2);
                            mList.get(target).setAnim(true);
                        } else {
                            if (j == toY - 1) {
                                continue;
                            }
                            int number = mList.get(myIndex).getNumber();
                            mList.get(myIndex).setNumber(0);
                            mList.get(myIndex).setChangeTwice(true);
                            mSlideListener.translate(i, i, j, toY - 1, number + "");
                            mList.get(target - level).setNumber(number);
                        }
                    }
                }
                break;
            case 3:
                for (int i = 0; i < level; i++) {
                    for (int j = 1; j < level; j++) {
                        int myIndex = i * level + j;
                        if (mList.get(myIndex).getNumber() == 0) {
                            continue;
                        }
                        int target = myIndex;
                        int toX = 0;
                        for (int k = j - 1; k >= 0; k--) {
                            target = i * level + k;
                            toX = k;
                            if (mList.get(target).getNumber() != 0) {
                                break;
                            }
                        }
                        if (mList.get(target).getNumber() == 0) {
                            int number = mList.get(myIndex).getNumber();
                            mList.get(myIndex).setNumber(0);
                            mList.get(myIndex).setChangeTwice(true);
                            mSlideListener.translate(j, toX, i, i, number + "");
                            mList.get(target).setNumber(number);
                        } else if (mList.get(myIndex).getNumber() == mList.get(target).getNumber()) {
                            int number = mList.get(myIndex).getNumber();
                            mList.get(myIndex).setNumber(0);
                            mList.get(myIndex).setChangeTwice(true);
                            mSlideListener.translate(j, toX, i, i, number + "");
                            mList.get(target).setNumber(number * 2);
                            mList.get(target).setAnim(true);

                        } else {
                            if (j == toX + 1) {
                                continue;
                            }
                            int number = mList.get(myIndex).getNumber();
                            mList.get(myIndex).setNumber(0);
                            mList.get(myIndex).setChangeTwice(true);
                            mSlideListener.translate(j, toX + 1, i, i, number + "");
                            mList.get(target + 1).setNumber(number);

                        }
                    }
                }
                break;
            case 4:
                for (int i = 0; i < level; i++) {
                    for (int j = level - 2; j >= 0; j--) {
                        int myIndex = i * level + j;
                        if (mList.get(myIndex).getNumber() == 0) {
                            continue;
                        }
                        int target = myIndex;
                        int toX = 0;
                        for (int k = j + 1; k < level; k++) {
                            target = i * level + k;
                            toX = k;
                            if (mList.get(target).getNumber() != 0) {
                                break;
                            }
                        }
                        if (mList.get(target).getNumber() == 0) {
                            int number = mList.get(myIndex).getNumber();
                            mList.get(myIndex).setNumber(0);
                            mList.get(myIndex).setChangeTwice(true);
                            mSlideListener.translate(j, toX, i, i, number + "");
                            mList.get(target).setNumber(number);
                        } else if (mList.get(myIndex).getNumber() == mList.get(target).getNumber()) {
                            int number = mList.get(myIndex).getNumber();
                            mList.get(myIndex).setNumber(0);
                            mList.get(myIndex).setChangeTwice(true);
                            mSlideListener.translate(j, toX, i, i, number + "");
                            mList.get(target).setNumber(number * 2);
                            mList.get(target).setAnim(true);
                        } else {
                            if (j == toX - 1) {
                                continue;
                            }
                            int number = mList.get(myIndex).getNumber();
                            mList.get(myIndex).setNumber(0);
                            mList.get(myIndex).setChangeTwice(true);
                            mSlideListener.translate(j, toX - 1, i, i, number + "");
                            mList.get(target - 1).setNumber(number);
                        }
                    }
                }
                break;
        }
        sendScore();
        addCard();
        judgeGame();
        notifyItemRangeChanged(0, mList.size());
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
                cardView.setNum(mList.get(getAdapterPosition()).getNumber(), mList.get(getAdapterPosition()).isAnim(), preList.get(getAdapterPosition()), mList.get(getAdapterPosition()).isChangeTwice());
            }
            mList.get(getAdapterPosition()).setAdd(false);
            mList.get(getAdapterPosition()).setAnim(false);
            mList.get(getAdapterPosition()).setChangeTwice(false);
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
