package com.bhumiconverter;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;


public class SelectStateFrament extends Fragment {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
private Spinner selectStateSpinner;
private SaveDataOnSharePref saveDataOnSharePref;
private final String SPINNER_SHAREPREF_TAG = "SAVE_SELECT_STATE_IN_SPINNER";

    @Override
    public void onPause() {
        String pos = String.valueOf(selectStateSpinner.getSelectedItemPosition());
        saveDataOnSharePref.setData(getActivity(),SPINNER_SHAREPREF_TAG,pos);
        super.onPause();
    }

    public SelectStateFrament() {

    }


    public static SelectStateFrament newInstance( String param1, String param2 ) {
        SelectStateFrament fragment = new SelectStateFrament();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate( Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            String mParam1 = getArguments().getString(ARG_PARAM1);
            String mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState ) {

        View view = inflater.inflate(R.layout.fragment_select_state_frament, container, false);
        saveDataOnSharePref = new SaveDataOnSharePref();
setSelectStateSpinner(view);
        return view;
    }

    private void setSelectStateSpinner(View view){
        selectStateSpinner = view.findViewById(R.id.selectStateSpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.StateList, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        selectStateSpinner.setAdapter(adapter);
int position = Integer.parseInt(saveDataOnSharePref.getData(getActivity(),SPINNER_SHAREPREF_TAG,"3"));
selectStateSpinner.setSelection(position);
        selectStateSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected( AdapterView<?> parent, View view, int position, long id ) {

            }

            @Override
            public void onNothingSelected( AdapterView<?> parent ) {

            }
        });
    }
}