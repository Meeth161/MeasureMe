package com.android.example.measureme.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.example.measureme.Objects.Measurement;
import com.android.example.measureme.R;

import java.util.List;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;

public class MeasurementAdapter extends RecyclerView.Adapter<MeasurementAdapter.ViewHolder>{

    List<Measurement> measurementList;
    Context context;
    ListItemClickListener mListItemClickListener;

    public MeasurementAdapter(List<Measurement> measurementList, Context context, ListItemClickListener mListItemClickListener) {
        this.measurementList = measurementList;
        this.context = context;
        this.mListItemClickListener = mListItemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.measurement_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Measurement m = measurementList.get(position);
        holder.tvCustomerName.setText(m.getCustomer().getCustomerName());
        if (m.getCustomer().getCustomerAddress().equals("")) {
            holder.tvCustomerAddress.setVisibility(View.GONE);
        } else {
            holder.tvCustomerAddress.setVisibility(View.VISIBLE);
            holder.tvCustomerAddress.setText(m.getCustomer().getCustomerAddress());
        }
        if (m.getCustomer().getCustomerPhone().equals("") && m.getCustomer().getAlternatePhone().equals("")) {
            holder.tvCustomerPhone.setVisibility(View.GONE);
        } else {
            holder.tvCustomerPhone.setVisibility(View.VISIBLE);
            holder.tvCustomerPhone.setText(m.getCustomer().getCustomerPhone() + ", " + m.getCustomer().getAlternatePhone());
        }
        holder.tvTotalItems.setText(String.valueOf(m.getProductList().size()));
        if (m.isSynced == 1) {
            holder.ivSynced.setImageResource(R.drawable.ic_synced);
        } else {
            holder.ivSynced.setImageResource(R.drawable.ic_not_synced);
        }
        holder.tvDate.setText(m.getDate());
    }

    @Override
    public int getItemCount() {
        return measurementList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvCustomerName;
        TextView tvCustomerAddress;
        TextView tvCustomerPhone;
        TextView tvTotalItems;
        TextView tvDate;
        ImageView ivSynced;
        LinearLayout llMeasurementItem;
        public ViewHolder(View itemView) {
            super(itemView);

            tvCustomerName = (TextView) itemView.findViewById(R.id.text_view_customer_name);
            tvCustomerAddress = (TextView) itemView.findViewById(R.id.text_view_customer_address);
            tvCustomerPhone = (TextView) itemView.findViewById(R.id.text_view_customer_phone_number);
            tvTotalItems = (TextView) itemView.findViewById(R.id.text_view_total_items);
            tvDate = (TextView) itemView.findViewById(R.id.text_view_date);
            ivSynced = (ImageView) itemView.findViewById(R.id.iv_cloud_status);
            llMeasurementItem = (LinearLayout) itemView.findViewById(R.id.ll_measurement_item);
            llMeasurementItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    mListItemClickListener.onItemClicked(position);
                }
            });
        }
    }

    public interface ListItemClickListener {
        void onItemClicked(int clickedPosition);
    }

}
