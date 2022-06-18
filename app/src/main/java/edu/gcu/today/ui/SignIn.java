package edu.gcu.today.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

import edu.gcu.today.databinding.ActivitySignInBinding;

public class SignIn extends AppCompatActivity {

    private ActivitySignInBinding binding;

    // Firebase
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignInBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        // check for user already signed in
        CheckForUserAuthenticationStatus();

        binding.btnSignIn.setOnClickListener(view1 -> AuthenticateUser());
        binding.tvNeedAnAccount.setOnClickListener(view12 -> NavigateToSignUp());
    }

    private void NavigateToSignUp() {
        startActivity(new Intent(this, SignUp.class));
    }

    private void AuthenticateUser() {
        String email = binding.oitEmail.toString();
        String password = binding.oitPassword.toString();

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                   if(task.isSuccessful()){
                        NavigateToTodayMain();
                   }
                   else {
                      IncorrectSignInProcedure();
                   }
                });
    }

    private void IncorrectSignInProcedure() {
        Toast.makeText(this, "The email or password that you have entered is " +
                "incorrect. Please try again", Toast.LENGTH_SHORT).show();
        Objects.requireNonNull(binding.etEmail.getText()).clear();
        Objects.requireNonNull(binding.etPassword.getText()).clear();
    }


    private void CheckForUserAuthenticationStatus() {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            NavigateToTodayMain();

        }
    }

    private void NavigateToTodayMain() {
        startActivity(new Intent(this, TodayMain.class));
    }
}