package com.enterprise.lu.uni.notebook.app.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.enterprise.lu.uni.notebook.R;
import com.enterprise.lu.uni.notebook.app.model.Question;
import com.enterprise.lu.uni.notebook.app.tools.QuestionsGenerator;

import java.util.List;

public class ExamActivity extends AppCompatActivity {

    private TextView questionTextView;
    private RadioButton radioButtonA, radioButtonB, radioButtonC, radioButtonD;
    private Button nextQuestionButton;
    private List<Question> questionList;
    private Question currentQuestion;
    private int questionId = 0;
    private int score = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam);
        InitializeWidgets();
        questionList = QuestionsGenerator.getAllQuestions();
        currentQuestion = questionList.get(questionId);
        setQuestionView();
        nextQuestionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
                RadioButton radioAnswer = (RadioButton) findViewById(radioGroup.getCheckedRadioButtonId());
                if(currentQuestion.getAnswer().equals(radioAnswer.getText())){
                    score++;
                }
                if(questionId < 5){
                    currentQuestion = questionList.get(questionId);
                    setQuestionView();
                }else {
                    Intent intent = new Intent(getBaseContext(), ResultActivity.class);
                    intent.putExtra("SCORE", score);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

    private void InitializeWidgets(){
        questionTextView = (TextView) findViewById(R.id.questionTextView);
        radioButtonA = (RadioButton) findViewById(R.id.radioA);
        radioButtonB = (RadioButton) findViewById(R.id.radioB);
        radioButtonC = (RadioButton) findViewById(R.id.radioC);
        radioButtonD = (RadioButton) findViewById(R.id.radioD);
        nextQuestionButton = (Button) findViewById(R.id.nextButton);
    }

    private void setQuestionView(){
        questionTextView.setText(currentQuestion.getQuestion());
        radioButtonA.setText(currentQuestion.getOptionA());
        radioButtonB.setText(currentQuestion.getOptionB());
        radioButtonC.setText(currentQuestion.getOptionC());
        radioButtonD.setText(currentQuestion.getOptionD());
        questionId++;
    }
}
