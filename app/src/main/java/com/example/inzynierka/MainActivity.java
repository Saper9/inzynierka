package com.example.inzynierka;

import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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
            int n = rand.nextInt(1); //0-1, zmienic potem jak bedzie wiecej liter
            String temp = Cons.ALFABET[n];
            patterngen = patterngen + temp;
        }
        pattern = patterngen;
        //MakeSoundArray();
        return patterngen;
    }



    private void PlayGeneratedPattern(){
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


}
