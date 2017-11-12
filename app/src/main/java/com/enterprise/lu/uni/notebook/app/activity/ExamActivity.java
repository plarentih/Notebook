package com.enterprise.lu.uni.notebook.app.activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.enterprise.lu.uni.notebook.R;
import com.enterprise.lu.uni.notebook.app.model.Question;
import com.enterprise.lu.uni.notebook.app.tools.QuestionsGenerator;
import com.enterprise.lu.uni.notebook.app.tools.UIHelper;

import java.util.List;

public class ExamActivity extends AppCompatActivity {

    private TextView questionTextView, numberTextView, textAnswer;
    private RadioButton radioButtonA, radioButtonB, radioButtonC, radioButtonD;
    private Button nextQuestionButton, checkAnswerButton;
    private List<Question> questionList;
    private Question currentQuestion;
    private int questionId = 0;
    private int score = 0;
    private int questionNo = 1;
    RadioGroup radioGroup;
    RadioButton radioAnswer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam);
        InitializeWidgets();
        UIHelper.slideBackButton(this);
        questionList = QuestionsGenerator.getAllQuestions();
        currentQuestion = questionList.get(questionId);
        setQuestionView();

        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        radioAnswer = (RadioButton) findViewById(radioGroup.getCheckedRadioButtonId());

        textAnswer.setVisibility(View.GONE);
        nextQuestionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textAnswer.setVisibility(View.GONE);
                radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
                radioAnswer = (RadioButton) findViewById(radioGroup.getCheckedRadioButtonId());
                makeChoicesClickable();
                if(currentQuestion.getAnswer().equals(radioAnswer.getText())){
                    score++;
                }
                if(questionNo == 5){
                    nextQuestionButton.setText("Finish Exam");
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

        checkAnswerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
                radioAnswer = (RadioButton) findViewById(radioGroup.getCheckedRadioButtonId());
                if(currentQuestion.getAnswer().equals(radioAnswer.getText())){
                    textAnswer.setVisibility(View.VISIBLE);
                    textAnswer.setText("Correct!");
                    textAnswer.setTextColor(Color.GREEN);
                }else {
                    textAnswer.setVisibility(View.VISIBLE);
                    textAnswer.setText("Wrong!");
                    textAnswer.setTextColor(Color.RED);
                }
                makeChoicesNotClickable();
            }
        });
    }

    private void InitializeWidgets(){
        questionTextView = (TextView) findViewById(R.id.questionTextView);
        numberTextView = (TextView) findViewById(R.id.numberTextView);
        textAnswer = (TextView) findViewById(R.id.checkedAnswer);
        radioButtonA = (RadioButton) findViewById(R.id.radioA);
        radioButtonB = (RadioButton) findViewById(R.id.radioB);
        radioButtonC = (RadioButton) findViewById(R.id.radioC);
        radioButtonD = (RadioButton) findViewById(R.id.radioD);
        nextQuestionButton = (Button) findViewById(R.id.nextButton);
        checkAnswerButton = (Button) findViewById(R.id.checkAnswerButton);
    }

    private void setQuestionView(){
        questionTextView.setText(currentQuestion.getQuestion());
        numberTextView.setText(questionNo+".");
        radioButtonA.setText(currentQuestion.getOptionA());
        radioButtonB.setText(currentQuestion.getOptionB());
        radioButtonC.setText(currentQuestion.getOptionC());
        radioButtonD.setText(currentQuestion.getOptionD());
        questionId++;
        questionNo++;
    }

    private void makeChoicesNotClickable(){
        radioButtonA.setClickable(false);
        radioButtonB.setClickable(false);
        radioButtonC.setClickable(false);
        radioButtonD.setClickable(false);
    }

    private void makeChoicesClickable(){
        radioButtonA.setClickable(true);
        radioButtonB.setClickable(true);
        radioButtonC.setClickable(true);
        radioButtonD.setClickable(true);
    }
}
