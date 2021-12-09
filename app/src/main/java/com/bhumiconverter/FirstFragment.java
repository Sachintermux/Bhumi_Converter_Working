package com.bhumiconverter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
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

public class FirstFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private final String SPINNER_SHAREPREF_TAG = "SAVE_FIRST_SPINNER_DATA";
    private final String EDITTEXT_SHAREPREF_TAG = "SAVE_FIRST_EDITTEXT_DATA";
    private SaveDataOnSharePref saveDataOnSharePref;
    private Spinner spinner;
    private CharSequence currentEdt_TextData = "";
    private CharSequence currentSpinner_data = "";
    private boolean flag = false;
    private EditText firstEdt_Text;
    private firstFragment_InterFace listener;
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
        if(firstTimeStart)
        firstEdt_Text.setText(s);
        else {
            firstEdt_Text.setText(currentEdt_TextData);
            firstTimeStart = true;
        }
    }

    @Override
    public void onPause() {
        String pos = String.valueOf(spinner.getSelectedItemPosition());
      saveDataOnSharePref.setData(requireActivity(),SPINNER_SHAREPREF_TAG,pos);
      saveDataOnSharePref.setData(requireActivity(),EDITTEXT_SHAREPREF_TAG,firstEdt_Text.getText().toString());
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
            @SuppressLint("ResourceAsColor")
            @Override
            public void onFocusChange( View v, boolean hasFocus ) {
                flag = hasFocus;
                if (checkDecimal(currentEdt_TextData) && flag && focusCheck) {
                    Log.i("Hello" , "First");
                    listener.onStartFirstFragmentData(currentSpinner_data);
                    listener.onInputFirstFragment(currentEdt_TextData, (String) currentSpinner_data);
                }
                else {
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
                if (checkDecimal(s) && flag) {
                    listener.onInputFirstFragment(s, (String) currentSpinner_data);
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
        String dataForEditText = saveDataOnSharePref.getData(requireActivity(), EDITTEXT_SHAREPREF_TAG);
        currentEdt_TextData = dataForEditText;
        setFirstEdt_Text(view);
        setFirstSpinner(view);
        return view;
    }

    private void setFirstSpinner( View view ) {
        spinner = view.findViewById(R.id.FirstSpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.firstList, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
      int position = Integer.parseInt(saveDataOnSharePref.getData(requireActivity(),SPINNER_SHAREPREF_TAG));
        spinner.setSelection(position);
        currentSpinner_data = adapter.getItem(position);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onItemSelected( AdapterView<?> parent, View view, int position, long id ) {
                if (checkDecimal(currentEdt_TextData)) {
                    currentSpinner_data = parent.getItemAtPosition(position).toString();
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

    private boolean checkDecimal( CharSequence a ) {
        CharSequence s = a;
        int count = 0;
        boolean flag = false;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '.') count++;
            else if (Character.isAlphabetic(s.charAt(i)) && s.charAt(i) != 'E'|| Character.isSpaceChar(s.charAt(i))) {
                flag = true;
            }
        }
        if (count > 1 || flag) {
            firstEdt_Text.setTextColor(Color.RED);
            if (count > 1)
                Toast.makeText(getActivity(), "You Can't Enter two decimal", Toast.LENGTH_SHORT).show();
            if (flag)
                Toast.makeText(getActivity(), "You Can't Enter Character Value", Toast.LENGTH_SHORT).show();
        } else firstEdt_Text.setTextColor(Color.BLACK);
        return count <= 1 && !flag;
    }

    public interface firstFragment_InterFace {
        void onInputFirstFragment( CharSequence s, String data );
        void onStartFirstFragmentData( CharSequence data );
    }
}