package br.fadep.cadastrocomlista;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import br.fadep.cadastrocomlista.dao.UsuarioDAO;
import br.fadep.cadastrocomlista.models.Usuario;

public class CadastroAct extends AppCompatActivity {

    private TextInputEditText edtNome;
    private TextInputEditText edtEmail;
    private TextInputEditText edtSenha;
    private Button btnSalvar;
    public Usuario usuario;
    public UsuarioDAO dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        dao = new UsuarioDAO(this);

        edtNome = findViewById(R.id.edtNome);
        edtEmail = findViewById(R.id.edtEmail);
        edtSenha = findViewById(R.id.edtSenha);


        int id = getIntent().getIntExtra("id", -1);
        if (id != -1) {
            usuario = dao.get(id);
            carregarDados();
        } else {
            usuario = new Usuario();
        }
    }

    public void carregarDados() {
        edtNome.setText(usuario.getNome());
        edtEmail.setText(usuario.getEmail());
        edtSenha.setText(usuario.getSenha());
    }

    public void salvar(View v) {

        if (edtNome.getText().toString().equals("")) {
            edtNome.setError("Campo inválido");
            edtNome.requestFocus();
        }
        usuario.setNome(edtNome.getText().toString());
        usuario.setEmail(edtEmail.getText().toString());
        usuario.setSenha(edtSenha.getText().toString());

        if (usuario.getId() == 0) {
            dao.salvar(usuario);
        } else {
            dao.alterar(usuario);
        }

        Snackbar bar = Snackbar.make(v, "Clicou em salvar", 20);
        bar.show();
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_act_cad_usuario, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.action_cancelar:
                finish();
                break;
            case R.id.action_salvar:
                salvar(getWindow().getDecorView().getRootView());
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
