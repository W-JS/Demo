package com.wjs.demo.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.wjs.demo.R;
import com.wjs.demo.entity.Fruit;
import com.wjs.demo.utils.LogUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.wjs.demo.utils.ConstantUtil.currentSelectedPosition;

public class FruitAdapter extends RecyclerView.Adapter<FruitAdapter.ViewHolder> {

    private static final String TAG = "FruitAdapter";

    private Map<Integer, ViewHolder> mViewHolderList = new HashMap<>();
    ;
    private List<Fruit> mFruitList;

    private CallBack callBack;

    public interface CallBack {
        void fruitData(Fruit fruit);
    }

    public void setCallBack(CallBack callBack) {
        this.callBack = callBack;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView fruitImage;
        TextView fruitName1;
        TextView fruitName2;
        Button fruitBtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            fruitImage = itemView.findViewById(R.id.fruit_image);
            fruitName1 = itemView.findViewById(R.id.fruit_name1);
            fruitName2 = itemView.findViewById(R.id.fruit_name2);
            fruitBtn = itemView.findViewById(R.id.fruit_btn);
        }
    }

    public FruitAdapter(List<Fruit> mFruitList) {
        this.mFruitList = mFruitList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fruit_item, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        holder.fruitBtn.setSelected(false);
        holder.fruitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.fruit_btn:
                        if (currentSelectedPosition != -1 && currentSelectedPosition < mViewHolderList.size()) {
                            ViewHolder holder = mViewHolderList.get(currentSelectedPosition);
                            holder.fruitBtn.setSelected(false);
                            LogUtil.i("取消选中 第 " + currentSelectedPosition + " 个数据");
                        }
                        LogUtil.i("mViewHolderList.size() " + mViewHolderList.size());

                        int position = holder.getAdapterPosition();
                        holder.fruitBtn.setSelected(true);
                        currentSelectedPosition = position;
                        LogUtil.i("选中 第 " + position + " 个数据");
                        Fruit fruit = mFruitList.get(position);
                        callBack.fruitData(fruit);
                        break;
                    default:
                }
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        LogUtil.i("加载 第 " + position + " 个数据");
        Fruit fruit = mFruitList.get(position);
        holder.fruitImage.setImageResource(fruit.getFruitImageId());
        holder.fruitName1.setText(fruit.getFruitName1());
        holder.fruitName2.setText(fruit.getFruitName2());
        holder.fruitBtn.setText(fruit.getFruitBtn());

        if (position == currentSelectedPosition) {
            holder.fruitBtn.setSelected(true);
            LogUtil.i("选中 第 " + position + " 个数据");
        } else {
            holder.fruitBtn.setSelected(false);
            LogUtil.i("取消选中 第 " + position + " 个数据");
        }

        mViewHolderList.put(position, holder);
    }

    @Override
    public int getItemCount() {
        return mFruitList.size();
    }
}
