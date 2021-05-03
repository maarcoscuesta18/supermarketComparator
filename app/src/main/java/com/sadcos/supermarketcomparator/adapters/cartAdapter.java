package com.sadcos.supermarketcomparator.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.sadcos.supermarketcomparator.R;
import com.sadcos.supermarketcomparator.products.mercadonaProducts;

import java.util.ArrayList;

public class cartAdapter extends RecyclerView.Adapter<cartAdapter.PersonajeViewHolder>{
    ArrayList<mercadonaProducts> listCartMercadona;
    Integer posicion;
    public cartAdapter(ArrayList<mercadonaProducts> listCartMercadona) {
        this.listCartMercadona=listCartMercadona;
    }

    @Override
    public PersonajeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.cartitem,null,false);
        return new PersonajeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PersonajeViewHolder holder, int position) {
        holder.product_name.setText(listCartMercadona.get(position).getCartproduct_name());
        holder.price.setText("Price: "+listCartMercadona.get(position).getCartprice()+" €");
        holder.txtCount.setText(listCartMercadona.get(position).getQty());
        posicion=position;
    }

    @Override
    public int getItemCount() {
        return listCartMercadona.size();
    }

    public class PersonajeViewHolder extends RecyclerView.ViewHolder {
        TextView product_name,price,txtCount;

        public PersonajeViewHolder(View itemView) {
            super(itemView);
            product_name = itemView.findViewById(R.id.product_name);
            price = itemView.findViewById(R.id.price);
            txtCount =(TextView) itemView.findViewById(R.id.qty);
            Button buttonInc= (Button) itemView.findViewById(R.id.qtyplus);
            Button buttonDec= (Button) itemView.findViewById(R.id.qtyless);
            Button remove = (Button) itemView.findViewById(R.id.removefromcart);
            remove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listCartMercadona.remove(posicion);
                }
            });
            final int[] count = {1};
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
        }
    }
}
