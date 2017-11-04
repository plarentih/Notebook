package com.enterprise.lu.uni.notebook.app.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.enterprise.lu.uni.notebook.R;
import com.enterprise.lu.uni.notebook.app.model.Domain;

public class AddDomainActivity extends AppCompatActivity {

    private Button addButton;
    private EditText editTextDomainName;

    private Domain domainToEdit;
    private long domainToEditId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_domain);
        initializeWidgets();

        domainToEdit = (Domain) getIntent().getSerializableExtra("EDIT_DOMAIN");
        domainToEditId = getIntent().getLongExtra("EDIT_DOMAIN_ID", -1);
        if(domainToEdit != null){
            editTextDomainName.setText(domainToEdit.getDomainName());
        }

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Editable typedDomainName = editTextDomainName.getText();
                if(domainToEdit != null){
                    Domain domain = Domain.load(Domain.class, domainToEditId);
                    domain.domainName = typedDomainName.toString();
                    domain.save();
                    setResult(RESULT_OK);
                    finish();
                }
                if(domainToEdit == null){
                    Domain newDomain = new Domain();
                    newDomain.domainName = typedDomainName.toString();
                    newDomain.save();
                    setResult(RESULT_OK);
                    finish();
                }
            }
        });
    }

    private void initializeWidgets(){
        addButton = (Button) findViewById(R.id.addDomainButton);
        editTextDomainName = (EditText) findViewById(R.id.editTextDomain);
    }
}
