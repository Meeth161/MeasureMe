package com.android.example.measureme.Activities;

import android.content.Intent;
import android.renderscript.Double2;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.example.measureme.Objects.Measurement;
import com.android.example.measureme.Objects.Product;
import com.android.example.measureme.R;

import static android.R.attr.width;
import static com.android.example.measureme.Activities.CustomerDetails.measurement;

public class AddItemActivity extends AppCompatActivity {

    String type;
    String desc;
    String det;
    int no;
    int position;

    EditText etDescription;
    EditText etDetails;

    TextView tvSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        final Intent intent = getIntent();

        etDescription = (EditText) findViewById(R.id.edit_text_description);
        etDetails = (EditText) findViewById(R.id.edit_text_details);

        type = getIntent().getStringExtra("type");
        no = getIntent().getIntExtra("number", 0);
        position = intent.getIntExtra("position", -1);

        if (intent != null) {
            desc = intent.getStringExtra("description");
            det = intent.getStringExtra("details");
            etDescription.setText(desc);
            etDetails.setText(det);
        }

        tvSave = (TextView) findViewById(R.id.text_view_save_add_item);
        tvSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String description = etDescription.getText().toString().trim();
                String details = etDetails.getText().toString().trim();


                if((description.equals("")|| details.equals(""))){

                    Toast.makeText(AddItemActivity.this, "Enter required details", Toast.LENGTH_SHORT).show();

                }else {
                    if (no == 1) {
                        Product p = new Product(type);
                        p.setDescription(description);
                        p.setDetails(details);
                        measurement.addProduct(p);
                        setResult(RESULT_OK);
                        finish();
                    }
                    else if (no == 2) {
                        Product pro = measurement.getProductList().get(position);
                        String type2 = measurement.getProductList().get(position).getType();
                        measurement.removeProduct(pro);
                        Product p = new Product(type2);
                        p.setDescription(description);
                        p.setDetails(details);
                        measurement.addProduct(p);
                        setResult(RESULT_OK);
                        finish();
                    }
                }
            }
        });

    }
}
