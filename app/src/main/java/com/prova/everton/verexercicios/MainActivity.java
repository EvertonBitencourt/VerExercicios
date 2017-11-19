package com.prova.everton.verexercicios;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private ExercicioResolvido exercicioResolvido;

    TextView ta;
    TextView tm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Context contexto = getApplicationContext();
        Intent objIntent = new Intent(contexto, ExercicioResolvido.class);
        startService(objIntent);


        ta = (TextView) findViewById(R.id.pergunta);
        tm = (TextView) findViewById(R.id.resposta);


        exercicioResolvido = new ExercicioResolvido();

        try {
            exercicioResolvido.setPergunta(getIntent().getExtras().getString("pergunta"));
            exercicioResolvido.setResposta(getIntent().getExtras().getString("resposta"));
            Log.d("Pergunta MAIN", exercicioResolvido.getPergunta());
            Log.d("Resposta MAIN", exercicioResolvido.getResposta());
            ta.setText(exercicioResolvido.getPergunta());
            tm.setText(exercicioResolvido.getResposta());

        } catch (Exception e) {
            e.printStackTrace();
            vaiWebService();
        }
    }

    private void vaiWebService() {

        String url = "https://approbabilidade.herokuapp.com/exercicioscompletos.php";
        new ListService().execute(url);
    }

    private class ListService extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            try {
                return WebServiceUtil.getContentAsString(urls[0]);
            } catch (IOException e) {
                Log.e("Exception", e.toString());//Observe que aqui uso o log.e e não log.d
                return "Problema ao montar a requisição";
            }
        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            Gson parser = new Gson();
            ExercicioResolvido msg;

            msg = parser.fromJson(result,
                    new TypeToken<ExercicioResolvido>() {
                    }.getType());

            if (msg.getPergunta() != null) {
                exercicioResolvido.setIdExercicio(msg.getIdExercicio());
                exercicioResolvido.setPergunta(msg.getPergunta());
                exercicioResolvido.setResposta(msg.getResposta());
                ta.setText(exercicioResolvido.getPergunta());
                tm.setText(exercicioResolvido.getResposta());
            } else {
                exercicioResolvido.setPergunta("Não perguntas ainda");
                exercicioResolvido.setResposta("");
                Log.d("Pergunta", "NULL");
                ta.setText(exercicioResolvido.getPergunta());
                tm.setText(exercicioResolvido.getResposta());
            }
        }
        }

    public void initService(View v) {

        Context contexto = getApplicationContext();
        Intent objIntent = new Intent(contexto, ExercicioService.class);
        startService(objIntent);

    }
}