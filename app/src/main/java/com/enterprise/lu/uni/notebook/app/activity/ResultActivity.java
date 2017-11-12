package com.enterprise.lu.uni.notebook.app.activity;

import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RatingBar;
import android.widget.TextView;

import com.enterprise.lu.uni.notebook.R;
import com.enterprise.lu.uni.notebook.app.tools.UIHelper;

import org.w3c.dom.Text;

public class ResultActivity extends AppCompatActivity {

    private TextView textViewResult;
    private RatingBar ratingBar;
    private int score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        initializeWidgets();
        UIHelper.slideBackButton(this);
        ratingBar.setNumStars(5);
        ratingBar.setStepSize(0.5f);
        score = getIntent().getIntExtra("SCORE", -1);
        ratingBar.setRating(score);
        ratingBar.setIsIndicator(true);

        switch(score){
            case 0:
                textViewResult.setText("At least read your notes before taking a test.");
                break;
            case 1:
                textViewResult.setText("Try to study before taking a test.");
                break;
            case 2:
                textViewResult.setText("You need to try harder.");
                break;
            case 3:
                textViewResult.setText("You can do better next time.");
                break;
            case 4:
                textViewResult.setText("This is a good result. Keep it up.");
                break;
            case 5:
                textViewResult.setText("You are doing great learning new words.");
                break;
        }
    }

    private void initializeWidgets(){
        textViewResult = (TextView) findViewById(R.id.textResult);
        ratingBar = (RatingBar) findViewById(R.id.ratingBar);
    }
}
