package com.prova.everton.verexercicios;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;



/**
 * Created by Everton on 19/11/2017.
 */

public class ExercicioService extends Service {

    private Timer timerAtual = new Timer();
    private TimerTask task;
    private final Handler handler = new Handler();

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        Log.v("TAG_PERGUNTA","SERVICE - onCreate()");
        super.onCreate();
    }


    @Override
    public int onStartCommand(final Intent intent, int flags, int startId){
        Log.i("TAG_PERGUNTA", "onStartCommand()");


        //Wtf?
        task = new TimerTask() {
            public void run() {
                handler.post(new Runnable() {
                    public void run() {

                        vaiWebService();
                    }
                });
            }};

        timerAtual.schedule(task, 300, 60000);


        return(super.onStartCommand(intent, flags, startId));//Continua ciclo de vida do meu serviço
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
                    new TypeToken<ExercicioResolvido>(){}.getType());


            try {
                if(msg != null) {
                    if (msg.getPergunta() != null) {
                        Bundle bundle = new Bundle();
                        bundle.putInt("ID:", msg.getIdExercicio());
                        bundle.putString("Pergunta", msg.getPergunta());
                        bundle.putString("Resposta", msg.getResposta());
                        Context contexto = getApplicationContext();
/*                        Intent objIntent = new Intent(contexto, NotificationTrigger.class);
                        objIntent.putExtras(bundle);
                        contexto.sendBroadcast(objIntent);*/
                    } else {
                        Log.d("Pergunta", "NULL");
                    }
                }

            } catch (Exception e){
                e.printStackTrace();
            }


        }


    }

}
