package com.example.qthjen.mynote.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

public class DataBase extends SQLiteOpenHelper {

    public DataBase(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public void QueryData(String sql) {
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL(sql);
    }

    public void INSERT_ITEMS(String title, String note, String date) {

        SQLiteDatabase database = getWritableDatabase();
        String sql = "INSERT INTO Notes VALUES(null, ?, ?, ?)";
        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();

        statement.bindString(1, title);
        statement.bindString(2, note);
        statement.bindString(3, date);

        statement.executeInsert();

    }

    public void INSERT_NOTEIMAGE(String title, String note, String date, byte[] image) {

        SQLiteDatabase database = getWritableDatabase();
        String sql = "INSERT INTO Notesimage VALUES(null, ?,?,?,?)";
        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();

        statement.bindString(1, title);
        statement.bindString(2, note);
        statement.bindString(3, date);
        statement.bindBlob(4, image);

        statement.executeInsert();

    }

    public Cursor GetData(String sql) {
        SQLiteDatabase database = getReadableDatabase();
        return database.rawQuery(sql, null);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}