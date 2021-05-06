package com.sadcos.supermarketcomparator.adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.content.SharedPreferences;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.sadcos.supermarketcomparator.R;
import com.sadcos.supermarketcomparator.products.mercadonaProducts;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by haerul on 17/03/18.
 */

public class AdapterMercadona extends RecyclerView.Adapter<AdapterMercadona.MyViewHolder> {
    public static ArrayList<mercadonaProducts> mercadonaCartProducts = new ArrayList<>();
    private List<mercadonaProducts> product;
    private Context context;

    public AdapterMercadona(List<mercadonaProducts> products, Context context) {
        this.product = products;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.product_name.setText(product.get(position).getProduct_name());
        holder.link.setText(product.get(position).getLink());
        holder.price.setText("Price: "+product.get(position).getPrice()+" €");
    }
    @Override
    public int getItemCount() {
        return product.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView product_name,link,price;
        public MyViewHolder(View itemView) {
            super(itemView);
            product_name = itemView.findViewById(R.id.product_name);
            link = itemView.findViewById(R.id.link);
            price = itemView.findViewById(R.id.price);
            Button addtocart = itemView.findViewById(R.id.addtocart);
            final int[] count = {1};
            TextView txtCount =(TextView) itemView.findViewById(R.id.qty);
            Button buttonInc= (Button) itemView.findViewById(R.id.qtyplus);
            Button buttonDec= (Button) itemView.findViewById(R.id.qtyless);
            buttonInc.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    count[0]++;
                    txtCount.setText(String.valueOf(count[0]));
                }
            });
            buttonDec.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(count[0] == 1){
                        count[0] = 1;
                        txtCount.setText(String.valueOf(count[0]));
                    } else{
                        count[0]--;
                        txtCount.setText(String.valueOf(count[0]));
                    }
                }
            });
            addtocart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(v.getContext(),"Producto añadido correctamente",Toast.LENGTH_SHORT).show();
                    mercadonaCartProducts.add(new mercadonaProducts(product_name.getText().toString(),price.getText().toString().substring(7,11),String.valueOf(count[0]),String.valueOf(Double.parseDouble(price.getText().toString().substring(7,11))*count[0])));
                    saveCart(v);
                }
            });
        }
        public void saveCart(View v){
            SharedPreferences cartPreferences=v.getContext().getSharedPreferences("cartPreferences", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = cartPreferences.edit();
            Gson gson = new Gson();
            String json = gson.toJson(AdapterMercadona.mercadonaCartProducts);
            editor.putString("cartMercadona", json);
            editor.apply();
        }
    }
}
