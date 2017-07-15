package com.at.bd_dictionary.activity;

import android.app.Activity;
import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;
import android.widget.TextView;

import com.at.bd_dictionary.R;
import com.at.bd_dictionary.adapter.E2BListViewAdapter;
import com.at.bd_dictionary.db.DictionaryDBOpenHelper;
import com.at.bd_dictionary.model.Dictionary;

import java.util.ArrayList;


public class MainActivity extends Activity implements OnQueryTextListener {
    DictionaryDBOpenHelper dbHelper;;
    public static final String FONT = "SolaimanLipi.ttf";
    SharedPreferences preferences;
    private static final String myPreference = "Mypreference";
    private boolean isSelect = false;
    private boolean isChackBookmarks = false;
    private boolean status = false;
    SearchView textSearch;
    ListView lv;
    final String[] fontsSize = {"Small", "Medium", "Large", "Extra Large"};
    ArrayAdapter<String> adapter;
    AlertDialog a;
    ArrayList<Dictionary> wordList;
    E2BListViewAdapter listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_main);
        textSearch = (SearchView) findViewById(R.id.etSearch);
        lv = (ListView) findViewById(R.id.dictionaryList);
        createFontAlert();
        dbHelper = DictionaryDBOpenHelper.getInstance(getApplicationContext());
        wordList = dbHelper.getAllwords();

        loadE2BListView();

        lv.setOnItemLongClickListener(new OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> adapter, View v,
                                           int position, long id) {
                String word_eng = wordList.get(position).getEngWord();
                String word_bang = wordList.get(position).getBangWord();
                Dictionary b = new Dictionary(word_eng, word_bang);
                openAlert();
                if (isChackBookmarks) {

                }
                return true;
            }
        });

        setStatus();

        textSearch.setOnQueryTextListener(this);
    }

    public void openAlert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

        builder.setTitle("Add Bookmarks");
        builder.setMessage("do you add bookmarks...");

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                isChackBookmarks = true;

            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                isChackBookmarks = false;
            }
        });

        AlertDialog alertDialog = builder.create();

        alertDialog.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.speeling:
                Intent intent3 = new Intent(MainActivity.this,
                        TextSpeelingActivity.class);
                Bundle banAnimation2 = ActivityOptions.makeCustomAnimation(
                        getApplicationContext(), R.anim.animation_next,
                        R.anim.animaton_pre).toBundle();
                startActivity(intent3, banAnimation2);
                return true;
            case R.id.action_font:
                a.show();
                break;

            case R.id.action_exit:
                finish();
                break;

        }
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        return true;
    }

    public void loadE2BListView() {
        status = true;
        isSelect = true;
        listAdapter = new E2BListViewAdapter(getApplicationContext(), wordList);
        lv.setAdapter(listAdapter);

    }

    @Override
    public boolean onQueryTextChange(String newText) {
        if (isSelect) {
            listAdapter.getFilter().filter(newText);

        }
        return false;
    }

    @Override
    public boolean onQueryTextSubmit(String text) {
        return false;
    }

    public void createFontAlert() {
        adapter = new ArrayAdapter<String>(MainActivity.this,
                android.R.layout.select_dialog_item, fontsSize);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setIcon(R.drawable.ic_launcher);
        builder.setTitle("Select Font Size");
        builder.setAdapter(adapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                preferences = getSharedPreferences(myPreference, MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();

                editor.putString("select_fonts", fontsSize[which]);
                editor.commit();

                if (isSelect) {
                    loadE2BListView();
                } else {

                }
            }
        });
        a = builder.create();
    }

    public void setStatus() {
        preferences = getSharedPreferences(myPreference, MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("status", status);
        editor.commit();
    }



}