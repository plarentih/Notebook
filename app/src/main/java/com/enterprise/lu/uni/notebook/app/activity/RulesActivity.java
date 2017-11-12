package com.enterprise.lu.uni.notebook.app.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.enterprise.lu.uni.notebook.R;
import com.enterprise.lu.uni.notebook.app.tools.UIHelper;

public class RulesActivity extends AppCompatActivity {

    private Button startButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rules);
        UIHelper.slideBackButton(this);
        startButton = (Button)findViewById(R.id.buttonStartExam);

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), ExamActivity.class);
                startActivity(intent);
            }
        });
    }
}
