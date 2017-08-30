package com.android.example.measureme.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.android.example.measureme.Adapters.ItemAdapter;
import com.android.example.measureme.Objects.Product;
import com.android.example.measureme.R;

import java.util.ArrayList;
import java.util.List;

import static com.android.example.measureme.Activities.MainActivity.products;

public class ItemView extends AppCompatActivity implements ItemAdapter.OnItemClickListener {

    RecyclerView rvItems;
    ItemAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_view);

        rvItems = (RecyclerView) findViewById(R.id.recycler_view_items);
        rvItems.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ItemAdapter(products, 0, this);
        rvItems.setAdapter(adapter);
    }

    @Override
    public void onListItemClicked(int clickedPosition) {

    }
}
