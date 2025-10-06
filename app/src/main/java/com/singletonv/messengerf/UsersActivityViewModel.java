package com.singletonv.messengerf;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class UsersActivityViewModel extends AndroidViewModel {

    private final FirebaseAuth mAuth;
    private final FirebaseDatabase firebaseDatabase;
    private final DatabaseReference userReference;

    private final MutableLiveData<List<User>> users = new MutableLiveData<>();

    public UsersActivityViewModel(@NonNull Application application) {
        super(application);
        mAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        userReference = firebaseDatabase.getReference("Users");
        loadUsers();
    }

    public void loadUsers() {
        userReference.get().addOnSuccessListener(dataSnapshot -> {
            List<User> usersInDb = new ArrayList<>();

            for (DataSnapshot userSnapshot: dataSnapshot.getChildren()) {
                usersInDb.add(userSnapshot.getValue(User.class));
            }
           users.setValue(usersInDb);
        });
    }

    public void logout() {
        mAuth.signOut();
    }

    public LiveData<List<User>> getUsers() {
        return users;
    }
}
