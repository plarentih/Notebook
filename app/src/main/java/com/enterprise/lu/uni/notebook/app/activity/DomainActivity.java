package com.enterprise.lu.uni.notebook.app.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.activeandroid.query.Select;
import com.enterprise.lu.uni.notebook.R;
import com.enterprise.lu.uni.notebook.app.model.Domain;
import com.enterprise.lu.uni.notebook.app.model.NewWord;
import com.enterprise.lu.uni.notebook.app.tools.UIHelper;

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
        UIHelper.slideBackButton(this);
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

        domainListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final int internalPosition = position;

                final AlertDialog.Builder builder = new AlertDialog.Builder(DomainActivity.this);
                builder.setTitle("Edit/Delete");
                builder.setMessage("Do you want to edit or delete this word?");
                builder.setPositiveButton("Edit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String selectedDomainName = arrayAdapter.getItem(internalPosition);
                        Domain selectedDomain = getDomainFromName(selectedDomainName);
                        Intent intent = new Intent(getBaseContext(), AddDomainActivity.class);
                        intent.putExtra("EDIT_DOMAIN", selectedDomain);
                        intent.putExtra("EDIT_DOMAIN_ID", selectedDomain.getId());
                        startActivityForResult(intent, REQUEST_CODE_ADD_DOMAIN);
                    }
                });
                builder.setNegativeButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String selectedDomainName = arrayAdapter.getItem(internalPosition);
                        Domain selectedDomain = getDomainFromName(selectedDomainName);
                        if(domainExistsInWordTable(selectedDomain)){
                            UIHelper.showDialog(DomainActivity.this, "Attention!", "You can not delete this domain " +
                                    "because it is used in your word list.", "OK", null);
                        }else {
                            selectedDomain.delete();
                            getDomainNames();
                            arrayAdapter = new ArrayAdapter<String>(getBaseContext(),
                                    android.R.layout.simple_list_item_1, domainNames);
                            domainListView.setAdapter(arrayAdapter);
                            Toast.makeText(getBaseContext(), "Domain is deleted!", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
                builder.setCancelable(true);
                builder.show();
            }
        });
    }

    private boolean domainExistsInWordTable(Domain domain){
        for(int index = 0; index < getAllNewWords().size(); index++){
            if(domain.equals(getAllNewWords().get(index).getDomain())){
                return true;
            }
        }
        return false;
    }

    public static Domain getDomainFromName(String domainName){
        return new Select()
                .from(Domain.class)
                .where("Name = ?", domainName)
                .executeSingle();
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

    public static List<NewWord> getAllNewWords(){
        return new Select()
                .from(NewWord.class)
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
