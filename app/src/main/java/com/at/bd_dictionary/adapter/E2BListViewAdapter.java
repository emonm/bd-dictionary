package com.at.bd_dictionary.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;


import com.at.bd_dictionary.R;
import com.at.bd_dictionary.model.Dictionary;

import java.util.ArrayList;
import java.util.Locale;

public class E2BListViewAdapter extends BaseAdapter implements Filterable{
    public static final String FONT = "SolaimanLipi.ttf";

    SharedPreferences preferences;
    Context context;
    private static final String myPreference = "Mypreference";
    private static final int VR_REQUEST = 999;

    private int MY_DATA_CHECK_CODE = 0;

    public TextToSpeech tts;
    ArrayList<Dictionary> wordLists;
    ArrayList<Dictionary> searchWorld;
    WordFilter valueFilter;

    public E2BListViewAdapter(Context context, ArrayList<Dictionary> words) {
        this.context = context;
        this.wordLists = words;
        this.searchWorld = words;
    }

    @Override
    public int getCount() {

        return wordLists.size();
    }

    @Override
    public Object getItem(int position) {
        return wordLists.get(position);
    }

    private void speakOut(String text) {

        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
    }

    public class ViewHolder {
        TextView eng_word, bang_word;
        ImageButton imageButton;

    }

    @Override
    public long getItemId(int position) {
        return wordLists.indexOf(getItem(position));
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.e2b_list_view, null);
            holder.eng_word = (TextView) convertView.findViewById(R.id.view_eng);
            holder.bang_word = (TextView) convertView.findViewById(R.id.view_bang);
            holder.imageButton = (ImageButton) convertView.findViewById(R.id.soundButton);
            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.eng_word.setText(wordLists.get(position).getEngWord());
        holder.bang_word.setText(wordLists.get(position).getBangWord());
        holder.bang_word.setTypeface(Typeface.createFromAsset(
                context.getAssets(), E2BListViewAdapter.FONT));

        if (getFonts().equals("Small")) {
            holder.eng_word.setTextSize(15);
            holder.bang_word.setTextSize(15);
        } else if (getFonts().equals("Medium")) {
            holder.eng_word.setTextSize(18);
            holder.bang_word.setTextSize(18);
        } else if (getFonts().equals("Large")) {
            holder.eng_word.setTextSize(21);
            holder.bang_word.setTextSize(21);
        } else if (getFonts().equals("Extra Large")) {
            holder.eng_word.setTextSize(24);
            holder.bang_word.setTextSize(24);
        }


        holder.imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tts = new TextToSpeech(context, new TextToSpeech.OnInitListener() {
                    @Override
                    public void onInit(int status) {
                        if (status == TextToSpeech.SUCCESS){
                            int result = tts.setLanguage(Locale.ENGLISH);
                            Dictionary word_eng = wordLists.get(position);
                            result = tts.setLanguage(Locale.ENGLISH);

                            String toSpeak = word_eng.getEngWord();
                            Log.w("Dictinary Data:", toSpeak);
                            tts.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
                        }else{
                            Toast.makeText(context, "Not Supported in your Device", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        return convertView;
    }


    @Override
    public Filter getFilter() {
        if (valueFilter == null) {
            valueFilter = new WordFilter();
        }
        return valueFilter;
    }

    private class WordFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            if (constraint != null && constraint.length() > 0) {
                ArrayList<Dictionary> filterList = new ArrayList<Dictionary>();
                for (int i = 0; i < searchWorld.size(); i++) {
                    if ((searchWorld.get(i).getEngWord().toLowerCase())
                            .contains(constraint.toString().toLowerCase())) {
                        Dictionary words = new Dictionary(searchWorld.get(i).getEngWord(),
                                searchWorld.get(i).getBangWord());
                        filterList.add(words);
                    }
                }
                results.count = filterList.size();
                results.values = filterList;
            } else {
                results.count = searchWorld.size();
                results.values = searchWorld;
            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            wordLists = (ArrayList<Dictionary>) results.values;
            notifyDataSetChanged();
        }

    }

    public String getFonts() {
        preferences = context.getSharedPreferences(myPreference, Context.MODE_PRIVATE);
        String tempFonts;
        String orginalFonts = "";
        tempFonts = preferences.getString("select_fonts", "");
        if (tempFonts != null && !tempFonts.equals(""))
            orginalFonts = tempFonts;
        Log.d("Disctionary adapter......... ", orginalFonts);
        return orginalFonts;
    }
}