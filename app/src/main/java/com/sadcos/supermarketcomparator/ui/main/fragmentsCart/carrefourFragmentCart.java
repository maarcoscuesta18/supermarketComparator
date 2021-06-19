package com.sadcos.supermarketcomparator.ui.main.fragmentsCart;


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
import com.sadcos.supermarketcomparator.adapters.supermercadosAdapters.AdapterCarrefour;
import com.sadcos.supermarketcomparator.adapters.cartAdapters.carrefourCartAdapter;
import com.sadcos.supermarketcomparator.products.stringPriceProducts;

import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */
public class carrefourFragmentCart extends Fragment  {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private OnFragmentInteractionListener mListener;

    RecyclerView recyclerCartCarrefour;
    public static ArrayList<stringPriceProducts> listCartCarrefour;
    TextView totalprice,cartempty;
    double cartprice=0;
    public SwipeRefreshLayout swipeRefreshLayout;

    public carrefourFragmentCart() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ListaPersonajesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static carrefourFragmentCart newInstance(String param1, String param2) {
        carrefourFragmentCart fragment = new carrefourFragmentCart();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View vista=inflater.inflate(R.layout.fragment_cart, container, false);
        listCartCarrefour=new ArrayList<>();
        recyclerCartCarrefour= (RecyclerView) vista.findViewById(R.id.recyclerId);
        recyclerCartCarrefour.setLayoutManager(new LinearLayoutManager(getContext()));
        carrefourCartAdapter adapter=new carrefourCartAdapter(AdapterCarrefour.carrefourCartProducts,new carrefourCartAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(stringPriceProducts item) {
                moveToDescription(item);
            }
        });
        recyclerCartCarrefour.setAdapter(adapter);

        //set cart totalprice
        totalprice =vista.findViewById(R.id.totalprice);
        cartprice=0;
        cartempty = vista.findViewById(R.id.cartempty);
        try{
            if(!AdapterCarrefour.carrefourCartProducts.isEmpty()){
                cartempty.setVisibility(View.INVISIBLE);
                for(stringPriceProducts product : AdapterCarrefour.carrefourCartProducts){
                    cartprice+=product.getTotalprice();
                }
                totalprice.setText(String.format("Precio Total: %.2f €",cartprice));
            }else{
                cartempty.setVisibility(View.VISIBLE);
                totalprice.setText(String.format("Precio Total: %.2f €",cartprice));
            }
        }catch (Exception e){

        }


        //refresh the cart price
        swipeRefreshLayout = vista.findViewById(R.id.fragmentLayout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new UnaTarea().execute();
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
            try{
                if(!AdapterCarrefour.carrefourCartProducts.isEmpty()){
                    cartempty.setVisibility(View.INVISIBLE);
                    for(stringPriceProducts product : AdapterCarrefour.carrefourCartProducts){
                        cartprice+=product.getTotalprice();
                    }
                    totalprice.setText(String.format("Precio Total: %.2f €",cartprice));
                }else{
                    cartempty.setVisibility(View.VISIBLE);
                    totalprice.setText(String.format("Precio Total: %.2f €",cartprice));
                }
            }catch (Exception e){

            }
            Toast.makeText(getContext(),"Carrito de Compra Acutalizado",Toast.LENGTH_SHORT).show();
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
    public void moveToDescription(stringPriceProducts item){
        Bundle bundle = new Bundle();
        Intent intent = new Intent(getContext(), ItemDetail.class);
        bundle.putString("itemName", item.getCartproduct_name());
        bundle.putString("itemPrice", item.getCartprice().toString());
        bundle.putString("itemLink", item.getCartlink());
        bundle.putString("itemPricePerKg", item.getCartpriceperkg());
        bundle.putString("supermarketType","carrefour");
        intent.putExtras(bundle);
        startActivity(intent);
    }
}