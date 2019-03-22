package com.example.pqc.a2048;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

public class GameRecycleView extends RecyclerView {

    private Context mContext;
    private GridLayoutManager mLayoutManager;
    private CardAdapter mCardAdapter;
    private SlideListener mSlideListener;

    public GameRecycleView(@NonNull Context context) {
        this(context, null);
    }

    public GameRecycleView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GameRecycleView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext = context;
        setOnTouchListener(new OnTouchListener() {
            //记录起始位置和偏移坐标
            private float startX, startY, offsetX, offsetY;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: //监听手指按下的初始位置坐标
                        startX = event.getX();
                        startY = event.getY();
                        break;

                    case MotionEvent.ACTION_UP: //监听手指离开时的位置坐标
                        offsetX = event.getX() - startX;
                        offsetY = event.getY() - startY;

                        if (Math.abs(offsetX) > Math.abs(offsetY)) {
                            if (offsetX < -5) {
                                mCardAdapter.move(3);
                            } else if (offsetX > 5) {
                                mCardAdapter.move(4);
                            }
                        } else {
                            if (offsetY < -5) {
                                mCardAdapter.move(1);
                            } else if (offsetY > 5) {
                                mCardAdapter.move(2);
                            }
                        }
                        break;
                    case MotionEvent.ACTION_MOVE:
                        break;
                    default:
                        break;
                }

                return true;
            }
        });
    }

    public void setGrid(int num) {
        mLayoutManager = new GridLayoutManager(mContext, num);
        init();
    }

    private void init() {
        setLayoutManager(mLayoutManager);
      //  addItemDecoration(new SpaceItemDecoration(4));
        setBackgroundColor(Color.parseColor("#C5C1AA"));
    }

    public void setCardAdapter(CardAdapter adapter) {
        mCardAdapter = adapter;
        setAdapter(mCardAdapter);
    }


    public void setSlideListener(SlideListener listener){
        mSlideListener = listener;
    }
}
