package com.example.pqc.a2048;

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


    public void setNum(int num, boolean anim) {
        if (anim && num != 0) {
            startAnim();
        }
        if (num == 0) {
            mCardView.setBackground(getResources().getDrawable(R.drawable.text_round_border));
        } else {
            mCardView.setBackground(getResources().getDrawable(R.drawable.text_round_border_full));
        }
        switch (num) {
            case 0:
                mCardView.setText("");
                break;
            case 2:
                mCardView.setText(num + "");
                break;
            case 4:
                mCardView.setText(num + "");
                break;
            case 8:
                mCardView.setText(num + "");
                break;
            case 16:
                mCardView.setText(num + "");
                break;
            case 32:
                mCardView.setText(num + "");
                break;
            case 64:
                mCardView.setText(num + "");
                break;
            case 128:
                mCardView.setText(num + "");
                break;
            case 256:
                mCardView.setText(num + "");
                break;
            case 512:
                mCardView.setText(num + "");
                break;
            case 1024:
                mCardView.setText(num + "");
                break;
            case 2048:
                mCardView.setText(num + "");
                break;
        }
    }

    private void startAppearAnim() {
        ScaleAnimation scaleAnimation = new ScaleAnimation(0, 1, 0, 1,Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        scaleAnimation.setDuration(400);
        startAnimation(scaleAnimation);
    }


    private void startAnim() {
        final ScaleAnimation scaleAnimation = new ScaleAnimation(1, 1.1f, 1, 1.1f,Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        scaleAnimation.setDuration(150);
        startAnimation(scaleAnimation);
        final ScaleAnimation scaleAnimation2 = new ScaleAnimation(1.1f, 1, 1.1f, 1,Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        scaleAnimation.setDuration(150);
        startAnimation(scaleAnimation);
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
