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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_domain);
        initializeWidgets();

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Editable typedDomainName = editTextDomainName.getText();
                Domain newDomain = new Domain();
                newDomain.domainName = typedDomainName.toString();
                newDomain.save();
                setResult(RESULT_OK);
                finish();
            }
        });
    }

    private void initializeWidgets(){
        addButton = (Button) findViewById(R.id.addDomainButton);
        editTextDomainName = (EditText) findViewById(R.id.editTextDomain);
    }
}
