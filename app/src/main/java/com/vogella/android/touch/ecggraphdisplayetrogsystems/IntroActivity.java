package com.vogella.android.touch.ecggraphdisplayetrogsystems;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class IntroActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        Button data1 = findViewById(R.id.url_1);
        Button data2 = findViewById(R.id.url_2);
        Button data3 = findViewById(R.id.url_3);

        data1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(IntroActivity.this, MainActivity.class);
                intent.putExtra("url", "https://gw.etrogsystems.com/etrogsystems/data/dev/ECG20192019101_2021-04-06_12-20-57.txt");
                startActivity(intent);
            }
        });

        data2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(IntroActivity.this, MainActivity.class);
                intent.putExtra("url", "https://gw.etrogsystems.com/etrogsystems/data/dev/ECG20192019102_2020-02-19_11-49-54.txt");
                startActivity(intent);
            }
        });

        data3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(IntroActivity.this, MainActivity.class);
                intent.putExtra("url", "https://gw.etrogsystems.com/etrogsystems/data/dev/ECG20192019102_2020-06-24_09-56-50.txt");
                startActivity(intent);
            }
        });
    }
}
