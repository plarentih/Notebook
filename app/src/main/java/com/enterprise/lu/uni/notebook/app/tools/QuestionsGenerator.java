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

            if(question.optionA == null){
                int randomWordj = random.nextInt(getAllWordList().size());
                NewWord wordj = getAllWordList().get(randomWordj);
                if(wordj == word){
                    int randomWor = random.nextInt(getAllWordList().size());
                    NewWord wor = getAllWordList().get(randomWor);
                    question.optionA = wor.getTranslation();
                }
                question.optionA = wordj.getTranslation();
            }
            if(question.optionB == null){
                int randomWordj = random.nextInt(getAllWordList().size());
                NewWord wordj = getAllWordList().get(randomWordj);
                if(wordj == word){
                    int randomWor = random.nextInt(getAllWordList().size());
                    NewWord wor = getAllWordList().get(randomWor);
                    question.optionB = wor.getTranslation();
                }
                question.optionB = wordj.getTranslation();
            }
            if(question.optionC == null){
                int randomWordj = random.nextInt(getAllWordList().size());
                NewWord wordj = getAllWordList().get(randomWordj);
                if(wordj == word){
                    int randomWor = random.nextInt(getAllWordList().size());
                    NewWord wor = getAllWordList().get(randomWor);
                    question.optionC = wor.getTranslation();
                }
                question.optionC = wordj.getTranslation();
            }
            if(question.optionD == null){
                int randomWordj = random.nextInt(getAllWordList().size());
                NewWord wordj = getAllWordList().get(randomWordj);
                if(wordj == word){
                    int randomWor = random.nextInt(getAllWordList().size());
                    NewWord wor = getAllWordList().get(randomWor);
                    question.optionD = wor.getTranslation();
                }
                question.optionD = wordj.getTranslation();
            }
            questionList.add(question);
        }
        return questionList;
    }
}