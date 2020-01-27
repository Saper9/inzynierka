package com.example.inzynierka;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class Settings extends AppCompatActivity implements AdapterView.OnItemSelectedListener {


    int lvlofAdvanc = 1;

    public void saveSettings() {

        //word length
        //TODO by nei bylo ujemnych w dlugosci slowa
        EditText lengthField = findViewById(R.id.lenghtofword);
        String lengthStrin = lengthField.getText().toString();
        if (!lengthStrin.isEmpty()) {
            int tmpLength = Integer.parseInt((lengthStrin));
            MainActivity.setLenghtPattern(tmpLength);
        }
        //dot length kurwa naucz sie pisac LENGTH debilu
        EditText dotField=findViewById(R.id.dotlength);
        String lengthDot=dotField.getText().toString();
        if(!lengthDot.isEmpty()){
            int tmpDot=Integer.parseInt((lengthDot));
            BeepClassMain.SetTimeOfDot(tmpDot);
        }
        EditText fieldHZ = findViewById(R.id.hztextfield);
        String txt = fieldHZ.getText().toString();
        MainActivity.setLvl(lvlofAdvanc);
        if (txt.isEmpty()) return;
        int tmp = Integer.parseInt(txt);
        BeepClassMain.SetHz((float) tmp);



    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Spinner spinner = findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.levelOfLearning, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);


        Button save = findViewById(R.id.savesettingsbutton);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveSettings();
                finish();
            }
        });


    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        lvlofAdvanc = position + 1;


    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
