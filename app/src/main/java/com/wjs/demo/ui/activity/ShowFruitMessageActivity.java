package com.wjs.demo.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.wjs.demo.R;
import com.wjs.demo.base.BaseActivity;
import com.wjs.demo.entity.Fruit;

public class ShowFruitMessageActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_fruit_message);

        ImageView fruitImage = findViewById(R.id.fruit_image);
        TextView fruitName1 = findViewById(R.id.fruit_name1);
        TextView fruitName2 = findViewById(R.id.fruit_name2);
        Button fruitBtn = findViewById(R.id.fruit_btn);

        Intent intent = getIntent();
        if (intent != null) {
            Fruit fruit = (Fruit) intent.getSerializableExtra("fruit");
            fruitImage.setImageResource(fruit.getFruitImageId());
            fruitName1.setText(fruit.getFruitName1());
            fruitName2.setText(fruit.getFruitName2());
            fruitBtn.setText(fruit.getFruitBtn());
        }

        fruitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onHomeAsUpClick();
            }
        });
    }
}