package com.example.rxjava2;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.jakewharton.rxbinding3.widget.RxTextView;

public class Validator extends AppCompatActivity {

    private static final String TAG = "RxJava2";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_validator);

        EditText input = findViewById(R.id.password);
        TextView message = findViewById(R.id.message);

        RxTextView.textChanges(input)
                .map(this::isPasswordValid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        valid -> {
                            if (valid) {
                                message.setText("Haslo jest prawidłowe.");
                                message.setTextColor(Color.GREEN);
                            } else {
                                message.setText("Haslo niezgodne z wymaganiami.");
                                message.setTextColor(Color.RED);
                            }
                        });
    }

    public boolean isPasswordValid(CharSequence pass) {
        /*
        ^                 # start-of-string
        (?=.*[0-9])       # a digit must occur at least once
        (?=.*[a-z])       # a lower case letter must occur at least once
        (?=.*[A-Z])       # an upper case letter must occur at least once
        (?=.*[@#$%^&+=])  # a special character must occur at least once
        .{8,}             # anything, at least eight places though
        $                 # end-of-string
         */
        return pass.toString().matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).{8,}$");
    }

}
