package com.example.rxjava2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.disposables.Disposable;

public class BackPressure extends AppCompatActivity {

    private Disposable timeLogger;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_back_pressure);

        timeLogger = Flowable
                .interval(1, TimeUnit.NANOSECONDS)
                .buffer(3, TimeUnit.SECONDS)
                .map(String::valueOf)
                .subscribe(this::print);

    }

    @Override
    protected void onPause() {
        super.onPause();
        timeLogger.dispose();
    }

    private void print(String message) {
        Log.d("RxJava2", message);
    }
}
