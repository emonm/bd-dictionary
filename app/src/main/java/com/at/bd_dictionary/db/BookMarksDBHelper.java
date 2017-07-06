package com.at.bd_dictionary.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.at.bd_dictionary.model.Bean;

import java.util.ArrayList;

public class BookMarksDBHelper extends SQLiteOpenHelper {

    private static BookMarksDBHelper dbInstance;
    ArrayList<Bean> addBookMarkListWords;
    private static final String DB_NAME = "bookmarksdb";
    private static final int DB_VERSION = 1;

    private static final String TABLE_NAME = "bookmarks";
    public static final String ID = "_id";
    public static final String ENGLISH_WORD = "engword";
    public static final String BANGLA_WORD = "bnword";
    public static final String DICTIONARY_STATUS = "dic_status";

    public static final String STUDENT_TABLE_SQL = "CREATE TABLE " + TABLE_NAME
            + " (" + ID + " INTEGER PRIMARY KEY, " + ENGLISH_WORD + " TEXT, "
            + BANGLA_WORD + " TEXT, " + DICTIONARY_STATUS + " TEXT)";

    private BookMarksDBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);

        // TODO Auto-generated constructor stub
    }

    public static BookMarksDBHelper getDbHelperInstance(Context context) {
        if (dbInstance == null) {
            dbInstance = new BookMarksDBHelper(context);
        }
        return dbInstance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(STUDENT_TABLE_SQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    // insert Item table

    public void insertBookMarks(Bean marks) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ENGLISH_WORD, marks.getEngWord());
        values.put(BANGLA_WORD, marks.getBangWord());
        db.insert(TABLE_NAME, null, values);

        db.close();
    }

    // data fatch table

    public ArrayList<Bean> getAllBooksMarks() {
        ArrayList<Bean> allWords = new ArrayList<Bean>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, null, null, null, null, null,
                null, null);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            for (int i = 0; i < cursor.getCount(); i++) {
                String eng = cursor.getString(cursor
                        .getColumnIndex(ENGLISH_WORD));
                String bang = cursor.getString(cursor
                        .getColumnIndex(BANGLA_WORD));
                Bean e = new Bean(eng, bang);
                allWords.add(e);
                cursor.moveToNext();

            }
        }
        cursor.close();
        db.close();
        return allWords;

    }

    public void removeBookMarks(String del) {

        // get reference of the bookmarks database
        SQLiteDatabase db = this.getWritableDatabase();

        // delete word
        db.delete(TABLE_NAME, ENGLISH_WORD + " = ?", new String[] { ""
                + del });
        db.close();
    }
}