package com.sadcos.supermarketcomparator.ui.main;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.sadcos.supermarketcomparator.ItemDetail;
import com.sadcos.supermarketcomparator.R;
import com.sadcos.supermarketcomparator.adapters.supermercadosAdapters.AdapterMercadona;
import com.sadcos.supermarketcomparator.adapters.cartAdapters.mercadonaCartAdapter;
import com.sadcos.supermarketcomparator.products.mercadonaProducts;

import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */
public class mercadonaFragmentCart extends Fragment  {
    private OnFragmentInteractionListener mListener;

    RecyclerView recyclerCartMercadona;
    public static ArrayList<mercadonaProducts> listCartMercadona;
    TextView totalprice,cartempty;
    double cartprice=0;
    public SwipeRefreshLayout swipeRefreshLayout;

    public mercadonaFragmentCart() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View vista=inflater.inflate(R.layout.fragment_cart, container, false);
        listCartMercadona=new ArrayList<>();
        recyclerCartMercadona= (RecyclerView) vista.findViewById(R.id.recyclerId);
        recyclerCartMercadona.setLayoutManager(new LinearLayoutManager(getContext()));
        mercadonaCartAdapter adapter=new mercadonaCartAdapter(AdapterMercadona.mercadonaCartProducts,new mercadonaCartAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(mercadonaProducts item) {
                moveToDescription(item);
            }
        });
        recyclerCartMercadona.setAdapter(adapter);

        //set cart totalprice
        totalprice =vista.findViewById(R.id.totalprice);
        cartprice=0;
        cartempty = vista.findViewById(R.id.cartempty);
        if(!AdapterMercadona.mercadonaCartProducts.isEmpty()){
            cartempty.setVisibility(View.INVISIBLE);
            for(mercadonaProducts product : AdapterMercadona.mercadonaCartProducts){
                cartprice+=product.getTotalprice();
            }
            totalprice.setText(String.format("Precio Total: %.2f €",cartprice));

        }else{
            cartempty.setVisibility(View.VISIBLE);
            totalprice.setText(String.format("Precio Total: %.2f €",cartprice));
        }

        //refresh the cart price
        swipeRefreshLayout = vista.findViewById(R.id.fragmentLayout);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                try {
                    new UnaTarea().execute();
                }catch(Exception e){

                }
            }
        });
        return vista;
    }
    private class UnaTarea extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            swipeRefreshLayout.setRefreshing(false);
            cartprice=0;
            if(!AdapterMercadona.mercadonaCartProducts.isEmpty()){
                cartempty.setVisibility(View.INVISIBLE);
                for(mercadonaProducts product : AdapterMercadona.mercadonaCartProducts){
                    cartprice+=product.getTotalprice();
                }
                totalprice.setText(String.format("Precio Total: %.2f €",cartprice));

            }else{
                cartempty.setVisibility(View.VISIBLE);
                totalprice.setText(String.format("Precio Total: %.2f €",cartprice));
            }
            Toast.makeText(getActivity(),"Carrito de Compra Acutalizado",Toast.LENGTH_SHORT).show();
        }
    }
    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
    public void moveToDescription(mercadonaProducts item){
        Bundle bundle = new Bundle();
        Intent intent = new Intent(getContext(), ItemDetail.class);
        bundle.putString("itemName", item.getCartproduct_name());
        bundle.putString("itemPrice", item.getCartprice().toString());
        bundle.putString("itemLink", item.getCartlink());
        bundle.putString("itemPricePerKg", "Price per kg/unit/lb: no data");
        bundle.putString("supermarketType","mercadona");
        intent.putExtras(bundle);
        startActivity(intent);
    }
}