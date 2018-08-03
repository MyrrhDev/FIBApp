package com.odysseus.fibapp.ServiceSettings;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;


// This is the interface definition which is passed
// to ServiceGenerator to create a Retrofit HTTP client.
public interface AccessTokenService {
    @FormUrlEncoded
    @POST("o/token")
    Call<TokenResponse> getAccessToken(
            @Field("grant_type") String grantType,
            @Field("code") String code,
            @Field("redirect_uri") String redirectURI,
            @Field("client_id") String clientID,
            @Field("client_secret") String client_secret);

    @FormUrlEncoded
    @POST("o/token")
    Call<TokenResponse> getRefreshToken(
            @Field("grant_type") String grantType,
            @Field("refresh_token") String code,
            @Field("client_id") String clientID,
            @Field("client_secret") String client_secret);

}
