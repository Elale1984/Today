package edu.gcu.today;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;

import edu.gcu.today.databinding.ActivitySignInBinding;

public class SignIn extends AppCompatActivity {

    private ActivitySignInBinding binding;

    // Firebase
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = binding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
    }
}