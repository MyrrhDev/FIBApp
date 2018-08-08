package com.odysseus.fibapp;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import com.odysseus.fibapp.Constants.OAuthParams;
import com.odysseus.fibapp.Fragments.HorarioFragment;
import com.odysseus.fibapp.Fragments.MenuActivity;
import com.odysseus.fibapp.Fragments.Notas;
import com.odysseus.fibapp.Models.User;
import com.odysseus.fibapp.ServiceSettings.AccessTokenService;
import com.odysseus.fibapp.ServiceSettings.RacoAPIService;
import com.odysseus.fibapp.ServiceSettings.ServiceGenerator;
import com.odysseus.fibapp.ServiceSettings.TokenResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    //Main Activity es el Drawer!
    DrawerLayout drawer;
    NavigationView navigationView;
    TextView name;


    SharedPreferences prefs;
    String accessToken;
    RacoAPIService racoAPIService;
    TokenResponse ts;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
/*
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        Log.v("next drawer", "next drawer");

        prefs = this.getSharedPreferences("com.inlab.racodemoapi", Context.MODE_PRIVATE);
        //getUserInfo();

        Fragment f = new MenuActivity();
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.fragment_container,f);
        ft.commit();

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menuActivity; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        Fragment f = null;
        navigationView.setCheckedItem(id);

        if (id == R.id.nav_notas) {
            f = new Notas();

        }  else if (id == R.id.nav_avisos) {

        } else if (id == R.id.nav_calendario) {

        } else if (id == R.id.nav_horario) {
            f = new HorarioFragment();

        } else if (id == R.id.log_out) {
            clearPrefs();
            goToLogin();
        }

        if(f != null){
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.fragment_container,f);
            ft.commit();
        }
        Log.v("next frag", "next frag");
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
/*
    private void getUserInfo() {

        accessToken = prefs.getString("accessToken", null);
        racoAPIService = ServiceGenerator.createService(RacoAPIService.class, OAuthParams.clientID, OAuthParams.clientSecret, accessToken, this);
        Call<User> call1 = racoAPIService.getMyInfo();

        final TextView textViewJo = (TextView) findViewById(R.id.textViewJo);
        final TextView textViewUsername = (TextView) findViewById(R.id.textViewUsername);
        final TextView textViewEmail = (TextView) findViewById(R.id.textViewEmail);
        final TextView textViewAccessToken = (TextView) findViewById(R.id.textViewAccessToken);
        final TextView textViewRefreshToken = (TextView) findViewById(R.id.textViewRefreshToken);
        call1.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    String jo = response.body().getNom();
                    jo += " " + response.body().getCognoms();
                    String username = response.body().getUsername();
                    String email = response.body().getEmail();
                    textViewJo.setText(jo);
                    textViewUsername.setText(username);
                    textViewEmail.setText(email);
                    String textAccessToken = "Access Token: " + accessToken;
                    textViewAccessToken.setText(textAccessToken);
                    String textRefreshToken = "Refresh Token: " + prefs.getString("refreshToken", null);
                    textViewRefreshToken.setText(textRefreshToken);

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

                getUserPhoto();
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.d("onFailure", t.toString());
            }
        });
    }
*/
    private Activity getActivity() {
        return this;
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
                    //getUserInfo();
                } else {
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
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
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
