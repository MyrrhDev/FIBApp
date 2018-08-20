package com.odysseus.fibapp.Fragments;

import android.content.Context;
import android.graphics.Typeface;
import android.text.Editable;
import android.text.Layout;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.odysseus.fibapp.Asignaturas.CalculoAC;
import com.odysseus.fibapp.Asignaturas.CalculoCI;
import com.odysseus.fibapp.Asignaturas.CalculoIDI;
import com.odysseus.fibapp.Asignaturas.CalculoIES;
import com.odysseus.fibapp.R;

import java.util.HashMap;
import java.util.List;
import java.util.zip.Inflater;

import io.realm.Realm;
import io.realm.RealmResults;

import static com.odysseus.fibapp.R.layout.list_nomasignatura;

public class ExpandableListAdapter extends BaseExpandableListAdapter {
    private Context context;
    private List<String> listDataHeader;
    private HashMap<String,List<String>> listHashMap;

    double[] c1 = {0};
    double[] c2 = {0};
    double[] c3 = {0};
    double[] c4 = {0};
    double[] c5 = {0};
    double[] c6 = {0};
    double[] result = {0};

    EditText control1;
    EditText control2;
    EditText control3;
    EditText controllab;

    EditText examen1;
    EditText examen2;
    EditText examen3;
    EditText participacion;


    TextView res;
    Button save;

    LinearLayout mainLayout;
    LinearLayout asigHead;

