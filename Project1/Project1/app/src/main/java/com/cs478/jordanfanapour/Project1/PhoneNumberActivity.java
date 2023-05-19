package com.cs478.jordanfanapour.Project1;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class PhoneNumberActivity extends AppCompatActivity {

    protected EditText editPhoneNumberText;

    protected Button button3;

    protected String phoneNumber;

    protected static final String phoneKey = "Phone Number";

    protected static final String phoneHint = "Ex: 1234567890 or (123) 456-7890" ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_number);

        editPhoneNumberText = findViewById(R.id.editPhoneNumberText);
        button3 = (Button) findViewById(R.id.button3);

        // Are we running from scratch?  Yes
        if (savedInstanceState == null) {
            phoneNumber = "";
        }
        // No, there is a previously-saved state
        else {
            phoneNumber = savedInstanceState.getString(phoneKey);
        }

        editPhoneNumberText.setOnEditorActionListener(phoneNumberListener);
        button3.setOnClickListener(button3Listener);

        updateField();
    }

    // This will be called when the app loses focus; save
    // current state
    @Override
    public void onSaveInstanceState(Bundle outState) {
        // Always do this
        super.onSaveInstanceState(outState)  ;

        // Save the counter's state
        outState.putString(phoneKey, phoneNumber); ;
    }

    public View.OnClickListener button3Listener = v -> {
        phoneNumber = editPhoneNumberText.getText().toString();
        switchToMain();
    };

    // Called when down Button is selected
    // Listener for the down button
    public TextView.OnEditorActionListener phoneNumberListener = new TextView.OnEditorActionListener() {
        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            if(actionId == EditorInfo.IME_ACTION_DONE) {
                phoneNumber = v.getText().toString();
                switchToMain();
            }
            return true;
        }
    };

    private boolean isValidPhoneNumber(String str) {
        //validates phone numbers having 10 digits (1234567890)
        if (str.matches("\\d{10}"))
            return true;
        //validates phone numbers having parentheses and dash ex (123)456-7890
        else if (str.matches("\\/(d{3})\\d{3}-\\d{4}"))
            return true;
        //validates phone numbers having parentheses, dash and space ex (123) 456-7890
        else if (str.matches("\\/(d{3}) \\d{3}-\\d{4}"))
            return true;
        //return false if any of the input matches is not found
        else
            return false;
    }


    private void switchToMain() {
        Intent i = new Intent();
        if(isValidPhoneNumber(phoneNumber)) {
            i.putExtra(phoneKey, phoneNumber);
            setResult(RESULT_OK, i);
        } else {
            setResult(RESULT_CANCELED, i);
        }
        finish();
    }

    @SuppressLint("DefaultLocale")
    protected void updateField() {

        // update content of edit text field
        if(!phoneNumber.equals("")) {
            editPhoneNumberText.setText(String.format("%d", phoneNumber));
        }
    }
}