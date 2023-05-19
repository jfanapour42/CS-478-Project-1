package com.cs478.jordanfanapour.Project1;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    protected Button button1;

    protected Button button2;

    protected String phoneNumber;		// String to store phone number

    protected int resultCode;           // int from result code

    protected static final String phoneKey = "Phone Number" ;

    protected static final String resultCodeKey = "Result Code" ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Bind the interface elements to the corresponding fields
        button1 = (Button) findViewById(R.id.button1);
        button2 = (Button) findViewById(R.id.button2);

        // Are we running from scratch?  Yes
        if (savedInstanceState == null) {
            phoneNumber = "";
            resultCode = RESULT_CANCELED;
        }
        // No, there is a previously-saved state
        else {
            phoneNumber = savedInstanceState.getString(phoneKey) ;
            resultCode = savedInstanceState.getInt(resultCodeKey);
        }

        button1.setOnClickListener(button1Listener);
        button2.setOnClickListener(button2Listener);

        updateField();
    }

    // This will be called when the app loses focus; save
    // current state
    @Override
    public void onSaveInstanceState(Bundle outState) {
        // Always do this
        super.onSaveInstanceState(outState)  ;

        // Save the counter's state
        outState.putString(phoneKey, phoneNumber);
        outState.putInt(resultCodeKey, resultCode);
    }

    // Listener to open activity to enter phone number
    public View.OnClickListener button1Listener = v -> switchToPhoneNumber();

    // Listener to open up dialer if result_ok was returned. Otherwise open toast message
    public View.OnClickListener button2Listener = v -> {
        if(resultCode == RESULT_OK) {
            openDialer();
        } else {

        }
    };

    private void switchToPhoneNumber() {
        Intent i = new Intent(MainActivity.this, PhoneNumberActivity.class);
        startActivityForResult(i,1);
    }

    private void openDialer() {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        String temp = String.format("tel:%s", phoneNumber);
        Log.i("ActivityMain", "Entered Phone Number: " + temp);
        intent.setData(Uri.parse(String.format("tel:%s", phoneNumber)));
        startActivity(intent);
    }

    private void makeToast() {
        Toast.makeText(MainActivity.this, String.format("You entered an invalid phone number: %s", phoneNumber), Toast.LENGTH_LONG);
    }

    protected void onActivityResult(int code, int result_code, Intent i) {
        super.onActivityResult(code, result_code, i);
        if(result_code == RESULT_OK) {
            resultCode = RESULT_OK;
            phoneNumber = i.getStringExtra(phoneKey);
            Log.i("ActivityMain", "Entered Phone Number: " + i.getStringExtra(phoneKey));
        }
    }

    @SuppressLint("DefaultLocale")
    protected void updateField() {

    }
}