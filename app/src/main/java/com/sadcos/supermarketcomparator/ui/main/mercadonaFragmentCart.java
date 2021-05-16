package com.sadcos.supermarketcomparator.ui.main;


import android.content.Context;
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

import com.sadcos.supermarketcomparator.R;
import com.sadcos.supermarketcomparator.adapters.AdapterMercadona;
import com.sadcos.supermarketcomparator.adapters.mercadonaCartAdapter;
import com.sadcos.supermarketcomparator.products.mercadonaProducts;

import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */
public class mercadonaFragmentCart extends Fragment  {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private OnFragmentInteractionListener mListener;

    RecyclerView recyclerCartMercadona;
    public static ArrayList<mercadonaProducts> listCartMercadona;
    TextView totalprice;
    double cartprice=0;
    public SwipeRefreshLayout swipeRefreshLayout;

    public mercadonaFragmentCart() {
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
    public static mercadonaFragmentCart newInstance(String param1, String param2) {
        mercadonaFragmentCart fragment = new mercadonaFragmentCart();
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
        listCartMercadona=new ArrayList<>();
        recyclerCartMercadona= (RecyclerView) vista.findViewById(R.id.recyclerId);
        recyclerCartMercadona.setLayoutManager(new LinearLayoutManager(getContext()));
        mercadonaCartAdapter adapter=new mercadonaCartAdapter(AdapterMercadona.mercadonaCartProducts);
        recyclerCartMercadona.setAdapter(adapter);

        //set cart totalprice
        totalprice =vista.findViewById(R.id.totalprice);
        cartprice=0;
        if(!AdapterMercadona.mercadonaCartProducts.isEmpty()){
            for(mercadonaProducts product : AdapterMercadona.mercadonaCartProducts){
                cartprice+=product.getTotalprice();
            }
            totalprice.setText(String.format("Precio Total: %.2f €",cartprice));

        }else{
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
                for(mercadonaProducts product : AdapterMercadona.mercadonaCartProducts){
                    cartprice+=product.getTotalprice();
                }
                totalprice.setText(String.format("Precio Total: %.2f €",cartprice));

            }else{
                totalprice.setText(String.format("Precio Total: %.2f €",cartprice));
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
}