package com.enterprise.lu.uni.notebook.app.activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.activeandroid.query.Select;
import com.enterprise.lu.uni.notebook.app.adapter.NewWordAdapter;
import com.enterprise.lu.uni.notebook.app.model.NewWord;
import com.enterprise.lu.uni.notebook.R;
import com.enterprise.lu.uni.notebook.app.tools.UIHelper;

import java.util.ArrayList;
import java.util.List;

public class WordListActivity extends AppCompatActivity {

    public static final int REQUEST_CODE_WORD_LIST = 30;

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
        UIHelper.slideBackButton(this);
        setHeaderActivity();

        if(notebookLetter == null){
            wordList = getWordsFromDirect();
        }
        else if(letterFromDirect == null){
            wordList = getWordsFromNotebook();
        }

        wordAdapter = new NewWordAdapter(getBaseContext(), wordList);
        wordListView.setAdapter(wordAdapter);

        wordListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final int internalPosition = position;

                final AlertDialog.Builder builder = new AlertDialog.Builder(WordListActivity.this);
                builder.setTitle("Edit/Delete");
                builder.setMessage("Do you want to edit or delete this word?");
                builder.setPositiveButton("Edit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        NewWord selectedWord = wordAdapter.getItem(internalPosition);
                        long spinnerId = selectedWord.getDomain().getId();
                        Intent intent = new Intent(getBaseContext(), AddWordActivity.class);
                        intent.putExtra("EDIT_WORD", selectedWord);
                        intent.putExtra("EDIT_WORD_ID", selectedWord.getId());
                        intent.putExtra("SPINNER_ID", spinnerId);
                        startActivityForResult(intent, REQUEST_CODE_WORD_LIST);
                    }
                });
                builder.setNegativeButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        NewWord selectedWord = wordAdapter.getItem(internalPosition);
                        selectedWord.delete();
                        wordList = getWordsFromNotebook();
                        wordAdapter = new NewWordAdapter(getBaseContext(), wordList);
                        wordListView.setAdapter(wordAdapter);
                        Toast.makeText(getBaseContext(), "Word is deleted!", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.setCancelable(true);
                builder.show();
            }
        });
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
            startActivityForResult(intent, REQUEST_CODE_WORD_LIST);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK){
            if(requestCode == REQUEST_CODE_WORD_LIST){
                wordList.clear();
                wordList = getWordsFromNotebook();
                wordAdapter = new NewWordAdapter(getBaseContext(), wordList);
                wordListView.setAdapter(wordAdapter);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}