package com.singletonv.messengerf;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

public class UsersActivity extends AppCompatActivity {

    public static Intent makeIntent(Context context) {
        return new Intent(context, UsersActivity.class);
    }

    private UsersActivityViewModel viewModel;

    private RecyclerView recyclerViewUsers;
    private UsersAdapter usersAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);
        initViews();
        viewModel = new ViewModelProvider(this).get(UsersActivityViewModel.class);
        viewModel.getUsers().observe(this, usersAdapter::setUsers);
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

    private void initViews() {
        recyclerViewUsers = findViewById(R.id.recyclerViewUsers);
        usersAdapter = new UsersAdapter();
        recyclerViewUsers.setAdapter(usersAdapter);
    }
}
