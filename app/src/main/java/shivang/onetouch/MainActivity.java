package shivang.onetouch;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.DialogFragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;

import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;

import Object_Structure.NYTimes_Object;
import Object_Structure.URL_List;
import Remote_API.NYTimes_API;


public class MainActivity extends ActionBarActivity implements NavigationView.OnNavigationItemSelectedListener {

    private LinearLayoutManager lLayout;
    private RecyclerView rView;
    private static Context context;
    private static int cFlag=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.super_activity);
        setTitle(null);
        context=getBaseContext();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        ProgressBar pBar=(ProgressBar)findViewById(R.id.progressBar2);
        pBar.setVisibility(View.VISIBLE);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        //navigationView.setSelected(true);
        navigationView.setNavigationItemSelectedListener(this);

        lLayout = new LinearLayoutManager(MainActivity.this);

        rView = (RecyclerView) findViewById(R.id.recycler_view);
        rView.setLayoutManager(lLayout);
        Log.v("LL Resp : ", "1");


        Runnable show_toast = new Runnable()
        {
            public void run()
            {
                Toast.makeText(MainActivity.this, "Internet is off! Redidirecting", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS));
            }
        };

        if(!isOnline()) {
            show_toast.run();
        }

        String URL= URL_List.US;
        new SearchNYTimes().executeOnExecutor(AsyncTask.SERIAL_EXECUTOR,URL,null);
        navigationView.getMenu().findItem(R.id.nav_us).setChecked(true);
        toolbar.setTitle("US News");

    }

    public void onWindowFocusChanged(boolean hasFocus) {

        super.onWindowFocusChanged(hasFocus);

        if(hasFocus)
        {
            String URL = "https://api.rss2json.com/v1/api.json?rss_url=http%3A%2F%2Frss.nytimes.com%2Fservices%2Fxml%2Frss%2Fnyt%2FUS.xml";
            new SearchNYTimes().executeOnExecutor(AsyncTask.SERIAL_EXECUTOR, URL, null);
        }
        else
            MainActivity.super.onPause();
    }

    public static Context getContext(){

        return context;
    }

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnected();
    }

        private class SearchNYTimes extends AsyncTask<String,ArrayList<NYTimes_Object>,Void> {

        @Override
        protected Void doInBackground(String... params) {

            NYTimes_API apiCall=new NYTimes_API();
            try {
                ArrayList<NYTimes_Object> aList=apiCall.searchAPI(params[0]);
                Log.v("Search URL : ",params[0]);
                publishProgress(aList);

            } catch (JSONException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            Log.v("Server Response: ","Search");
            return null;
        }


        protected void onProgressUpdate(ArrayList<NYTimes_Object>... aList) {

            ProgressBar pBar=(ProgressBar)findViewById(R.id.progressBar2);
            if(aList[0] == null){
                Runnable show_toast = new Runnable()
                {
                    public void run()
                    {
                        Toast.makeText(MainActivity.this, "Internet is off! Redidirecting", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS));
                    }
                };

                if(!isOnline()) {
                    show_toast.run();
                }
            }else {
                Log.v("LL Resp : ", "2");
                RecyclerViewAdapter rcAdapter = new RecyclerViewAdapter(MainActivity.this, aList[0]);
                rView.animate();
                rView.setAdapter(rcAdapter);
                Log.v("LL Resp : ", "3");
                pBar.setVisibility(View.INVISIBLE);
            }
        }
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

        //Uncheck
        navigationView.getMenu().findItem(R.id.nav_us).setChecked(false);
        navigationView.getMenu().findItem(R.id.nav_world).setChecked(false);
        navigationView.getMenu().findItem(R.id.nav_business).setChecked(false);
        navigationView.getMenu().findItem(R.id.nav_sports).setChecked(false);
        navigationView.getMenu().findItem(R.id.nav_technology).setChecked(false);
        navigationView.getMenu().findItem(R.id.nav_fashion).setChecked(false);

        if (id == R.id.nav_us) {
            navigationView.getMenu().findItem(R.id.nav_us).setChecked(true);
            String URL= URL_List.US;
            ProgressBar pBar=(ProgressBar)findViewById(R.id.progressBar2);
            pBar.setVisibility(View.VISIBLE);
            new SearchNYTimes().executeOnExecutor(AsyncTask.SERIAL_EXECUTOR,URL,null);
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            toolbar.setTitle("US News");
        } else if (id == R.id.nav_world) {
            navigationView.getMenu().findItem(R.id.nav_world).setChecked(true);
            String URL=URL_List.WORLD;
            ProgressBar pBar=(ProgressBar)findViewById(R.id.progressBar2);
            pBar.setVisibility(View.VISIBLE);
            new SearchNYTimes().executeOnExecutor(AsyncTask.SERIAL_EXECUTOR,URL,null);
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            toolbar.setTitle("World");
        } else if (id == R.id.nav_business) {
            navigationView.getMenu().findItem(R.id.nav_business).setChecked(true);
            String URL=URL_List.BUSINESS;
            ProgressBar pBar=(ProgressBar)findViewById(R.id.progressBar2);
            pBar.setVisibility(View.VISIBLE);
            new SearchNYTimes().executeOnExecutor(AsyncTask.SERIAL_EXECUTOR,URL,null);
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            toolbar.setTitle("Business");
        } else if (id == R.id.nav_technology) {
            navigationView.getMenu().findItem(R.id.nav_technology).setChecked(true);
            String URL=URL_List.TECHNOLOGY;
            ProgressBar pBar=(ProgressBar)findViewById(R.id.progressBar2);
            pBar.setVisibility(View.VISIBLE);
            new SearchNYTimes().executeOnExecutor(AsyncTask.SERIAL_EXECUTOR,URL,null);
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            toolbar.setTitle("Technology");
        } else if (id == R.id.nav_sports) {
            navigationView.getMenu().findItem(R.id.nav_sports).setChecked(true);
            String URL=URL_List.SPORTS;
            ProgressBar pBar=(ProgressBar)findViewById(R.id.progressBar2);
            pBar.setVisibility(View.VISIBLE);
            new SearchNYTimes().executeOnExecutor(AsyncTask.SERIAL_EXECUTOR,URL,null);
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            toolbar.setTitle("Sports");
        }
        else if (id == R.id.nav_fashion) {
            navigationView.getMenu().findItem(R.id.nav_fashion).setChecked(true);
            String URL=URL_List.FASHION;
            ProgressBar pBar=(ProgressBar)findViewById(R.id.progressBar2);
            pBar.setVisibility(View.VISIBLE);
            new SearchNYTimes().executeOnExecutor(AsyncTask.SERIAL_EXECUTOR,URL,null);
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            toolbar.setTitle("Fashion");
        }

        lLayout = new LinearLayoutManager(MainActivity.this);
        rView = (RecyclerView)findViewById(R.id.recycler_view);
        rView.setLayoutManager(lLayout);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}
