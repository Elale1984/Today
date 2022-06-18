package edu.gcu.today;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

import edu.gcu.today.databinding.ActivitySignUpBinding;


public class SignUp extends AppCompatActivity {

    private ActivitySignUpBinding binding;

    // Firebase
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);


        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

    }
}