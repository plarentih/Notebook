package com.enterprise.lu.uni.notebook.app.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.renderscript.Sampler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.activeandroid.query.Select;
import com.enterprise.lu.uni.notebook.app.model.Domain;
import com.enterprise.lu.uni.notebook.app.model.NewWord;
import com.enterprise.lu.uni.notebook.R;
import com.enterprise.lu.uni.notebook.app.tools.UIHelper;
import com.r0adkll.slidr.Slidr;
import com.r0adkll.slidr.model.SlidrConfig;
import com.r0adkll.slidr.model.SlidrInterface;
import com.r0adkll.slidr.model.SlidrListener;
import com.r0adkll.slidr.model.SlidrPosition;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class AddWordActivity extends AppCompatActivity {

    private EditText entryNewWord;
    private EditText entryTranslate;
    private Spinner domainSpinner;
    private Button button;
    private ArrayList<Domain> domains;
    private ArrayList<String> domainNames;

    public static final String NEW_WORD_LETTER = "letter";

    private NewWord wordToEdit;
    private long wordToEditId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_word);
        InitializeWidgets();

        UIHelper.slideBackButton(this);

        setTitle("Add a new word");

        domains = new ArrayList<>();
        domainNames = new ArrayList<>();

        domains = (ArrayList<Domain>) getAllDomains();
        for(int index = 0; index < domains.size(); index++){
            domainNames.add(index, domains.get(index).domainName);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, R.layout.spinner_item, domainNames);
        domainSpinner.setAdapter(adapter);

        wordToEdit = (NewWord) getIntent().getSerializableExtra("EDIT_WORD");
        wordToEditId = getIntent().getLongExtra("EDIT_WORD_ID", -1);
        int spinnerIndex = (int) getIntent().getLongExtra("SPINNER_ID", -1);
        if(wordToEdit != null){
            entryNewWord.setText(wordToEdit.getWord());
            entryTranslate.setText(wordToEdit.getTranslation());
            domainSpinner.setSelection(spinnerIndex - 1);
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Editable editWord = entryNewWord.getText();
                String field = editWord.toString().trim();
                if(TextUtils.isEmpty(field)){
                    Toast.makeText(getBaseContext(), "You can not leave new word field empty!", Toast.LENGTH_SHORT).show();
                }else {
                    if(wordToEdit != null){
                        NewWord newWord = NewWord.load(NewWord.class, wordToEditId);
                        newWord.word = editWord.toString();
                        newWord.translation = entryTranslate.getText().toString();
                        String domainName = domainSpinner.getSelectedItem().toString();
                        newWord.domain = getDomainFromName(domainName);
                        newWord.save();
                        setResult(RESULT_OK);
                        finish();
                    }
                    if(wordToEdit == null){
                        String word = entryNewWord.getText().toString();
                        String translation = entryTranslate.getText().toString();
                        String domainName = domainSpinner.getSelectedItem().toString();
                        Domain domain = getDomainFromName(domainName);
                        saveWord(word,translation, domain);
                        setResult(RESULT_OK);
                        finish();
                    }
                }

            }
        });
    }

    private void saveWord(String word, String translation, Domain domain){
        NewWord newWord = new NewWord();
        newWord.word = word;
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
}
