package com.sadcos.supermarketcomparator.apis;

import com.sadcos.supermarketcomparator.products.mercadonaProducts;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by haerul on 17/03/18.
 */

public interface ApiInterfaceMercadona {

    @GET("getproductsMercadona.php")
    Call<List<mercadonaProducts>> getProduct(
            @Query("item_type") String item_type,
            @Query("key") String keyword
    );
}
