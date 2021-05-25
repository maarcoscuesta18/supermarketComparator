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
import com.sadcos.supermarketcomparator.products.mercadonaProducts;
import com.sadcos.supermarketcomparator.ui.main.mercadonaFragmentCart;

import java.util.ArrayList;

public class mercadonaCartAdapter extends RecyclerView.Adapter<mercadonaCartAdapter.PersonajeViewHolder>{
    public static ArrayList<mercadonaProducts> listCartMercadona;
    public static mercadonaCartAdapter.OnItemClickListener listener;
    public interface OnItemClickListener{
        void onItemClick(mercadonaProducts item);
    }
    public mercadonaCartAdapter(ArrayList<mercadonaProducts> listCartMercadona, mercadonaCartAdapter.OnItemClickListener listener) {
        mercadonaCartAdapter.listCartMercadona =listCartMercadona;
        this.listener = listener;
    }

    @Override
    public PersonajeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.cartitem,null,false);
        return new PersonajeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PersonajeViewHolder holder, int position) {
        holder.product_name.setText(AdapterMercadona.mercadonaCartProducts.get(position).getCartproduct_name());
        holder.price.setText("Price: "+AdapterMercadona.mercadonaCartProducts.get(position).getCartprice()+" €");
        holder.txtqty.setText(AdapterMercadona.mercadonaCartProducts.get(position).getQty());
        final int[] count = {Integer.parseInt(AdapterMercadona.mercadonaCartProducts.get(position).getQty())};
        holder.buttonInc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count[0]++;
                AdapterMercadona.mercadonaCartProducts.get(position).setQty(String.valueOf(count[0]));
                AdapterMercadona.mercadonaCartProducts.get(position).setTotalprice(AdapterMercadona.mercadonaCartProducts.get(position).getCartprice()*count[0]);
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
                    AdapterMercadona.mercadonaCartProducts.get(position).setQty(String.valueOf(count[0]));
                    AdapterMercadona.mercadonaCartProducts.get(position).setTotalprice(AdapterMercadona.mercadonaCartProducts.get(position).getCartprice()*count[0]);
                    notifyDataSetChanged();
                    notifyItemChanged(position);
                    saveCart(v);
                } else{
                    count[0]--;
                    AdapterMercadona.mercadonaCartProducts.get(position).setQty(String.valueOf(count[0]));
                    AdapterMercadona.mercadonaCartProducts.get(position).setTotalprice(AdapterMercadona.mercadonaCartProducts.get(position).getCartprice()*count[0]);
                    notifyDataSetChanged();
                    notifyItemChanged(position);
                    saveCart(v);
                }
            }
        });
        holder.remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(AdapterMercadona.mercadonaCartProducts.get(position).getCartproduct_name() == holder.product_name.getText().toString()){
                    AdapterMercadona.mercadonaCartProducts.remove(position);
                    notifyItemRemoved(position);
                    notifyDataSetChanged();
                    saveCart(v);
                }
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(new mercadonaProducts(holder.product_name.getText().toString(),AdapterMercadona.mercadonaCartProducts.get(position).getCartlink(),holder.price.getText().toString().substring(7,11),String.valueOf(count[0]),String.valueOf(Double.parseDouble(holder.price.getText().toString().substring(7,11))*count[0])));
            }
        });
    }

    @Override
    public int getItemCount() {
        return listCartMercadona.size();
    }

    public class PersonajeViewHolder extends RecyclerView.ViewHolder {
        TextView product_name,price,txtqty;
        Button remove,buttonInc,buttonDec;

        public PersonajeViewHolder(View itemView) {
            super(itemView);
            product_name = itemView.findViewById(R.id.product_name);
            price = itemView.findViewById(R.id.price);
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
        String json = gson.toJson(AdapterMercadona.mercadonaCartProducts);
        editor.putString("cartMercadona", json);
        editor.apply();
    }
}
