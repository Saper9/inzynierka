package com.example.inzynierka;

import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.util.Log;

public class BeepsClass {
    //konfiguracja audiotracka
    static int buff = AudioTrack.getMinBufferSize(48000, AudioFormat.CHANNEL_OUT_MONO, AudioFormat.ENCODING_PCM_16BIT);
    private static AudioTrack track = new AudioTrack(AudioManager.STREAM_MUSIC, 48000, AudioFormat.CHANNEL_OUT_MONO, AudioFormat.ENCODING_PCM_16BIT, buff, AudioTrack.MODE_STREAM);

    //odstep mieczy znakami =3 kropki
    //silence= 3 kropki
    //zalozmy, ze kropka rowna 0,25 sekundy
    //kreska=3*kropka=0,75 sekundy
    //odstep miedzy poszczegolnymi elementami znakow- jedna kropka, 0,25 sekundy

    public static int frequency = 500; //Hz
    public static int rate = 8000;
    public static int time = 250; //ms
    public static int samples = time * rate / 1000;
    public static short sampleTab[] = new short[samples];
    public static int cycleLen;
    public static int relStart;
    public static int samplesCycle;
    public static int numberOfSampls;


    //generowanie tonow
    public static void GenerateTones() {
        /// numberOfSampls=250*rate/1000; ///czas trwania ms

        samplesCycle = rate / frequency;
        cycleLen = samplesCycle * 4;// ilosc probek w cyklu
        relStart = samples - cycleLen; //23.01.2020 2000-64=1936


        //probkowanie, tak jak pan Maka powiedzial
        for (int i = 0; i < samples; i++) {
            double tempSound = Math.sin(2 * Math.PI * i / (rate / frequency)); //generowanie dzwieku
            double tempLvl = Math.sin(tempSound) * Short.MAX_VALUE;


            tempLvl = shapeAmplitude(i, tempLvl);
            short smp = (short) tempLvl;
            sampleTab[i] = smp;


        }

        //
    }

    //przerobic


    private static double shapeAmplitude(int sampleNum, double input) {
        double output;

        if (isAttackCycle(sampleNum)) {
            output = input * getAttackGain(sampleNum);
        } else if (isReleaseCycle(sampleNum)) {
            output = input * getReleaseGain(sampleNum);
        } else {
            output = input;
        }

        return output;
    }

    //narasta
    private static boolean isAttackCycle(int sampleNum) {
        return (sampleNum < cycleLen);
    }

    //maleje
    private static boolean isReleaseCycle(int sampleNum) {
        return (relStart <= sampleNum);
    }

    private static double getAttackGain(int sampleNum) {
        int rampPosition = sampleNum;

        if (rampPosition < 0) rampPosition = 0;
        if (rampPosition > cycleLen) rampPosition = cycleLen;
        double gain = (double) sampleNum / (double) cycleLen;
        return gain;
    }

    private static double getReleaseGain(int sampleNum) {
        int rampPosition = samples - sampleNum;

        if (rampPosition < 0) rampPosition = 0;
        if (rampPosition > cycleLen) rampPosition = cycleLen;
        double gain = (double) rampPosition / (double) cycleLen;
        return gain;
    }

    //koniec przerobic
    public static void generateSilence() {

        for (int i = 0; i < samples; i++) {
            sampleTab[i] = 0;
        }
    }

    //odgrywanie
    public static String ConvertPatternToMorsePattern(String pattern) {
        String morsePattern = "";

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
        GenerateTones();
        track.write(sampleTab, 0, rate);

    }

    //line linia play

    private static void PlayLine() {
        GenerateTones();
        track.write(sampleTab, 0, rate);
        GenerateTones();
        track.write(sampleTab, 0, rate);
        GenerateTones();
        track.write(sampleTab, 0, rate);

    }

    //odstep miedzy znakami play
    private static void PlaySpaceInCharacter() {
        generateSilence();
        track.write(sampleTab, 0, rate);


    }

    //odstep miedzy znakami litery play

    private static void PlaySpaceBetweenCharacter() {
        generateSilence();
        track.write(sampleTab, 0, rate);
        generateSilence();
        track.write(sampleTab, 0, rate);
        generateSilence();
        track.write(sampleTab, 0, rate);
    }


    //potem następnie z patternu zrobić pattern kodu morse , oddzielany spacjami. spacja to przerwa miedzy znakami
    //

    public static int isStillSameCharacter(String morsePattern, int i) {

        if (morsePattern.charAt(i + 1) == '.' || morsePattern.charAt(i + 1) == '-') return 1;

        else return 0;
    }


    public static void PlayPattern(String morsePattern) {


        track.play();
        for (int i = 0; i < morsePattern.length(); i++) {
            //tutaj zrobic tablice dzwiekow
            int sampleRate = 8000;


            if (morsePattern.charAt(i) == '.') {
                Log.i("play .", " ");
                int numersofSamples = 250 * sampleRate / 1000; ///ms*rate/1000
                int samples[] = new int[numersofSamples];
                //play znak
                PlayDot();
                if (isStillSameCharacter(morsePattern, i) == 1) continue;
                //play cisza miedzy znakami
                PlaySpaceInCharacter();
                numersofSamples = 250 * sampleRate / 1000;
            }
            if (morsePattern.charAt(i) == '-') {
                int numersofSamples = 750 * sampleRate / 1000;
                int samples[] = new int[numersofSamples];
                //play znak
                PlayLine();
                if (isStillSameCharacter(morsePattern, i) == 1) continue;
                //play cisza miedzy znakami
                numersofSamples = 250 * sampleRate / 1000;
            }
            if (morsePattern.charAt(i) == ' ') {
                int numersofSamples = 750 * sampleRate / 1000;
                int samples[] = new int[numersofSamples];
                //play cisza

            }


        }


    }


}
