package com.cloudflare.soccerapp;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.firebase.client.Firebase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

public class RegisterTeamActivity extends AppCompatActivity implements View.OnClickListener {

    Button AddTeamMembers, Date;
    ImageView imageView;
    EditText editTeamName, editTeamCaptain, editChairman;
    private int mYear, mMonth, mDay;
    Spinner spinner;
    RequestQueue requestQueue;
    String league,teamName,teamCaptain,teamChairman;
    Toolbar toolbar;
    private static final int CAMERA_REQUEST = 1888;
    private static final int RESULT_LOAD_IMAGE=1;
    ByteArrayOutputStream bytes;
    FloatingActionButton choose,camera,gallery;
    private DatabaseReference mdatabaseRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_team);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("New Team");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Firebase.setAndroidContext(this);

        //Newer version of Firebase
        mdatabaseRef = FirebaseDatabase.getInstance().getReference().child("Teams");

        editTeamName = (EditText) findViewById(R.id.editTeamName);
        editTeamCaptain = (EditText) findViewById(R.id.editTeamCaptain);
        editChairman = (EditText) findViewById(R.id.editChairman);

        spinner = (Spinner) findViewById(R.id.spinner);

        AddTeamMembers = (Button) findViewById(R.id.btnAddTeamMembers);
        imageView = (ImageView)findViewById(R.id.logo);
        choose = (FloatingActionButton)findViewById(R.id.choose);
        choose.setOnClickListener(this);
        final LinearLayout layout = (LinearLayout)findViewById(R.id.register);
        Animation anim = AnimationUtils.loadAnimation(getBaseContext(),R.anim.zoom_in);
        layout.startAnimation(anim);
        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                layout.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        Spinner spinner = (Spinner)findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getApplicationContext(),R.array.Leagues,R.layout.dropdown_items);
        adapter.setDropDownViewResource(R.layout.dropdown_items);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                league = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                league = parent.getItemAtPosition(0).toString();
            }
        });
        AddTeamMembers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                String url = "http://soccer.payghost.co.za/teamdata.php";
                StringRequest request = new StringRequest(Request.Method.POST,url,new Response.Listener<String>(){
                    @Override
                    public void onResponse(String response)
                    {
                        Toast.makeText(getApplicationContext(),response,Toast.LENGTH_LONG).show();
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
                        parameters.put("Name",editTeamName.getText().toString());
                        parameters.put("Captain",editTeamCaptain.getText().toString());
                        parameters.put("Chairman",editChairman.getText().toString());
                        parameters.put("League",league);
                        return parameters;
                    }
                };
                request.setRetryPolicy(new DefaultRetryPolicy(0,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                requestQueue = Volley.newRequestQueue(getApplicationContext());
                requestQueue.add(request);
                */
                final  String encodeImage = Base64.encodeToString(bytes.toByteArray(),Base64.DEFAULT);
                teamName = editTeamName.getText().toString();
                teamCaptain = editTeamCaptain.getText().toString();
                teamChairman = editChairman.getText().toString();
                Map<String,String> team = new HashMap<String, String>();
                team.put("Logo",encodeImage);
                team.put("Name",teamName);
                team.put("Captain",teamCaptain);
                team.put("Chairman",teamChairman);
                team.put("League",league);
                mdatabaseRef.push().setValue(team);
                Toast.makeText(getApplicationContext(),"Team Added",Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch(v.getId())
        {

            case R.id.choose:
                final Dialog dialog = new Dialog(this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setCanceledOnTouchOutside(false);
                dialog.setContentView(R.layout.choose_logo);
                dialog.show();
                camera = (FloatingActionButton)dialog.findViewById(R.id.fab_camera);
                gallery = (FloatingActionButton)dialog.findViewById(R.id.fab_gallery);
                camera.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(cameraIntent,CAMERA_REQUEST);
                        dialog.dismiss();
                    }
                });
                gallery.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(intent,RESULT_LOAD_IMAGE);
                        dialog.dismiss();
                    }
                });
             break;

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }
     protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK ) {

            Bitmap photo = (Bitmap) data.getExtras().get("data");
            imageView.setDrawingCacheEnabled(true);
            imageView.buildDrawingCache();
            imageView.setImageBitmap(photo);
            //showing it on the image view widget
            bytes = new ByteArrayOutputStream();
            photo.compress(Bitmap.CompressFormat.PNG, 100, bytes);
        }
        else
            if(requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK )
            {
                Uri photo =  data.getData();
                imageView.setDrawingCacheEnabled(true);
                imageView.buildDrawingCache();
                imageView.setImageURI(photo);

                Bitmap bitmap= ((BitmapDrawable)imageView.getDrawable()).getBitmap();
                bytes = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, bytes);
            }
    }

}