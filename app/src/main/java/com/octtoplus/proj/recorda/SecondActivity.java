package com.octtoplus.proj.recorda;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SecondActivity extends AppCompatActivity {

    int res = 0;
    TextView resLabel;
    Button goto3Button;

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

        goto3Button = (Button) findViewById(R.id.goto3Button);

        goto3Button.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                           public void onClick(View v) {
                                            Intent intent = new Intent(SecondActivity.this,
                                                    ThirdActivity.class);
                                            intent.putExtra("result", 500);
                                            startActivity(intent);
                                           }
                                       }


        );

    }
}
