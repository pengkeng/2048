package com.example.pqc.a2048;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
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
            if(i == 0){
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
