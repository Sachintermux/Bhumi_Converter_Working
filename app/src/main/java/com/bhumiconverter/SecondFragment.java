package com.bhumiconverter;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
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


public class SecondFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private final String SPINNER_SHAREPREF_TAG = "SAVE_SECOND_SPINNER_DATA";
    private final String EDIT_TEXT_SHAREPREF_TAG = "SAVE_SECOND_EDIT_TEXT_DATA";
    private final String FEET_EDT_TEXT_SHAREPREF_TAG = "FEET_SAVE_SECOND_EDIT_TEXT_DATA";
    public secondFragment_InterFace listener;
    boolean firstTimeFocusCheck = false;
    private Spinner spinner;
    private CharSequence currentEdt_TextData = "";
    private boolean flag = false;
    private EditText secondEdt_Text;
    private String currentSpinner_data = "";
    private SaveDataOnSharePref saveDataOnSharePref;
    private boolean firstTimeStart = false;
    private boolean focusCheck = false;
    private EditText feetSecondEdt_Text_View;
    private EditText inchSecondEdt_Text_View;
    private TextView showInchText_View;
    private double feetData_EdtText_View = 0, inchData_EdtText_View = 0;
    private boolean flagFeet = false, flagInch = false;

    public SecondFragment() {
    }

    public static SecondFragment newInstance( String param1, String param2 ) {
        SecondFragment fragment = new SecondFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public void setSecondEdt_Text( CharSequence s ) {
        if (firstTimeStart) {
            double data = Double.parseDouble(String.valueOf(s));
                data *= 12;
                feetData_EdtText_View = data / 12;
                inchData_EdtText_View = data % 12;
                feetSecondEdt_Text_View.setText(String.valueOf((int) feetData_EdtText_View));
                inchSecondEdt_Text_View.setText(String.valueOf(inchData_EdtText_View));
            secondEdt_Text.setText(s);
        } else {
            double data = Double.parseDouble(String.valueOf(currentEdt_TextData));
            data *= 12;
            feetData_EdtText_View = data / 12;
            inchData_EdtText_View = data % 12;
            feetSecondEdt_Text_View.setText(String.valueOf((int) feetData_EdtText_View));
            inchSecondEdt_Text_View.setText(String.valueOf(inchData_EdtText_View));
            secondEdt_Text.setText(currentEdt_TextData);
            firstTimeStart = true;
        }

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
    public void onPause() {
        String pos = String.valueOf(spinner.getSelectedItemPosition());
        saveDataOnSharePref.setData(requireActivity(), SPINNER_SHAREPREF_TAG, pos);
        saveDataOnSharePref.setData(requireActivity(), EDIT_TEXT_SHAREPREF_TAG, secondEdt_Text.getText().toString());
        feetData_EdtText_View = hasDataOrNot(feetSecondEdt_Text_View);
        inchData_EdtText_View = hasDataOrNot(inchSecondEdt_Text_View);
        currentEdt_TextData = String.valueOf(feetData_EdtText_View + inchData_EdtText_View/12);
        saveDataOnSharePref.setData(requireActivity(),FEET_EDT_TEXT_SHAREPREF_TAG,String.valueOf(currentEdt_TextData));
        super.onPause();
    }

    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState ) {
        View view = inflater.inflate(R.layout.fragment_second, container, false);
        saveDataOnSharePref = new SaveDataOnSharePref();
        feetSecondEdt_Text_View = view.findViewById(R.id.secondEdt_Feet_view);
        inchSecondEdt_Text_View = view.findViewById(R.id.secondEdt_Inch_view);
        showInchText_View = view.findViewById(R.id.secondText_Inch_view);
        String dataForEditText = saveDataOnSharePref.getData(requireActivity(), EDIT_TEXT_SHAREPREF_TAG, "-1");
        if(dataForEditText.endsWith("-1")) dataForEditText = saveDataOnSharePref.getData(requireActivity(),FEET_EDT_TEXT_SHAREPREF_TAG,"-1");
        if(dataForEditText.endsWith("-1")) dataForEditText = "0";
        currentEdt_TextData = dataForEditText;
        setSpinner(view);
        setSecondEdt_Text(view);
        setFeetAndInch();
        return view;
    }

    private void setSecondEdt_Text( View view ) {
        secondEdt_Text = view.findViewById(R.id.secondEdt_Text);
        secondEdt_Text.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange( View v, boolean hasFocus ) {
                flag = hasFocus;
                if (checkDecimal(currentEdt_TextData, secondEdt_Text) && flag && focusCheck) {
                    listener.onStartSecondFragmentData(currentSpinner_data);
                    listener.onInputSecondFragment(currentEdt_TextData, (String) currentSpinner_data);
                } else {
                    focusCheck = true;
                }
            }
        });
        secondEdt_Text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged( CharSequence s, int start, int count, int after ) {
            }

            @Override
            public void onTextChanged( CharSequence s, int start, int before, int count ) {
                currentEdt_TextData = s;
                if (checkDecimal(s, secondEdt_Text) && flag) {
                    listener.onInputSecondFragment(currentEdt_TextData, (String) currentSpinner_data);
                }
            }

            @Override
            public void afterTextChanged( Editable s ) {


            }
        });

    }

    private void setSpinner( View view ) {
        spinner = view.findViewById(R.id.SecondSpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.convertList, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        int position = Integer.parseInt(saveDataOnSharePref.getData(requireActivity(), SPINNER_SHAREPREF_TAG, "2"));
        spinner.setSelection(position);
        listener.onStartSecondFragmentData(adapter.getItem(position));
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected( AdapterView<?> parent, View view, int position, long id ) {
                currentSpinner_data = parent.getItemAtPosition(position).toString();
                if (checkDecimal(currentEdt_TextData, secondEdt_Text)) {
                    if (String.valueOf(currentSpinner_data).endsWith("Feet")) {
                        feetIsVisible();
                    } else feetIsInvisible();
                    listener.onStartSecondFragmentData(currentSpinner_data);
                }
            }

            public void onNothingSelected( AdapterView<?> parent ) {

            }
        });
    }

    private void feetIsVisible() {
        secondEdt_Text.setVisibility(View.GONE);
        feetSecondEdt_Text_View.setVisibility(View.VISIBLE);
        inchSecondEdt_Text_View.setVisibility(View.VISIBLE);
        showInchText_View.setVisibility(View.VISIBLE);

    }

    private void feetIsInvisible() {
        secondEdt_Text.setVisibility(View.VISIBLE);
        feetSecondEdt_Text_View.setVisibility(View.GONE);
        inchSecondEdt_Text_View.setVisibility(View.GONE);
        showInchText_View.setVisibility(View.GONE);
    }

    private void setFeetAndInch() {

        feetSecondEdt_Text_View.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange( View v, boolean hasFocus ) {
                flagFeet = hasFocus;
                if (checkDecimal(currentEdt_TextData, secondEdt_Text) && flagFeet && firstTimeFocusCheck) {
                    listener.onStartSecondFragmentData(currentSpinner_data);
                    inchData_EdtText_View = hasDataOrNot(inchSecondEdt_Text_View);
                    feetData_EdtText_View = hasDataOrNot(feetSecondEdt_Text_View);
                    currentEdt_TextData = String.valueOf(feetData_EdtText_View + (inchData_EdtText_View / 12));
                    listener.onInputSecondFragment(currentEdt_TextData, (String) currentSpinner_data);
                } else {
                    firstTimeFocusCheck = true;
                }
            }
        });
        feetSecondEdt_Text_View.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged( CharSequence s, int start, int count, int after ) {

            }

            @Override
            public void onTextChanged( CharSequence s, int start, int before, int count ) {
                if (checkDecimal(s, feetSecondEdt_Text_View) && flagFeet) {
                    feetData_EdtText_View = hasDataOrNot(feetSecondEdt_Text_View);
                    ;
                    inchData_EdtText_View = hasDataOrNot(inchSecondEdt_Text_View);
                    currentEdt_TextData = String.valueOf(feetData_EdtText_View + inchData_EdtText_View / 12);
                    listener.onInputSecondFragment(currentEdt_TextData, (String) currentSpinner_data);
                } else {
                    if (checkDecimal(s, feetSecondEdt_Text_View) && flagInch) {
                        inchData_EdtText_View = hasDataOrNot(inchSecondEdt_Text_View);
                        currentEdt_TextData = String.valueOf(inchData_EdtText_View / 12);
                        listener.onInputSecondFragment(currentEdt_TextData, (String) currentSpinner_data);
                    }
                }
            }

            @Override
            public void afterTextChanged( Editable s ) {

            }
        });
        inchSecondEdt_Text_View.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange( View v, boolean hasFocus ) {
                flagInch = hasFocus;

                if (checkDecimal(currentEdt_TextData, secondEdt_Text) && flagInch && firstTimeFocusCheck) {
                    inchData_EdtText_View = hasDataOrNot(inchSecondEdt_Text_View);
                    feetData_EdtText_View = hasDataOrNot(feetSecondEdt_Text_View);
                    currentEdt_TextData = String.valueOf(feetData_EdtText_View + inchData_EdtText_View / 12);
                    listener.onInputSecondFragment(currentEdt_TextData, (String) currentSpinner_data);
                } else {
                    firstTimeFocusCheck = true;
                }
            }
        });
        inchSecondEdt_Text_View.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged( CharSequence s, int start, int count, int after ) {

            }

            @Override
            public void onTextChanged( CharSequence s, int start, int before, int count ) {
                if (checkDecimal(s, inchSecondEdt_Text_View) && flagInch) {
                    feetData_EdtText_View = hasDataOrNot(feetSecondEdt_Text_View);
                    inchData_EdtText_View = hasDataOrNot(inchSecondEdt_Text_View);
                    currentEdt_TextData = String.valueOf(inchData_EdtText_View / 12 + feetData_EdtText_View);
                    ;
                    listener.onInputSecondFragment(currentEdt_TextData, (String) currentSpinner_data);
                } else {
                    if (checkDecimal(s, inchSecondEdt_Text_View) && flagFeet) {
                        feetData_EdtText_View = hasDataOrNot(feetSecondEdt_Text_View);
                        currentEdt_TextData = String.valueOf(feetData_EdtText_View);
                        listener.onInputSecondFragment(currentEdt_TextData, (String) currentSpinner_data);
                    }
                }
            }

            @Override
            public void afterTextChanged( Editable s ) {

            }
        });

    }

    private double hasDataOrNot( EditText view ) {
        String data = view.getText().toString();
        if (data.length() <= 0) return 0;
        return Double.parseDouble(data);
    }

    @Override
    public void onAttach( @NonNull Context context ) {
        super.onAttach(context);
        if (context instanceof secondFragment_InterFace) {
            listener = (secondFragment_InterFace) context;
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

    public interface secondFragment_InterFace {
        void onInputSecondFragment( CharSequence s, String data );

        void onStartSecondFragmentData( CharSequence data );
    }


}