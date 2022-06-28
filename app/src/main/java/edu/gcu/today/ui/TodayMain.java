package edu.gcu.today.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import edu.gcu.today.R;
import edu.gcu.today.databinding.ActivitySignUpBinding;
import edu.gcu.today.databinding.ActivityTodayMainBinding;

public class TodayMain extends AppCompatActivity {

    ActivityTodayMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTodayMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        DisplayTodayList();
    }

    private void DisplayTodayList() {

    }
}