package br.fadep.cadastrocomlista;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import java.io.File;

import br.fadep.cadastrocomlista.dao.UsuarioDAO;
import br.fadep.cadastrocomlista.models.Usuario;

public class CadastroAct extends AppCompatActivity implements View.OnClickListener {

    private TextInputEditText edtNome;
    private TextInputEditText edtEmail;
    private TextInputEditText edtSenha;
    private Button btnSalvar;
    public Usuario usuario;
    public UsuarioDAO dao;

    private ImageButton btnImage;
    private File caminhoImagem;

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

        btnImage = findViewById(R.id.btnImagem);
        btnImage.setOnClickListener(this);

        int id = getIntent().getIntExtra("id", -1);
        if (id != -1) {
            usuario = dao.get(id);
            carregarDados();
        } else {
            usuario = new Usuario();
        }

        requestPermissions();
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

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File caminho = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "IMAGENS");
        if (!caminho.exists()) {
            caminho.mkdirs();
        }
        caminhoImagem = new File(caminho, "usuario.jpg");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, getExternalPath(caminhoImagem));
        startActivityForResult(intent, 1);
    }

    private void requestPermissions() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
            == PackageManager.PERMISSION_DENIED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA},
                    2);
        } else if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_DENIED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    2);
        } else if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_DENIED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    2);
        }
    }

    private Uri getExternalPath(File caminhoImagem) {
        return FileProvider.getUriForFile(
                this,
                BuildConfig.APPLICATION_ID + ".fileprovider",
                caminhoImagem
        );
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == 1) {
                Bitmap imagem = BitmapFactory.decodeFile(caminhoImagem.getPath());
                Bitmap thumbnail = ThumbnailUtils.extractThumbnail(imagem, 400, 400);
                btnImage.setImageBitmap(thumbnail);
            }
        }
    }
}
