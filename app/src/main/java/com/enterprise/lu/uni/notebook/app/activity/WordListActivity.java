package com.enterprise.lu.uni.notebook.app.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import com.activeandroid.query.Select;
import com.enterprise.lu.uni.notebook.app.adapter.NewWordAdapter;
import com.enterprise.lu.uni.notebook.app.model.NewWord;
import com.enterprise.lu.uni.notebook.R;

import java.util.ArrayList;
import java.util.List;

public class WordListActivity extends AppCompatActivity {

    public static final int REQUEST_CODE = 40;

    private TextView title;
    private String notebookLetter;
    private String letterFromDirect;
    private ListView wordListView;
    private NewWordAdapter wordAdapter;
    private List<NewWord> wordList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_list);
        initializeWidgets();
        setHeaderActivity();

        if(notebookLetter == null){
            wordList = getWordsFromDirect();
        }
        else if(letterFromDirect == null){
            wordList = getWordsFromNotebook();
        }

        wordAdapter = new NewWordAdapter(getBaseContext(), wordList);
        wordListView.setAdapter(wordAdapter);
    }

    private void initializeWidgets(){
        title = (TextView) findViewById(R.id.textViewTitle);
        wordListView = (ListView) findViewById(R.id.wordListView);
    }

    private void setHeaderActivity(){
        Intent intent = getIntent();
        notebookLetter = intent.getStringExtra("letterTitle");
        letterFromDirect = intent.getStringExtra(AddWordActivity.NEW_WORD_LETTER);
        if(notebookLetter == null){
            title.setText(letterFromDirect);
        }else {
            title.setText(notebookLetter);
        }
    }

    private List<NewWord> getWordsFromDirect(){
        List<NewWord> newList = new ArrayList<>();
        wordList = getAll();
        for(int index = 0; index < wordList.size(); index++){
            if(wordList.get(index).getWord().toUpperCase().startsWith(letterFromDirect)){
                newList.add(wordList.get(index));
            }
        }
        return newList;
    }

    private List<NewWord> getWordsFromNotebook(){
        List<NewWord> newList = new ArrayList<>();
        wordList = getAll();
        for(int index = 0; index < wordList.size(); index++){
            if(wordList.get(index).getWord().toUpperCase().startsWith(notebookLetter)){
                newList.add(wordList.get(index));
            }
        }
        return newList;
    }

    public static List<NewWord> getAll(){
        return new Select()
                .from(NewWord.class)
                .execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_add) {
            Intent intent = new Intent(getBaseContext(), AddWordActivity.class);
            intent.putExtra("LETTER", notebookLetter);
            startActivityForResult(intent, REQUEST_CODE);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUEST_CODE){
            if(resultCode == RESULT_OK){
                wordList = getWordsFromNotebook();
                wordAdapter = new NewWordAdapter(getBaseContext(), wordList);
                wordListView.setAdapter(wordAdapter);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}