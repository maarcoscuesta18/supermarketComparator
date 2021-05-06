package com.sadcos.supermarketcomparator.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.sadcos.supermarketcomparator.R;
import com.sadcos.supermarketcomparator.products.mercadonaProducts;
import com.sadcos.supermarketcomparator.ui.main.mercadonaFragmentCart;

import java.util.ArrayList;

public class cartAdapter extends RecyclerView.Adapter<cartAdapter.PersonajeViewHolder>{
    public static ArrayList<mercadonaProducts> listCartMercadona;

    public cartAdapter(ArrayList<mercadonaProducts> listCartMercadona) {
        cartAdapter.listCartMercadona =listCartMercadona;
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
        holder.txtCount.setText(AdapterMercadona.mercadonaCartProducts.get(position).getQty());
        holder.remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(AdapterMercadona.mercadonaCartProducts.get(position).getCartproduct_name() == holder.product_name.getText().toString()){
                    AdapterMercadona.mercadonaCartProducts.remove(position);
                    notifyItemRemoved(position);
                    notifyDataSetChanged();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return listCartMercadona.size();
    }

    public class PersonajeViewHolder extends RecyclerView.ViewHolder {
        TextView product_name,price,txtCount;
        Button remove;

        public PersonajeViewHolder(View itemView) {
            super(itemView);
            product_name = itemView.findViewById(R.id.product_name);
            price = itemView.findViewById(R.id.price);
            txtCount =(TextView) itemView.findViewById(R.id.qty);
            Button buttonInc= (Button) itemView.findViewById(R.id.qtyplus);
            Button buttonDec= (Button) itemView.findViewById(R.id.qtyless);
            remove = (Button) itemView.findViewById(R.id.removefromcart);
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
