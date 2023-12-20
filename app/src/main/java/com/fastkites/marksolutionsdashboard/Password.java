package com.fastkites.marksolutionsdashboard;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class Password extends AppCompatActivity implements PasswordKeyboardAdapter.OnItemClick {

    List<String> keyboardKeys = new ArrayList<>(Arrays.asList("1", "2", "3", "4", "5", "6", "7", "8", "9", "x", "0", ">"));
    EditText pinDigitsEditText;
    GridView keyboardGridView;
    ProgressDialog progressDialog;
    TextView errorText;
    boolean firstAttempt = false;
    String previousPin = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Processing...");

        errorText = findViewById(R.id.errorText);
        pinDigitsEditText = findViewById(R.id.pinDigitEditText);
        keyboardGridView = findViewById(R.id.keyboardGridView);
        keyboardGridView.setAdapter(new PasswordKeyboardAdapter(this, this, keyboardKeys, pinDigitsEditText, progressDialog));

        if (getIntent().getBooleanExtra("FROM_SETTINGS", false)) {
            errorText.setText("Verify the pin");
        }
    }

    @Override
    public void itemClickListener(String pin) {
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("MarkKeyStore", Context.MODE_PRIVATE);
        if (sharedPreferences.getString("EncryptedPIN", null) != null) {
            retrieveAndDecryptThePin(pin, sharedPreferences);
        } else {
            progressDialog.dismiss();
            encryptAndStoreThePin(pin, sharedPreferences);
        }
    }

    private void encryptAndStoreThePin(String pin, SharedPreferences sharedPreferences) {
        errorText.setVisibility(View.VISIBLE);
        errorText.setText("Confirm the pin");
        if (!firstAttempt) {
            previousPin = pin;
            firstAttempt = true;
        } else {
            if (previousPin.equals(pin)) {
                errorText.setText("Finish");
                sharedPreferences.edit().putString("EncryptedPIN", pin).apply();
                progressDialog.dismiss();
//                setUpServer();
                SharedPreferences preferences;
                preferences = getSharedPreferences("COUNT_DOWN", MODE_PRIVATE);
                preferences.edit().putLong("STARTED", System.currentTimeMillis()).apply();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
            } else {
                errorText.setText("Incorrect pin");
            }
        }
    }

    private void retrieveAndDecryptThePin(String pin, SharedPreferences sharedPreferences) {
        String retrievedPin = sharedPreferences.getString("EncryptedPIN", null);
        if (pin.equals(retrievedPin)) {
            errorText.setText("Correct");
            errorText.setVisibility(View.VISIBLE);
            SharedPreferences preferences = getSharedPreferences("COUNT_DOWN", MODE_PRIVATE);
            long startedTime = preferences.getLong("STARTED", 0);
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(startedTime);
            calendar.add(Calendar.DAY_OF_MONTH, 7);
            long endingTime = calendar.getTimeInMillis();
            if (Calendar.getInstance().getTimeInMillis() < endingTime) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            } else {
                Toast.makeText(this, "Sorry! Your testing period is over", Toast.LENGTH_SHORT).show();
            }
            finish();
        } else {
            errorText.setText("Incorrect pin");
            errorText.setVisibility(View.VISIBLE);
        }
        progressDialog.dismiss();
    }
}