package com.example.simpleui.simpleui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class DrinkMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drink_menu);
    }

    public void add(View view)
    {
        Button button = (Button)view;   //取得作用中的按鈕
        int num = Integer.parseInt(button.getText().toString());
        num++;
        button.setText(String.valueOf(num));    //寫回button資料
    }

    public void cancel(View view)
    {
        finish();
    }
}
