package com.example.pqc.a2048.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.NavUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.pqc.a2048.R;

import java.util.ArrayList;

import static com.example.pqc.a2048.utils.ConstantNUM.SLIDE_TIME;

public class CardView extends RelativeLayout {
    private TextView mCardView;

    public CardView(@NonNull Context context) {
        this(context, null);
    }

    public CardView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CardView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        View view = View.inflate(context, R.layout.crad_view, this);
        mCardView = view.findViewById(R.id.card_tv);
    }


    public void setNum(int num, boolean anim, int oldNum, boolean changeTwice) {
        if (changeTwice) {
            mCardView.setText("");
            mCardView.setBackground(getResources().getDrawable(R.drawable.text_round_border));
        }
        if (num == 0) {
            mCardView.setText("");
            mCardView.setBackground(getResources().getDrawable(R.drawable.text_round_border));
        } else if (anim) {
            startAnim(num, oldNum, changeTwice);
        } else {
            delayAnim(num, oldNum, changeTwice);
        }
    }

    private void delayAnim(final int num, final int oldNum, boolean changeTwice) {
        if (oldNum != 0 && !changeTwice) {
            mCardView.setText(oldNum + "");
            mCardView.setBackground(getResources().getDrawable(R.drawable.text_round_border_full));
        } else {
            mCardView.setText("");
            mCardView.setBackground(getResources().getDrawable(R.drawable.text_round_border));
        }
        ScaleAnimation delayAnimation = new ScaleAnimation(1, 1, 1, 1, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        delayAnimation.setDuration(SLIDE_TIME);
        startAnimation(delayAnimation);
        delayAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mCardView.setText(num + "");
                mCardView.setBackground(getResources().getDrawable(R.drawable.text_round_border_full));
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    private void startAppearAnim() {
        ScaleAnimation scaleAnimation = new ScaleAnimation(0, 1, 0, 1, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        scaleAnimation.setDuration(SLIDE_TIME - 100);
        startAnimation(scaleAnimation);
    }


    private void startAnim(final int num, final int oldNum, boolean changeTwice) {
        if (oldNum != 0 && !changeTwice) {
            mCardView.setText(oldNum + "");
            mCardView.setBackground(getResources().getDrawable(R.drawable.text_round_border_full));
        } else {
            mCardView.setText("");
            mCardView.setBackground(getResources().getDrawable(R.drawable.text_round_border));
        }
        final ScaleAnimation delayAnimation = new ScaleAnimation(1, 1, 1, 1, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        delayAnimation.setDuration(SLIDE_TIME);
        final ScaleAnimation scaleAnimation = new ScaleAnimation(1, 1.1f, 1, 1.1f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        scaleAnimation.setDuration(150);
        final ScaleAnimation scaleAnimation2 = new ScaleAnimation(1.1f, 1, 1.1f, 1, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        scaleAnimation.setDuration(150);
        startAnimation(delayAnimation);
        delayAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mCardView.setText(num + "");
                mCardView.setBackground(getResources().getDrawable(R.drawable.text_round_border_full));
                startAnimation(scaleAnimation);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        scaleAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                startAnimation(scaleAnimation2);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    public void addCard() {
        mCardView.setBackground(getResources().getDrawable(R.drawable.text_round_border_full));
        mCardView.setText(2 + "");
        startAppearAnim();
    }
}
