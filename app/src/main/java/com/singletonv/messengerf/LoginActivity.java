package com.singletonv.messengerf;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

public class LoginActivity extends AppCompatActivity {

    private EditText editTextEmail;
    private EditText editTextPassword;
    private Button buttonLogin;
    private TextView textViewForgotPassword;
    private TextView textViewRegister;

    private LoginActivityViewModel viewModel;

    public static Intent makeIntent(Context context) {
        return new Intent(context, LoginActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initViews();
        viewModel = new ViewModelProvider(this).get(LoginActivityViewModel.class);

        textViewForgotPassword.setOnClickListener(view -> {
            Intent intent = ResetPasswordActivity.makeIntent(this, getEmail());
            startActivity(intent);
        });

        textViewRegister.setOnClickListener(view -> {
            Intent intent = RegisterActivity.makeIntent(this, getEmail());
            startActivity(intent);
        });

        buttonLogin.setOnClickListener(view -> {
            String email = getEmail();
            String password = getPassword();

            if (email.isEmpty() || password.isEmpty()) {
                showToast(getString(R.string.fill_all_fields));
            } else {
                viewModel.login(email, password).addOnFailureListener(
                        exception -> showToast(exception.getMessage())
                ).addOnSuccessListener(authResult -> {
                    Intent intent = UsersActivity.makeIntent(this);
                    startActivity(intent);
                    finish();
                });
            }
        });
    }

    private void showToast(String notification) {
        Toast.makeText(this, notification, Toast.LENGTH_SHORT).show();
    }

    private String getEmail() {
        return editTextEmail.getText().toString().trim();
    }

    private String getPassword() {
        return editTextPassword.getText().toString().trim();
    }

    private void initViews() {
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonLogin = findViewById(R.id.buttonLogin);
        textViewForgotPassword = findViewById(R.id.textViewForgotPassword);
        textViewRegister = findViewById(R.id.textViewRegister);
    }

}
