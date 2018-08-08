package com.odysseus.fibapp.Fragments;

import android.content.Context;
import android.graphics.Typeface;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.odysseus.fibapp.Asignaturas.CalculoAC;
import com.odysseus.fibapp.R;

import java.util.HashMap;
import java.util.List;

public class ExpandableListAdapter extends BaseExpandableListAdapter {
    private Context context;
    private List<String> listDataHeader;

    private HashMap<String,List<String>> listHashMap;



    public ExpandableListAdapter(Context context, List<String> listDataHeader, HashMap<String, List<String>> listHashMap) {
        this.context = context;
        this.listDataHeader = listDataHeader;
        this.listHashMap = listHashMap;
    }

    @Override
    public int getGroupCount() {
        return listDataHeader.size();
    }


    @Override
    public int getChildrenCount(int i) {
        return 1;
        //return listHashMap.get(listDataHeader.get(i)).size();
    }

    @Override
    public Object getGroup(int i) {
        return listDataHeader.get(i);
    }

    @Override
    public Object getChild(int i, int i1) {
        return listHashMap.get(listDataHeader.get(i)).get(i1); // i = Group Item , i1 = ChildItem
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i1) {
        return i1;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
        String headerTitle = (String)getGroup(i);
        if(view == null)
        {
            LayoutInflater inflater = (LayoutInflater)this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            //view = inflater.inflate(R.layout.list_nomasignatura,null);
            view = inflater.inflate(R.layout.list_nomasignatura, null);
        }
        TextView lblListHeader = (TextView)view.findViewById(R.id.lblListHeader);
        lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setText(headerTitle);
        return view;
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        final String parentAsig = (String)getGroup(i);

        /*if(view == null) //Daba problema:  java.lang.NullPointerException: Attempt to invoke virtual method 'int android.view.View.getImportantForAccessibility()' on a null object reference
        al cargar este fragmento ir a otro y volver porque ya no era null supongo (?)
        {*/
            LayoutInflater inflater = (LayoutInflater)this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            //Inflate CardView - aqui buscamos cual
            if (parentAsig.equals("AC")){
                view = inflater.inflate(R.layout.cardview_ac, null);
                final double[] result = {0};
                final EditText control1 = view.findViewById(R.id.result1);
                final EditText control2 = view.findViewById(R.id.result2);
                final EditText control3 = view.findViewById(R.id.result3);
                final EditText controllab = view.findViewById(R.id.resultlab);
                final TextView res = view.findViewById(R.id.showResultText);
                final double[] c1 = {0};
                final double[] c2 = {0};
                final double[] c3 = {0};
                final double[] c4 = {0};

                //Esto cambia automaticamente los editTexts
                final View finalView = view;
                TextWatcher textWatcher = new TextWatcher() {
                    public void afterTextChanged(Editable s) {
                        if (control1.getText().equals("") || TextUtils.isEmpty(control1.getText())) {
                            c1[0] = 0;
                        } else if (!TextUtils.isDigitsOnly(control1.getText())) {
                            Toast.makeText(finalView.getContext(), "Sólo numberos, pues", Toast.LENGTH_SHORT).show();
                            control1.setText("");
                        } else c1[0] = Double.parseDouble(control1.getText().toString());

                        if (control2.getText().equals("") || TextUtils.isEmpty(control2.getText())) {
                            c2[0] = 0;
                        } else if (!TextUtils.isDigitsOnly(control2.getText())) {
                            Toast.makeText(finalView.getContext(), "Sólo numberos, pues", Toast.LENGTH_SHORT).show();
                            control2.setText("");
                        } else c2[0] = Double.parseDouble(control2.getText().toString());

                        if (control3.getText().equals("") || TextUtils.isEmpty(control3.getText())) {
                            c3[0] = 0;
                        } else if (!TextUtils.isDigitsOnly(control3.getText())) {
                            Toast.makeText(finalView.getContext(), "Sólo numberos, pues", Toast.LENGTH_SHORT).show();
                            control3.setText("");
                        } else c3[0] = Double.parseDouble(control3.getText().toString());


                        if (controllab.getText().equals("") || TextUtils.isEmpty(controllab.getText())) {
                            c4[0] = 0;
                        } else if (!TextUtils.isDigitsOnly(controllab.getText())) {
                            Toast.makeText(finalView.getContext(), "Sólo numberos, pues", Toast.LENGTH_SHORT).show();
                            controllab.setText("");
                        } else c4[0] = Double.parseDouble(controllab.getText().toString());


                        CalculoAC.calculate(c1[0], c2[0], c3[0], c4[0]);
                        result[0] = CalculoAC.getNotafinal();
                        res.setText(Double.toString(result[0]));
                    }
                    public void beforeTextChanged(CharSequence s, int start, int count, int after){}
                    public void onTextChanged(CharSequence s, int start, int before, int count){}
                };

                // Adds the TextWatcher as TextChangedListener to both EditTexts
                control1.addTextChangedListener(textWatcher);
                control2.addTextChangedListener(textWatcher);
                control3.addTextChangedListener(textWatcher);
                controllab.addTextChangedListener(textWatcher);
            }
            else if (parentAsig.equals("CI")){
                view = inflater.inflate(R.layout.cardview_ci, null);



            }
            else if (parentAsig.equals("IDI")){
                view = inflater.inflate(R.layout.cardview_idi, null);
            }
            else if (parentAsig.equals("IES")){
                view = inflater.inflate(R.layout.cardview_ies, null);
            }
        //}



        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }
}
