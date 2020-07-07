package com.best.cy.activitystore;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.main1).setOnClickListener(OnMyClick);
        findViewById(R.id.main2).setOnClickListener(OnMyClick);
    }

    //---------------------------------
    // OnClick Listener
    //---------------------------------
    Button.OnClickListener OnMyClick = new Button.OnClickListener() {
        @Override    // here main means icon id in first screen ....startgame.xml ... not xml file
        public void onClick(View v) {
            switch (v.getId()) {

                case R.id.main1:
                    startActivity(new Intent(MainActivity.this, ActivityStore.class));
                    break;
                case R.id.main2:   //나가기
                    finish();
                    break;
            }
        }
    };

}