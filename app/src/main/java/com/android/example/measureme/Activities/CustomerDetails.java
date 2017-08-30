package com.android.example.measureme.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.example.measureme.Objects.Customer;
import com.android.example.measureme.Objects.Measurement;
import com.android.example.measureme.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class CustomerDetails extends AppCompatActivity {

    static Measurement measurement;

    EditText etName;
    EditText etAddress;
    EditText etPhone;
    EditText etAltPhone;
    TextView tvNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_details);

        etName = (EditText) findViewById(R.id.edit_text_name_frag_customer_details);
        etAddress = (EditText) findViewById(R.id.edit_text_address_frag_customer_details);
        etPhone = (EditText) findViewById(R.id.edit_text_phone_frag_customer_details);
        etAltPhone = (EditText) findViewById(R.id.edit_text_alternate_phone_frag_customer_details);

        measurement = new Measurement();
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        measurement.setDate(df.format(c.getTime()));

        tvNext = (TextView) findViewById(R.id.text_view_next_frag_customer_details);
        tvNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String customerName = etName.getText().toString();
                String customerAddress = etAddress.getText().toString();
                String customerPhone = etPhone.getText().toString();
                String alternatePhone = etAltPhone.getText().toString();

                if (customerName.length() < 3) {
                    Toast.makeText(CustomerDetails.this, "Please enter Customer Name(min 3 characters)", Toast.LENGTH_SHORT).show();
                } else {
                    Customer c = new Customer(customerName, customerAddress, customerPhone, alternatePhone);
                    measurement.setCustomer(c);
                    startActivity(new Intent(CustomerDetails.this, ItemsActivity.class));
                }

            }
        });

    }
}
