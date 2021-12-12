package com.bhumiconverter;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity implements SecondFragment.secondFragment_InterFace, FirstFragment.firstFragment_InterFace {
    private FirstFragment firstFragment;
    private SecondFragment secondFragment;
    private SelectStateFrament selectStateFrament;
    private ConvertBhumi convertBhumi;
    private CharSequence SecondFragment_SpinnerData = "";
    private CharSequence FirstFragment_SpinnerData = "";
    private CharSequence FirstFragment_Edt_TextData = "";
    private CharSequence SecondFragment_Edt_TextData = "";
    private String ans = "";
    private boolean onStart = false;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        firstFragment = new FirstFragment();
        secondFragment = new SecondFragment();
        convertBhumi = new ConvertBhumi();
selectStateFrament = new SelectStateFrament();

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.firstFragment, firstFragment);
        ft.replace(R.id.secondFragment, secondFragment);
        ft.replace(R.id.selectStateFragment, selectStateFrament);
        ft.commit();
    }


    @Override
    public void onInputSecondFragment( CharSequence s, String data ) {
        SecondFragment_Edt_TextData = s;
        ans = convertBhumi.useFormula(s, data, FirstFragment_SpinnerData);
        if(Character.isAlphabetic(ans.charAt(0))) showToast();
        else firstFragment.setFirstEdt_Text(ans);
    }

    @Override
    public void onStartSecondFragmentData( CharSequence data ) {
        SecondFragment_SpinnerData = data;
        if (onStart) {
            ans = convertBhumi.useFormula(FirstFragment_Edt_TextData, (String) FirstFragment_SpinnerData, SecondFragment_SpinnerData);
            if(Character.isAlphabetic(ans.charAt(0))) showToast();
            else  secondFragment.setSecondEdt_Text(ans);
        }
        onStart = true;
    }

    @Override
    public void onInputFirstFragment( CharSequence s, String data ) {
        FirstFragment_Edt_TextData = s;
        ans = convertBhumi.useFormula(FirstFragment_Edt_TextData, data, SecondFragment_SpinnerData);
        if(Character.isAlphabetic(ans.charAt(0))) showToast();
        else secondFragment.setSecondEdt_Text(ans);
    }

    @Override
    public void onStartFirstFragmentData( CharSequence data ) {
        FirstFragment_SpinnerData = data;
        ans = convertBhumi.useFormula(SecondFragment_Edt_TextData, SecondFragment_SpinnerData, FirstFragment_SpinnerData);
        if(Character.isAlphabetic(ans.charAt(0)))  showToast();
           else firstFragment.setFirstEdt_Text(ans);
    }
    private void showToast(){
        Toast.makeText(this, "We can't convert value at this time\nBecause i am in development phase", Toast.LENGTH_SHORT).show();
    }
}