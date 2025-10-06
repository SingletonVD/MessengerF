package com.singletonv.messengerf;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

public class UsersActivity extends AppCompatActivity {

    public static Intent makeIntent(Context context) {
        return new Intent(context, UsersActivity.class);
    }

    private UsersActivityViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);
        viewModel = new ViewModelProvider(this).get(UsersActivityViewModel.class);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.itemLogout) {
            viewModel.logout();
            Intent intent = LoginActivity.makeIntent(this);
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
