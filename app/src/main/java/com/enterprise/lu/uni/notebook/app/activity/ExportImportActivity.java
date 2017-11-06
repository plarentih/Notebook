package com.enterprise.lu.uni.notebook.app.activity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.activeandroid.query.Select;
import com.enterprise.lu.uni.notebook.R;
import com.enterprise.lu.uni.notebook.app.model.Domain;
import com.enterprise.lu.uni.notebook.app.model.NewWord;
import com.enterprise.lu.uni.notebook.app.tools.CSVWriter;
import com.enterprise.lu.uni.notebook.app.tools.DBHelper;
import com.enterprise.lu.uni.notebook.app.tools.PermissionHelper;
import com.opencsv.CSVReader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ExportImportActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_export_import);

        Button exportButton = (Button) findViewById(R.id.buttonExport);
        Button importButton = (Button) findViewById(R.id.buttonImport);


        exportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String DB_PATH = "/data/data/com.enterprise.lu.uni.notebook/databases/";

                String DB_NAME = "Notebook.db";
                SQLiteDatabase myDataBase;
                String myPath = DB_PATH + DB_NAME;
                if(PermissionHelper.checkForPermissions(ExportImportActivity.this)){
                    File exportDir = new File(Environment.getExternalStorageDirectory(), "");
                    if (!exportDir.exists())
                    {
                        exportDir.mkdirs();
                    }

                    File file = new File(exportDir, "csvname.csv");
                    try
                    {
                        DBHelper mHelper= new DBHelper(getApplicationContext());
                        file.createNewFile();
                        myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
                        mHelper.openDataBase();
                        CSVWriter csvWrite = new CSVWriter(new FileWriter(file));

                        Cursor curCSV = myDataBase.rawQuery("SELECT Word, translatedWord, Domainthi FROM NewWord",null);
                        csvWrite.writeNext(curCSV.getColumnNames());
                        while(curCSV.moveToNext())
                        {
                            //Which column you want to export
                            String arrStr[] ={curCSV.getString(0), curCSV.getString(1),curCSV.getString(2)};
                            csvWrite.writeNext(arrStr);
                        }
                        csvWrite.close();
                        curCSV.close();
                    }
                    catch(Exception sqlEx)
                    {
                        Log.e("MainActivity", sqlEx.getMessage(), sqlEx);
                    }
                    Toast.makeText(getBaseContext(),"Your list has been exported",Toast.LENGTH_SHORT).show();
                }else {
                    PermissionHelper.askForPermissions(ExportImportActivity.this);
                }

            }

        });

        importButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (PermissionHelper.checkForPermissions(ExportImportActivity.this)) {
                    Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                    intent.addCategory(Intent.CATEGORY_OPENABLE);
                    intent.setType("text/*");
                    startActivityForResult(Intent.createChooser(intent, "Select file"), 40);
                }
               else{
                    PermissionHelper.askForPermissions(ExportImportActivity.this);
                }

            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == 40){
            if(resultCode == RESULT_OK){
                readFile(data);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void readFile(Intent data){
        Uri uri = data.getData();
        BufferedReader reader = null;
        String line;
        try{
            InputStream inputStream = getContentResolver().openInputStream(uri);
            reader = new BufferedReader(new InputStreamReader(inputStream));
            while ((line = reader.readLine()) != null){
                String[] columns = line.split(",");
                NewWord newWord = new NewWord();
                String word = columns[0];
                String translation = columns[1];
                String domain = columns[2];

                word.replaceAll("^\\s+|\\s+$", "");
                translation.replaceAll("^\\s+|\\s+$", "");
                domain.replaceAll("^\\s+|\\s+$", "");
                domain.replace("\t", "");
                domain.trim();

                newWord.word = word;
                newWord.translation = translation;

                if(domain == null){

                }else {
                    if(getDomainFromName(domain) == null){
                        Domain newDomain = new Domain();
                        newDomain.domainName = domain;
                        newDomain.save();
                        newWord.domain = newDomain;
                    }else {
                        newWord.domain = getDomainFromName(domain);
                    }
                }
                newWord.save();
            }
            reader.close();
        }catch (IOException ioe){
            ioe.printStackTrace();
        }
    }

    private void readCSVFile(File file) throws FileNotFoundException {
        CSVReader csvReader = new CSVReader(new FileReader(file));
        String[] nextLine;
        try {
            while ((nextLine = csvReader.readNext()) != null) {
                NewWord newWord = new NewWord();
                newWord.word = nextLine[0];
                newWord.translation = nextLine[1];
                String domain = nextLine[2];
                if(getDomainFromName(domain) == null){
                    Domain newDomain = new Domain();
                    newDomain.domainName = domain;
                    newDomain.save();
                }
                newWord.save();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Domain getDomainFromName(String domainName) {
        return new Select()
                .from(Domain.class)
                .where("Name = ?", domainName)
                .executeSingle();
    }

}
