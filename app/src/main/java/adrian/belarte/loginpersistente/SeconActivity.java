package adrian.belarte.loginpersistente;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import java.io.UnsupportedEncodingException;
import java.util.Base64;

import adrian.belarte.loginpersistente.cofiguracion.Constantes;
import adrian.belarte.loginpersistente.databinding.ActivitySeconBinding;

public class SeconActivity extends AppCompatActivity {
  private ActivitySeconBinding binding;
  private SharedPreferences sp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySeconBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        sp = getSharedPreferences(Constantes.USUARIOS, MODE_PRIVATE); //para acceder al fichero
        //obtener la informacion de la etiqueta y mostrarla
        binding.lbNombreSecond.setText(sp.getString(Constantes.USUARIO,""));
        String password = descodificarPassword(sp.getString(Constantes.password,""));
        binding.lbpasswordSecond.setText(password);

        binding.btnLogOutSecond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sp.edit();
                editor.clear();
                editor.apply();
                finish();
            }
        });
    }

    private String descodificarPassword(String texto) {
        String resultado = null;

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            try {
                resultado = new String(Base64.getDecoder().decode(texto.getBytes("UTF8")),"UTF-8");
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }
        }
        return String.valueOf(resultado);
    }
}