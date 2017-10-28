package com.enterprise.lu.uni.notebook.app.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Plarent on 10/18/2017.
 */
@Table(name="Domain")
public class Domain extends Model implements Serializable {

    @Column(name="Name")
    public String domainName;

    public List<NewWord> words(){
        return getMany(NewWord.class, "Domain");
    }

    public Domain(){
        super();
    }

    public Domain(String name){
        super();
        domainName = name;
    }

    public String getDomainName() {
        return domainName;
    }

    public void setDomainName(String domainName) {
        this.domainName = domainName;
    }
}
