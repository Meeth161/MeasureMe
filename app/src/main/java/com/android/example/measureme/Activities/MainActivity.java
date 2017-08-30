package com.android.example.measureme.Activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.example.measureme.Adapters.MeasurementAdapter;
import com.android.example.measureme.Objects.Measurement;
import com.android.example.measureme.Objects.Product;
import com.android.example.measureme.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MeasurementAdapter.ListItemClickListener{

    TextView tvNewMeasurement;
    RecyclerView rvMain;
    SwipeRefreshLayout swipeRefreshLayout;
    ProgressBar progressBar;
    MeasurementAdapter adapter;

    FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener mAuthListener;

    List<Measurement> measurements = new ArrayList<>();
    static List<Product> products;

    DatabaseReference mDataRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user == null) {
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                    finish();
                }
                else {
                    String name = mAuth.getCurrentUser().getEmail();
                    if (name.equals("user@santosh.com")) {
                        mDataRef = FirebaseDatabase.getInstance().getReference().child("SantoshFurnishing").child("measurements");
                    }
                    else if (name.equals("user@saifurnishing.com")) {
                        mDataRef = FirebaseDatabase.getInstance().getReference().child("SaiFurnishing").child("measurements");
                    }
                    else {
                        mDataRef = FirebaseDatabase.getInstance().getReference().child("Anonymous").child("measurements");
                    }
                    progressBar = (ProgressBar) findViewById(R.id.progress_bar);

                    rvMain = (RecyclerView) findViewById(R.id.rv_recent_entries);
                    swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MainActivity.this);
                    linearLayoutManager.setStackFromEnd(true);
                    linearLayoutManager.setReverseLayout(true);
                    rvMain.setLayoutManager(linearLayoutManager);
                    adapter = new MeasurementAdapter(measurements, MainActivity.this, MainActivity.this);
                    rvMain.setAdapter(adapter);

                    load();


                    swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                        @Override
                        public void onRefresh() {
                            load();
                        }
                    });



                    tvNewMeasurement = (TextView) findViewById(R.id.text_view_new_measurement);
                    tvNewMeasurement.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            startActivity(new Intent(MainActivity.this, CustomerDetails.class));
                        }
                    });
                }
            }
        };


    }

    void load() {
        measurements.clear();
        mDataRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Measurement m = dataSnapshot.getValue(Measurement.class);
                measurements.add(m);
                adapter.notifyDataSetChanged();
                swipeRefreshLayout.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
                swipeRefreshLayout.setRefreshing(false);
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
    public void onItemClicked(int clickedPosition) {
        products = measurements.get(clickedPosition).getProductList();
        Intent intent = new Intent(MainActivity.this, ItemView.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.action_logout:
                mAuth.signOut();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mAuth.removeAuthStateListener(mAuthListener);
    }
}
