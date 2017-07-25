package com.cloudflare.soccerapp;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.firebase.client.Firebase;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener,View.OnTouchListener{

    // CONNECTION_TIMEOUT and READ_TIMEOUT are in milliseconds
    public static final int CONNECTION_TIMEOUT=10000;
    public static final int READ_TIMEOUT=15000;
    ImageView expandedImage;
    Button Submit, Cancel;
    EditText username, password,message;
    RequestQueue requestQueue;
    RecyclerView mRecyclerView;
    LinearLayoutManager mLayoutManager;
    CommentViewAdapter mAdapter;
    CardView post_comment;
    TextView close,send,error_input;
    CardView post;
    private DatabaseReference mdatabaseRef,reference;
    List<commentObject> myDataset;
    Comments comments;
    String key;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.comments_layout);

        Firebase.setAndroidContext(getApplicationContext());
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String title = getIntent().getExtras().get("Title").toString();
        key = getIntent().getExtras().get("key").toString();
        getSupportActionBar().setTitle(title );
        mdatabaseRef = FirebaseDatabase.getInstance().getReference().child("Post");
        expandedImage = (ImageView)findViewById(R.id.expandedImage);
        Bitmap bitmap= getIntent().getParcelableExtra("Image");
        expandedImage.setImageBitmap(bitmap);

        mRecyclerView = (RecyclerView)findViewById(R.id.my_recycler_comment);
        mRecyclerView.setHasFixedSize(true);
        myDataset = new ArrayList<commentObject>();

        mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new CommentViewAdapter(getApplicationContext(),myDataset);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(),mLayoutManager.getOrientation()));
        mRecyclerView.setAdapter(mAdapter);

        getAllItemList();

        post = (CardView)findViewById(R.id.post);
        post_comment= (CardView)findViewById(R.id.add_comment);
        post_comment.setOnClickListener(this);

        close = (TextView)findViewById(R.id.closing);
        close.setOnTouchListener(this);

        send = (TextView)findViewById(R.id.sending);
        send.setOnTouchListener(this);

        error_input = (TextView)findViewById(R.id.error_message);
        message = (EditText)findViewById(R.id.message);
        message.setOnTouchListener(this);
    }
    private void getAllItemList(){

        mdatabaseRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                reference =  FirebaseDatabase.getInstance().getReference().child("Post").child(key).child("Comments");
                reference.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        Toast.makeText(getApplicationContext(), key, Toast.LENGTH_SHORT).show();
                        comments =dataSnapshot.getValue(Comments.class);
                        myDataset.add(new commentObject(comments.time,comments.comment));
                        mAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onChildRemoved(DataSnapshot dataSnapshot) {

                    }

                    @Override
                    public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    @Override
    public void onClick(View view)
    {
        int id = view.getId();
        switch(id)
        {
            case R.id.add_comment:

                Animation upAnim = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.zoom_out);
                Animation anim = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.zoom_in);
                post.startAnimation(anim);
                post.setVisibility(View.VISIBLE);

                post_comment.startAnimation(upAnim);
                post_comment.setVisibility(View.GONE);

        }
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        int id = view.getId();
        switch(id)
        {


            case R.id.closing:
                Animation upAnim = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.zoom_in);
                Animation anim = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.zoom_out);
                post.startAnimation(anim);
                post.setVisibility(View.GONE);
                error_input.setVisibility(View.GONE);

                post_comment.startAnimation(upAnim);
                post_comment.setVisibility(View.VISIBLE);
            break;
            case R.id.sending:
                final Animation textAnim = new AlphaAnimation(0.0f,1.0f);

                textAnim.setDuration(50);
                textAnim.setStartOffset(20);
                textAnim.setRepeatMode(Animation.REVERSE);
                textAnim.setRepeatCount(6);
                if((message.getText().toString()).isEmpty())
                {
                    error_input.setVisibility(View.VISIBLE);
                    error_input.startAnimation(textAnim);
                }
                else
                {
                    Animation uAnim = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.zoom_in);
                    Animation pAnim = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.translate_right);
                    post.startAnimation(pAnim);
                    post.setVisibility(View.GONE);

                    post_comment.startAnimation(uAnim);
                    post_comment.setVisibility(View.VISIBLE);
                    message.setText("");
                }
            break;
            case R.id.message:
                Animation animGone = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.zoom_out);
                error_input.setVisibility(View.GONE);
                error_input.startAnimation(animGone);
            break;

        }
        return false;
    }
}