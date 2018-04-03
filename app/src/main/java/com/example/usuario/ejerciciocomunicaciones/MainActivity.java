package com.example.usuario.ejerciciocomunicaciones;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.MovementMethod;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class MainActivity extends AppCompatActivity {

    private TextView tvPagina;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvPagina = findViewById(R.id.tvPagina);
        tvPagina.setMovementMethod(new ScrollingMovementMethod() {
        });
        findViewById(R.id.boton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //método de respuesta al click del botón
                //iniciar tarea en segundo plano
                ComunicacionTask com=new ComunicacionTask();
                //le pasa como parámetro la dirección
                //de la página
                com.execute("http://www.google.es");
            }
        });
    }
    //el primer tipo es lo que recibirá el doInBackGround,
    // que en nuestro caso es la dirección a la que se
    // tiene que conectar. El tercer tipo
    // es el resultado que entregará la tarea, y que en este caso
    // es la página recuperada
    private class ComunicacionTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            String pagina="";
            try{ URL url=new URL(params[0]);
            URLConnection con=url.openConnection();
            //recuperacion de la página línea a línea
             String s;
             InputStream is=con.getInputStream();
             BufferedReader bf=new BufferedReader( new InputStreamReader(is));
             while((s=bf.readLine())!=null){
                 pagina+=s;
             }
            } catch(IOException ex){
             ex.printStackTrace();
            }
            return pagina;
        }
        @Override
        protected void onPostExecute(String result) {
        //volcar el resultado en el TextView
            tvPagina.setText(result);
        }
    }
}


