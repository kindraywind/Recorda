package com.octtoplus.proj.recorda;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class SecondActivity extends AppCompatActivity {

    int res = 0;
    TextView resLabel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        Intent intent = getIntent();
        res = intent.getIntExtra("result", 0);

        initInstances();
    }

    private void initInstances() {
        resLabel = (TextView) findViewById(R.id.resLabel);
        resLabel.setText("Res: "+res);
    }
}
