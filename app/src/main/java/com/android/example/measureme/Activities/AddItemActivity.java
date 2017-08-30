package com.android.example.measureme.Activities;

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

    EditText etDescription;
    EditText etDetails;

    TextView tvSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        etDescription = (EditText) findViewById(R.id.edit_text_description);
        etDetails = (EditText) findViewById(R.id.edit_text_details);

        type = getIntent().getStringExtra("type");

        tvSave = (TextView) findViewById(R.id.text_view_save_add_item);
        tvSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String description = etDescription.getText().toString().trim();
                String details = etDetails.getText().toString().trim();


                if((description.equals("")|| details.equals(""))){

                    Toast.makeText(AddItemActivity.this, "Enter required details", Toast.LENGTH_SHORT).show();

                }else {
                    Product p = new Product(type);
                    p.setDescription(description);
                    p.setDetails(details);
                    measurement.addProduct(p);
                    setResult(RESULT_OK);
                    finish();
                }
            }
        });

    }
}
