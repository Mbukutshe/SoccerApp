package com.cloudflare.soccerapp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PostsActivity extends AppCompatActivity {

    private ImageButton photoButton;
    private Button upload;
    private static final int CAMERA_REQUEST = 1888;
    private ImageView imageView;
    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posts);
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Capture & Post Image");
        imageView = (ImageView)findViewById(R.id.imageView2);
        upload = (Button)findViewById(R.id.btnUpload);
        final EditText title = (EditText)findViewById(R.id.title_image);

        upload.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Bitmap image = ((BitmapDrawable)imageView.getDrawable()).getBitmap();
                post(image,title.getText().toString());
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.camera_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.capture)
        {
            Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(cameraIntent, CAMERA_REQUEST);
        }
        return super.onOptionsItemSelected(item);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            imageView.setImageBitmap(photo); //showing it on the image view widget
        }
    }
    public void post(Bitmap image,final String name)
    {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.PNG,100,byteArrayOutputStream);
        final  String encodeImage = Base64.encodeToString(byteArrayOutputStream.toByteArray(),Base64.DEFAULT);

        final ProgressDialog myProgressDialog = new ProgressDialog(PostsActivity.this);
        //myProgressDialog.setProgressStyle(R.style.ProgressBar);


        myProgressDialog.show();
        myProgressDialog.setContentView(R.layout.progress);
        ProgressBar progressBar = (ProgressBar)myProgressDialog.findViewById(R.id.progressBar);
        progressBar.getIndeterminateDrawable()
                .setColorFilter(Color.parseColor("#d5fd00"), PorterDuff.Mode.MULTIPLY);

        String url = "https://soccer.payghost.co.za/post.php";
        StringRequest request = new StringRequest(Request.Method.POST,url,new Response.Listener<String>(){
            @Override
            public void onResponse(String response)
            {
                Toast.makeText(getApplicationContext(),response,Toast.LENGTH_LONG).show();
                myProgressDialog.dismiss();
            }
        },new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error)
            {

            }
        }){
            @Override
            protected Map<String, String> getParams(){
                Map<String,String> parameters = new HashMap<String, String>();
                parameters.put("image",encodeImage);
                parameters.put("name",name);
                return parameters;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(0,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(request);
    }

}
