package br.fadep.cadastrocomlista.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import br.fadep.cadastrocomlista.models.Usuario;

public class UsuarioDAO extends DBManager {

    private static final String TABLE_NAME = "usuario";
    public static final String COL_ID = "_id";
    public static final String COL_NOME = "nome";
    public static final String COL_EMAIL = "email";
    public static final String COL_SENHA = "senha";

    private SQLiteDatabase db;

    public UsuarioDAO(Context ctx) {
        super(ctx);
    }

    public static String getSQLCreateTable() {
        StringBuilder sql = new StringBuilder();
        sql.append("create table " + TABLE_NAME + "(");
        sql.append(COL_ID + " integer primary key, ");
        sql.append(COL_NOME + " text, ");
        sql.append(COL_EMAIL + " text, ");
        sql.append(COL_SENHA + " text)");
        return sql.toString();
    }

    public void salvar(Usuario usuario) {
        db = getWritableDatabase();

//        ContentValues values = new ContentValues();
//        values.put(COL_NOME, usuario.getNome());
//        values.put(COL_EMAIL, usuario.getEmail());
//        values.put(COL_SENHA, usuario.getSenha());


        db.insert(TABLE_NAME, null, mount(usuario));
        db.close();
    }

    public Cursor listar() {
        db = getReadableDatabase();

        Cursor cursor = db.query(TABLE_NAME, null, null, null, null, null, null);

        return cursor;
    }

    public Usuario get(int id) {
        db = getReadableDatabase();

//        Cursor cursor = db.query(TABLE_NAME,
//                null,
//                COL_ID + "=?",
//                new String[]{id+""},
//                null,
//                null,
//                null);

        Cursor cursor = db.rawQuery(
                "select * from usuario where _id = ?",
                new String[]{id+""});

        Usuario usuario = new Usuario();
        if (cursor.moveToNext()) {
            usuario.setId(cursor.getInt(cursor.getColumnIndex(COL_ID)));
            usuario.setNome(cursor.getString(cursor.getColumnIndex(COL_NOME)));
            usuario.setEmail(cursor.getString(cursor.getColumnIndex(COL_EMAIL)));
            usuario.setSenha(cursor.getString(cursor.getColumnIndex(COL_SENHA)));
        }
        return usuario;
    }

    public void alterar(Usuario usuario) {
        db = getWritableDatabase();

        String[] paramsWhere = new String[1];
        paramsWhere[0] = usuario.getId() + "";

//        ContentValues values = new ContentValues();
//        values.put(COL_NOME, usuario.getNome());
//        values.put(COL_EMAIL, usuario.getEmail());
//        values.put(COL_SENHA, usuario.getSenha());

        db.update(TABLE_NAME, mount(usuario), COL_ID + " = ?", paramsWhere);
    }

    public ContentValues mount(Usuario usuario) {
        ContentValues values = new ContentValues();
        values.put(COL_NOME, usuario.getNome());
        values.put(COL_EMAIL, usuario.getEmail());
        values.put(COL_SENHA, usuario.getSenha());

        return values;
    }
}
