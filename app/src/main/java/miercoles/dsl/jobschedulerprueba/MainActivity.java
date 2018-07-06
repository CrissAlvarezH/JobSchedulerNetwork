package miercoles.dsl.jobschedulerprueba;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private int contadorReceived = 1;

    private TextView txtTexto;
    private TextView txtEspera;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtTexto = findViewById(R.id.texto);
        txtEspera = findViewById(R.id.txt_espera);

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("RED_CAMBIO");
        intentFilter.addAction("ESPERA_CONEXION");
        LocalBroadcastManager.getInstance(this).registerReceiver(new MiReceiver(), intentFilter);

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            JobScheduler jobScheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);

            ComponentName jobServiceName = new ComponentName(getPackageName(), MiJobScheduler.class.getName());

            JobInfo jobInfo = new JobInfo.Builder(1, jobServiceName)
                    .setRequiredNetworkType( JobInfo.NETWORK_TYPE_UNMETERED )
                    .build();

            if(jobScheduler != null) {
                int jobId = jobScheduler.schedule(jobInfo);// iniciamos el servicio

                Log.v(RedReceiver.TAG, "JobId: "+jobId);
            }else{
                Log.e(RedReceiver.TAG, "JobScheduler nulo");
            }
        }
    }

    private class MiReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent == null) return;

            switch (intent.getAction()){
                case "RED_CAMBIO":
                    if(!isFinishing()) {
                        String mensaje = intent.getStringExtra("red");

                        Log.v(RedReceiver.TAG, "mensaje recivido " + mensaje);

                        txtTexto.setText(mensaje + " (" + contadorReceived + ")");

                        contadorReceived++;
                    }
                    break;

                case "ESPERA_CONEXION":
                    if(!isFinishing()) {
                        String tiempo = intent.getStringExtra("espera");
                        Log.v(RedReceiver.TAG, "tiempo " + tiempo);

                        txtEspera.setText(tiempo);
                    }
                    break;
            }
        }
    }
}
