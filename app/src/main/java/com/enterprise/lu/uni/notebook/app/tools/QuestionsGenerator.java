package com.enterprise.lu.uni.notebook.app.tools;

import com.activeandroid.query.Select;
import com.enterprise.lu.uni.notebook.app.model.NewWord;
import com.enterprise.lu.uni.notebook.app.model.Question;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Plarent on 11/4/2017.
 */

public class QuestionsGenerator {

    private static int randomWordA;
    private static int randomWordB;
    private static int randomWordC;
    private static int randomWordD;

    public static List<NewWord> getAllWordList(){
        return new Select()
                .from(NewWord.class)
                .execute();
    }

    public static List<Question> getAllQuestions(){
        List<Question> questionList = new ArrayList<>();
        for(int index = 0; index < 5; index++){
            Random random = new Random();

            int randomWord = random.nextInt(getAllWordList().size());
            NewWord word = getAllWordList().get(randomWord);

            Question question = new Question();
            question.question = "What is the translation of the word " + word.getWord() + "?";
            question.answer = word.getTranslation();

            int randi = random.nextInt(4);
            switch (randi){
                case 0:
                    question.optionA = word.getTranslation();
                    break;
                case 1:
                    question.optionB = word.getTranslation();
                    break;
                case 2:
                    question.optionC = word.getTranslation();
                    break;
                case 3:
                    question.optionD = word.getTranslation();
                    break;
            }

            if(randi == 3){
                if(question.optionA == null){
                    randomWordA = random.nextInt(getAllWordList().size());
                    while (randomWordA == randomWord){
                        randomWordA = random.nextInt(getAllWordList().size());
                    }
                    NewWord wordj = getAllWordList().get(randomWordA);
                    if(wordj == word){
                        int randomWorA = random.nextInt(getAllWordList().size());
                        NewWord wor = getAllWordList().get(randomWorA);
                        question.optionA = wor.getTranslation();
                    }else {
                        question.optionA = wordj.getTranslation();
                    }
                }
                if(question.optionB == null){
                    randomWordB = random.nextInt(getAllWordList().size());
                    while(randomWordB == randomWordA){
                        randomWordB = random.nextInt(getAllWordList().size());
                    }
                    NewWord wordj = getAllWordList().get(randomWordB);
                    if(wordj == word){
                        int randomWorB = random.nextInt(getAllWordList().size());
                        while(randomWorB == randomWordA){
                            randomWorB = random.nextInt(getAllWordList().size());
                        }
                        NewWord wor = getAllWordList().get(randomWorB);
                        question.optionB = wor.getTranslation();
                    }else {
                        question.optionB = wordj.getTranslation();
                    }
                }
                if(question.optionC == null){
                    randomWordC = random.nextInt(getAllWordList().size());
                    while (randomWordC == randomWordB){
                        randomWordC = random.nextInt(getAllWordList().size());
                    }
                    NewWord wordj = getAllWordList().get(randomWordC);
                    if(wordj == word){
                        int randomWorC = random.nextInt(getAllWordList().size());
                        while (randomWorC == randomWordB){
                            randomWorC = random.nextInt(getAllWordList().size());
                        }
                        NewWord wor = getAllWordList().get(randomWorC);
                        question.optionC = wor.getTranslation();
                    }else {
                        question.optionC = wordj.getTranslation();
                    }
                }
                /*if(question.optionD == null){
                    randomWordD = random.nextInt(getAllWordList().size());
                    while (randomWordD == randomWordC){
                        randomWordD = random.nextInt(getAllWordList().size());
                    }
                    NewWord wordj = getAllWordList().get(randomWordD);
                    if(wordj == word){
                        int randomWorD = random.nextInt(getAllWordList().size());
                        while (randomWorD == randomWordC){
                            randomWorD = random.nextInt(getAllWordList().size());
                        }
                        NewWord wor = getAllWordList().get(randomWorD);
                        question.optionD = wor.getTranslation();
                    }
                    question.optionD = wordj.getTranslation();
                }*/
            }else if(randi == 2) {
                if(question.optionA == null){
                    randomWordA = random.nextInt(getAllWordList().size());
                    while (randomWordA == randomWord){
                        randomWordA = random.nextInt(getAllWordList().size());
                    }
                    NewWord wordj = getAllWordList().get(randomWordA);
                    if(wordj == word){
                        int randomWorA = random.nextInt(getAllWordList().size());
                        NewWord wor = getAllWordList().get(randomWorA);
                        question.optionA = wor.getTranslation();
                    }else {
                        question.optionA = wordj.getTranslation();
                    }
                }
                if(question.optionB == null){
                    randomWordB = random.nextInt(getAllWordList().size());
                    while(randomWordB == randomWordA){
                        randomWordB = random.nextInt(getAllWordList().size());
                    }
                    NewWord wordj = getAllWordList().get(randomWordB);
                    if(wordj == word){
                        int randomWorB = random.nextInt(getAllWordList().size());
                        while(randomWorB == randomWordA){
                            randomWorB = random.nextInt(getAllWordList().size());
                        }
                        NewWord wor = getAllWordList().get(randomWorB);
                        question.optionB = wor.getTranslation();
                    }else {
                        question.optionB = wordj.getTranslation();
                    }
                }
                if(question.optionC == null){
                    randomWordC = random.nextInt(getAllWordList().size());
                    while (randomWordC == randomWordB){
                        randomWordC = random.nextInt(getAllWordList().size());
                    }
                    NewWord wordj = getAllWordList().get(randomWordC);
                    if(wordj == word){
                        int randomWorC = random.nextInt(getAllWordList().size());
                        while (randomWorC == randomWordB){
                            randomWorC = random.nextInt(getAllWordList().size());
                        }
                        NewWord wor = getAllWordList().get(randomWorC);
                        question.optionC = wor.getTranslation();
                    }else {
                        question.optionC = wordj.getTranslation();
                    }
                }
                if(question.optionD == null) {
                    randomWordD = random.nextInt(getAllWordList().size());
                    while (randomWordD == randomWordB) {
                        randomWordD = random.nextInt(getAllWordList().size());
                    }
                    NewWord wordj = getAllWordList().get(randomWordD);
                    if (wordj == word) {
                        int randomWorD = random.nextInt(getAllWordList().size());
                        while (randomWorD == randomWordB) {
                            randomWorD = random.nextInt(getAllWordList().size());
                        }
                        NewWord wor = getAllWordList().get(randomWorD);
                        question.optionD = wor.getTranslation();
                    }else {
                        question.optionD = wordj.getTranslation();
                    }
                }
            }else if(randi == 1){
                if(question.optionA == null){
                    randomWordA = random.nextInt(getAllWordList().size());
                    while (randomWordA == randomWord){
                        randomWordA = random.nextInt(getAllWordList().size());
                    }
                    NewWord wordj = getAllWordList().get(randomWordA);
                    if(wordj == word){
                        int randomWorA = random.nextInt(getAllWordList().size());
                        NewWord wor = getAllWordList().get(randomWorA);
                        question.optionA = wor.getTranslation();
                    }else {
                        question.optionA = wordj.getTranslation();
                    }
                }
                if(question.optionB == null){
                    randomWordB = random.nextInt(getAllWordList().size());
                    while(randomWordB == randomWordA){
                        randomWordB = random.nextInt(getAllWordList().size());
                    }
                    NewWord wordj = getAllWordList().get(randomWordB);
                    if(wordj == word){
                        int randomWorB = random.nextInt(getAllWordList().size());
                        while(randomWorB == randomWordA){
                            randomWorB = random.nextInt(getAllWordList().size());
                        }
                        NewWord wor = getAllWordList().get(randomWorB);
                        question.optionB = wor.getTranslation();
                    }else {
                        question.optionB = wordj.getTranslation();
                    }
                }
                if(question.optionC == null){
                    randomWordC = random.nextInt(getAllWordList().size());
                    while (randomWordC == randomWordA){
                        randomWordC = random.nextInt(getAllWordList().size());
                    }
                    NewWord wordj = getAllWordList().get(randomWordC);
                    if(wordj == word){
                        int randomWorC = random.nextInt(getAllWordList().size());
                        while (randomWorC == randomWordA){
                            randomWorC = random.nextInt(getAllWordList().size());
                        }
                        NewWord wor = getAllWordList().get(randomWorC);
                        question.optionC = wor.getTranslation();
                    }else {
                        question.optionC = wordj.getTranslation();
                    }
                }
                if(question.optionD == null) {
                    randomWordD = random.nextInt(getAllWordList().size());
                    while (randomWordD == randomWordC) {
                        randomWordD = random.nextInt(getAllWordList().size());
                    }
                    NewWord wordj = getAllWordList().get(randomWordD);
                    if (wordj == word) {
                        int randomWorD = random.nextInt(getAllWordList().size());
                        while (randomWorD == randomWordC) {
                            randomWorD = random.nextInt(getAllWordList().size());
                        }
                        NewWord wor = getAllWordList().get(randomWorD);
                        question.optionD = wor.getTranslation();
                    }else{
                        question.optionD = wordj.getTranslation();
                    }
                }
            }else if(randi == 0){
                if(question.optionA == null){
                    randomWordA = random.nextInt(getAllWordList().size());
                    NewWord wordj = getAllWordList().get(randomWordA);
                    if(wordj == word){
                        int randomWorA = random.nextInt(getAllWordList().size());
                        NewWord wor = getAllWordList().get(randomWorA);
                        question.optionA = wor.getTranslation();
                    }else {
                        question.optionA = wordj.getTranslation();
                    }
                }
                if(question.optionB == null){
                    randomWordB = random.nextInt(getAllWordList().size());
                    while(randomWordB == randomWord){
                        randomWordB = random.nextInt(getAllWordList().size());
                    }
                    NewWord wordj = getAllWordList().get(randomWordB);
                    if(wordj == word){
                        int randomWorB = random.nextInt(getAllWordList().size());
                        while(randomWorB == randomWordA){
                            randomWorB = random.nextInt(getAllWordList().size());
                        }
                        NewWord wor = getAllWordList().get(randomWorB);
                        question.optionB = wor.getTranslation();
                    }else {
                        question.optionB = wordj.getTranslation();
                    }
                }
                if(question.optionC == null){
                    randomWordC = random.nextInt(getAllWordList().size());
                    while (randomWordC == randomWordB){
                        randomWordC = random.nextInt(getAllWordList().size());
                    }
                    NewWord wordj = getAllWordList().get(randomWordC);
                    if(wordj == word){
                        int randomWorC = random.nextInt(getAllWordList().size());
                        while (randomWorC == randomWordB){
                            randomWorC = random.nextInt(getAllWordList().size());
                        }
                        NewWord wor = getAllWordList().get(randomWorC);
                        question.optionC = wor.getTranslation();
                    }else {
                        question.optionC = wordj.getTranslation();
                    }
                }
                if(question.optionD == null) {
                    randomWordD = random.nextInt(getAllWordList().size());
                    while (randomWordD == randomWordC) {
                        randomWordD = random.nextInt(getAllWordList().size());
                    }
                    NewWord wordj = getAllWordList().get(randomWordD);
                    if (wordj == word) {
                        int randomWorD = random.nextInt(getAllWordList().size());
                        while (randomWorD == randomWordC) {
                            randomWorD = random.nextInt(getAllWordList().size());
                        }
                        NewWord wor = getAllWordList().get(randomWorD);
                        question.optionD = wor.getTranslation();
                    }else {
                        question.optionD = wordj.getTranslation();
                    }
                }
            }
            questionList.add(question);
        }
        return questionList;
    }
}