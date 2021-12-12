package com.bhumiconverter;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

public class FirstFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private final String SPINNER_SHAREPREF_TAG = "SAVE_FIRST_SPINNER_DATA";
    private final String EDITTEXT_SHAREPREF_TAG = "SAVE_FIRST_EDITTEXT_DATA";
    boolean focusCheck1 = false;
    private SaveDataOnSharePref saveDataOnSharePref;
    private Spinner spinner;
    private CharSequence currentEdt_TextData = "";
    private CharSequence currentSpinner_data = "";
    private EditText firstEdt_Text;
    private EditText feetFirstEdt_Text_View;
    private EditText inchFirstEdt_Text_View;
    private TextView showInchText_View;
    private firstFragment_InterFace listener;
    private double feetData_EdtText_View = 0, inchData_EdtText_View = 0;
    private boolean flagFeet = false, flagInch = false, flag = false;
    private boolean firstTimeStart = false;
    private boolean focusCheck = false;

    public FirstFragment() {

    }

    public static FirstFragment newInstance( String param1, String param2 ) {
        FirstFragment fragment = new FirstFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public void setFirstEdt_Text( CharSequence s ) {
        if (firstTimeStart) {
            if (!flagInch && !flagFeet) {
                double data = Double.parseDouble(String.valueOf(s));
                data *= 12;
                feetData_EdtText_View = data / 12;
                inchData_EdtText_View = data % 12;
                feetFirstEdt_Text_View.setText(String.valueOf((int) feetData_EdtText_View));
                inchFirstEdt_Text_View.setText(String.valueOf(inchData_EdtText_View));
            }
            firstEdt_Text.setText(s);
        } else {
            double data = Double.parseDouble(String.valueOf(s));
            data *= 12;
            feetData_EdtText_View = data / 12;
            inchData_EdtText_View = data % 12;
            feetFirstEdt_Text_View.setText(String.valueOf((int) feetData_EdtText_View));
            inchFirstEdt_Text_View.setText(String.valueOf(inchData_EdtText_View));
            firstEdt_Text.setText(currentEdt_TextData);
        }
        firstTimeStart = true;
    }

    @Override
    public void onPause() {
        String pos = String.valueOf(spinner.getSelectedItemPosition());
        saveDataOnSharePref.setData(requireActivity(), SPINNER_SHAREPREF_TAG, pos);
        saveDataOnSharePref.setData(requireActivity(), EDITTEXT_SHAREPREF_TAG, firstEdt_Text.getText().toString());
        super.onPause();
    }

    @Override
    public void onCreate( Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            String mParam1 = getArguments().getString(ARG_PARAM1);
            String mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    private void setFirstEdt_Text( View view ) {
        firstEdt_Text = view.findViewById(R.id.firstEdt_Text);
        firstEdt_Text.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange( View v, boolean hasFocus ) {
                flag = hasFocus;
                if (checkDecimal(currentEdt_TextData, firstEdt_Text) && flag && focusCheck) {
                    listener.onStartFirstFragmentData(currentSpinner_data);
                    listener.onInputFirstFragment(currentEdt_TextData, (String) currentSpinner_data);
                } else {
                    focusCheck = true;
                }
            }
        });
        firstEdt_Text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged( CharSequence s, int start, int count, int after ) {
            }

            @Override
            public void onTextChanged( CharSequence s, int start, int before, int count ) {
                currentEdt_TextData = s;
                if (checkDecimal(s, firstEdt_Text) && flag) {
                    listener.onInputFirstFragment(s, (String) currentSpinner_data);
                }
            }

            @Override
            public void afterTextChanged( Editable s ) {
            }
        });
    }

    private void setFeetAndInch() {

        feetFirstEdt_Text_View.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange( View v, boolean hasFocus ) {
                flagFeet = hasFocus;

                if (checkDecimal(currentEdt_TextData, firstEdt_Text) && flagFeet && focusCheck1) {
                    feetData_EdtText_View = hasDataOrNot(feetFirstEdt_Text_View);
                    inchData_EdtText_View = hasDataOrNot(inchFirstEdt_Text_View);
                    listener.onStartFirstFragmentData(currentSpinner_data);
                    currentEdt_TextData = String.valueOf(feetData_EdtText_View + (inchData_EdtText_View / 12));
                    listener.onInputFirstFragment(currentEdt_TextData, (String) currentSpinner_data);
                } else {
                    focusCheck1 = true;
                }
            }
        });
        feetFirstEdt_Text_View.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged( CharSequence s, int start, int count, int after ) {

            }

            @Override
            public void onTextChanged( CharSequence s, int start, int before, int count ) {
                if (checkDecimal(s, feetFirstEdt_Text_View) && flagFeet) {
                    feetData_EdtText_View = hasDataOrNot(feetFirstEdt_Text_View);
                    inchData_EdtText_View = hasDataOrNot(inchFirstEdt_Text_View);
                    currentEdt_TextData = String.valueOf(feetData_EdtText_View + inchData_EdtText_View / 12);
                    listener.onInputFirstFragment(currentEdt_TextData, (String) currentSpinner_data);
                } else {
                    if (checkDecimal(s, feetFirstEdt_Text_View) && flagInch) {
                        inchData_EdtText_View = hasDataOrNot(inchFirstEdt_Text_View);
                        currentEdt_TextData = String.valueOf(inchData_EdtText_View / 12);
                        listener.onInputFirstFragment(currentEdt_TextData, (String) currentSpinner_data);
                    }
                }
            }

            @Override
            public void afterTextChanged( Editable s ) {

            }
        });
        inchFirstEdt_Text_View.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange( View v, boolean hasFocus ) {
                flagInch = hasFocus;

                if (checkDecimal(currentEdt_TextData, firstEdt_Text) && flagInch && focusCheck1) {
                    feetData_EdtText_View = hasDataOrNot(feetFirstEdt_Text_View);
                    inchData_EdtText_View = hasDataOrNot(inchFirstEdt_Text_View);
                    currentEdt_TextData = String.valueOf((feetData_EdtText_View) + inchData_EdtText_View / 12);
                    listener.onInputFirstFragment(currentEdt_TextData, (String) currentSpinner_data);
                } else {
                    focusCheck1 = true;
                }
            }
        });
        inchFirstEdt_Text_View.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged( CharSequence s, int start, int count, int after ) {

            }

            @Override
            public void onTextChanged( CharSequence s, int start, int before, int count ) {
                if (checkDecimal(s, inchFirstEdt_Text_View) && flagInch) {
                    inchData_EdtText_View = hasDataOrNot(inchFirstEdt_Text_View);
                    feetData_EdtText_View = hasDataOrNot(feetFirstEdt_Text_View);
                    currentEdt_TextData = String.valueOf(inchData_EdtText_View / 12 + feetData_EdtText_View);
                    listener.onInputFirstFragment(currentEdt_TextData, (String) currentSpinner_data);
                } else {
                    if (checkDecimal(s, inchFirstEdt_Text_View) && flagFeet) {
                        feetData_EdtText_View = hasDataOrNot(feetFirstEdt_Text_View);
                        currentEdt_TextData = String.valueOf(feetData_EdtText_View);
                        listener.onInputFirstFragment(currentEdt_TextData, (String) currentSpinner_data);
                    }
                }
            }

            @Override
            public void afterTextChanged( Editable s ) {

            }
        });

    }

    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState ) {
        View view = inflater.inflate(R.layout.fragment_first, container, false);
        saveDataOnSharePref = new SaveDataOnSharePref();
        feetFirstEdt_Text_View = view.findViewById(R.id.firstEdt_Feet_view);
        inchFirstEdt_Text_View = view.findViewById(R.id.firstEdt_Inch_view);
        showInchText_View = view.findViewById(R.id.firstText_Inch_view);
        currentEdt_TextData = saveDataOnSharePref.getData(requireActivity(), EDITTEXT_SHAREPREF_TAG, "0");
        setFirstEdt_Text(view);
        setFirstSpinner(view);
        setFeetAndInch();
        return view;
    }

    private void setFirstSpinner( View v ) {
        spinner = v.findViewById(R.id.FirstSpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.convertList, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        int position = Integer.parseInt(saveDataOnSharePref.getData(requireActivity(), SPINNER_SHAREPREF_TAG, "1"));
        spinner.setSelection(position);
        currentSpinner_data = adapter.getItem(position);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onItemSelected( AdapterView<?> parent, View view, int position, long id ) {
                currentSpinner_data = parent.getItemAtPosition(position).toString();
                if (checkDecimal(currentEdt_TextData, firstEdt_Text)) {
                    if (String.valueOf(currentSpinner_data).endsWith("Feet")) {
                        feetIsVisible();
                    } else feetIsInvisible();
                    listener.onStartFirstFragmentData(currentSpinner_data);
                }
            }

            public void onNothingSelected( AdapterView<?> parent ) {

            }
        });
    }

    @Override
    public void onAttach( @NonNull Context context ) {
        super.onAttach(context);
        if (context instanceof FirstFragment.firstFragment_InterFace) {
            listener = (FirstFragment.firstFragment_InterFace) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement FragmentAListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    private void feetIsVisible() {
        firstEdt_Text.setVisibility(View.GONE);
        feetFirstEdt_Text_View.setVisibility(View.VISIBLE);
        inchFirstEdt_Text_View.setVisibility(View.VISIBLE);
        showInchText_View.setVisibility(View.VISIBLE);

    }

    private void feetIsInvisible() {
        firstEdt_Text.setVisibility(View.VISIBLE);
        feetFirstEdt_Text_View.setVisibility(View.GONE);
        inchFirstEdt_Text_View.setVisibility(View.GONE);
        showInchText_View.setVisibility(View.GONE);
    }

    private double hasDataOrNot( EditText view ) {
        String data = view.getText().toString();
        if (data.length() <= 0) return 0;
        return Double.parseDouble(data);
    }

    private boolean checkDecimal( CharSequence a, EditText view ) {
        CharSequence s = a;
        int count = 0;
        boolean flag = false;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '.') count++;
            else if (!Character.isDigit(s.charAt(i)) && s.charAt(i) != 'E') {
                flag = true;
            }
        }
        if (count > 1 || flag) {
            view.setTextColor(Color.RED);
            if (count > 1)
                Toast.makeText(getActivity(), "You Can't Enter two decimal", Toast.LENGTH_SHORT).show();
            if (flag)
                Toast.makeText(getActivity(), "You Can't Enter Character Value", Toast.LENGTH_SHORT).show();
        } else view.setTextColor(Color.BLACK);
        return count <= 1 && !flag;
    }

    public interface firstFragment_InterFace {
        void onInputFirstFragment( CharSequence s, String data );

        void onStartFirstFragmentData( CharSequence data );
    }
}