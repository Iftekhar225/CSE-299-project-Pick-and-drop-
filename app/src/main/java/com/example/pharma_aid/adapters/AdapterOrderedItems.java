package com.example.pharma_aid.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pharma_aid.R;
import com.example.pharma_aid.models.ModelOrderedItems;

import java.util.ArrayList;

public class AdapterOrderedItems extends RecyclerView.Adapter<AdapterOrderedItems.HolderOrderedItems> {

    private Context context;
    private ArrayList<ModelOrderedItems> orderedItemsArrayList;

    public AdapterOrderedItems(Context context, ArrayList<ModelOrderedItems> orderedItemsArrayList) {
        this.context = context;
        this.orderedItemsArrayList = orderedItemsArrayList;
    }

    @NonNull
    @Override
    public HolderOrderedItems onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //inflate layout
        View view= LayoutInflater.from(context).inflate(R.layout.row_ordereditems,parent,false);
        return new HolderOrderedItems(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderOrderedItems holder, int position) {
        //get data at position
        ModelOrderedItems modelOrderedItems=orderedItemsArrayList.get(position);
        String getpId=modelOrderedItems.getpId();
        String name=modelOrderedItems.getName();
        String cost=modelOrderedItems.getCost();
        String price=modelOrderedItems.getPrice();
        String quantity=modelOrderedItems.getQuantity();

        //set data
        holder.itemTitleTv.setText(name);
        holder.itemPriceEachTv.setText("৳"+price);
        holder.itemPriceTv.setText("৳"+cost);
        holder.itemQuantityTv.setText("[" + quantity + "]");

    }

    @Override
    public int getItemCount() {
        return orderedItemsArrayList.size(); //return
    }

    //view holder class
    class HolderOrderedItems extends RecyclerView.ViewHolder {

        //views of row_ordereditems.xml
        private TextView itemTitleTv,itemPriceTv,itemPriceEachTv,itemQuantityTv;

        public HolderOrderedItems(@NonNull View itemView) {
            super(itemView);
            //intial views
            itemTitleTv=itemView.findViewById(R.id.itemTitleTv);
            itemPriceTv=itemView.findViewById(R.id.itemPriceTv);
            itemPriceEachTv=itemView.findViewById(R.id.itemPriceEachTv);
            itemQuantityTv=itemView.findViewById(R.id.itemQuantityTv);
        }
    }
}
