package com.singletonv.messengerf;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivityViewModel extends AndroidViewModel {

    private final FirebaseAuth mAuth;
    private final FirebaseDatabase firebaseDatabase;
    private final DatabaseReference userReference;

    public RegisterActivityViewModel(@NonNull Application application) {
        super(application);
        mAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        userReference = firebaseDatabase.getReference("Users");
    }

    public Task<AuthResult> signUp(String email, String password, String name, String lastName, int age) {
        return mAuth.createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener(authResult -> {
                    FirebaseUser firebaseUser = authResult.getUser();
                    if (firebaseUser == null) return;
                    User user = new User(
                            firebaseUser.getUid(),
                            name,
                            lastName,
                            age,
                            false
                    );
                    userReference.child(user.getId()).setValue(user);
                });
    }
}
