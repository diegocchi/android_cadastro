package br.fadep.cadastrocomlista.services;

import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import br.fadep.cadastrocomlista.models.Usuario;

public class UsuarioService extends AsyncTask<Void, Void, Void> {

    private final Usuario usuario;

    public UsuarioService(Usuario usuario) {
        this.usuario = usuario;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        try {
            URL url = new URL("http://192.168.38.154:8081/Pedidos/rest/usuario/salvar");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);

            DataOutputStream out = new DataOutputStream(conn.getOutputStream());
            JSONObject obj = new JSONObject();
            obj.put("id", this.usuario.getId());
            obj.put("nome", this.usuario.getNome());
            obj.put("email", this.usuario.getEmail());
            obj.put("senha", this.usuario.getSenha());
            obj.put("imagem", this.usuario.getImagem());

            out.writeBytes(obj.toString(4));
            out.flush();
            out.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
