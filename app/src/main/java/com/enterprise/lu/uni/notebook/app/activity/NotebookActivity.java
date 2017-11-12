package com.enterprise.lu.uni.notebook.app.activity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import com.activeandroid.query.Select;
import com.enterprise.lu.uni.notebook.R;
import com.enterprise.lu.uni.notebook.app.adapter.GridAdapter;
import com.enterprise.lu.uni.notebook.app.model.Domain;
import com.enterprise.lu.uni.notebook.app.model.NewWord;
import com.enterprise.lu.uni.notebook.app.tools.CSVWriter;
import com.enterprise.lu.uni.notebook.app.tools.DBHelper;
import com.enterprise.lu.uni.notebook.app.tools.PermissionHelper;
import com.enterprise.lu.uni.notebook.app.tools.UIHelper;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class NotebookActivity extends AppCompatActivity {

    private GridView gridView;
    private static final String[] letters = new String[] {
            "A", "B", "C", "D", "E",
            "F", "G", "H", "I", "J",
            "K", "L", "M", "N", "O",
            "P", "Q", "R", "S", "T",
            "U", "V", "W", "X", "Y", "Z"
    };
    private static final int[] colors = new int[] {
            Color.parseColor("#F44336"),
            Color.parseColor("#E91E63"),
            Color.parseColor("#9C27B0"),
            Color.parseColor("#673AB7"),
            Color.parseColor("#3F51B5"),
            Color.parseColor("#2196F3"),
            Color.parseColor("#03A9F4"),
            Color.parseColor("#00BCD4"),
            Color.parseColor("#009688"),
            Color.parseColor("#4CAF50"),
            Color.parseColor("#8BC34A"),
            Color.parseColor("#CDDC39"),
            Color.parseColor("#FFEB3B"),
            Color.parseColor("#FFC107"),
            Color.parseColor("#FF9800"),
            Color.parseColor("#FF5722"),
            Color.parseColor("#795548"),
            Color.parseColor("#9E9E9E"),
            Color.parseColor("#607D8B"),
            Color.parseColor("#F44336"),
            Color.parseColor("#E91E63"),
            Color.parseColor("#9C27B0"),
            Color.parseColor("#673AB7"),
            Color.parseColor("#3F51B5"),
            Color.parseColor("#2196F3"),
            Color.parseColor("#03A9F4")
    };

    private Button exportBtn;
    private Button importBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        UIHelper.slideBackButton(this);
        initializeWidgets();

        GridAdapter gridAdapter = new GridAdapter(this, letters, colors);
        gridView.setAdapter(gridAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedLetter = letters[position];
                Intent intent = new Intent(getBaseContext(), WordListActivity.class);
                intent.putExtra("letterTitle", selectedLetter);
                startActivity(intent);
            }
        });

        exportBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String DB_PATH = "/data/data/com.enterprise.lu.uni.notebook/databases/";

                String DB_NAME = "Notebook.db";
                SQLiteDatabase myDataBase;
                String myPath = DB_PATH + DB_NAME;
                if(PermissionHelper.checkForPermissions(NotebookActivity.this)){
                    File exportDir = new File(Environment.getExternalStorageDirectory(), "");
                    if (!exportDir.exists())
                    {
                        exportDir.mkdirs();
                    }

                    File file = new File(exportDir, "notebook.csv");
                    try
                    {
                        DBHelper mHelper= new DBHelper(getApplicationContext());
                        file.createNewFile();
                        myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
                        mHelper.openDataBase();
                        CSVWriter csvWrite = new CSVWriter(new FileWriter(file));

                        Cursor curCSV = myDataBase.rawQuery("SELECT Word, translatedWord, Domain.Name as DomainName " +
                                "FROM NewWord,Domain where NewWord.id=Domain.id",null);
                        csvWrite.writeNext(curCSV.getColumnNames());
                        while(curCSV.moveToNext())
                        {
                            //Which column you want to export
                            String arrStr[] ={curCSV.getString(0), curCSV.getString(1),
                                    curCSV.getString(2)};
                            csvWrite.writeNext(arrStr);
                        }
                        csvWrite.close();
                        curCSV.close();
                    }
                    catch(Exception sqlEx)
                    {
                        Log.e("MainActivity", sqlEx.getMessage(), sqlEx);
                    }
                    Toast.makeText(getBaseContext(),"Your list has been exported!",Toast.LENGTH_SHORT).show();
                }else {
                    PermissionHelper.askForPermissions(NotebookActivity.this);
                }
            }
        });

        importBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (PermissionHelper.checkForPermissions(NotebookActivity.this)) {
                    Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                    intent.addCategory(Intent.CATEGORY_OPENABLE);
                    intent.setType("text/csv");
                    startActivityForResult(Intent.createChooser(intent, "Select file"), 40);
                }
                else{
                    PermissionHelper.askForPermissions(NotebookActivity.this);
                }
            }
        });
    }

    private void initializeWidgets(){
        gridView = (GridView)findViewById(R.id.gridView);
        exportBtn = (Button) findViewById(R.id.buttonExport);
        importBtn = (Button) findViewById(R.id.buttonImport);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == 40){
            if(resultCode == RESULT_OK){
                readFile(data);
                Toast.makeText(getBaseContext(),"Your list has been imported!",Toast.LENGTH_SHORT).show();
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

    public static Domain getDomainFromName(String domainName) {
        return new Select()
                .from(Domain.class)
                .where("Name = ?", domainName)
                .executeSingle();
    }
}