package com.singletonv.messengerf;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ResetPasswordActivityViewModel extends AndroidViewModel {

    private final FirebaseAuth mAuth;

    public ResetPasswordActivityViewModel(@NonNull Application application) {
        super(application);
        mAuth = FirebaseAuth.getInstance();
    }

    public Task<Void> resetPassword(String email) {
        return mAuth.sendPasswordResetEmail(email);
    }
}
