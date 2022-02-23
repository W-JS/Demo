package com.wjs.demo.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.RelativeLayout;

import com.wjs.demo.R;
import com.wjs.demo.ui.view.CallingTextView;
import com.wjs.demo.utils.LogUtil;

public class FullScreenPopup extends Dialog {

    private Context mContext;

    private CallingTextView tvDesc;

    private static FullScreenPopup instance = null;
    private View fullScreenPopupRel;

    private Handler handler = new Handler(Looper.getMainLooper());
    private final long time = 10_000;// 设置壁纸默认时长，超过则设置失败

    /**
     * 样式引用
     *
     * @param context
     */
    private FullScreenPopup(Context context) {
        this(context, R.style.Plane_Dialog);
    }

    public static FullScreenPopup getInstance(Context context) {
        if (instance == null) {
            instance = new FullScreenPopup(context);
        }
        return instance;
    }

    /**
     * Dialog构造
     *
     * @param context
     * @param themeResId
     */
    private FullScreenPopup(Context context, int themeResId) {
        super(context, themeResId);
        this.mContext = context;
    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (isShowing()) {
                LogUtil.e("耗时 " + (time / 1000) + " s，未成功设置壁纸");
                dismiss();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    public void init() {
        setContentView(R.layout.popup_full_screen);

        fullScreenPopupRel = findViewById(R.id.rel_full_screen_popup);
        tvDesc = findViewById(R.id.tv_loading);

        fullScreenPopupRel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LogUtil.i("点击 Dialog 隐藏 全屏弹窗");
                dismiss();
            }
        });

        setOnShowListener(new OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                goPointAnim();
            }
        });

        setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                offPointAnim();
            }
        });

        //设置全屏
        getWindow().setLayout(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        //按Dialog外空白处能取消动画
        setCanceledOnTouchOutside(true);
    }

    @Override
    public void show() {
        if (!isShowing()) {
            super.show();
            LogUtil.i("显示 全屏弹窗");
        }
    }

    @Override
    public void dismiss() {
        if (isShowing()) {
            if (runnable != null && handler != null) {
                super.dismiss();
                LogUtil.i("隐藏 全屏弹窗");
                handler.removeCallbacks(runnable);
            }
        }
    }

    private void goPointAnim() {
        if (tvDesc != null) {
            LogUtil.i("正在应用壁纸 Dialog 3个点加载动效 开始");
            tvDesc.start();
        }
    }

    private void offPointAnim() {
        if (tvDesc != null) {
            LogUtil.i("正在应用壁纸 Dialog 3个点加载动效 结束");
            tvDesc.stop();
        }
    }

    public void setTvDesc(String desc) {
        tvDesc.setText(desc);
    }

    /**
     * 延时 10 秒，10秒内未成功设置壁纸，则设置壁纸失败
     */
    public void timeOutDismissDialog() {
        if (runnable != null && handler != null) {
            handler.postDelayed(runnable, time);
        }
    }
}


