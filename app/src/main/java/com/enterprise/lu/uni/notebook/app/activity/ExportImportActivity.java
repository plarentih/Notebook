package com.enterprise.lu.uni.notebook.app.activity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.enterprise.lu.uni.notebook.R;

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
                    CSVWriter csvWrite = new CSVWriter(new FileWriter(file));
                    SQLiteDatabase db = mHelper.getReadableDatabase();
                    Cursor curCSV = db.rawQuery("SELECT * FROM NewWord",null);
                    csvWrite.writeNext(curCSV.getColumnNames());
                    while(curCSV.moveToNext())
                    {
                        //Which column you want to export
                        String arrStr[] ={curCSV.getString(0),curCSV.getString(1), curCSV.getString(2)};
                        csvWrite.writeNext(arrStr);
                    }
                    csvWrite.close();
                    curCSV.close();
                }
                catch(Exception sqlEx)
                {
                    Log.e("MainActivity", sqlEx.getMessage(), sqlEx);
                }
            }

        });

        importButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
