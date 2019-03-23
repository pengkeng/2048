package com.example.pqc.a2048.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pqc.a2048.R;

public class MyDialog extends Dialog {
    private Context mContext;
    private boolean mWin;
    private ImageView mPic;
    private TextView mText;

    public MyDialog(Context context, boolean isWin) {
        super(context);
        mContext = context;
        mWin = isWin;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = View.inflate(mContext, R.layout.my_dialog, null);
        setContentView(view);
        mPic = findViewById(R.id.pic);
        mText = findViewById(R.id.laji);
        mText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        mPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        if (mWin) {
            mPic.setImageDrawable(mContext.getResources().getDrawable(R.drawable.win));
            mText.setText("奖励1000金币");
        } else {
            mPic.setImageDrawable(mContext.getResources().getDrawable(R.drawable.fail));
            mText.setText("辣鸡！再来一把");
        }
    }


}
