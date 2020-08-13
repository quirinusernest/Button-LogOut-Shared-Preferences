package com.seedgrow.modullima;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private EditText edtEmail,edtPassword;
    private CheckBox checkBox;
    private Button btnSignIn;
    private SharedPreferences SM;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set up the login form.
        edtEmail = findViewById(R.id.simple_login_email);
        edtPassword = findViewById(R.id.simple_login_password);
        checkBox = findViewById(R.id.checkBoxRememberMe);
        btnSignIn = findViewById(R.id.email_sign_in_button);

        edtPassword.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == R.id.simple_login_password || actionId == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptLogin();
            }
        });
        //Here we will validate saved preferences
        if (!new PrefManager(this).isUserLogOut()) {
        //user's email and password both are saved in preferences
            startHomeActivity();
        }
    }

    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {
    // Reset errors.
        edtEmail.setError(null);
        edtEmail.setError(null);

        // Store values at the time of the login attempt.
        String email = edtEmail.getText().toString();
        String password = edtPassword.getText().toString();
        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            edtPassword.setError(getString(R.string.error_invalid_password));
            focusView = edtPassword;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            edtEmail.setError(getString(R.string.error_field_required));
            focusView = edtEmail;
            cancel = true;
        } else if (!isEmailValid(email)) {
            edtEmail.setError(getString(R.string.error_invalid_email));
            focusView = edtEmail;
            cancel = true;
        }

        if (cancel) {
        // There was an error; don't attempt login and focus the first
        // form field with an error.
            focusView.requestFocus();
        } else {
        // save data in local shared preferences
            if (checkBox.isChecked())
                saveLoginDetails(email, password);
            startHomeActivity();
        }
    }
    private void startHomeActivity() {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
        finish();
    }
    private void saveLoginDetails(String email, String password) {
        new PrefManager(this).saveLoginDetails(email, password);
    }

    private boolean isEmailValid(String email) {
    //TODO: Replace this with your own logic
        return email.contains("mobilecomputing@gmail.com");
    }
    private boolean isPasswordValid(String password) {
    //TODO: Replace this with your own logic
        return password.length() > 4 && password.equals("1234567");
    }


}