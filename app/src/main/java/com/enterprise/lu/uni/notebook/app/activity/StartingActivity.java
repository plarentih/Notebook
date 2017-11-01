package com.enterprise.lu.uni.notebook.app.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.activeandroid.query.Select;
import com.enterprise.lu.uni.notebook.R;
import com.enterprise.lu.uni.notebook.app.model.Domain;

import java.util.ArrayList;
import java.util.List;

public class StartingActivity extends AppCompatActivity {
    private static final String SAMPLE_DB_NAME = "Notebook.db";
    private static final String SAMPLE_TABLE_NAME = "NewWord";

    public static final int REQUEST_CODE_STARTING = 40;

    Button newWordBtn;
    Button notebookBtn;
    Button domainsBtn;
    Button examModeBtn;
    Button exportBtn;
    private ArrayList<Domain> dms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_starting);
        InitializeWidgets();

        dms = new ArrayList<Domain>();
        dms = (ArrayList<Domain>) getAllDomains();
        if(dms.size() == 0){
            InitialDomains();
        }

        newWordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), AddWordActivity.class);
                startActivityForResult(intent, REQUEST_CODE_STARTING);
            }
        });
        notebookBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), NotebookActivity.class);
                startActivity(intent);
            }
        });
        exportBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), ExportImportActivity.class);
                startActivity(intent);
            }
        });
    }

    public static List<Domain> getAllDomains(){
        return new Select()
                .all()
                .from(Domain.class)
                .execute();
    }

    private void InitialDomains(){
        Domain economy = new Domain();
        economy.domainName = "Economy";
        economy.save();

        Domain politics = new Domain();
        politics.domainName = "Politics";
        politics.save();

        Domain sports = new Domain();
        sports.domainName = "Sports";
        sports.save();

        Domain IT = new Domain();
        IT.domainName = "IT";
        IT.save();

        Domain cooking = new Domain();
        cooking.domainName = "Cooking";
        cooking.save();

        Domain travel = new Domain();
        travel.domainName = "Travel";
        travel.save();
    }

    private void InitializeWidgets(){
        newWordBtn = (Button) findViewById(R.id.buttonNewWord);
        notebookBtn = (Button) findViewById(R.id.buttonNotebook);
        domainsBtn = (Button) findViewById(R.id.buttonDomains);
        examModeBtn = (Button) findViewById(R.id.buttonExam);
        exportBtn= (Button) findViewById(R.id.buttonExport);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_CODE_STARTING) {
            }
        }
    }
}
