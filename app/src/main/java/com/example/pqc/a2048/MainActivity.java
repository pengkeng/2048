package com.example.pqc.a2048;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private GameRecycleView mRecycleView;
    private CardAdapter mAdapter;
    private ArrayList mList = new ArrayList<CardBean>();
    private MediaPlayer mediaPlayer = new MediaPlayer();

    private TextView mScoreView, mMoneyView;
    private ImageButton mBackView, mMusicView;
    public static String MONEY = "MONEY";
    private boolean isShowWinDialog = false;
    private RelativeLayout mContainer;
    private ArrayList mTextList = new ArrayList<TextView>();
    //构建手势探测器

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EventBus.getDefault().register(this);
        play();
        initData();
        initView();

    }


    private void play() {
        try {
            AssetManager assetManager = this.getAssets();   ////获得该应用的AssetManager
            AssetFileDescriptor afd = assetManager.openFd("bgmusic.mp3");   //根据文件名找到文件
            //对mediaPlayer进行实例化
            mediaPlayer = new MediaPlayer();
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.reset();    //如果正在播放，则重置为初始状态
            }
            mediaPlayer.setDataSource(afd.getFileDescriptor(),
                    afd.getStartOffset(), afd.getLength());     //设置资源目录
            mediaPlayer.prepare();//缓冲
            mediaPlayer.start();//开始或恢复播放
            mediaPlayer.setLooping(true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void initData() {
        mList.clear();
        for (int i = 0; i < 25; i++) {
            CardBean bean = new CardBean();
            bean.setIndex(i);
            if (i == 0) {
                bean.setNumber(2);
            }
            mList.add(bean);
        }
    }

    private void initView() {
        mScoreView = findViewById(R.id.score_tv);
        mMoneyView = findViewById(R.id.money_tv);
        mBackView = findViewById(R.id.back_iv);
        mMusicView = findViewById(R.id.music_iv);
        mRecycleView = findViewById(R.id.recycleView);
        mContainer = findViewById(R.id.container);
        mRecycleView.setGrid(5);
        mAdapter = new CardAdapter();
        mAdapter.setList(mList);
        mRecycleView.setCardAdapter(mAdapter);
        mScoreView.setOnClickListener(this);
        mMoneyView.setOnClickListener(this);
        mBackView.setOnClickListener(this);
        mMusicView.setOnClickListener(this);
        SharedPreferences sharedPreferences = getSharedPreferences("cache", 0);
        int money = sharedPreferences.getInt(MONEY, 0);
        mMoneyView.setText("金币: " + money);
        mAdapter.setSlideListener(new SlideListener() {
            @Override
            public void translate(int fromX, int toX, int fromY, int toY, String num) {
                final TextView textView = new TextView(getApplicationContext());
                mTextList.add(textView);
                textView.setBackground(getResources().getDrawable(R.drawable.text_round_border_full));
                textView.setTextColor(Color.parseColor("#111111"));
                textView.setTypeface(Typeface.MONOSPACE);
                if (!"0".equals(num)) {
                    textView.setText(num);
                } else {
                    textView.setText("");
                }
                textView.setTextSize(20);
                textView.setGravity(Gravity.CENTER);
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                params.width = DpUtils.dip2px(50);
                params.height = DpUtils.dip2px(50);
                params.leftMargin = DpUtils.dip2px(10 + 60 * fromX);
                params.topMargin = DpUtils.dip2px(10 + 60 * fromY);
                textView.setLayoutParams(params);
                mContainer.addView(textView);
                TranslateAnimation translateAnimation = new TranslateAnimation(0, DpUtils.dip2px(60 * (toX - fromX)), 0, DpUtils.dip2px(60 * (toY - fromY)));
                translateAnimation.setDuration(200);
                translateAnimation.setFillAfter(true);
                textView.startAnimation(translateAnimation);
                translateAnimation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        mContainer.removeView(textView);
                     //   mAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });

            }
        });
    }

    @Override
    public void onClick(View view) {
        if (view == mBackView) {
            finish();
        } else if (view == mMusicView) {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.pause();
            } else {
                mediaPlayer.start();
            }
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mediaPlayer.stop();
        mediaPlayer.release();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(ScoreEvents event) {
        mScoreView.setText("分数: " + event.getScore());
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(GameEvents event) {
        if (event.isWin() && !isShowWinDialog) {
            SharedPreferences sharedPreferences = getSharedPreferences("cache", 0);
            int money = sharedPreferences.getInt(MONEY, 0);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            money += 1000;
            editor.putInt(MONEY, money);
            editor.apply();
            mMoneyView.setText("金币: " + money);
            MyDialog myDialog = new MyDialog(this, event.isWin());
            myDialog.show();
            isShowWinDialog = true;
        }

        if (event.isFail()) {
            MyDialog myDialog = new MyDialog(this, false);
            myDialog.show();
            myDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialogInterface) {
                    initData();
                    mAdapter.setList(mList);
                    mAdapter.notifyDataSetChanged();
                }
            });
        }
    }
}
