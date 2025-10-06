package com.singletonv.messengerf;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

public class RegisterActivity extends AppCompatActivity {

    private EditText editTextEmail;
    private EditText editTextPassword;
    private EditText editTextName;
    private EditText editTextLastName;
    private EditText editTextAge;
    private Button buttonSignUp;

    private RegisterActivityViewModel viewModel;

    private static final String EMAIL_EXTRA = "email";

    public static Intent makeIntent(Context context, String email) {
        Intent intent = new Intent(context, RegisterActivity.class);
        intent.putExtra(EMAIL_EXTRA, email);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initViews();
        viewModel = new ViewModelProvider(this).get(RegisterActivityViewModel.class);

        String prefilledEmail = getIntent().getStringExtra(EMAIL_EXTRA);
        editTextEmail.setText(prefilledEmail);

        buttonSignUp.setOnClickListener(view -> {
            String email = Util.getTrimmedValue(editTextEmail);
            String password = Util.getTrimmedValue(editTextPassword);
            String name = Util.getTrimmedValue(editTextName);
            String lastName = Util.getTrimmedValue(editTextLastName);
            String age = Util.getTrimmedValue(editTextAge);

            if (email.isEmpty() ||
                    password.isEmpty() ||
                    name.isEmpty() ||
                    lastName.isEmpty() ||
                    age.isEmpty()) {
                showToast(getString(R.string.fill_all_fields));
            } else {
                viewModel.signUp(email, password).addOnSuccessListener(authResult -> {
                            Intent intent = UsersActivity.makeIntent(this);
                            startActivity(intent);
                            finish();
                        }).addOnFailureListener(
                        exception -> showToast(exception.getMessage())
                );
            }
        });
    }

    private void showToast(String notification) {
        Toast.makeText(this, notification, Toast.LENGTH_SHORT).show();
    }

    private void initViews() {
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextName = findViewById(R.id.editTextName);
        editTextLastName = findViewById(R.id.editTextLastName);
        editTextAge = findViewById(R.id.editTextAge);
        buttonSignUp = findViewById(R.id.buttonSignUp);
    }
}