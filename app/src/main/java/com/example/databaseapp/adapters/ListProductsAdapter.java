package com.example.databaseapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.databaseapp.R;
import com.example.databaseapp.Visualize;
import com.example.databaseapp.entities.Products;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class ListProductsAdapter extends RecyclerView.Adapter<ListProductsAdapter.ProductViewHolder> {

    //Constructor
    ArrayList<Products> listProducts;
    private Context mContext;
    private ArrayList<Products> mFilteredList;
    public ListProductsAdapter(ArrayList<Products> listProducts, Context mContext){
        this.listProducts = listProducts;
        this.mContext = mContext;
        this.mFilteredList = listProducts;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Append each product to recyclerView
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_product, parent, false); //2nd argument, let our cardView be set fullWidth (match_parent)
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        //To assign elements that corresponds to their options, and setting them up to be shown in UI
        try{
            holder.viewName.setText(listProducts.get(position).getName()); //calling "get" methods from encapsulated fields
            holder.viewPrice.setText("S/." + String.valueOf(listProducts.get(position).getPrice()));
            //we set red color to text if stock is less than 10 items
            if ((listProducts.get(position).getStock() < 10)) {
                holder.viewStock.setTextColor(Color.parseColor("#c82f1d"));
            } else {
                holder.viewStock.setTextColor(Color.parseColor("#51ab23"));
            }
            holder.viewStock.setText(String.valueOf(listProducts.get(position).getStock()));
        }catch(Exception exception){
            System.out.println(exception);
        }

    }

    @Override
    public int getItemCount() {
        return listProducts.size();
    }
    public void setFilter(ArrayList<Products> newList){
        listProducts = new ArrayList<>();
        listProducts.addAll(newList);
        notifyDataSetChanged();
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder {
        TextView viewName, viewPrice, viewStock;
        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            viewName = itemView.findViewById(R.id.viewName);
            viewPrice = itemView.findViewById(R.id.viewPrice);
            viewStock = itemView.findViewById(R.id.viewStock);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Context context = view.getContext();
                    Intent intent = new Intent(context, Visualize.class);
                    intent.putExtra("ID", listProducts.get(getAdapterPosition()).getId());
                    context.startActivity(intent);
                }
            });
        }
    }

}
