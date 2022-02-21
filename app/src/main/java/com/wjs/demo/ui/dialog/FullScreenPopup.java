package com.wjs.demo.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.wjs.demo.R;
import com.wjs.demo.utils.LogUtil;

public class FullScreenPopup extends Dialog {

    private Context mContext;

    private static FullScreenPopup instance = null;
    private View fullScreenPopupRel;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    public void init() {
        setContentView(R.layout.popup_full_screen);
        fullScreenPopupRel = findViewById(R.id.rel_full_screen_popup);
        fullScreenPopupRel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LogUtil.i("点击 Dialog 隐藏 全屏弹窗");
                dismiss();
            }
        });

        //设置全屏
        getWindow().setLayout(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        //按Dialog外空白处能取消动画
        setCanceledOnTouchOutside(true);
    }

    @Override
    public void show() {
        super.show();
        LogUtil.i("显示 全屏弹窗");
    }

    @Override
    public void dismiss() {
        super.dismiss();
        LogUtil.i("隐藏 全屏弹窗");
    }
}


