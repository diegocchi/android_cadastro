package br.fadep.cadastrocomlista.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBManager extends SQLiteOpenHelper {

    private static final String DB_NAME = "cadastro";
    private static final int DB_VERSION = 1;

    public DBManager(Context ctx) {
        super(ctx, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(UsuarioDAO.getSQLCreateTable());
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
