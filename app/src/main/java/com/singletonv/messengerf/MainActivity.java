package com.singletonv.messengerf;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

public class MainActivity extends AppCompatActivity {

    private MainActivityViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);

        Intent intent;

        if (viewModel.getCurrentUser() == null) {
            intent = LoginActivity.makeIntent(this);
        } else {
            intent = UsersActivity.makeIntent(this);
        }

        startActivity(intent);
        finish();
    }
}
