package com.wjs.demo.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.wjs.demo.R;
import com.wjs.demo.adapter.FruitAdapter;
import com.wjs.demo.base.BaseFragment;
import com.wjs.demo.entity.Fruit;
import com.wjs.demo.interfaces.ListContract;
import com.wjs.demo.presenter.ListPresenter;
import com.wjs.demo.ui.activity.ShowFruitMessageActivity;
import com.wjs.demo.utils.LogUtil;

import java.util.ArrayList;
import java.util.List;

public class ListFragment extends BaseFragment implements ListContract.View {

    private static ListContract.Presenter presenter;

    private static ListFragment instance = null;

    private int lastSelected = -1;

    private RecyclerView recyclerView;
    private FruitAdapter adapter;
    private List<Fruit> fruitList = new ArrayList<>();

    private ListFragment() {
    }

    public static ListFragment getInstance() {
        if (instance == null) {
            instance = new ListFragment();
            presenter = new ListPresenter(ListFragment.getInstance());
        }
        return instance;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtil.i("onCreate");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        LogUtil.i("onCreateView");
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        initView(view);
        initData();
        initListener();
        return view;
    }

    public void initView(View view) {
        recyclerView = view.findViewById(R.id.recycler_view);
    }

    public void initData() {
        for (int i = 0; i < 2; i++) {
            Fruit apple = new Fruit(R.mipmap.apple, "苹果1", "苹果2", "苹果");
            Fruit orange = new Fruit(R.mipmap.orange, "橙子1", "橙子2", "橙子");
            Fruit strawberry = new Fruit(R.mipmap.strawberry, "草莓1", "草莓2", "草莓");
            Fruit litchi = new Fruit(R.mipmap.litchi, "荔枝1", "荔枝2", "荔枝");
            Fruit grape = new Fruit(R.mipmap.grape, "葡萄1", "葡萄2", "葡萄");
            Fruit mulberry = new Fruit(R.mipmap.mulberry, "桑葚1", "桑葚2", "桑葚");
            Fruit peach = new Fruit(R.mipmap.peach, "桃子1", "桃子2", "桃子");
            Fruit cherry = new Fruit(R.mipmap.cherry, "樱桃1", "樱桃2", "樱桃");
            Fruit carambola = new Fruit(R.mipmap.carambola, "杨桃1", "杨桃2", "杨桃");
            Fruit coconut = new Fruit(R.mipmap.coconut, "椰子1", "椰子2", "椰子");

            fruitList.add(apple);
            fruitList.add(orange);
            fruitList.add(strawberry);
            fruitList.add(litchi);
            fruitList.add(grape);
            fruitList.add(mulberry);
            fruitList.add(peach);
            fruitList.add(cherry);
            fruitList.add(carambola);
            fruitList.add(coconut);
        }
        LogUtil.i("数据量 size = " + fruitList.size());

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        adapter = new FruitAdapter(fruitList);
        recyclerView.setAdapter(adapter);
    }

    public void initListener() {
        adapter.setCallBack(new FruitAdapter.CallBack() {
            @Override
            public void fruitData(Fruit fruit) {
                Intent intent = new Intent(getActivity(), ShowFruitMessageActivity.class);
                intent.putExtra("fruit", fruit);
                getActivity().startActivity(intent);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        if (presenter != null) {
            presenter.subscribe();
        }
        LogUtil.i("onResume");
    }

    @Override
    public void restoreListMode(int position) {
        lastSelected = -2;
        setSelectedBtn(position);
    }

    @Override
    public void setPresenter(ListContract.Presenter presenter) {
        this.presenter = presenter;
    }

    private void setSelectedBtn(int position) {
        if (presenter == null) {
            LogUtil.e("presenter == null");
            return;
        }

        if (lastSelected == position) {
            LogUtil.w("该位置已选中: " + position);
            return;
        }

        // 取消上次选中的模式

        // 选中当前点击的模式
        presenter.setListMode(position);

        LogUtil.i("选中位置 position = " + position);

        lastSelected = position;
    }
}