package br.fadep.cadastrocomlista;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;

import br.fadep.cadastrocomlista.adapter.UsuarioAdapter;
import br.fadep.cadastrocomlista.dao.UsuarioDAO;

public class MainActivity extends AppCompatActivity implements ListView.OnItemClickListener {

    private UsuarioDAO dao;
    private UsuarioAdapter adapter;
    private ListView listUsuarios;
    private Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CadastroAct.class);
                startActivityForResult(intent, 0);
            }
        });

        listUsuarios = findViewById(R.id.listUsuarios);
        listUsuarios.setOnItemClickListener(this);
        carregarUsuarios();
    }

    public void carregarUsuarios() {
        try {
            dao = new UsuarioDAO(this);
            cursor = dao.listar();
            adapter = new UsuarioAdapter(this, cursor);
            listUsuarios.setAdapter(adapter);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        cursor = dao.listar();
        adapter.changeCursor(cursor);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int pos, long id) {
        cursor.moveToPosition(pos);
        int idUsuario = cursor.getInt(cursor.getColumnIndex(UsuarioDAO.COL_ID));
        Intent intent = new Intent(this, CadastroAct.class);
        intent.putExtra("id", idUsuario);
        startActivityForResult(intent, 0);
    }
}
