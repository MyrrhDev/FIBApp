package com.odysseus.fibapp.ServiceSettings;

import com.odysseus.fibapp.Models.Asignaturas;
import com.odysseus.fibapp.Models.User;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;


public interface RacoAPIService {
        /**
         * Here we have all the calls to the API endpoints.
         */
        @Headers("Accept: application/json")
        @GET("jo/")
        Call<User> getMyInfo(
        );

        @Headers("Accept: image/jpeg")
        @GET("jo/foto.jpg")
        Call<ResponseBody> getMyPhoto(
        );

        @Headers("Accept: application/json")
        @GET("jo/assignatures")
        Call<Asignaturas> getMyClasses(
        );

}
