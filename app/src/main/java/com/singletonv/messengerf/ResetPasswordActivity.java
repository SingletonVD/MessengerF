package com.singletonv.messengerf;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

public class ResetPasswordActivity extends AppCompatActivity {

    private static final String EMAIL_EXTRA = "email";

    private EditText editTextEmail;
    private Button buttonResetPassword;

    private ResetPasswordActivityViewModel viewModel;

    public static Intent makeIntent(Context context, String email) {
        Intent intent = new Intent(context, ResetPasswordActivity.class);
        intent.putExtra(EMAIL_EXTRA, email);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        initViews();
        viewModel = new ViewModelProvider(this).get(ResetPasswordActivityViewModel.class);

        String prefilledEmail = getIntent().getStringExtra(EMAIL_EXTRA);
        editTextEmail.setText(prefilledEmail);

        buttonResetPassword.setOnClickListener(view -> {
            String email = Util.getTrimmedValue(editTextEmail);

            if (email.isEmpty()) {
                showToast(getString(R.string.fill_all_fields));
            } else {
                viewModel.resetPassword(email).addOnSuccessListener(authResult -> {
                    Intent intent = LoginActivity.makeIntent(this);
                    startActivity(intent);
                    finish();
                }).addOnFailureListener(
                        exception -> showToast(exception.getMessage())
                );
            }
        });
    }

    private void initViews() {
        editTextEmail = findViewById(R.id.editTextEmail);
        buttonResetPassword = findViewById(R.id.buttonResetPassword);
    }

    private void showToast(String notification) {
        Toast.makeText(this, notification, Toast.LENGTH_SHORT).show();
    }
}