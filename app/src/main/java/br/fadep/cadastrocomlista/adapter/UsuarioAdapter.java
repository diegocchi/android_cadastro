package br.fadep.cadastrocomlista.adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import br.fadep.cadastrocomlista.R;
import br.fadep.cadastrocomlista.dao.UsuarioDAO;

public class UsuarioAdapter extends CursorAdapter {

    private LayoutInflater inflater;

    public UsuarioAdapter(Context ctx, Cursor cursor) {
        super(ctx, cursor, false);
        inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        return inflater.inflate(R.layout.list_item_usuario, null);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView txtNome = view.findViewById(R.id.txtNome);
        TextView txtEmail = view.findViewById(R.id.txtEmail);

        String nome = cursor.getString(cursor.getColumnIndex(UsuarioDAO.COL_NOME));
        txtNome.setText(nome);

        String email = cursor.getString(cursor.getColumnIndex(UsuarioDAO.COL_EMAIL));
        txtEmail.setText(email);
    }
}
