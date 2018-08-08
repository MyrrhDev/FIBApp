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
import com.odysseus.fibapp.Models.User;
import com.odysseus.fibapp.R;
import com.odysseus.fibapp.ServiceSettings.AccessTokenService;
import com.odysseus.fibapp.ServiceSettings.RacoAPIService;
import com.odysseus.fibapp.ServiceSettings.ServiceGenerator;
import com.odysseus.fibapp.ServiceSettings.TokenResponse;

import org.w3c.dom.Text;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HorarioFragment extends Fragment {

    public HorarioFragment() {
        // Required empty public constructor
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View v = inflater.inflate(R.layout.fragment_horario, container, false);




        //getActivity() es el getContext() de fragmentos


        return v;
    }






}
