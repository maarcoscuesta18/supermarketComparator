package com.sadcos.supermarketcomparator.adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.sadcos.supermarketcomparator.R;
import com.sadcos.supermarketcomparator.products.carrefourProducts;
import com.sadcos.supermarketcomparator.products.diaProducts;

import java.util.ArrayList;

public class carrefourCartAdapter extends RecyclerView.Adapter<carrefourCartAdapter.PersonajeViewHolder>{
    public static ArrayList<carrefourProducts> listCartCarrefour;
    public static carrefourCartAdapter.OnItemClickListener listener;
    public interface OnItemClickListener{
        void onItemClick(carrefourProducts item);
    }
    public carrefourCartAdapter(ArrayList<carrefourProducts> listCartCarrefour,carrefourCartAdapter.OnItemClickListener listener) {
        carrefourCartAdapter.listCartCarrefour =listCartCarrefour;
        this.listener = listener;
    }

    @Override
    public PersonajeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.cartitem,null,false);
        return new PersonajeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PersonajeViewHolder holder, int position) {
        holder.product_name.setText(AdapterCarrefour.carrefourCartProducts.get(position).getCartproduct_name());
        holder.price.setText("Price: "+AdapterCarrefour.carrefourCartProducts.get(position).getCartprice()+" €");
        holder.price_per_kg.setText("Price per kg/l/unit: "+AdapterCarrefour.carrefourCartProducts.get(position).getPrice_per_kg());
        holder.txtqty.setText(AdapterCarrefour.carrefourCartProducts.get(position).getQty());
        final int[] count = {Integer.parseInt(AdapterCarrefour.carrefourCartProducts.get(position).getQty())};
        holder.buttonInc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count[0]++;
                AdapterCarrefour.carrefourCartProducts.get(position).setQty(String.valueOf(count[0]));
                AdapterCarrefour.carrefourCartProducts.get(position).setTotalprice(AdapterCarrefour.carrefourCartProducts.get(position).getCartprice()*count[0]);
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
                    AdapterCarrefour.carrefourCartProducts.get(position).setQty(String.valueOf(count[0]));
                    AdapterCarrefour.carrefourCartProducts.get(position).setTotalprice(AdapterCarrefour.carrefourCartProducts.get(position).getCartprice()*count[0]);
                    notifyDataSetChanged();
                    notifyItemChanged(position);
                    saveCart(v);
                } else{
                    count[0]--;
                    AdapterCarrefour.carrefourCartProducts.get(position).setQty(String.valueOf(count[0]));
                    AdapterCarrefour.carrefourCartProducts.get(position).setTotalprice(AdapterCarrefour.carrefourCartProducts.get(position).getCartprice()*count[0]);
                    notifyDataSetChanged();
                    notifyItemChanged(position);
                    saveCart(v);
                }
            }
        });
        holder.remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(AdapterCarrefour.carrefourCartProducts.get(position).getCartproduct_name() == holder.product_name.getText().toString()){
                    AdapterCarrefour.carrefourCartProducts.remove(position);
                    notifyItemRemoved(position);
                    notifyDataSetChanged();
                    saveCart(v);
                }
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(new carrefourProducts(holder.product_name.getText().toString(),AdapterCarrefour.carrefourCartProducts.get(position).getCartlink(),holder.price.getText().toString().substring(7,11),holder.price_per_kg.getText().toString(),String.valueOf(count[0]),String.valueOf(Double.parseDouble(holder.price.getText().toString().substring(7,11))*count[0])));
            }
        });
    }

    @Override
    public int getItemCount() {
        try{
            return listCartCarrefour.size();
        }catch (Exception e){
           return 0;
        }

    }

    public class PersonajeViewHolder extends RecyclerView.ViewHolder {
        TextView product_name,price,price_per_kg,txtqty;
        ImageView remove,buttonInc,buttonDec;

        public PersonajeViewHolder(View itemView) {
            super(itemView);
            product_name = itemView.findViewById(R.id.product_name);
            price = itemView.findViewById(R.id.price);
            price_per_kg = itemView.findViewById(R.id.priceperkg);
            txtqty =(TextView) itemView.findViewById(R.id.qty);
            buttonInc= (ImageView) itemView.findViewById(R.id.qtyplus);
            buttonDec= (ImageView) itemView.findViewById(R.id.qtyless);
            remove = (ImageView) itemView.findViewById(R.id.removefromcart);
        }
    }
    public void saveCart(View v){
        SharedPreferences cartPreferences=v.getContext().getSharedPreferences("cartPreferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = cartPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(AdapterCarrefour.carrefourCartProducts);
        editor.putString("cartCarrefour", json);
        editor.apply();
    }
}
