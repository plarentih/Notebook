package com.enterprise.lu.uni.notebook.app.activity;

import com.enterprise.lu.uni.notebook.app.model.Domain;

/**
 * Created by Ema Kepuska on 10/30/2017.
 */

public class WordSample {
    String word;
    String translatedWord;
    Domain domain;

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getTranslatedWord() {
        return translatedWord;
    }

    public void setTranslatedWord(String translatedWord) {
        this.translatedWord = translatedWord;
    }

    public Domain getDomain() {
        return domain;
    }

    public void setDomain(Domain domain) {
        this.domain = domain;
    }
}
