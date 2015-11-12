package com.daemon.drecyclerviewadapter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

    private Button bt1;
    private Button bt2;
    private Button bt3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.bt3 = (Button) findViewById(R.id.bt3);
        this.bt2 = (Button) findViewById(R.id.bt2);
        this.bt1 = (Button) findViewById(R.id.bt1);


        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(MainActivity.this,DemoActivity.class);
                intent.putExtra("type",DemoActivity.TYPE1);
                startActivity(intent);

            }
        });

        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,DemoActivity.class);
                intent.putExtra("type",DemoActivity.TYPE2);
                startActivity(intent);
            }
        });

        bt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,DemoActivity.class);
                intent.putExtra("type",DemoActivity.TYPE3);
                startActivity(intent);
            }
        });
    }
}

