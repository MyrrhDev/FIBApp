package com.odysseus.fibapp.Fragments;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.support.v4.app.Fragment;

import com.odysseus.fibapp.Constants.OAuthParams;
import com.odysseus.fibapp.LoginActivity;
import com.odysseus.fibapp.Models.Asignaturas;
import com.odysseus.fibapp.Models.Result;
import com.odysseus.fibapp.Models.User;
import com.odysseus.fibapp.R;
import com.odysseus.fibapp.ServiceSettings.AccessTokenService;
import com.odysseus.fibapp.ServiceSettings.RacoAPIService;
import com.odysseus.fibapp.ServiceSettings.ServiceGenerator;
import com.odysseus.fibapp.ServiceSettings.TokenResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.TextView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import io.realm.Realm;
import io.realm.RealmResults;

public class Notas extends Fragment {
    private ExpandableListView listView;
    private ExpandableListAdapter listAdapter;

    private List<String> listDataHeader; //Result, Lista de asignaturas
    private HashMap<String,List<String>> listHash;

    private List <Result> asignaturas;
    private int count;
    SharedPreferences prefs;

    String accessToken;

    RacoAPIService racoAPIService;
    TokenResponse ts;

    View v;

    public Notas() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        v = inflater.inflate(R.layout.fragment_notas, container, false);

        listView = (ExpandableListView) v.findViewById(R.id.lvExp);
        prefs = getActivity().getSharedPreferences("com.inlab.racodemoapi", Context.MODE_PRIVATE);



        //getActivity() es el getContext() de fragmentos
        //listAdapter = new ExpandableListAdapter(getActivity(),listDataHeader,listHash);
        //listView.setAdapter(listAdapter);
        getData();

        return v;
    }


    void getData () {
        asignaturas = new ArrayList<>();
        accessToken = prefs.getString("accessToken", null);
        racoAPIService = ServiceGenerator.createService(RacoAPIService.class, OAuthParams.clientID, OAuthParams.clientSecret, accessToken, getActivity());
        Call<Asignaturas> call = racoAPIService.getMyClasses();

        call.enqueue(new Callback<Asignaturas>() {
            @Override
            public void onResponse(Call<Asignaturas> call, Response<Asignaturas> response) {
                if (response.isSuccessful()) {
                    asignaturas = response.body().getResults();
                    count = response.body().getCount();
                    Log.v("count", String.valueOf(count));
                    drawData();

                }
                else {
                    if (response.code() == 401) {
                        // The call returns 401 if the access token has expired
                        refreshAccessToken();
                    }
                    else {
                        showErrorDialog(response.code());
                    }
                }
            }

            @Override
            public void onFailure(Call<Asignaturas> call, Throwable t) {

            }
        });

    }



    //Loads Asignaturas Data

    private void drawData() {

        listDataHeader = new ArrayList<>();
        listHash = new HashMap<>();


        Log.v("count2", String.valueOf(count));

        for (int i = 0; i < count; ++i) {
            Result temp = asignaturas.get(i);
            Log.v("asignaturas.get(i)", String.valueOf(asignaturas.get(i)));

            listDataHeader.add(temp.getId());

            Log.v("temp.getId()", String.valueOf(temp.getId()));
        }


        listAdapter = new ExpandableListAdapter(getActivity(),listDataHeader,listHash);
        listView.setAdapter(listAdapter);


    }





    private void refreshAccessToken() {
        AccessTokenService ats = ServiceGenerator.createService(AccessTokenService.class);
        String refreshToken = prefs.getString("refreshToken", null);
        Call<TokenResponse> call = ats.getRefreshToken("refresh_token", refreshToken, OAuthParams.clientID, OAuthParams.clientSecret);
        call.enqueue(new Callback<TokenResponse>() {
            @Override
            public void onResponse(Call<TokenResponse> call, Response<TokenResponse> response) {
                if (response.isSuccessful()) {
                    ts = response.body();
                    // Save the new values
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putString("accessToken", ts.getAccessToken());
                    System.out.println(ts.getAccessToken());
                    editor.putString("refreshToken", ts.getRefreshToken());
                    editor.apply();
                    // At this point, we go back to the call to get the user's info that will be displayed
                    getData();
                }
                else {
                    showErrorDialog(response.code());
                }

            }

            @Override
            public void onFailure(Call<TokenResponse> call, Throwable t) {
                Log.d("onFailure", t.toString());
            }
        });


    }

    private void showErrorDialog(int code) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Codi d'error: "+ code)
                .setTitle(R.string.dialog_error_title);
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked OK button
                clearPrefs();
                goToLogin();
            }
        });
        builder.create();
        builder.show();
    }




    private void goToLogin() {
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
    }

    private void clearPrefs() {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("accessToken", null);
        editor.putString("refreshToken", null);
        editor.putBoolean("isLogged", false);
        editor.apply();
    }


}
