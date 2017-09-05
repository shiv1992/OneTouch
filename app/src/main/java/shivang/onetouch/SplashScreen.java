package shivang.onetouch;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ProgressBar;

/**
 * Created by SHIVVVV on 4/27/2017.
 */
public class SplashScreen extends ActionBarActivity {

    private static int SPLASH_TIME_START=1500;
    private static int ctr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
        setContentView(R.layout.splash_screen);

        ProgressBar pBar=(ProgressBar)findViewById(R.id.progressBar);

        ctr=0;

        while(ctr<=5){
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    ProgressBar pBar=(ProgressBar)findViewById(R.id.progressBar);
                    pBar.setProgress(ctr*20);
                    Log.v("LL: ",String.valueOf(ctr*20));
                }
            },1000);

            ctr++;
        }


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent newIntent=new Intent(SplashScreen.this,MainActivity.class);
                startActivity(newIntent);
                finish();
            }
        },SPLASH_TIME_START);


    }
}
