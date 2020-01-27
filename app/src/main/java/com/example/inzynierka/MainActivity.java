package com.example.inzynierka;

import android.content.Intent;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.Random;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    Consts Cons = new Consts();
    static int patternLength = 10;
    String pattern;


    int userInputCorrectNumber;
    static int level = 26;
    static boolean isThreadWorking = false;

    public static void setLvl(int lvl) {
        Log.i("lvl", "ustawiam lvl na: " + lvl);
        level = lvl;
    }

    public static void setLenghtPattern(int lngth) {
        patternLength = lngth;
    }


    private String GeneratePattern(int lenght) {
        String patterngen = "";

        Random rand = new Random();
        for (int i = 0; i < lenght; i++) {
            int n = rand.nextInt(level);
            String temp = Cons.ALFABET[n];
            patterngen = patterngen + temp;
            //TODO change to get other chars
        }
        pattern = patterngen;
        //MakeSoundArray();
        return patterngen;
    }


    private void PlayGeneratedPattern() {
        BeepClassMain.SetUpEverything();
        BeepClassMain.GenerateSoundWave();
        BeepClassMain.GenerateSoundWaveLine();
        String morsePat = BeepClassMain.ConvertPatternToMorsePattern(pattern);
        BeepClassMain.PlayPattern(morsePat);

    }
//o okreslonej czestotliwosci i czasie trwania
    //czestotliwosc i predkosc nadawania
    //mozna dlugoscia kropki regulowac


    void checkPattern() {
        if (pattern == null) return;
        userInputCorrectNumber = 0;
        EditText userPatternField = findViewById(R.id.userPatternInput);
        String patternTemp = userPatternField.getText().toString();
        patternTemp = patternTemp.toUpperCase();

        if (patternTemp.length() != patternLength) {
            for (int i = patternTemp.length(); i < patternLength; i++) patternTemp += "`";
        }
        for (int i = 0; i < patternLength; i++) {
            if (pattern.charAt(i) == patternTemp.charAt(i)) userInputCorrectNumber += 1;
        }
        Log.i("checkPattern", "ilosc liczb: " + patternLength + " ilosc popprawnych liczby usera: " + userInputCorrectNumber);

        float prcnt = (float) (userInputCorrectNumber * 100) / patternLength;
        Log.i("checkPattern", "procent: " + prcnt + "%");
        String tmpTODialog;
        if (prcnt >= 90.0) {
            tmpTODialog = "Gratulację, opanowałeś ten poziom";
        } else {
            tmpTODialog = "Nie opanowałeś tego poziomu. Musisz jeszcze poćwiczyć";
        }

        if (patternTemp.length() > patternLength) {
            tmpTODialog = "Podano zbyt dużą liczbę znaków";
        }
        PatternCheckDialog dialog = PatternCheckDialog.newInstance(tmpTODialog);
        dialog.show(getSupportFragmentManager(), "wiadro");


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("MorseCode Trainer");


        //TODO zamienic te 3 guziki na 2, jeden od generowania i grania paternu, drugi od checku
        //TODO zrobic tablicę/obrazek z kodem morse'a jako ściągawka
        //Generowanie patternu do nauki
        Button start = findViewById(R.id.make_pattern);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pattern = GeneratePattern(patternLength);
                Log.i("Pattern", pattern);
            }
        });
        //Odtwarzanie patternuu
        Button playpattern = findViewById(R.id.playPattern);
        playpattern.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!isThreadWorking) {
                    isThreadWorking = true;
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            PlayGeneratedPattern();
                        }
                    }).start();
                }


            }
        });

        //sprawdzenie patternu
        Button checkPatternB = findViewById(R.id.checkpatternButton);
        checkPatternB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkPattern();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.settingsscreen:
                startActivity(new Intent(MainActivity.this, Settings.class));
                break;

        }

        return true;
    }

}
