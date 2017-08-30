package com.android.example.measureme.Adapters;

import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.example.measureme.Objects.Product;
import com.android.example.measureme.R;

import org.w3c.dom.Text;

import java.util.List;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder>{

    List<Product> listItems;
    int act;
    OnItemClickListener onItemClickListener;

    public ItemAdapter(List<Product> listItems, int act, OnItemClickListener onItemClickListener) {
        this.listItems = listItems;
        this.act = act;
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        if(act == 1) {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        } else {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view, parent, false);
        }
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Product p = listItems.get(position);
        holder.tvProductType.setText(p.getType());
        holder.tvDescription.setText(p.getDescription());
        holder.tvDetails.setText(p.getDetails());
        holder.tvItemNumber.setText(String.valueOf(position + 1) + ".");

    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvProductType;
        TextView tvDescription;
        TextView tvDetails;
        TextView tvItemNumber;
        ImageView ivDelete;
        CardView cvItem;

        public ViewHolder(View itemView) {
            super(itemView);

            tvProductType = (TextView) itemView.findViewById(R.id.text_view_product_type);
            tvDescription = (TextView) itemView.findViewById(R.id.text_view_description);
            tvDetails = (TextView) itemView.findViewById(R.id.text_view_details);
            tvItemNumber = (TextView) itemView.findViewById(R.id.text_view_item_number);
            cvItem = (CardView) itemView.findViewById(R.id.cv_item);
            cvItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();
                    onItemClickListener.onListItemClicked(pos);
                }
            });

            if(act == 1) {
                ivDelete = (ImageView) itemView.findViewById(R.id.iv_delete);
                ivDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        listItems.remove(getAdapterPosition());
                        notifyDataSetChanged();
                    }
                });
            }

        }
    }

    public interface OnItemClickListener {
        void onListItemClicked(int clickedPosition);
    }

}
