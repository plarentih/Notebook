package com.enterprise.lu.uni.notebook.app.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.io.Serializable;

/**
 * Created by Plarent on 10/18/2017.
 */
@Table(name="NewWord")
public class NewWord extends Model implements Serializable {

    @Column(name="Word")
    public String word;

    @Column(name="translatedWord")
    public String translation;

    @Column(name="Domain")
    public Domain domain;

    public NewWord(){
        super();
    }



    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getTranslation() {
        return translation;
    }

    public void setTranslation(String translation) {
        this.translation = translation;
    }

    public Domain getDomain() {
        return domain;
    }

    public void setDomain(Domain domain) {
        this.domain = domain;
    }
}

