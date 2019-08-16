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

import br.fadep.cadastrocomlista.models.Usuario;

public class CadastroAct extends AppCompatActivity {

    private TextInputEditText edtNome;
    private TextInputEditText edtEmail;
    private TextInputEditText edtSenha;
    private Button btnSalvar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);
        Toolbar toolbar = findViewById(R.id.toolbar_act);
        setSupportActionBar(toolbar);


        edtNome = findViewById(R.id.edtNome);
        edtEmail = findViewById(R.id.edtEmail);
        edtSenha = findViewById(R.id.edtSenha);

        btnSalvar = findViewById(R.id.btnSalvar);
    }

    public void salvar(View v) {
        Usuario usuario = new Usuario();
        if (edtNome.getText().toString().equals("")) {
            edtNome.setError("Campo inválido");
            edtNome.requestFocus();
        }
        usuario.setNome(edtNome.getText().toString());
        usuario.setEmail(edtEmail.getText().toString());
        usuario.setSenha(edtSenha.getText().toString());

        Snackbar bar = Snackbar.make(v, "Clicou em salvar", 20);
        bar.show();
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
