package com.example.morrassignment;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.renderscript.ScriptGroup;
import android.view.View;
import android.widget.Button;

import com.example.morrassignment.databinding.ActivityMainBinding;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding activityMainBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(activityMainBinding.getRoot());
    }

    public void submit(View view) {
        if (!validateCard() | !validateExpDate() | !validateCVV() | !validateFirstName() | !validateLastName()){
            return;
        }
        AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this)
                .setTitle("Payment Successful")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                }).show();
    }


    // Card Validation
    private Boolean validateCard() {
        ArrayList<String> listOfPattern=new ArrayList();
        String val = activityMainBinding.cardNumber.getEditText().getText().toString();
        if (val.isEmpty()){
            activityMainBinding.cardNumber.setError("Card is not Valid");
            return false;
        }
        String ptVisa = "^4[0-9]{6,}$";
        listOfPattern.add(ptVisa);
        String ptMasterCard = "^5[1-5][0-9]{5,}$";
        listOfPattern.add(ptMasterCard);
        String ptAmeExp = "^3[47][0-9]{5,}$";
        listOfPattern.add(ptAmeExp);
        String ptDinClb = "^3(?:0[0-5]|[68][0-9])[0-9]{4,}$";
        listOfPattern.add(ptDinClb);
        String ptDiscover = "^6(?:011|5[0-9]{2})[0-9]{3,}$";
        listOfPattern.add(ptDiscover);
        String ptJcb = "^(?:2131|1800|35[0-9]{3})[0-9]{3,}$";
        listOfPattern.add(ptJcb);
        boolean check=false;
        for (int i=0;i<listOfPattern.size();i++){
            if(val.matches(listOfPattern.get(i)));
                check=true;
                break;
        }
        boolean result = luhnCheck(val);
        if (result == true){
            activityMainBinding.cardNumber.setError(null);
            activityMainBinding.cardNumber.setErrorEnabled(false);
            return true;
        }
        else
        {
            activityMainBinding.cardNumber.setError("Card is not Valid");
            return false;

        }
    }
    private static boolean luhnCheck(String cardNumber) {
        // number must be validated as 0..9 numeric first!!
        int digits = cardNumber.length();
        int oddOrEven = digits & 1;
        long sum = 0;
        for (int count = 0; count < digits; count++) {
            int digit = 0;
            try {
                digit = Integer.parseInt(cardNumber.charAt(count) + "");
            } catch(NumberFormatException e) {
                return false;
            }

            if (((count & 1) ^ oddOrEven) == 0) { // not
                digit *= 2;
                if (digit > 9) {
                    digit -= 9;
                }
            }
            sum += digit;
        }

        return (sum == 0) ? false : (sum % 10 == 0);
    }

    private Boolean validateExpDate() {
        String val = activityMainBinding.expDate.getEditText().getText().toString();
        if (val.isEmpty()){
            activityMainBinding.expDate.setError("Field cannot be empty");
            return false;
        }
        activityMainBinding.expDate.setError(null);
        activityMainBinding.expDate.setErrorEnabled(false);
        return true;
    }

    private Boolean validateCVV() {
        String val = activityMainBinding.CVV.getEditText().getText().toString();
        if (val.isEmpty()){
            activityMainBinding.CVV.setError("Field cannot be empty");
            return false;
        }
        else if(val.matches( "^[0-9]")){
            activityMainBinding.CVV.setError("enter only digit");
            return false;
        }
        else if(val.length()==3 || val.length() == 4){
            activityMainBinding.CVV.setError(null);
            activityMainBinding.CVV.setErrorEnabled(false);
            return true;
        }
        else {
            activityMainBinding.CVV.setError("Invalid security code");
            return false;
        }
    }

    private Boolean validateFirstName() {
        String val = activityMainBinding.firstName.getEditText().getText().toString();
        if (val.isEmpty()) {
            activityMainBinding.firstName.setError("Field cannot be empty");
            return false;
        }
        else if(val.matches("^[0-9]+$")){
            activityMainBinding.firstName.setError("Field is not correct");
            return false;
        }
        else if(val.matches("[`~!@#$%^&*()_+[\\\\]\\\\\\\\;\\',./{}|:\\\"<>?]")){
            activityMainBinding.firstName.setError("Field is not correct");
            return false;
        }
        else{
            activityMainBinding.firstName.setError(null);
            activityMainBinding.firstName.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validateLastName() {
        String val = activityMainBinding.lastName.getEditText().getText().toString();
        if (val.isEmpty()) {
            activityMainBinding.lastName.setError("Field cannot be empty");
            return false;
        }
        else if(val.matches("^[0-9]+$")){
            activityMainBinding.lastName.setError("Field is not correct");
            return false;
        }
        else if(val.matches("[`~!@#$%^&*()_+[\\\\]\\\\\\\\;\\',./{}|:\\\"<>?]")){
            activityMainBinding.lastName.setError("Field is not correct");
            return false;
        }
        else {
            activityMainBinding.lastName.setError(null);
            activityMainBinding.lastName.setErrorEnabled(false);
            return true;
        }

    }
}