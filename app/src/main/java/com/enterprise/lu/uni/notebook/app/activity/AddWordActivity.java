package com.enterprise.lu.uni.notebook.app.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.activeandroid.query.Select;
import com.enterprise.lu.uni.notebook.app.model.Domain;
import com.enterprise.lu.uni.notebook.app.model.NewWord;
import com.enterprise.lu.uni.notebook.R;

import java.util.ArrayList;
import java.util.List;

public class AddWordActivity extends AppCompatActivity {

    private EditText entryNewWord;
    private EditText entryTranslate;
    private Spinner domainSpinner;
    private Button button;
    private ArrayList<Domain> domains;
    private ArrayList<String> domainNames;

    public static final String NEW_WORD_LETTER = "letter";
    private String letterFromWordList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_word);
        InitializeWidgets();
        letterToStartWith();

        setTitle("Add a new word");

        domains = new ArrayList<>();
        domainNames = new ArrayList<>();

        domains = (ArrayList<Domain>) getAllDomains();
        for(int index = 0; index < domains.size(); index++){
            domainNames.add(index, domains.get(index).domainName);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, domainNames);
        domainSpinner.setAdapter(adapter);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String firstLetter;
                String wordi = entryNewWord.getText().toString();
                String translation = entryTranslate.getText().toString();
                String domainName = domainSpinner.getSelectedItem().toString();
                Domain domain = getDomainFromName(domainName);
                firstLetter = String.valueOf(wordi.charAt(0)).toUpperCase();
                saveWord(wordi,translation, domain);

                Intent intent = new Intent(getBaseContext(), WordListActivity.class);
                intent.putExtra(NEW_WORD_LETTER, firstLetter);
                startActivity(intent);
            }
        });
    }

    private void saveWord(String wordi, String translation, Domain domain){
        NewWord newWord = new NewWord();
        newWord.word = wordi;
        newWord.translation = translation;
        newWord.domain = domain;
        newWord.save();
    }

    public static Domain getDomainFromName(String domainName){
        return new Select()
                .from(Domain.class)
                .where("Name = ?", domainName)
                .executeSingle();
    }

    public static List<Domain> getAllDomains(){
        return new Select()
                .all()
                .from(Domain.class)
                .execute();
    }

    private void InitializeWidgets(){
        entryNewWord = (EditText) findViewById(R.id.editTextNewWord);
        entryTranslate = (EditText) findViewById(R.id.editTextTranslate);
        domainSpinner = (Spinner) findViewById(R.id.spinner);
        button = (Button) findViewById(R.id.button);
    }

    private void letterToStartWith(){
        Intent intent = getIntent();
        letterFromWordList = intent.getStringExtra("LETTER");
    }
}
