package com.example.rxjava2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;

import static io.reactivex.Completable.timer;

public class TimeManagement extends AppCompatActivity {

    private static final String TAG = "RxJava2";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_management);

        // Timer - Opoznia WYKONANIE danej operacji o jednostke czasu

        Log.d(TAG, "Before: " + System.currentTimeMillis());

        Observable
                .timer(2, TimeUnit.SECONDS)
                .subscribe(time -> Log.d(TAG, "After: " + System.currentTimeMillis()));


        // Delay - Opoznia EMISJE danej wartosci o jednostke czasu

        Observable
                .create(emitter -> {
                    Log.d(TAG, "START: " + System.currentTimeMillis());
                    emitter.onNext("");
                    emitter.onComplete();
                })
                .delay(2, TimeUnit.SECONDS)
                .subscribe(value -> Log.d(TAG, "END: " + System.currentTimeMillis()));

        // Interval - Opoznia emisje KOLEJNEJ wartosci o jednostke czasu
        // Domyslnie emituje nieskonczona ilosc liczb zaczynajac od 0.
        // By uzywac 'swoich' wartosci musimy je zmapowac

        String[] letters = {"R", "X", "J", "A", "V", "A", "2"};

        Observable
                .interval(1, TimeUnit.SECONDS)
                .take(7)
                .map(i -> letters[i.intValue()])
                .subscribe(number -> Log.d(TAG, "Number is: " + number));

    }
}
