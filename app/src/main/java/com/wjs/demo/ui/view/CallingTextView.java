package com.wjs.demo.ui.view;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.wjs.demo.utils.LogUtil;

public class CallingTextView extends LinearLayout {

    private static final String TAG = "CallingTextView";

    private int mCurrentIndex = 0;
    private boolean isRunning = false;

    private TextView mContentTextView;
    private TextView mEndingTextView;

    public CallingTextView(Context context) {
        super(context);
        init(context, null, 0, 0);
    }

    public CallingTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0, 0);
    }

    public CallingTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr, 0);
    }

    public CallingTextView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs, defStyleAttr, defStyleRes);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        setOrientation(HORIZONTAL);
        mContentTextView = new TextView(context, attrs, defStyleAttr, defStyleRes);
        mEndingTextView = new TextView(context, attrs, defStyleAttr, defStyleRes);

        mEndingTextView.setGravity(Gravity.START);
        mEndingTextView.setText("");

        addView(mContentTextView);

        int width = (int) mEndingTextView.getPaint().measureText("...");
        addView(mEndingTextView, new LayoutParams(width, ViewGroup.LayoutParams.WRAP_CONTENT));
    }

    private final Handler mHandler = new Handler(Looper.getMainLooper());

    private final Runnable mUpdateCallingTextRunnable = new Runnable() {
        @Override
        public void run() {
            setCallingText();
        }
    };

    public void setText(String stringRes) {
        setText(stringRes, false);
    }

    public void setText(String stringRes, boolean start) {
        LogUtil.i(TAG, "start: " + start);
        mContentTextView.setText(stringRes);
        if (start) {
            start();
        } else {
            stop();
        }
    }

    public void start() {
        if (!isRunning) {
            mCurrentIndex = 0;
            isRunning = true;
            mEndingTextView.setText("");
            mHandler.postDelayed(mUpdateCallingTextRunnable, 400);
        }
    }

    public void stop() {
        mCurrentIndex = 0;
        isRunning = false;
        mCurrentIndex = -1;
        mEndingTextView.setText("");
        mHandler.removeCallbacks(mUpdateCallingTextRunnable);
    }

    private void setCallingText() {
        if (!isRunning) {
            return;
        }
        mCurrentIndex = (++mCurrentIndex) % 4;
        String ending = "";
        switch (mCurrentIndex) {
            case 1:
                ending = ".";
                break;
            case 2:
                ending = "..";
                break;
            case 3:
                ending = "...";
                break;
            case 4:
                ending = "";
                break;
            default:
                break;
        }
        mEndingTextView.setText(ending);
        if (mCurrentIndex == 0) {
            mHandler.postDelayed(mUpdateCallingTextRunnable, 240);
        } else {
            mHandler.postDelayed(mUpdateCallingTextRunnable, 400);
        }
    }
}
