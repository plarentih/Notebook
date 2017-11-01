package com.enterprise.lu.uni.notebook.app.activity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.enterprise.lu.uni.notebook.R;
import com.enterprise.lu.uni.notebook.app.tools.PermissionHelper;

import java.io.File;
import java.io.FileWriter;

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

                        Cursor curCSV = myDataBase.rawQuery("SELECT Word, translatedWord, Domain FROM NewWord",null);
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
                Intent intent = new Intent(Settings.ACTION_INTERNAL_STORAGE_SETTINGS);
                startActivityForResult(intent, 200);
            }
        });
    }
}
