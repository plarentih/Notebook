package com.enterprise.lu.uni.notebook.app.tools;

import android.content.Context;
import android.speech.tts.TextToSpeech;

import java.util.Locale;

/**
 * Created by Plarent on 11/11/2017.
 */

public class TextToSpeechHelper {
    private static TextToSpeech textToSpeech;

    public static void initializeTextToSpeech(Context context){
        textToSpeech = new TextToSpeech(context, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status){
                if(status != TextToSpeech.ERROR){
                    textToSpeech.setLanguage(Locale.US);
                }
            }
        });
    }

    public static void speakTheWord(String string){
        textToSpeech.speak(string, TextToSpeech.QUEUE_FLUSH, null);
    }
}
