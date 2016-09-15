package com.example.hongcheng.data.request;

import com.example.hongcheng.data.HttpConstants;
import com.example.hongcheng.data.response.CardResponse;
import com.example.hongcheng.data.response.CardDetailResponse;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by hongcheng on 16/9/11.
 */
public interface CardRetrofit {

    @GET(HttpConstants.GET_CARDS_URL)
    Observable<CardResponse> listCards();

    @GET(HttpConstants.GET_CARDS_DETAIL)
    Observable<CardDetailResponse> getCardDetail(@Query("type") String type);
}
