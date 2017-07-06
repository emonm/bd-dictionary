package com.at.bd_dictionary.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.at.bd_dictionary.model.Bean;
import com.at.bd_dictionary.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class BookMarksAdapter extends BaseAdapter{
    public static final String FONT = "SolaimanLipi.ttf";

    LayoutInflater inflater;
    private Context mcontext;
    private List<Bean> singleWord = null;
    private ArrayList<Bean> allWords = new ArrayList<Bean>();

    public BookMarksAdapter(Context context, ArrayList<Bean> words) {
        mcontext = context;
        this.singleWord = words;
        inflater = LayoutInflater.from(mcontext);
        this.allWords.addAll(words);
    }

    public class ViewHolder {
        TextView eng_word;
        TextView bang_word;
        // CheckBox check;
    }

    @Override
    public int getCount() {
        return singleWord.size();
    }

    @Override
    public Bean getItem(int position) {
        return singleWord.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.e2b_list_view, null);
            holder.eng_word = (TextView) convertView.findViewById(R.id.view_eng);
            holder.bang_word = (TextView) convertView
                    .findViewById(R.id.view_bang);
            // String str = ((TextView) convertView.findViewById(R.id.txt_eng))
            // .getText().toString();

            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.eng_word.setText(singleWord.get(position).getEngWord());
        holder.bang_word.setText(singleWord.get(position).getBangWord());
        holder.bang_word.setTypeface(Typeface.createFromAsset(
                mcontext.getAssets(), E2BListViewAdapter.FONT));

        return convertView;
    }

    // Filter Class
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        singleWord.clear();
        if (charText.length() == 0) {
            singleWord.addAll(allWords);
        } else {
            for (Bean wd : allWords) {
                if (wd.getEngWord().toLowerCase(Locale.getDefault())
                        .contains(charText)) {
                    singleWord.add(wd);
                }
            }
        }
        notifyDataSetChanged();
    }
    // Filter Class
}