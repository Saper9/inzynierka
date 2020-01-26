package com.example.inzynierka;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Settings extends AppCompatActivity {



    public void saveSettings(){

        EditText fieldHZ=findViewById(R.id.hztextfield);
        int  tmp=Integer.parseInt(fieldHZ.getText().toString());
        if(tmp==0) return;
        BeepClassMain.SetHz((float) tmp);

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);


        Button save=findViewById(R.id.savesettingsbutton);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveSettings();
                finish();
            }
        });


    }


}
