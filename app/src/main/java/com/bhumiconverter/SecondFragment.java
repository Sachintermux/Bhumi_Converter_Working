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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import java.util.Objects;


public class SecondFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private final String SPINNER_SHAREPREF_TAG = "SAVE_SECOND_SPINNER_DATA";
    private final String EDIT_TEXT_SHAREPREF_TAG = "SAVE_SECOND_EDIT_TEXT_DATA";
    public secondFragment_InterFace listener;
    private Spinner spinner;
    private CharSequence currentEdt_TextData = "";
    private boolean flag = false;
    private EditText secondEdt_Text;
    private String currentSpinner_data = "";
    private SaveDataOnSharePref saveDataOnSharePref;
    private boolean firstTimeStart = false;
    private boolean focusCheck = false;


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
        if(firstTimeStart) {
            secondEdt_Text.setText(s);
        }
        secondEdt_Text.setText(currentEdt_TextData);
        firstTimeStart = true;
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
saveDataOnSharePref.setData(requireActivity(), SPINNER_SHAREPREF_TAG,pos);
saveDataOnSharePref.setData(requireActivity(),EDIT_TEXT_SHAREPREF_TAG,secondEdt_Text.getText().toString());
      //  Log.i("Hello" , secondEdt_Text.getText().toString());
        super.onPause();
    }

    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState ) {
        View view = inflater.inflate(R.layout.fragment_second, container, false);
        saveDataOnSharePref = new SaveDataOnSharePref();
        String dataForEditText = saveDataOnSharePref.getData(requireActivity(), EDIT_TEXT_SHAREPREF_TAG);
        currentEdt_TextData = dataForEditText;
        setSpinner(view);
        setSecondEdt_Text(view);

        return view;
    }

    private void setSecondEdt_Text( View view ) {
        secondEdt_Text = view.findViewById(R.id.secondEdt_Text);
        secondEdt_Text.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange( View v, boolean hasFocus ) {
                flag = hasFocus;
                if (checkDecimal(currentEdt_TextData) && flag && focusCheck) {
                    Log.i("Hello" , "Second");
                    listener.onStartSecondFragmentData(currentSpinner_data);
                    listener.onInputSecondFragment(currentEdt_TextData, (String) currentSpinner_data);
                }
                else {
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
                Log.i("Hello" , "DATA " +String.valueOf(currentEdt_TextData));
                if (checkDecimal(s) && flag) {
                    listener.onInputSecondFragment(s, (String) currentSpinner_data);
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
                R.array.firstList, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        int position = Integer.parseInt(saveDataOnSharePref.getData(requireActivity(), SPINNER_SHAREPREF_TAG));
        spinner.setSelection(position);
        listener.onStartSecondFragmentData(adapter.getItem(position));
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected( AdapterView<?> parent, View view, int position, long id ) {
                if (checkDecimal(currentEdt_TextData)) {
                    currentSpinner_data = parent.getItemAtPosition(position).toString();
                   listener.onStartSecondFragmentData(currentSpinner_data);
                }
            }

            public void onNothingSelected( AdapterView<?> parent ) {

            }
        });
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

    private boolean checkDecimal( CharSequence a ) {
        CharSequence s = a;
        int count = 0;
        boolean flag = false;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '.') count++;
            else if (Character.isAlphabetic(s.charAt(i)) && s.charAt(i) != 'E' || Character.isSpaceChar(s.charAt(i))) {
                flag = true;
            }
        }
        if (count > 1 || flag) {
            secondEdt_Text.setTextColor(Color.RED);
            if (count > 1)
                Toast.makeText(getActivity(), "You Can't Enter two decimal", Toast.LENGTH_SHORT).show();
            if (flag)
                Toast.makeText(getActivity(), "You Can't Enter Character Value", Toast.LENGTH_SHORT).show();
        } else secondEdt_Text.setTextColor(Color.BLACK);
        return count <= 1 && !flag;
    }

    public interface secondFragment_InterFace {
        void onInputSecondFragment( CharSequence s, String data );

        void onStartSecondFragmentData( CharSequence data );
    }


}