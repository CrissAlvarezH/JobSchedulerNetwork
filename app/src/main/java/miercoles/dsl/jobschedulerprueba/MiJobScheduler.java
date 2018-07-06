package miercoles.dsl.jobschedulerprueba;

import android.annotation.SuppressLint;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

@SuppressLint("Registered")
@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class MiJobScheduler extends JobService{
    @Override
    public boolean onStartJob(JobParameters params) {
        Log.v(RedReceiver.TAG, "JobService arrancado");

        Intent intent = new Intent("RED_CAMBIO");

        String mensaje = "";

        if (RedUtils.isOnlineNet())
            mensaje = "Red En Linea";
        else
            mensaje = "Red Fuera de Linea";

        intent.putExtra("red", mensaje);

        LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent);

//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    Thread.sleep(2000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//
//                Log.v(RedReceiver.TAG, "JobService detenido manualmente");
//                jobFinished(params, true);
//            }
//        }).start();

        Log.v(RedReceiver.TAG, "JobService detenido manualmente");
        jobFinished(params, true);

        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        Log.v(RedReceiver.TAG, "JobService onStopJob");
        return false;
    }
}
