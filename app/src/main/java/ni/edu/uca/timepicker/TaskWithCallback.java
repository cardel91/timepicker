package ni.edu.uca.timepicker;

import android.app.Application;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/**
 * Created by Administrador on 23/11/2016.
 */
public class TaskWithCallback<T1, T2, T3> extends AsyncTask<T1, T2, T3> {
    protected Application app;
    protected Context context;
    protected ArrayList<String> errorMessages;
    protected ProgressDialog progressDialog;
    protected String loadingMessage;
    protected Integer timeout;
    protected Integer timer = 0;
    protected HashMap<String,String> queryParams = new HashMap<String, String>();
    protected StringBuilder query = new StringBuilder();

    public TaskWithCallback(Context context){
        this.context = context;
    }

    public TaskWithCallback(Context context, String loadingMessage){
        this.context = context;
        this.app =  (Application) context.getApplicationContext();
        this.loadingMessage = loadingMessage;
    }

    public Application getApp() {
        return app;
    }

    public void setApp(Application app) {
        this.app = app;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }


    public ArrayList<String> getErrorMessages() {
        return errorMessages;
    }

    public void setErrorMessages(ArrayList<String> errorMessages) {
        this.errorMessages = errorMessages;
    }

    public Integer getTimeout() {
        if(timeout == null)
            timeout = 10000;
        return timeout;
    }

    public void setTimeout(Integer timeout) {
        this.timeout = timeout;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        String message;
        if(loadingMessage != null)
            message = loadingMessage;
        else
            message = "Cargando";
//        this.progressDialog = ProgressDialog.show(context, "", message, true, true, new DialogInterface.OnCancelListener() {
//
//            public void onCancel(DialogInterface dialog) {
//                // TODO Auto-generated method stub
//                progressDialog.dismiss();
//                TaskWithCallback.this.cancel(true);
//            }
//        });
        progressDialog = new ProgressDialog(context);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMax(randInt(9,30));
        progressDialog.setProgress(0);
        progressDialog.setTitle("Progreso...");
        progressDialog.setMessage("Descargado");
        progressDialog.show();
    }

    @Override
    protected T3 doInBackground(T1... params) {
        while (progressDialog.getProgress()<progressDialog.getMax()) {
            try {
                Thread.sleep(Long.valueOf(String.valueOf(randInt(100,3000))));
                publishProgress();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return  null;

    }

    @Override
    protected void onPostExecute(T3 response) {
        super.onPostExecute(response);
        try {
            progressDialog.dismiss();
            Toast.makeText(context,"La tarea ha terminado",Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }

    public static int randInt(int min, int max) {

        // Usually this can be a field rather than a method variable
        Random rand = new Random();

        // nextInt is normally exclusive of the top value,
        // so add 1 to make it inclusive
        int randomNum = rand.nextInt((max - min) + 1) + min;

        return randomNum;
    }



    @Override
    protected void onCancelled() {
        // TODO Auto-generated method stub
        super.onCancelled();
        this.cancel(true);
    }

    @Override
    protected void onProgressUpdate(T2... values) {
        // TODO Auto-generated method stub
        super.onProgressUpdate(values);
        progressDialog.incrementProgressBy(randInt(1,10));
        if(timer.equals(timeout))
            this.cancel(true);
    }


    protected T3 executeSynchronous(T1... params) {
        return null;
    }
}
