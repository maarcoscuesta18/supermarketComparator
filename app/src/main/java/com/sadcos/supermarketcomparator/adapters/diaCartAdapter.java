package com.sadcos.supermarketcomparator.adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.sadcos.supermarketcomparator.R;
import com.sadcos.supermarketcomparator.products.diaProducts;
import com.sadcos.supermarketcomparator.products.mercadonaProducts;

import java.util.ArrayList;

public class diaCartAdapter extends RecyclerView.Adapter<diaCartAdapter.PersonajeViewHolder>{
    public static ArrayList<diaProducts> listCartDia;

    public diaCartAdapter(ArrayList<diaProducts> listCartDia) {
        diaCartAdapter.listCartDia =listCartDia;
    }

    @Override
    public PersonajeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.cartitem,null,false);
        return new PersonajeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PersonajeViewHolder holder, int position) {
        holder.product_name.setText(AdapterDia.diaCartProducts.get(position).getCartproduct_name());
        holder.price.setText("Price: "+AdapterDia.diaCartProducts.get(position).getCartprice()+" €");
        holder.price_per_kg.setText("Price per kg/l/unit: "+AdapterDia.diaCartProducts.get(position).getPrice_per_kg());
        holder.txtqty.setText(AdapterDia.diaCartProducts.get(position).getQty());
        final int[] count = {Integer.parseInt(AdapterDia.diaCartProducts.get(position).getQty())};
        holder.buttonInc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count[0]++;
                AdapterDia.diaCartProducts.get(position).setQty(String.valueOf(count[0]));
                AdapterDia.diaCartProducts.get(position).setTotalprice(AdapterDia.diaCartProducts.get(position).getCartprice()*count[0]);
                notifyItemChanged(position);
                notifyDataSetChanged();
                saveCart(v);
            }
        });
        holder.buttonDec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(count[0] == 1){
                    count[0] = 1;
                    AdapterDia.diaCartProducts.get(position).setQty(String.valueOf(count[0]));
                    AdapterDia.diaCartProducts.get(position).setTotalprice(AdapterDia.diaCartProducts.get(position).getCartprice()*count[0]);
                    notifyDataSetChanged();
                    notifyItemChanged(position);
                    saveCart(v);
                } else{
                    count[0]--;
                    AdapterDia.diaCartProducts.get(position).setQty(String.valueOf(count[0]));
                    AdapterDia.diaCartProducts.get(position).setTotalprice(AdapterDia.diaCartProducts.get(position).getCartprice()*count[0]);
                    notifyDataSetChanged();
                    notifyItemChanged(position);
                    saveCart(v);
                }
            }
        });
        holder.remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(AdapterDia.diaCartProducts.get(position).getCartproduct_name() == holder.product_name.getText().toString()){
                    AdapterDia.diaCartProducts.remove(position);
                    notifyItemRemoved(position);
                    notifyDataSetChanged();
                    saveCart(v);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return listCartDia.size();
    }

    public class PersonajeViewHolder extends RecyclerView.ViewHolder {
        TextView product_name,price,price_per_kg,txtqty;
        Button remove,buttonInc,buttonDec;

        public PersonajeViewHolder(View itemView) {
            super(itemView);
            product_name = itemView.findViewById(R.id.product_name);
            price = itemView.findViewById(R.id.price);
            price_per_kg = itemView.findViewById(R.id.priceperkg);
            txtqty =(TextView) itemView.findViewById(R.id.qty);
            buttonInc= (Button) itemView.findViewById(R.id.qtyplus);
            buttonDec= (Button) itemView.findViewById(R.id.qtyless);
            remove = (Button) itemView.findViewById(R.id.removefromcart);
        }
    }
    public void saveCart(View v){
        SharedPreferences cartPreferences=v.getContext().getSharedPreferences("cartPreferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = cartPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(AdapterDia.diaCartProducts);
        editor.putString("cartDia", json);
        editor.apply();
    }
}
