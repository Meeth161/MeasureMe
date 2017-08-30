package com.android.example.measureme.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.example.measureme.Adapters.ItemAdapter;
import com.android.example.measureme.Objects.Measurement;
import com.android.example.measureme.Objects.Product;
import com.android.example.measureme.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.R.attr.start;
import static android.R.attr.type;
import static com.android.example.measureme.Activities.CustomerDetails.measurement;

public class ItemsActivity extends AppCompatActivity implements ItemAdapter.OnItemClickListener {

    private static final int RC_ADDITEM = 1;
    TextView tvAddItem;
    TextView tvSave;
    RecyclerView rvItems;

    List<Product> productList = new ArrayList<>();
    ItemAdapter adapter;

    TextView tvBed;
    TextView tvCurtain;
    TextView tvWallPaper;
    TextView tvOther;

    FirebaseAuth mAuth;

    private DatabaseReference mDatabaseRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items);

        mAuth = FirebaseAuth.getInstance();

        String name = mAuth.getCurrentUser().getEmail();
        if (name.equals("user@santosh.com")) {
            mDatabaseRef = FirebaseDatabase.getInstance().getReference().child("SantoshFurnishing").child("measurements");
        }
        else if (name.equals("user@saifurnishing.com")) {
            mDatabaseRef = FirebaseDatabase.getInstance().getReference().child("SaiFurnishing").child("measurements");
        }
        else {
            mDatabaseRef = FirebaseDatabase.getInstance().getReference().child("Anonymous").child("measurements");
        }
        rvItems = (RecyclerView) findViewById(R.id.recycler_view_items);
        tvAddItem = (TextView) findViewById(R.id.text_view_add_item_items);
        tvSave = (TextView) findViewById(R.id.text_view_save);

        productList = measurement.getProductList();

        rvItems.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ItemAdapter(productList, 1, this);
        rvItems.setAdapter(adapter);
        if (adapter.getItemCount() > 0) {
            scrollToBottom();
        }

        final Intent intent = new Intent(ItemsActivity.this, AddItemActivity.class);

        tvAddItem = (TextView) findViewById(R.id.text_view_add_item_items);
        tvAddItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ItemsActivity.this);
                LayoutInflater layoutInflater = getLayoutInflater();
                View dialogView = layoutInflater.inflate(R.layout.product_type, null);
                alertDialogBuilder.setView(dialogView);

                tvBed = (TextView) dialogView.findViewById(R.id.text_view_beds);
                tvCurtain = (TextView) dialogView.findViewById(R.id.text_view_curtains);
                tvWallPaper = (TextView) dialogView.findViewById(R.id.text_view_wallpapers);
                tvOther = (TextView) dialogView.findViewById(R.id.text_view_other);


                alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                final AlertDialog ad = alertDialogBuilder.create();
                ad.show();

                tvBed.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        intent.putExtra("type", "Bed");
                        intent.putExtra("number", 1);
                        startActivityForResult(intent, RC_ADDITEM);
                        ad.dismiss();
                    }
                });

                tvCurtain.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ad.dismiss();
                        intent.putExtra("type", "Curtain");
                        intent.putExtra("number", 1);
                        startActivityForResult(intent, RC_ADDITEM);
                    }
                });

                tvWallPaper.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ad.dismiss();
                        intent.putExtra("type", "WallPaper");
                        intent.putExtra("number", 1);
                        startActivityForResult(intent, RC_ADDITEM);
                    }
                });

                tvOther.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        AlertDialog.Builder adb = new AlertDialog.Builder(ItemsActivity.this);
                        LayoutInflater layoutInflater = getLayoutInflater();
                        View dialogView1 = layoutInflater.inflate(R.layout.product_type_other, null);
                        adb.setView(dialogView1);
                        final EditText etProductType = (EditText) dialogView1.findViewById(R.id.edit_text_product_type);
                        adb.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        });
                        adb.setPositiveButton("Next", null);
                        final AlertDialog a = adb.create();
                        a.setOnShowListener(new DialogInterface.OnShowListener() {
                            @Override
                            public void onShow(DialogInterface dialogInterface) {
                                Button p = a.getButton(AlertDialog.BUTTON_POSITIVE);
                                p.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        if(etProductType.getText().toString().equals("")) {
                                            Toast.makeText(ItemsActivity.this, "Please Enter Product Type", Toast.LENGTH_SHORT).show();
                                        } else {
                                            a.dismiss();
                                            intent.putExtra("type", etProductType.getText().toString().trim());
                                            intent.putExtra("number", 1);
                                            startActivityForResult(intent, RC_ADDITEM);
                                        }
                                    }
                                });
                            }
                        });
                        ad.dismiss();
                        a.show();
                    }
                });
            }
        });

        tvSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(measurement.getProductList().size() > 0) {
                    final String key = mDatabaseRef.push().getKey();
                    mDatabaseRef.child(key).setValue(measurement).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            measurement.isSynced = 1;
                            mDatabaseRef.child(key).child("isSynced").setValue(1);
                        }
                    });
                    Intent intent = new Intent(ItemsActivity.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                } else {
                    Toast.makeText(ItemsActivity.this, "Minimum 1 item required", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_ADDITEM) {
            if (resultCode == RESULT_OK) {
                productList = measurement.getProductList();
                adapter.notifyDataSetChanged();
                scrollToBottom();
            }
        }
    }

    void scrollToBottom() {
        rvItems.post(new Runnable() {
            @Override
            public void run() {
                rvItems.smoothScrollToPosition(adapter.getItemCount() - 1);
            }
        });
    }

    @Override
    public void onListItemClicked(int clickedPosition) {
        Intent i = new Intent(ItemsActivity.this, AddItemActivity.class);
        i.putExtra("description", productList.get(clickedPosition).getDescription());
        i.putExtra("details", productList.get(clickedPosition).getDetails());
        i.putExtra("number", 2);
        i.putExtra("position", clickedPosition);
        startActivityForResult(i, RC_ADDITEM);
    }
}
