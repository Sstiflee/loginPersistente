package adrian.belarte.loginpersistente;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;
import java.util.Base64;

import adrian.belarte.loginpersistente.cofiguracion.Constantes;
import adrian.belarte.loginpersistente.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        sp = getSharedPreferences(Constantes.USUARIOS, MODE_PRIVATE);

        comprobaLogin();

        binding.btnLoginMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(binding.txtUsuarioMain.getText().toString().equalsIgnoreCase("Progresa")
                && binding.txtPasswordMain.getText().toString().equals("Alumno")){
                    //codificar password
                    String codificado = codificarPassword(binding.txtPasswordMain.getText().toString());
                    //editar el fichero
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putString(Constantes.USUARIO, binding.txtUsuarioMain.getText().toString());
                    editor.putString(Constantes.password, binding.txtPasswordMain.getText().toString());
                    editor.apply();

                    startActivity(new Intent(MainActivity.this, SeconActivity.class));
                    binding.txtUsuarioMain.setText("");
                    binding.txtPasswordMain.setText("");
                }else{
                    Toast.makeText(MainActivity.this, "Datos incorrectos", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    //codificar el password
    private String codificarPassword(String texto) {
        String resultado = null;

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {

            try {
                resultado = new String(Base64.getEncoder().encode(texto.getBytes("UTF8")),"UTF-8");
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }
        }

        return String.valueOf(resultado);
    }

    //funcion para ir a la segunda actividad si el login esta hecho
    private void comprobaLogin() {

        if(sp.contains(Constantes.USUARIO)&&
        sp.contains(Constantes.password)){
            startActivity(new Intent(MainActivity.this, SeconActivity.class));
        }
    }
}