package com.example.inzynierka;

import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.util.Log;

public class BeepClassMain {
    private static AudioTrack audioTrack;
    private static int SAMPLE_RATE_HZ = 48000;
    private static int numofSamples;
    private static short samples[];
    private static short silenceTab[];


    public static void SetUpEverything() {
        int bufferSize = AudioTrack.getMinBufferSize(SAMPLE_RATE_HZ, AudioFormat.CHANNEL_OUT_MONO, AudioFormat.ENCODING_PCM_16BIT);
        audioTrack = new AudioTrack(AudioManager.STREAM_MUSIC, SAMPLE_RATE_HZ, AudioFormat.CHANNEL_OUT_MONO, AudioFormat.ENCODING_PCM_16BIT, bufferSize, AudioTrack.MODE_STREAM);
        audioTrack.setStereoVolume(AudioTrack.getMaxVolume(), AudioTrack.getMaxVolume());
        numofSamples = 250 * 8000 / 1000; // duration in ms * samplerate/1000
        samples = new short[numofSamples];
        silenceTab=new short[numofSamples];


    }

    public static void GenerateSoundWave() {
        // Generate waveform
        /*
        for (int sampleNum = 0; sampleNum < numofSamples; sampleNum++) {
            double angle = (2 * Math.PI) * ((double) sampleNum / (double) 8000);
            double level = Math.sin(angle) * Short.MAX_VALUE;
            short sample;

            sample = (short) level;
            samples[sampleNum] = sample;

        }
        */

        for (int i = 0; i < numofSamples; i++) {
            samples[i] = 15000;
            silenceTab[i]=0;
        }
    }


    public static String ConvertPatternToMorsePattern(String pattern) {
        String morsePattern = "";
        GenerateSoundWave();

        for (int i = 0; i < pattern.length(); i++) {
            if (pattern.charAt(i) == 'A') morsePattern += ".- ";
            if (pattern.charAt(i) == 'B') morsePattern += "-... ";
            if (pattern.charAt(i) == 'C') morsePattern += "-.-. ";
            if (pattern.charAt(i) == 'D') morsePattern += "-.. ";
            if (pattern.charAt(i) == 'E') morsePattern += ". ";
            if (pattern.charAt(i) == 'F') morsePattern += "..-. ";
            if (pattern.charAt(i) == 'G') morsePattern += "--. ";
            if (pattern.charAt(i) == 'H') morsePattern += ".... ";
            if (pattern.charAt(i) == 'I') morsePattern += ".. ";
            if (pattern.charAt(i) == 'J') morsePattern += ".--- ";
            if (pattern.charAt(i) == 'K') morsePattern += "-.- ";
            if (pattern.charAt(i) == 'L') morsePattern += ".-.. ";
            if (pattern.charAt(i) == 'M') morsePattern += "-- ";
            if (pattern.charAt(i) == 'N') morsePattern += "-. ";
            if (pattern.charAt(i) == 'O') morsePattern += "--- ";
            if (pattern.charAt(i) == 'P') morsePattern += ".--. ";
            if (pattern.charAt(i) == 'Q') morsePattern += "--.- ";
            if (pattern.charAt(i) == 'R') morsePattern += ".-. ";
            if (pattern.charAt(i) == 'S') morsePattern += "... ";
            if (pattern.charAt(i) == 'T') morsePattern += "- ";
            if (pattern.charAt(i) == 'U') morsePattern += "..- ";
            if (pattern.charAt(i) == 'V') morsePattern += "...- ";
            if (pattern.charAt(i) == 'W') morsePattern += ".-- ";
            if (pattern.charAt(i) == 'X') morsePattern += "-..- ";
            if (pattern.charAt(i) == 'Y') morsePattern += "-.-- ";
            if (pattern.charAt(i) == 'Z') morsePattern += "--.. ";

        }
        Log.i("patterntomorsebeep", morsePattern);
        return morsePattern;

    }

    //dot kropka play
    private static void PlayDot() {
        Log.i("play Dot fun", " play dot fun");
        audioTrack.write(samples, 0, 8000);


    }
    private static void PlaySilence(){
    audioTrack.write(silenceTab,0,8000);
    }
    //line linia play


    //potem następnie z patternu zrobić pattern kodu morse , oddzielany spacjami. spacja to przerwa miedzy znakami
    //

    public static int isStillSameCharacter(String morsePattern, int i) {

        if (morsePattern.charAt(i + 1) == '.' || morsePattern.charAt(i + 1) == '-') return 1;

        else return 0;
    }


    public static void PlayPattern(String morsePattern) {


        audioTrack.play();
        for (int i = 0; i < morsePattern.length(); i++) {
            //tutaj zrobic tablice dzwiekow



            if (morsePattern.charAt(i) == '.') {

                //play znak
                PlayDot();
                if (isStillSameCharacter(morsePattern, i) == 1) continue;
                //play cisza miedzy znakami


            }
            if (morsePattern.charAt(i) == '-') {

                //play znak

                if (isStillSameCharacter(morsePattern, i) == 1) continue;
                //play cisza miedzy znakami

            }
            if (morsePattern.charAt(i) == ' ') {

                //play cisza

            }


        }
    }

}