    //init Realm/load realm
    Realm realm;





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
            view = inflater.inflate(list_nomasignatura, null);
            asigHead = view.findViewById(R.id.asigHeader);
            asigHead.setOnTouchListener(new View.OnTouchListener()
            {
                @Override
                public boolean onTouch(View view, MotionEvent ev)
                {
                    hideKeyboard(view);
                    return false;
                }
            });

        }
        TextView lblListHeader = (TextView)view.findViewById(R.id.lblListHeader);
        lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setText(headerTitle);
        return view;
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        final String parentAsig = (String)getGroup(i);

        Realm.init(this.context);
        realm = Realm.getDefaultInstance();



        /*if(view == null) //Daba problema:  java.lang.NullPointerException: Attempt to invoke virtual method 'int android.view.View.getImportantForAccessibility()' on a null object reference
        al cargar este fragmento ir a otro y volver porque ya no era null supongo (?)
        {*/
            LayoutInflater inflater = (LayoutInflater)this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            //Inflate CardView - aqui buscamos cual


            if (parentAsig.equals("AC")){
                view = inflater.inflate(R.layout.cardview_ac, null);

                mainLayout = view.findViewById(R.id.mainlayout);

                control1 = view.findViewById(R.id.result1);
                control2 = view.findViewById(R.id.result2);
                control3 = view.findViewById(R.id.result3);
                controllab = view.findViewById(R.id.resultlab);
                res = view.findViewById(R.id.showResultText);
                save = view.findViewById(R.id.saveButton);


                mainLayout.setOnTouchListener(new View.OnTouchListener()
                {
                    @Override
                    public boolean onTouch(View view, MotionEvent ev)
                    {
                        hideKeyboard(view);
                        return false;
                    }
                });


                RealmResults<CalculoAC> acResults;
                CalculoAC acSaved = new CalculoAC();
                acResults = realm.where(CalculoAC.class).findAll();


                if(acResults.isEmpty()) {
                    Log.v("nothing saved yet:", "nothing");

                } else {
                    acSaved = realm.where(CalculoAC.class).equalTo("name", "ac").findFirst();
                    control1.setText(String.valueOf(acSaved.getControl1()));
                    control2.setText(String.valueOf(acSaved.getControl2()));
                    control3.setText(String.valueOf(acSaved.getControl3()));
                    controllab.setText(String.valueOf(acSaved.getLab()));
                    res.setText(String.valueOf(String.format("%.2f", acSaved.getNotafinal())));
                }

                control1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        control1.setText("");
                    }
                });
                control2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        control2.setText("");
                    }
                });
                control3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        control3.setText("");
                    }
                });
                controllab.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        controllab.setText("");
                    }
                });

                final CalculoAC finalAcSaved = acSaved;
                TextWatcher textWatcher = new TextWatcher() {
                    public void afterTextChanged(Editable s) {
                        if (control1.getText().equals("") || TextUtils.isEmpty(control1.getText())) {
                            c1[0] = 0;
                        } else c1[0] = Double.parseDouble(control1.getText().toString());

                        if (control2.getText().equals("") || TextUtils.isEmpty(control2.getText())) {
                            c2[0] = 0;
                        }  else c2[0] = Double.parseDouble(control2.getText().toString());

                        if (control3.getText().equals("") || TextUtils.isEmpty(control3.getText())) {
                            c3[0] = 0;
                        } else c3[0] = Double.parseDouble(control3.getText().toString());
                        if (controllab.getText().equals("") || TextUtils.isEmpty(controllab.getText())) {
                            c4[0] = 0;
                        } else c4[0] = Double.parseDouble(controllab.getText().toString());

                        Log.v("c1[0]", String.valueOf(c1[0]));

                        finalAcSaved.calculate(c1[0], c2[0], c3[0], c4[0]);
                        result[0] = finalAcSaved.getNotafinal();
                        res.setText(String.valueOf(String.format("%.2f", result[0])));
                    }
                    public void beforeTextChanged(CharSequence s, int start, int count, int after){}
                    public void onTextChanged(CharSequence s, int start, int before, int count){}
                };


                // Adds the TextWatcher as TextChangedListener to both EditTexts
                control1.addTextChangedListener(textWatcher);
                control2.addTextChangedListener(textWatcher);
                control3.addTextChangedListener(textWatcher);
                controllab.addTextChangedListener(textWatcher);


                save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final CalculoAC ac = new CalculoAC();
                        ac.setName("ac");
                        Log.v("c111[0]", String.valueOf(c1[0]));
                        ac.setControl1(c1[0]);
                        Log.v("c222[0]", String.valueOf(c2[0]));
                        ac.setControl2(c2[0]);
                        ac.setControl3(c3[0]);
                        ac.setLab(c4[0]);
                        ac.setNotafinal(result[0]);
                        realm.executeTransaction(new Realm.Transaction() {
                            @Override
                            public void execute(Realm realm) {
                                // This will update an existing object with the same primary key
                                // or create a new object if an object with no primary key = 42
                                realm.copyToRealmOrUpdate(ac);

                            }
                        });
                    }
                });



            }
            else if (parentAsig.equals("CI")){
                view = inflater.inflate(R.layout.cardview_ci, null);
                //View genview = inflater.inflate(R.layout.list_nomasignatura, null);
                //asigHead = genview.findViewById(R.id.asigHeader);

                mainLayout = view.findViewById(R.id.mainlayout);
                control1 = view.findViewById(R.id.result1);
                control2 = view.findViewById(R.id.result2);
                control3 = view.findViewById(R.id.result3);
                controllab = view.findViewById(R.id.resultlab);
                res = view.findViewById(R.id.showResultText);
                save = view.findViewById(R.id.saveButton);

                mainLayout.setOnTouchListener(new View.OnTouchListener()
                {
                    @Override
                    public boolean onTouch(View view, MotionEvent ev)
                    {
                        hideKeyboard(view);
                        return false;
                    }
                });


                RealmResults<CalculoCI> ciResults;
                CalculoCI ciSaved = new CalculoCI();
                ciResults = realm.where(CalculoCI.class).findAll();


                if(ciResults.isEmpty()) {
                    Log.v("nothing saved yet:", "nothing");

                } else {
                    ciSaved = realm.where(CalculoCI.class).equalTo("name", "ci").findFirst();
                    control1.setText(String.valueOf(ciSaved.getControl1()));
                    control2.setText(String.valueOf(ciSaved.getControl2()));
                    control3.setText(String.valueOf(ciSaved.getControl3()));
                    controllab.setText(String.valueOf(ciSaved.getLab()));
                    res.setText(String.valueOf(String.format("%.2f", ciSaved.getNotafinal())));
                }

                control1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        control1.setText("");
                    }
                });
                control2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        control2.setText("");
                    }
                });
                control3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        control3.setText("");
                    }
                });
                controllab.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        controllab.setText("");
                    }
                });

                final CalculoCI finalCISaved = ciSaved;
                //Esto cambia automaticamente los editTexts

                TextWatcher textWatcher = new TextWatcher() {
                    public void afterTextChanged(Editable s) {
                        if (control1.getText().equals("") || TextUtils.isEmpty(control1.getText())) {
                            c1[0] = 0;
                        } else c1[0] = Double.parseDouble(control1.getText().toString());

                        if (control2.getText().equals("") || TextUtils.isEmpty(control2.getText())) {
                            c2[0] = 0;
                        } else c2[0] = Double.parseDouble(control2.getText().toString());
                        if (control3.getText().equals("") || TextUtils.isEmpty(control3.getText())) {
                            c3[0] = 0;
                        } else c3[0] = Double.parseDouble(control3.getText().toString());
                        if (controllab.getText().equals("") || TextUtils.isEmpty(controllab.getText())) {
                            c4[0] = 0;
                        } else c4[0] = Double.parseDouble(controllab.getText().toString());


                        finalCISaved.calculate(c1[0], c2[0], c3[0], c4[0]);
                        result[0] = finalCISaved.getNotafinal();
                        res.setText(String.valueOf(String.format("%.2f", result[0])));
                    }
                    public void beforeTextChanged(CharSequence s, int start, int count, int after){}
                    public void onTextChanged(CharSequence s, int start, int before, int count){}
                };

                // Adds the TextWatcher as TextChangedListener to both EditTexts
                control1.addTextChangedListener(textWatcher);
                control2.addTextChangedListener(textWatcher);
                control3.addTextChangedListener(textWatcher);
                controllab.addTextChangedListener(textWatcher);

                save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final CalculoCI ci = new CalculoCI();
                        ci.setName("ci");
                        Log.v("c111[0]", String.valueOf(c1[0]));
                        ci.setControl1(c1[0]);
                        Log.v("c222[0]", String.valueOf(c2[0]));
                        ci.setControl2(c2[0]);
                        ci.setControl3(c3[0]);
                        ci.setLab(c4[0]);
                        ci.setNotafinal(result[0]);
                        realm.executeTransaction(new Realm.Transaction() {
                            @Override
                            public void execute(Realm realm) {
                                // This will update an existing object with the same primary key
                                // or create a new object if an object with no primary key = 42
                                realm.copyToRealmOrUpdate(ci);

                            }
                        });
                    }
                });


            }
            else if (parentAsig.equals("IDI")){
                view = inflater.inflate(R.layout.cardview_idi, null);
                //View genview = inflater.inflate(R.layout.list_nomasignatura, null);
                //asigHead = genview.findViewById(R.id.asigHeader);


                mainLayout = view.findViewById(R.id.mainlayout);

                control1 = view.findViewById(R.id.result1);
                control2 = view.findViewById(R.id.result2);
                controllab = view.findViewById(R.id.resultlab);
                res = view.findViewById(R.id.showResultText);
                save = view.findViewById(R.id.saveButton);

                mainLayout.setOnTouchListener(new View.OnTouchListener()
                {
                    @Override
                    public boolean onTouch(View view, MotionEvent ev)
                    {
                        hideKeyboard(view);
                        return false;
                    }
                });


                RealmResults<CalculoIDI> idiResults;
                CalculoIDI idiSaved = new CalculoIDI();
                idiResults = realm.where(CalculoIDI.class).findAll();


                if(idiResults.isEmpty()) {
                    Log.v("nothing saved yet:", "nothing");

                } else {
                    idiSaved = realm.where(CalculoIDI.class).equalTo("name", "idi").findFirst();
                    control1.setText(String.valueOf(idiSaved.getControl1()));
                    control2.setText(String.valueOf(idiSaved.getControl2()));
                    controllab.setText(String.valueOf(idiSaved.getLab()));
                    res.setText(String.valueOf(String.format("%.2f", idiSaved.getNotafinal())));
                }

                control1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        control1.setText("");
                    }
                });
                control2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        control2.setText("");
                    }
                });
                controllab.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        controllab.setText("");
                    }
                });

                final CalculoIDI finalIDISaved = idiSaved;
                TextWatcher textWatcher = new TextWatcher() {
                    public void afterTextChanged(Editable s) {
                        if (control1.getText().equals("") || TextUtils.isEmpty(control1.getText())) {
                            c1[0] = 0;
                        } else c1[0] = Double.parseDouble(control1.getText().toString());

                        if (control2.getText().equals("") || TextUtils.isEmpty(control2.getText())) {
                            c2[0] = 0;
                        } else c2[0] = Double.parseDouble(control2.getText().toString());


                        if (controllab.getText().equals("") || TextUtils.isEmpty(controllab.getText())) {
                            c3[0] = 0;
                        } else c3[0] = Double.parseDouble(controllab.getText().toString());


                        finalIDISaved.calculate(c1[0], c2[0], c3[0]);
                        result[0] = finalIDISaved.getNotafinal();
                        res.setText(String.valueOf(String.format("%.2f", result[0])));
                    }
                    public void beforeTextChanged(CharSequence s, int start, int count, int after){}
                    public void onTextChanged(CharSequence s, int start, int before, int count){}
                };

                // Adds the TextWatcher as TextChangedListener to both EditTexts
                control1.addTextChangedListener(textWatcher);
                control2.addTextChangedListener(textWatcher);
                controllab.addTextChangedListener(textWatcher);

                save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final CalculoIDI idi = new CalculoIDI();
                        idi.setName("idi");
                        Log.v("c111[0]", String.valueOf(c1[0]));
                        idi.setControl1(c1[0]);
                        Log.v("c222[0]", String.valueOf(c2[0]));
                        idi.setControl2(c2[0]);
                        idi.setLab(c3[0]);
                        idi.setNotafinal(result[0]);
                        realm.executeTransaction(new Realm.Transaction() {
                            @Override
                            public void execute(Realm realm) {
                                // This will update an existing object with the same primary key
                                // or create a new object if an object with no primary key = 42
                                realm.copyToRealmOrUpdate(idi);

                            }
                        });
                    }
                });
            }
            else if (parentAsig.equals("IES")){
                view = inflater.inflate(R.layout.cardview_ies, null);
                //View genview = inflater.inflate(R.layout.list_nomasignatura, null);
                //asigHead = genview.findViewById(R.id.asigHeader);


                mainLayout = view.findViewById(R.id.mainlayout);

                control1 = view.findViewById(R.id.result1);
                control2 = view.findViewById(R.id.result2);
                examen1 = view.findViewById(R.id.resultE1);
                examen2 = view.findViewById(R.id.resultE2);
                examen3 = view.findViewById(R.id.resultE3);
                participacion = view.findViewById(R.id.result3);
                res = view.findViewById(R.id.showResultText);
                save = view.findViewById(R.id.saveButton);


                mainLayout.setOnTouchListener(new View.OnTouchListener()
                {
                    @Override
                    public boolean onTouch(View view, MotionEvent ev)
                    {
                        hideKeyboard(view);
                        return false;
                    }
                });


                RealmResults<CalculoIES> iesResults;
                CalculoIES iesSaved = new CalculoIES();
                iesResults = realm.where(CalculoIES.class).findAll();


                if(iesResults.isEmpty()) {
                    Log.v("nothing saved yet:", "nothing");

                } else {
                    iesSaved = realm.where(CalculoIES.class).equalTo("name", "ies").findFirst();
                    control1.setText(String.valueOf(iesSaved.getControl1()));
                    control2.setText(String.valueOf(iesSaved.getControl2()));
                    examen1.setText(String.valueOf(iesSaved.getFhc1()));
                    examen2.setText(String.valueOf(iesSaved.getFhc2()));
                    examen3.setText(String.valueOf(iesSaved.getFhc3()));
                    participacion.setText(String.valueOf(iesSaved.getParticipacion()));
                    res.setText(String.valueOf(String.format("%.2f", iesSaved.getNotafinal())));
                }

                control1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        control1.setText("");
                    }
                });
                control2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        control2.setText("");
                    }
                });
                examen1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        examen1.setText("");
                    }
                });
                examen2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        examen1.setText("");
                    }
                });
                examen3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        examen1.setText("");
                    }
                });
                participacion.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        examen2.setText("");
                    }
                });

                final CalculoIES finalIEScSaved = iesSaved;

                TextWatcher textWatcher = new TextWatcher() {
                    public void afterTextChanged(Editable s) {
                        if (control1.getText().equals("") || TextUtils.isEmpty(control1.getText())) {
                            c1[0] = 0;
                        } else c1[0] = Double.parseDouble(control1.getText().toString());

                        if (control2.getText().equals("") || TextUtils.isEmpty(control2.getText())) {
                            c2[0] = 0;
                        } else c2[0] = Double.parseDouble(control2.getText().toString());

                        if (participacion.getText().equals("") || TextUtils.isEmpty(participacion.getText())) {
                            c6[0] = 0;
                        } else c6[0] = Double.parseDouble(participacion.getText().toString());

                        if (examen1.getText().equals("") || TextUtils.isEmpty(examen1.getText())) {
                            c3[0] = 0;
                        } else c3[0] = Double.parseDouble(examen1.getText().toString());

                        if (examen2.getText().equals("") || TextUtils.isEmpty(examen2.getText())) {
                            c4[0] = 0;
                        } else c4[0] = Double.parseDouble(examen2.getText().toString());

                        if (examen3.getText().equals("") || TextUtils.isEmpty(examen3.getText())) {
                            c5[0] = 0;
                        } else c5[0] = Double.parseDouble(examen3.getText().toString());


                        finalIEScSaved.calculate(c1[0], c2[0], c3[0], c4[0], c5[0], c6[0]);
                        result[0] = finalIEScSaved.getNotafinal();
                        res.setText(String.valueOf(String.format("%.2f", result[0])));
                    }
                    public void beforeTextChanged(CharSequence s, int start, int count, int after){}
                    public void onTextChanged(CharSequence s, int start, int before, int count){}
                };

                // Adds the TextWatcher as TextChangedListener to both EditTexts
                control1.addTextChangedListener(textWatcher);
                control2.addTextChangedListener(textWatcher);
                participacion.addTextChangedListener(textWatcher);
                examen1.addTextChangedListener(textWatcher);
                examen2.addTextChangedListener(textWatcher);
                examen3.addTextChangedListener(textWatcher);

                save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final CalculoIES ies = new CalculoIES();
                        ies.setName("ies");
                        Log.v("c111[0]", String.valueOf(c1[0]));
                        ies.setControl1(c1[0]);
                        Log.v("c222[0]", String.valueOf(c2[0]));
                        ies.setControl2(c2[0]);
                        ies.setFhc1(c3[0]);
                        ies.setFhc2(c4[0]);
                        ies.setFhc3(c5[0]);
                        ies.setParticipacion(c6[0]);
                        ies.setNotafinal(result[0]);
                        realm.executeTransaction(new Realm.Transaction() {
                            @Override
                            public void execute(Realm realm) {
                                // This will update an existing object with the same primary key
                                // or create a new object if an object with no primary key = 42
                                realm.copyToRealmOrUpdate(ies);

                            }
                        });
                    }
                });

            }

        //}
        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }

    protected void hideKeyboard(View view)
    {
        InputMethodManager in = (InputMethodManager) this.context.getSystemService(Context.INPUT_METHOD_SERVICE);

        in.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

}
