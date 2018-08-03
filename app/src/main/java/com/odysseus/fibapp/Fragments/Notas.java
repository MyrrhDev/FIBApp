package com.odysseus.fibapp.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.odysseus.fibapp.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

public class Notas extends Fragment {


    private ExpandableListView listView;
    private ExpandableListAdapter listAdapter;
    private List<String> listDataHeader;
    private HashMap<String,List<String>> listHash;

    public Notas() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View v = inflater.inflate(R.layout.fragment_notas, container, false);

        listView = (ExpandableListView) v.findViewById(R.id.lvExp);
        initData();
        listAdapter = new ExpandableListAdapter(getActivity(),listDataHeader,listHash);
        listView.setAdapter(listAdapter);

        return v;
    }

    //Loads Asignaturas Data
    private void initData() {
        listDataHeader = new ArrayList<>();
        listHash = new HashMap<>();

         //Añade los nombres de las asignaturas del estudiante
        listDataHeader.add("EDMTDev");
        listDataHeader.add("Android");
        listDataHeader.add("Xamarin");
        listDataHeader.add("UWP");

        //Aqui se añade contenido a cada despliege, queda personalizarlo para que cargue las asignaturas
        List<String> edmtDev = new ArrayList<>();
        edmtDev.add("This is Expandable ListView");

        List<String> androidStudio = new ArrayList<>();
        androidStudio.add("Expandable ListView");
        androidStudio.add("Google Map");
        androidStudio.add("Chat Application");
        androidStudio.add("Firebase ");

        List<String> xamarin = new ArrayList<>();
        xamarin.add("Xamarin Expandable ListView");
        xamarin.add("Xamarin Google Map");
        xamarin.add("Xamarin Chat Application");
        xamarin.add("Xamarin Firebase ");

        List<String> uwp = new ArrayList<>();
        uwp.add("UWP Expandable ListView");
        uwp.add("UWP Google Map");
        uwp.add("UWP Chat Application");
        uwp.add("UWP Firebase ");

        listHash.put(listDataHeader.get(0),edmtDev);
        listHash.put(listDataHeader.get(1),androidStudio);
        listHash.put(listDataHeader.get(2),xamarin);
        listHash.put(listDataHeader.get(3),uwp);
    }
}