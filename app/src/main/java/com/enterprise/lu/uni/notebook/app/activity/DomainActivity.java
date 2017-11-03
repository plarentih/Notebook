package com.enterprise.lu.uni.notebook.app.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.activeandroid.query.Select;
import com.enterprise.lu.uni.notebook.R;
import com.enterprise.lu.uni.notebook.app.model.Domain;
import com.enterprise.lu.uni.notebook.app.model.NewWord;

import java.util.ArrayList;
import java.util.List;

public class DomainActivity extends AppCompatActivity {

    public static final int REQUEST_CODE_ADD_DOMAIN = 30;

    private Button addButton;
    private ListView domainListView;
    private List<String> domainNames;
    private ArrayAdapter<String> arrayAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_domain);
        initializeWidgets();

        getDomainNames();

        arrayAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, domainNames);

        domainListView.setAdapter(arrayAdapter);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), AddDomainActivity.class);
                startActivityForResult(intent, REQUEST_CODE_ADD_DOMAIN);
            }
        });
    }

    private void getDomainNames(){
        domainNames = new ArrayList<>();
        for(int index = 0; index < getAllDomains().size(); index++){
            domainNames.add(index, getAllDomains().get(index).getDomainName());
        }
    }

    public static List<Domain> getAllDomains(){
        return new Select()
                .from(Domain.class)
                .execute();
    }

    private void initializeWidgets(){
        addButton = (Button) findViewById(R.id.button);
        domainListView = (ListView) findViewById(R.id.domainListView);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK){
            if(requestCode == REQUEST_CODE_ADD_DOMAIN){
                domainNames.clear();
                getDomainNames();
                arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, domainNames);
                domainListView.setAdapter(arrayAdapter);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
