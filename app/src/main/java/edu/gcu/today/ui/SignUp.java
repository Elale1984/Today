package edu.gcu.today.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edu.gcu.today.data.UserHelperClass;
import edu.gcu.today.databinding.ActivitySignUpBinding;


public class SignUp extends AppCompatActivity {

    private ActivitySignUpBinding binding;

    // Firebase
    private FirebaseAuth mAuth;
    private DatabaseReference mRef;

    // Firebase User ID
    private String uID;

    // Progress Dialogue
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);


        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        // Init Firebase Realtime Database
        // Firebase Realtime Database
        FirebaseDatabase root = FirebaseDatabase.getInstance();
        mRef = root.getReference("users");

        binding.btnSignUp.setOnClickListener(view1 -> ValidateUserInput());
        binding.tvHaveAnAccount.setOnClickListener(view12 -> NavigateToSignIn());

    }

    private void ValidateUserInput() {
        String name, email, password, re_password;
        
        name = Objects.requireNonNull(binding.etName.getText()).toString().trim();
        email = Objects.requireNonNull(binding.etEmail.getText()).toString().trim();
        password = Objects.requireNonNull(binding.etPassword.getText()).toString().trim();
        re_password = Objects.requireNonNull(binding.etReEnterPassword.getText()).toString().trim();

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.etEmail.setError("Invalid Email Format");
        } else if (TextUtils.isEmpty(password)) {
            // password not entered in field
            binding.etPassword.setError("Password Field is empty");
        }
        else if (!isValidPassword(password)) {
            // Password does not meet format requirements
            binding.etPassword.setError("Password must contain One Uppercase letter, " +
                    "one number, one special character, and be at least 8 characters long.");
        } else if (!re_password.equals(password)) {
            binding.etReEnterPassword.setError("The second password you entered does not match the first");
        } else {


            // Data was validated
            firebaseSignUp(name, email, password);
        }
    }

    private void firebaseSignUp(String name, String email, String password) {
        // configure progress dialogue
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Account Action");
        progressDialog.setMessage("Creating your account...");
        progressDialog.setCanceledOnTouchOutside(false);

        progressDialog.show();
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener(authResult -> {
                    // Register New Account
                    progressDialog.dismiss();

                    // Init User ID
                    uID = FirebaseAuth.getInstance().getUid();

                    // get the users info
                    FirebaseUser firebaseUser = mAuth.getCurrentUser();
                    assert firebaseUser != null;
                    String userEmail = firebaseUser.getEmail();
                    Toast.makeText(SignUp.this, "Account Created\n" + userEmail,
                            Toast.LENGTH_SHORT).show();

                    // Create Realtime Database
                    writeUserData(uID, name, email);

                    // log in user and launch weather app
                    startActivity(new Intent(SignUp.this, TodayMain.class));
                    finish();
                })
                .addOnFailureListener(e -> {
                    // Failed Registration of new user
                    Toast.makeText(SignUp.this, "There was an error " +
                                    "creating the new user" + e.getMessage(),
                            Toast.LENGTH_SHORT).show();
                });
    }

    private void writeUserData(String uID, String name, String email) {
        UserHelperClass newUser = new UserHelperClass();
        newUser.setName(name);
        newUser.setEmail(email);
        mRef.child(uID).setValue(newUser);
        mRef.keepSynced(true);
    }

    private boolean isValidPassword(String password) {
        Pattern pattern;
        Matcher matcher;

        // using regex.... The password must have one special char, one uppercase letter,
        // one number, and minimum of 8 chars
        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[@_#$%^&+=!])(?=\\S+$).{8,}$";
        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);

        return matcher.matches();

    }

    private void NavigateToSignIn() {
        startActivity(new Intent(this, SignIn.class));
    }
}