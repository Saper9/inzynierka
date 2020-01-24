package com.example.inzynierka;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
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
            int n = rand.nextInt(2); //0-1, zmienic potem jak bedzie wiecej liter
            String temp = Cons.ALFABET[n];
            patterngen = patterngen + temp;
        }
        pattern = patterngen;
        //MakeSoundArray();
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

    private void PlayShittyPattern(){
    BeepClassMain.SetUpEverything();
    BeepClassMain.GenerateSoundWave();
    String morsePat=BeepClassMain.ConvertPatternToMorsePattern(pattern);
    BeepClassMain.PlayPattern(morsePat);

    }
//o okreslonej czestotliwosci i czasie trwania
    //czestotliwosc i predkosc nadawania
    //mozna dlugoscia kropki regulowac



    private void TestPlaySound(){
         final int duration = 3; // seconds
         final int sampleRate = 8000;
         final int numSamples = duration * sampleRate;
          double sample[] = new double[numSamples];
         final double freqOfTone = 440; // hz
         byte generatedSnd[] = new byte[2 * numSamples];
        for (int i = 0; i < numSamples; ++i) {
            sample[i] = Math.sin(2 * Math.PI * i / (sampleRate/freqOfTone));
        }

        // convert to 16 bit pcm sound array
        // assumes the sample buffer is normalised.
        int idx = 0;
        for (final double dVal : sample) {
            // scale to maximum amplitude
            final short val = (short) ((dVal * 32767));
            // in 16 bit wav PCM, first byte is the low order byte
            generatedSnd[idx++] = (byte) (val & 0x00ff);
            generatedSnd[idx++] = (byte) ((val & 0xff00) >>> 8);
        }
            AudioTrack audioTrack = new AudioTrack(AudioManager.STREAM_MUSIC,
                    sampleRate, AudioFormat.CHANNEL_OUT_MONO,
                    AudioFormat.ENCODING_PCM_16BIT, generatedSnd.length,
                    AudioTrack.MODE_STATIC);





            audioTrack.write(generatedSnd, 0, generatedSnd.length);
            audioTrack.play();

        }




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
                PlayShittyPattern();
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
