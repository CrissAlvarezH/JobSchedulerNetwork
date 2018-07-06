package miercoles.dsl.jobschedulerprueba;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

public class RedReceiver extends BroadcastReceiver {
    public static String TAG = "ReciverDeRed";

    @Override
    public void onReceive(final Context context, Intent intent) {
        if( !RedUtils.estaNetDisponible(context) ){
            Log.e(TAG, "estaNetDisponible: false");

            Intent intentRed = new Intent("RED_CAMBIO");
            intentRed.putExtra("red", "Red no disponible");
            LocalBroadcastManager.getInstance(context.getApplicationContext()).sendBroadcast(intentRed);

            return;
        }


        new Thread(new Runnable() {

            @Override
            public void run() {
//                for(int i=0; i<=1; i++) {
//                    Intent intentEspera = new Intent("ESPERA_CONEXION");
//                    intentEspera.putExtra("espera", "Esperando "+i+" segundos");
//                    LocalBroadcastManager.getInstance(context.getApplicationContext()).sendBroadcast(intentEspera);
//
//                    try {
//                        // esperamos i segundo para que establesca conexion
//                        Thread.sleep(1000);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }

                Intent intentRed = new Intent("RED_CAMBIO");

                String mensaje = "";

                if (RedUtils.isOnlineNet())
                    mensaje = "Red En Linea";
                else
                    mensaje = "Red Fuera de Linea";


                intentRed.putExtra("red", mensaje);
                LocalBroadcastManager.getInstance(context.getApplicationContext()).sendBroadcast(intentRed);
            }
        }).start();

    }
}
