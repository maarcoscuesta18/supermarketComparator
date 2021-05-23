package com.sadcos.supermarketcomparator.adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.sadcos.supermarketcomparator.R;
import com.sadcos.supermarketcomparator.products.carrefourProducts;
import com.sadcos.supermarketcomparator.products.diaProducts;
import com.sadcos.supermarketcomparator.products.mercadonaProducts;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by haerul on 17/03/18.
 */

public class AdapterCarrefour extends RecyclerView.Adapter<AdapterCarrefour.MyViewHolder> {
    public static ArrayList<carrefourProducts> carrefourCartProducts = new ArrayList<>();
    private List<carrefourProducts> product;
    private Context context;
    public static int[] count = {1};
    public AdapterCarrefour(List<carrefourProducts> products, Context context) {
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
        holder.price_per_kg.setText("Price per kg/l/unit: "+product.get(position).getPrice_per_kg());
    }
    @Override
    public int getItemCount() {
        return product.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView product_name,link,price,price_per_kg;
        public MyViewHolder(View itemView) {
            super(itemView);
            product_name = itemView.findViewById(R.id.product_name);
            link = itemView.findViewById(R.id.link);
            price = itemView.findViewById(R.id.price);
            price_per_kg = itemView.findViewById(R.id.priceperkg);
            Button addtocart = itemView.findViewById(R.id.addtocart);
            count[0] = 1;
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
                    carrefourProducts newproduct = new carrefourProducts(product_name.getText().toString(),price.getText().toString().substring(7,11),price_per_kg.getText().toString(),String.valueOf(count[0]),String.valueOf(Double.parseDouble(price.getText().toString().substring(7,11))*count[0]));
                    if(carrefourCartProducts.isEmpty()){
                        carrefourCartProducts.add(newproduct);
                        saveCart(v);
                    }else{
                        if(isAlreadyInCart(newproduct)){
                            for (carrefourProducts product : carrefourCartProducts){
                                if(product.getCartproduct_name().equals(newproduct.getCartproduct_name())){
                                    product.setQty(String.valueOf(Integer.parseInt(product.getQty())+Integer.parseInt(newproduct.getQty())));
                                    product.setTotalprice(Double.parseDouble(String.valueOf(product.getTotalprice()+newproduct.getTotalprice())));
                                    saveCart(v);
                                }
                            }
                        }else{
                            carrefourCartProducts.add(newproduct);
                            saveCart(v);
                        }
                    }
                    count[0]=1;
                    txtCount.setText("1");
                }
            });
        }
        public void saveCart(View v){
            SharedPreferences cartPreferences=v.getContext().getSharedPreferences("cartPreferences", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = cartPreferences.edit();
            Gson gson = new Gson();
            String json = gson.toJson(AdapterCarrefour.carrefourCartProducts);
            editor.putString("cartCarrefour", json);
            editor.apply();
        }
        public boolean isAlreadyInCart(carrefourProducts product){
            boolean isInCart=false;
            for(int i=0;i<carrefourCartProducts.size();i++){
                if(product.getCartproduct_name().equals(carrefourCartProducts.get(i).getCartproduct_name())){
                    isInCart=true;
                    break;
                }
            }
            return isInCart;
        }
    }
}
