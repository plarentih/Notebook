package com.enterprise.lu.uni.notebook.app.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.enterprise.lu.uni.notebook.app.model.NewWord;
import com.enterprise.lu.uni.notebook.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by Plarent on 10/21/2017.
 */

public class NewWordAdapter extends ArrayAdapter<NewWord> {

    private ArrayList<NewWord> newWordList = new ArrayList<>();

    private static class ViewHolder{
        TextView newWord;
        TextView translatedWord;
        TextView domainSpinner;
    }
    public NewWordAdapter(Context context, List<NewWord> wordList){
        super(context, 0, wordList);
        newWordList.addAll(wordList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        NewWord newWord = newWordList.get(position);
        ViewHolder viewHolder;
        if(convertView == null){
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.new_word_row, parent, false);
            viewHolder.newWord = (TextView) convertView.findViewById(R.id.newWordTextView);
            viewHolder.translatedWord = (TextView) convertView.findViewById(R.id.translatedTextView);
            viewHolder.domainSpinner = (TextView) convertView.findViewById(R.id.domainTextView);
            convertView.setTag(viewHolder);
        }else {
                viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.newWord.setText(newWord.getWord());
        viewHolder.translatedWord.setText(newWord.getTranslation());
        if((newWord.getDomain()) == null){

        }else {
            viewHolder.domainSpinner.setText(newWord.getDomain().getDomainName());
        }
        return convertView;
    }
}
