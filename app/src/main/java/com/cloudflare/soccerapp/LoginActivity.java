package com.cloudflare.soccerapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.R.attr.button;
import static android.R.attr.editTextBackground;
import static com.cloudflare.soccerapp.R.id.editPassword;

public class LoginActivity extends AppCompatActivity {

    // CONNECTION_TIMEOUT and READ_TIMEOUT are in milliseconds
    public static final int CONNECTION_TIMEOUT=10000;
    public static final int READ_TIMEOUT=15000;

    Button Submit, Cancel;
    EditText username, password;
    RequestQueue requestQueue;
    RecyclerView mRecyclerView;
    LinearLayoutManager mLayoutManager;
    CommentViewAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.comments_layout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Motaung.....");
        mRecyclerView = (RecyclerView)findViewById(R.id.my_recycler_comment);
        mRecyclerView.setHasFixedSize(true);
        List<commentObject> myDataset = getAllItemList();

        mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new CommentViewAdapter(getApplicationContext(),myDataset);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(),mLayoutManager.getOrientation()));
        mRecyclerView.setAdapter(mAdapter);

    }
    private List<commentObject> getAllItemList(){

        List<commentObject> allItems = new ArrayList<commentObject>();
        allItems.add(new commentObject("17:00","Vim cu veritus accumsan. Eu pri quando nullam splendide, cu nisl dicat noluisse vis, unum wisi minimum eu has. Est justo nonumy ex, sed nobis assueverit no. Ad ius regione apeirian, qui ut quas pertinax quaerendum, dico percipit mel ut. Nec in cibo virtute. Quo no quis impedit. Accusata evertitur consequat eu eum."));
        allItems.add(new commentObject("20:25","Lorem ipsum dolor sit amet, tation volutpat id pri, vel an lorem simul graece. Ne sed lorem nemore honestatis, quo ex sonet mediocrem. Nullam tractatos salutatus sit ut, ludus honestatis qui ei. Quo error feugait reprimique an, nulla legimus detraxit an eam. Partem mandamus electram has ea, denique fierent per ex. Ei quo menandri instructior, ei graeco labitur eloquentiam vim."));
        allItems.add(new commentObject("23:50","Summo efficiantur pri et. Ferri fierent id est, per augue soluta scaevola ea. Quodsi assueverit sea no, mea ne mediocrem definiebas comprehensam, mundi numquam propriae sed eu. Et porro clita usu, duo an nisl iisque civibus, diam vivendum pro ex. Id amet unum altera sit."));
        allItems.add(new commentObject("07:10","Id iudico postea aliquando has, porro impedit recusabo ad pro. At esse democritum complectitur eum, ea sed placerat antiopam liberavisse, omittam singulis tincidunt vim no. Sonet animal posidonium at qui, minimum complectitur ei nam. Erat ridens sit ex, mel error labores referrentur in. Te vix dicat dignissim, minim eirmod delectus an pri. Eum docendi nostrum fastidii an, his an dicunt tritani principes."));
        return allItems;
    }
    private class AsyncLogin extends AsyncTask<String, String, String>
    {
        ProgressDialog pdLoading = new ProgressDialog(LoginActivity.this);
        HttpURLConnection conn;
        URL url = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            //this method will be running on UI thread
            pdLoading.setMessage("\tLoading...");
            pdLoading.setCancelable(false);
            pdLoading.show();

        }
        @Override
        protected String doInBackground(String... params) {
            try {

                // Enter URL address where your php file resides
                url = new URL("https://nkosingcobo142.000webhostapp.com/inc.php");

            } catch (MalformedURLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return "exception";
            }
            try {
                // Setup HttpURLConnection class to send and receive data from php and mysql
                conn = (HttpURLConnection)url.openConnection();
                conn.setReadTimeout(READ_TIMEOUT);
                conn.setConnectTimeout(CONNECTION_TIMEOUT);
                conn.setRequestMethod("POST");

                // setDoInput and setDoOutput method depict handling of both send and receive
                conn.setDoInput(true);
                conn.setDoOutput(true);

                // Append parameters to URL
                Uri.Builder builder = new Uri.Builder()
                        .appendQueryParameter("username", params[0])
                        .appendQueryParameter("password", params[1]);
                String query = builder.build().getEncodedQuery();

                // Open connection for sending data
                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
                writer.write(query);
                writer.flush();
                writer.close();
                os.close();
                conn.connect();

            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
                return "exception";
            }

            try {

                int response_code = conn.getResponseCode();

                // Check if successful connection made
                if (response_code == HttpURLConnection.HTTP_OK) {

                    // Read data sent from server
                    InputStream input = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                    StringBuilder result = new StringBuilder();
                    String line;

                    while ((line = reader.readLine()) != null) {
                        result.append(line);
                    }

                    // Pass data to onPostExecute method
                    return(result.toString());

                }else{

                    return("unsuccessful");
                }

            } catch (IOException e) {
                e.printStackTrace();
                return "exception";
            } finally {
                conn.disconnect();
            }


        }

        @Override
        protected void onPostExecute(String result) {

            //this method will be running on UI thread

            pdLoading.dismiss();

            if(result.equalsIgnoreCase("true"))
            {
                /* Here launching another activity when login successful. If you persist login state
                use sharedPreferences of Android. and logout button to clear sharedPreferences.
                 */

                Intent intent = new Intent(LoginActivity.this,RegisterTeamActivity.class);
                startActivity(intent);


            }else if (result.equalsIgnoreCase("false")){

                // If username and password does not match display a error message
                Toast.makeText(LoginActivity.this, "Invalid email or password", Toast.LENGTH_LONG).show();

            } else if (result.equalsIgnoreCase("exception") || result.equalsIgnoreCase("unsuccessful")) {

                Toast.makeText(LoginActivity.this, "OOPs! Something went wrong. Connection Problem.", Toast.LENGTH_LONG).show();

            }
        }

    }



}