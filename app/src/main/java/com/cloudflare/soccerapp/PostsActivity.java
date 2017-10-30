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
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
    private FloatingActionButton upload;
    private static final int CAMERA_REQUEST = 1888;
    private static final int RESULT_LOAD_IMAGE=1;
    private ImageView imageView;
    RequestQueue requestQueue;
    private Firebase dbFirebase ,firebase;
    private static Uri imageUri;
    private DatabaseReference mdatabaseRef,reference;
    private FirebaseStorage mStorage,storage;
    private StorageReference storageReference,storageRef;
    EditText title;
    String path;
    FrameLayout caption;
    CardView cardView;
    LinearLayout choose,layoutImage;
    ByteArrayOutputStream bytes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posts);
        Firebase.setAndroidContext(this);
        imageView = (ImageView)findViewById(R.id.imageView2);
        upload = (FloatingActionButton) findViewById(R.id.fab_send);
        title = (EditText)findViewById(R.id.title_image);

      /*String encodedImage = "";
        byte[] decodeImage = Base64.decode(encodedImage,Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(decodeImage,0,decodeImage.length);
        imageView.setImageBitmap(bitmap);*/

        cardView  = (CardView)findViewById(R.id.card_view);
        caption = (FrameLayout)findViewById(R.id.layout_caption);
        choose = (LinearLayout)findViewById(R.id.layout_choose);
        layoutImage = (LinearLayout)findViewById(R.id.layout_image);

        FloatingActionButton fab = (FloatingActionButton)findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                choose.setVisibility(View.GONE);
                cardView.setVisibility(View.GONE);
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent,CAMERA_REQUEST);
            }
        });
        FloatingActionButton fabGallery = (FloatingActionButton)findViewById(R.id.fab_gallery);
        fabGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                choose.setVisibility(View.GONE);
                cardView.setVisibility(View.GONE);
                Intent intent = new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent,RESULT_LOAD_IMAGE);
            }
        });
       // dbFirebase = new Firebase("https://soccerapp-a3542.firebaseio.com/").child("Posts").push();
        mdatabaseRef = FirebaseDatabase.getInstance().getReference().child("Post").push();
        reference = FirebaseDatabase.getInstance().getReference().child("Comments").push();
        upload.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                final  String encodeImage = Base64.encodeToString(bytes.toByteArray(),Base64.DEFAULT);
                mdatabaseRef.child("Picture").setValue(encodeImage);
                mdatabaseRef.child("Title").setValue(title.getText().toString());
                mdatabaseRef.child("Comments").push();
                finish();
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

        }
        return super.onOptionsItemSelected(item);
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK ) {

            Bitmap photo = (Bitmap) data.getExtras().get("data");
            cardView.setVisibility(View.VISIBLE);
            caption.setVisibility(View.VISIBLE);
            layoutImage.setVisibility(View.VISIBLE);
            cardView.setCardBackgroundColor(Color.BLACK);
            cardView.getBackground().setAlpha(240);
            caption.getBackground().setAlpha(0);
            imageView.setDrawingCacheEnabled(true);
            imageView.buildDrawingCache();
            imageView.setImageBitmap(photo);
            //showing it on the image view widget
            bytes = new ByteArrayOutputStream();
            photo.compress(Bitmap.CompressFormat.JPEG, 100, bytes);

/* Beautiful progress
            final ProgressDialog myProgressDialog = new ProgressDialog(PostsActivity.this);
            //myProgressDialog.setProgressStyle(R.style.ProgressBar);
            myProgressDialog.show();
            myProgressDialog.setContentView(R.layout.progress);
            ProgressBar progressBar = (ProgressBar)myProgressDialog.findViewById(R.id.progressBar);
            progressBar.getIndeterminateDrawable()
                    .setColorFilter(Color.parseColor("#d5fd00"), PorterDuff.Mode.MULTIPLY);*/

        }
        else
            if(requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK )
            {
                Uri photo =  data.getData();
                cardView.setVisibility(View.VISIBLE);
                caption.setVisibility(View.VISIBLE);
                layoutImage.setVisibility(View.VISIBLE);
                cardView.setCardBackgroundColor(Color.BLACK);
                cardView.getBackground().setAlpha(240);
                caption.getBackground().setAlpha(0);
                imageView.setDrawingCacheEnabled(true);
                imageView.buildDrawingCache();
                imageView.setImageURI(photo);

                Bitmap bitmap= ((BitmapDrawable)imageView.getDrawable()).getBitmap();
                bytes = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
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
