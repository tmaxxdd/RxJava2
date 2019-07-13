package com.example.rxjava2;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "RxJava2";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Learning

        AppCompatButton creators = findViewById(R.id.creators);
        creators.setOnClickListener(c -> startActivity(new Intent(this, Creators.class)));

        AppCompatButton observables = findViewById(R.id.observables);
        observables.setOnClickListener(c -> startActivity(new Intent(this, Observables.class)));

        AppCompatButton timeManagement = findViewById(R.id.time_management);
        timeManagement.setOnClickListener(c -> startActivity(new Intent(this, TimeManagement.class)));

        AppCompatButton threads = findViewById(R.id.threads);
        threads.setOnClickListener(c -> startActivity(new Intent(this, Threads.class)));

        AppCompatButton filters = findViewById(R.id.filters);
        filters.setOnClickListener(c -> startActivity(new Intent(this, Filters.class)));

        AppCompatButton numberFilter = findViewById(R.id.number_filter);
        numberFilter.setOnClickListener(c -> startActivity(new Intent(this, NumberFilter.class)));

        AppCompatButton combiners = findViewById(R.id.combiners);
        combiners.setOnClickListener(c -> startActivity(new Intent(this, Combiners.class)));

        AppCompatButton transformators = findViewById(R.id.transformators);
        transformators.setOnClickListener(c -> startActivity(new Intent(this, Transformators.class)));

        AppCompatButton wordsFinder = findViewById(R.id.words_finder);
        wordsFinder.setOnClickListener(c -> startActivity(new Intent(this, WordsFinder.class)));

        AppCompatButton firebase = findViewById(R.id.firebase);
        firebase.setOnClickListener(c -> startActivity(new Intent(this, Firebase.class)));


        // Implementation

        AppCompatButton clock = findViewById(R.id.clock);
        clock.setOnClickListener(c -> startActivity(new Intent(this, Clock.class)));

        AppCompatButton validator = findViewById(R.id.validator);
        validator.setOnClickListener(c -> startActivity(new Intent(this, Validator.class)));

    }


}
