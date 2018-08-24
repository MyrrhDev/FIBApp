package com.odysseus.fibapp.Fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;
import android.widget.Button;
import android.widget.TextView;


import com.odysseus.fibapp.Constants.OAuthParams;
import com.odysseus.fibapp.LoginActivity;
import com.odysseus.fibapp.Models.Asignaturas;
import com.odysseus.fibapp.Models.Result;
import com.odysseus.fibapp.Models.Schedule;
import com.odysseus.fibapp.Models.Subjects;
import com.odysseus.fibapp.Models.User;
import com.odysseus.fibapp.R;
import com.odysseus.fibapp.ServiceSettings.AccessTokenService;
import com.odysseus.fibapp.ServiceSettings.RacoAPIService;
import com.odysseus.fibapp.ServiceSettings.ServiceGenerator;
import com.odysseus.fibapp.ServiceSettings.TokenResponse;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HorarioFragment extends Fragment {


    private List<Subjects> classes;
    private int count;
    SharedPreferences prefs;

    String accessToken;

    RacoAPIService racoAPIService;
    TokenResponse ts;

    View v;

    TextView m1,m2,m3,m4,m5,m6,m7,m8,m9,m10,m11,m12,m13;
    TextView t1,t2,t3,t4,t5,t6,t7,t8,t9,t10,t11,t12,t13;
    TextView w1,w2,w3,w4,w5,w6,w7,w8,w9,w10,w11,w12,w13;
    TextView th1,th2,th3,th4,th5,th6,th7,th8,th9,th10,th11,th12,th13;
    TextView f1,f2,f3,f4,f5,f6,f7,f8,f9,f10,f11,f12,f13;



    public HorarioFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        v = inflater.inflate(R.layout.fragment_horario, container, false);
        prefs = getActivity().getSharedPreferences("com.inlab.racodemoapi", Context.MODE_PRIVATE);

        m1 = (TextView) v.findViewById(R.id.dillEight);
        m2 = v.findViewById(R.id.dillNine);
        m3 = v.findViewById(R.id.dillTen);

        t3 = v.findViewById(R.id.dimTen);
        t4 = v.findViewById(R.id.dimEleven);
        t5 = v.findViewById(R.id.dimTwelve);
        t6 = v.findViewById(R.id.dimOne);

        th3 = v.findViewById(R.id.dijTen);
        th4 = v.findViewById(R.id.dijEleven);

        f1 = v.findViewById(R.id.dijTen);
        f2 = v.findViewById(R.id.dijEleven);
        f3 = v.findViewById(R.id.dijTwelve);
        f4 = v.findViewById(R.id.dijOne);
        f5 = v.findViewById(R.id.dijTwo);
        f6 = v.findViewById(R.id.dijThree);

        /*m2,m3,m4,m5,m6,m7,m8,m9,m10,m11,m12,m13;
        TextView t1,t2,t3,t4,t5,t6,t7,t8,t9,t10,t11,t12,t13;
        TextView w1,w2,w3,w4,w5,w6,w7,w8,w9,w10,w11,w12,w13;
        TextView th1,th2,th3,th4,th5,th6,th7,th8,th9,th10,th11,th12,th13;
        TextView f1,f2,f3,f4,f5,f6,f7,f8,f9,f10,f11,f12,f13;*/


        getData();
        drawData();


        //getActivity() es el getContext() de fragmentos


        return v;
    }


    void getData () {
        classes = new ArrayList<>();
        accessToken = prefs.getString("accessToken", null);
        racoAPIService = ServiceGenerator.createService(RacoAPIService.class, OAuthParams.clientID, OAuthParams.clientSecret, accessToken, getActivity());
        Call<Schedule> classescall = racoAPIService.getMySchedule();

        classescall.enqueue(new Callback<Schedule>() {
            @Override
            public void onResponse(Call<Schedule> call, Response<Schedule> response) {
                if (response.isSuccessful()) {
                    classes = response.body().getResults();
                    count = response.body().getCount();
                    Log.v("count", String.valueOf(count));
                    //drawData();

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
            public void onFailure(Call<Schedule> call, Throwable t) {

            }
        });

    }


    private void drawData() {

        String displayClass = null;
        String whereClass= null;


        Log.v("count2", String.valueOf(count));

        for (int i = 0; i < count; ++i) {
            Subjects temp = classes.get(i);

            displayClass = "GRAU-" + temp.getCodiAssig() + " " + temp.getGrup() + " " + temp.getTipus();
            whereClass = "[" + temp.getAules() + "]";

            Log.v("asignaturas.get(i)", String.valueOf(classes.get(i)));



        }



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
