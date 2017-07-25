package com.cloudflare.soccerapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
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
import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class PostsActivity extends AppCompatActivity {

    private ImageButton photoButton;
    private Button upload;
    private static final int CAMERA_REQUEST = 1888;
    private ImageView imageView;
    RequestQueue requestQueue;
    private Firebase dbFirebase ,firebase;
    private static Uri imageUri;
    private DatabaseReference mdatabaseRef,reference;
    private FirebaseStorage mStorage,storage;
    private StorageReference storageReference,storageRef;
    EditText title;
    String path;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posts);
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Capture & Post Image");

        Firebase.setAndroidContext(this);
        imageView = (ImageView)findViewById(R.id.imageView2);
        upload = (Button)findViewById(R.id.btnUpload);
        title = (EditText)findViewById(R.id.title_image);
      /*  String encodedImage = "";
        byte[] decodeImage = Base64.decode(encodedImage,Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(decodeImage,0,decodeImage.length);
        imageView.setImageBitmap(bitmap);*/


       // dbFirebase = new Firebase("https://soccerapp-a3542.firebaseio.com/").child("Posts").push();
        mdatabaseRef = FirebaseDatabase.getInstance().getReference().child("Post").push();
        reference = FirebaseDatabase.getInstance().getReference().child("Comments").push();
        upload.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Bitmap image = ((BitmapDrawable)imageView.getDrawable()).getBitmap();

              //post(image,title.getText().toString());
            /*  Firebase childRef_name = mRoofRef.child("Image_Title");
                childRef_name.setValue(title.getText().toString());
                StorageReference filepath = mStorage.child("User_images").child(mImageUri.getLastPathSegment());
                filepath.putFile(mImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        mImageUri = taskSnapshot.getDownloadUrl();
                        mRoofRef.child("Image_URL").setValue(mImageUri.toString());
                        Toast.makeText(getApplicationContext(),"Success",Toast.LENGTH_LONG).show();
                    }
                });*/
            }
        });
    }
   public static Bitmap decodeFromFirebaseBase64(String image)
   {
       byte[] decodeByteArray = android.util.Base64.decode(image, Base64.DEFAULT);
       return BitmapFactory.decodeByteArray(decodeByteArray,0,decodeByteArray.length);
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
            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(cameraIntent,CAMERA_REQUEST);
        }
        return super.onOptionsItemSelected(item);
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK ) {

            Bitmap photo = (Bitmap) data.getExtras().get("data");
            imageView.setDrawingCacheEnabled(true);
            imageView.buildDrawingCache();
            imageView.setImageBitmap(photo);
            //showing it on the image view widget
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            photo.compress(Bitmap.CompressFormat.JPEG, 100, bytes);

            final  String encodeImage = Base64.encodeToString(bytes.toByteArray(),Base64.DEFAULT);
            mdatabaseRef.child("Picture").setValue(encodeImage);
            mdatabaseRef.child("Title").setValue("Picture Title");
            Map<String,String> users = new HashMap<String,String>();
            users.put("comment", "I got it bru");
            users.put("time","07:00 am");
            mdatabaseRef.child("Comments").push().setValue(users);



         /* String path = MediaStore.Images.Media.insertImage(getApplicationContext().getContentResolver(), photo,"title", null);
            imageUri =Uri.parse(path);
            imageUri = (Uri)data.getExtras().get("data");

            Cursor cursor = getContentResolver().query(imageUri, null, null, null, null);
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            imageUri = Uri.parse(cursor.getString(idx));*/
            final ProgressDialog myProgressDialog = new ProgressDialog(PostsActivity.this);
            //myProgressDialog.setProgressStyle(R.style.ProgressBar);
            myProgressDialog.show();
            myProgressDialog.setContentView(R.layout.progress);
            ProgressBar progressBar = (ProgressBar)myProgressDialog.findViewById(R.id.progressBar);
            progressBar.getIndeterminateDrawable()
                    .setColorFilter(Color.parseColor("#d5fd00"), PorterDuff.Mode.MULTIPLY);
/*
            storageReference = storageRef .child("images").child(imageUri.getLastPathSegment());
            storageReference.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    Uri downloadUrl = taskSnapshot.getDownloadUrl();
                    Toast.makeText(getApplicationContext(),downloadUrl+"",Toast.LENGTH_LONG).show();
                    myProgressDialog.dismiss();

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    TextView pr = (TextView)myProgressDialog.findViewById(R.id.progress_percent);
                    pr.setText(e.getMessage());

                }
            })
            .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                    double progress = (100*taskSnapshot.getBytesTransferred())/taskSnapshot.getTotalByteCount();
                    TextView pr = (TextView)myProgressDialog.findViewById(R.id.progress_percent);
                    pr.setText(""+(int)progress+"%");
                }
            });
            */

            this.path = path;
       //     Toast.makeText(getApplicationContext(),data.getData()+"",Toast.LENGTH_LONG).show();
        }
    }
    public void post(Bitmap image,final String name)
    {
      /*
        byte[] imageAsBytes = android.util.Base64.decode(mRoofRef.child("Posts").child("pic").toString().getBytes(), android.util.Base64.DEFAULT);
        imageView.setImageBitmap(BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length));*/

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
        final  String encodeImage = Base64.encodeToString(byteArrayOutputStream.toByteArray(),Base64.DEFAULT);
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("Comments","");



        // Get the data from an ImageView as bytes

        Bitmap bitmap = imageView.getDrawingCache();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();


        UploadTask uploadTask;
        InputStream stream;
        Uri file = Uri.fromFile(new File(path));
       StorageReference riversRef = storageReference.child(""+file.getLastPathSegment());
        uploadTask = riversRef.putFile(file);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure( Exception exception) {
                // Handle unsuccessful uploads
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                Uri downloadUrl = taskSnapshot.getDownloadUrl();
            }
        });

        final ProgressDialog myProgressDialog = new ProgressDialog(PostsActivity.this);
        //myProgressDialog.setProgressStyle(R.style.ProgressBar);        myProgressDialog.show();
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
