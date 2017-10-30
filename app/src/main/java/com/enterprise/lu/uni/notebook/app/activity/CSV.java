package com.enterprise.lu.uni.notebook.app.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.enterprise.lu.uni.notebook.R;
import com.enterprise.lu.uni.notebook.app.model.Domain;

import java.io.BufferedReader;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ema Kepuska on 10/30/2017.
 */

public class CSV extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_csv);
        
        readWordList();
        
    }
    private List<WordSample> list= new ArrayList<>();
    private void readWordList() {
            InputStream is= getResources().openRawResource(R.raw.list);
        BufferedReader reader= new BufferedReader(
                new InputStreamReader(is, Charset.forName("UTF-8"))

        );
        String line= null;

        try{
            while((line=reader.readLine())!=null){
                //Split by ','
                String[] tokens= line.split(",");
                //Read the data
                WordSample sample= new WordSample();
                sample.setWord(tokens[0]);
                sample.setTranslatedWord(tokens[1]);
                //sample.setDomain(Domain.toString
                list.add(sample);

                Log.d("MyActivity", "Just created" + sample);
            }
        }catch (IOException e){
            Log.wtf("MyActivity", "Error reading data file on line" + line, e);
            e.printStackTrace();

        }

    }
}
