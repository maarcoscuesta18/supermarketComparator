package com.sadcos.supermarketcomparator.ui.main;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.sadcos.supermarketcomparator.adapters.AdapterMercadona;
import com.sadcos.supermarketcomparator.products.mercadonaProducts;

public class PageViewModel extends ViewModel {

    private MutableLiveData<Integer> mIndex = new MutableLiveData<>();
    private LiveData<String> mText = Transformations.map(mIndex, new Function<Integer, String>() {
        @Override
        public String apply(Integer input) {
            String producto;
            if(input==1){
                if(AdapterMercadona.mercadonaCartProducts.isEmpty()){
                    return "Nada en el Carrito. Añade un producto";
                }else{
                    return AdapterMercadona.mercadonaCartProducts.toString();
                }
            }else if(input==2){
                return "Hello world from section22: " + input;
            }else{
                return "Hello world from section333: " + input;
            }
        }
    });

    public void setIndex(int index) {
        mIndex.setValue(index);
    }

    public LiveData<String> getText() {
        return mText;
    }
}