package com.cloudflare.soccerapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import java.util.HashMap;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        Submit= (Button)  findViewById(R.id.btnSubmit);
        Cancel= (Button)  findViewById(R.id.btnCancel2);
        username= (EditText)  findViewById(R.id.editUsername);
        password= (EditText)  findViewById(R.id.editPassword);


        Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                startActivity(intent); //onclick takes you to RegisterActivity.java
            }
        });


        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //login
               /*ProgressDialog progress = null;
                progress =  ProgressDialog.show(getApplicationContext(),null,"Authenticating...",true);
                new Thread(new Runnable() {
                    @Override
                    public void run() {

                    }

                }).start();*/

                String url = "https://soccer.payghost.co.za/login.inc.php";
                StringRequest request = new StringRequest(Request.Method.POST,url,new Response.Listener<String>(){
                    @Override
                    public void onResponse(String response)
                    {
                        Intent intent = new Intent(LoginActivity.this,RegisterTeamActivity.class);
                        startActivity(intent);
                        Toast.makeText(getApplicationContext(),response,Toast.LENGTH_LONG).show();
                    }
                },new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_LONG).show();
                    }
                }){
                    @Override
                    protected Map<String, String> getParams(){
                        Map<String,String> parameters = new HashMap<String, String>();
                        parameters.put("username",username.getText().toString());
                        parameters.put("password",password.getText().toString());
                        return parameters;
                    }
                };
                request.setRetryPolicy(new DefaultRetryPolicy(0,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                requestQueue = Volley.newRequestQueue(getApplicationContext());
                requestQueue.add(request);
            }
        });


    }private class AsyncLogin extends AsyncTask<String, String, String>
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