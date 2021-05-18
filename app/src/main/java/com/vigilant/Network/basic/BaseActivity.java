package com.vigilant.Network.basic;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


public class BaseActivity extends AppCompatActivity {
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        progressDialog = new ProgressDialog(BaseActivity.this);
    }

    public void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public void hideLoader() {
        try {
            if(null != progressDialog)
                progressDialog.dismiss();
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public void showLoader() {
        try{
            progressDialog.show();
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public void showLog(String tag, String msg) {
        Log.v(tag,msg);
    }

    public void showDailogForError(String msg) {
    }





    public static boolean isServiceRunning(Context mContext, Class<?> serviceClass) {

        ActivityManager manager = (ActivityManager) mContext.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())
                    && service.pid != 0) {
                //ShowLog("", "ser name "+serviceClass.getName());
                return true;
            }
        }
        return false;
    }
}
