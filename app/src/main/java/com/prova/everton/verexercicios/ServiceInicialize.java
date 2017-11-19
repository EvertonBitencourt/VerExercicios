package com.prova.everton.verexercicios;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by Everton on 19/11/2017.
 */

public class ServiceInicialize extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            intent = new Intent(context, ExercicioService.class);//explicita
            context.startService(intent);
            Log.d("SERVICE_INIT","SERVICO BISCO DA SORTE INICIADO");
        }
 }

