package com.example.inzynierka;

import android.app.AlertDialog;
import android.app.Dialog;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.ToneGenerator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    Consts Cons = new Consts();
    final int patternLenght = 10;
    String pattern;
    int[] soundArray = new int[patternLenght * 2];
    MediaPlayer[] mp = new MediaPlayer[patternLenght * 2];
    int userInputCorrectNumber;


    private String GeneratePattern(int lenght) {
        String patterngen = "";
        Random rand = new Random();
        for (int i = 0; i < lenght; i++) {
            int n = rand.nextInt(26); //0-1, zmienic potem jak bedzie wiecej liter
            String temp = Cons.ALFABET[n];
            patterngen = patterngen + temp;
        }
        pattern = patterngen;
        MakeSoundArray();
        return patterngen;
    }

    private void MakeSoundArray() {
        for (int i = 0; i < (pattern.length() * 2) ; i += 2) {

            char sound = pattern.charAt(i/2);

            if (sound == 'A') soundArray[i] = R.raw.a_sound_morse;
            if (sound == 'B') soundArray[i] = R.raw.b_sound_morse;
            if (sound == 'C') soundArray[i] = R.raw.c_morse_code;

            if (sound == 'D') soundArray[i] = R.raw.d_morse_code;
            if (sound == 'E') soundArray[i] = R.raw.e_morse_code;
            if (sound == 'F') soundArray[i] = R.raw.f_morse_code;

            if (sound == 'G') soundArray[i] = R.raw.g_morse_code;
            if (sound == 'H') soundArray[i] = R.raw.h_morse_code;
            if (sound == 'I') soundArray[i] = R.raw.i_morse_code;

            if (sound == 'J') soundArray[i] = R.raw.j_morse_code;
            if (sound == 'K') soundArray[i] = R.raw.k_morse_code;
            if (sound == 'L') soundArray[i] = R.raw.l_morse_code;

            if (sound == 'M') soundArray[i] = R.raw.m_morse_code;
            if (sound == 'N') soundArray[i] = R.raw.n_morse_code;
            if (sound == 'O') soundArray[i] = R.raw.o_morse_code;

            if (sound == 'P') soundArray[i] = R.raw.p_morse_code;
            if (sound == 'Q') soundArray[i] = R.raw.q_morse_code;
            if (sound == 'R') soundArray[i] = R.raw.r_morse_code;

            if (sound == 'S') soundArray[i] = R.raw.s_morse_code;
            if (sound == 'T') soundArray[i] = R.raw.t_morse_code;
            if (sound == 'U') soundArray[i] = R.raw.u_morse_code;

            if (sound == 'U') soundArray[i] = R.raw.u_morse_code;
            if (sound == 'V') soundArray[i] = R.raw.v_morse_code;
            if (sound == 'W') soundArray[i] = R.raw.w_morse_code;

            if (sound == 'X') soundArray[i] = R.raw.x_morse_code;
            if (sound == 'Y') soundArray[i] = R.raw.y_morse_code;
            if (sound == 'Z') soundArray[i] = R.raw.z_morse_code;


            //if (i + 1 >= patternLenght) break;


            soundArray[i + 1] = R.raw.silence;



        }

        for(int i=0;i<patternLenght*2;i++){
            Log.i("soundArray: ", "i:"+i+" " +soundArray[i]);

        }

    }


    private void PlayFile() {

        if (pattern == null) return;
        char nextSound;

        for (int i = 0; i < pattern.length() * 2; i++){
            Log.i("i", "" + i);


            mp[i] = MediaPlayer.create(MainActivity.this, soundArray[i]);
        }


        for (int i = 0; i < (patternLenght * 2) - 1; i++) mp[i].setNextMediaPlayer(mp[i + 1]);


        mp[0].start();


    }

    void checkPattern() {
        userInputCorrectNumber = 0;
        EditText userPatternField = findViewById(R.id.userPatternInput);
        String patternTemp = userPatternField.getText().toString();
        patternTemp = patternTemp.toUpperCase();

        if (patternTemp.length() != patternLenght) {
            for (int i = patternTemp.length(); i < patternLenght; i++) patternTemp += " ";
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
                PlayFile();
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


}
