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
    final int patternLenght = 10;
    String pattern;
    int[] soundArray = new int[patternLenght * 2];
    MediaPlayer[] mp = new MediaPlayer[patternLenght * 2];
    int userInputCorrectNumber;

    Toolbar toolbar;


    private String GeneratePattern(int lenght) {
        String patterngen = "";

        Random rand = new Random();
        for (int i = 0; i < lenght; i++) {
            int n = rand.nextInt(26); //0-1, zmienic potem jak bedzie wiecej liter
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

        if (patternTemp.length() != patternLenght) {
            for (int i = patternTemp.length(); i < patternLenght; i++) patternTemp += "`";
        }
        for (int i = 0; i < patternLenght; i++) {
            if (pattern.charAt(i) == patternTemp.charAt(i)) userInputCorrectNumber += 1;
        }
        Log.i("checkPattern", "ilosc liczb: " + patternLenght + " ilosc popprawnych liczby usera: " + userInputCorrectNumber);

        float prcnt = (float) (userInputCorrectNumber * 100) / patternLenght;
        Log.i("checkPattern", "procent: " + prcnt + "%");
        String tmpTODialog;
        if (prcnt >= 90.0) {
            tmpTODialog = "Gratulację, opanowałeś ten poziom";
        } else {
            tmpTODialog = "Nie opanowałeś tego poziomu. Musisz jeszcze poćwiczyć";
        }

        if (patternTemp.length() > patternLenght) {
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
                pattern = GeneratePattern(patternLenght);
                Log.i("Pattern", pattern);
            }
        });
        //Odtwarzanie patternu
        Button playpattern = findViewById(R.id.playPattern);
        playpattern.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //PlayFile();
                PlayGeneratedPattern();
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
                startActivity(new Intent(MainActivity.this,Settings.class));
                break;

        }

        return true;
    }

}
