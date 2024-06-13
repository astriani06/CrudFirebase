package com.example.crudfirebase;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class MainActivity extends AppCompatActivity {
    private Button btCreateDB;
    private Button btViewDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btCreateDB=(Button)findViewById(R.id.bt_createdata);
        btViewDB=(Button)findViewById(R.id.bt_viewdata);
        btCreateDB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Kelas yang akan dijalankan ketika tombol Create/insert di Klik
                startActivity(DBCreateActivity.getActIntent(MainActivity.this));
            }
        });
        btViewDB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Kelas untuk View Data
                startActivity(DBReadActivity.getActIntent(MainActivity.this));
            }
            //startActivity(DBReadActivity.getActIntent(MainActivity.this));
        });
    }
}