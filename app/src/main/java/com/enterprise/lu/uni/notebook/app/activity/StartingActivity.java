package com.enterprise.lu.uni.notebook.app.activity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.channels.FileChannel;

import android.database.Cursor;
import android.os.Environment;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.database.sqlite.SQLiteDatabase;
import com.activeandroid.query.Select;
import com.activeandroid.util.Log;
import com.enterprise.lu.uni.notebook.app.model.Domain;
import com.enterprise.lu.uni.notebook.R;
import android.widget.Toast;
import android.view.View;
import android.view.View.OnClickListener;
import java.util.ArrayList;
import java.util.List;
import com.opencsv.*;
public class StartingActivity extends AppCompatActivity {
    private static final String SAMPLE_DB_NAME = "Notebook.db";
    private static final String SAMPLE_TABLE_NAME = "NewWord";
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
                startActivity(intent);
            }
        });
        notebookBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), NotebookActivity.class);
                startActivity(intent);
            }
        });
        exportBtn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View view) {
                exportDB();
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

   /*private void exportDB(){
        File sd = Environment.getExternalStorageDirectory();
        File data = Environment.getDataDirectory();
        FileChannel source=null;
        FileChannel destination=null;
        String currentDBPath = "/data/"+ "com.enterprise.lu.uni.notebook" +"/databases/"+SAMPLE_DB_NAME;
        String backupDBPath = SAMPLE_DB_NAME;
        File currentDB = new File(data, currentDBPath);
        File backupDB = new File(sd, backupDBPath);
        try {
            source = new FileInputStream(currentDB).getChannel();
            destination = new FileOutputStream(backupDB).getChannel();
            destination.transferFrom(source, 0, source.size());
            source.close();
            destination.close();
            Toast.makeText(this, "DB Exported!", Toast.LENGTH_LONG).show();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }*/
    private void exportDB() {


       /* File dbFile=getDatabasePath("Notebook.db");
=======

        File dbFile=getDatabasePath("Notebook.db");
>>>>>>> 5f961e543ce328814f04bbe7ef1f1842ee75a3c1
        DBHelper dbhelper = new DBHelper(getApplicationContext());
        File exportDir = new File(Environment.getExternalStorageDirectory(), "");
        if (!exportDir.exists())
        {
            exportDir.mkdirs();
        }

        File file = new File(exportDir, "csvname.csv");
        try
        {
            file.createNewFile();
            CSVWriter csvWrite = new CSVWriter(new FileWriter(file));
            SQLiteDatabase db = dbhelper.getReadableDatabase();
            Cursor curCSV = db.rawQuery("SELECT * FROM NewWord",null);
            csvWrite.writeNext(curCSV.getColumnNames());
            while(curCSV.moveToNext())
            {
                //Which column you want to exprort
                String arrStr[] ={curCSV.getString(0),curCSV.getString(1), curCSV.getString(2)};
                csvWrite.writeNext(arrStr);
            }
            csvWrite.close();
            curCSV.close();
        }
        catch(Exception sqlEx)
        {
            Log.e("MainActivity", sqlEx.getMessage(), sqlEx);
        }*/

}}
