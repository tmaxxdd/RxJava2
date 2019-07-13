package com.example.rxjava2;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import hu.akarnokd.rxjava2.math.MathObservable;
import io.reactivex.Observable;


public class NumberFilter extends AppCompatActivity {

    private static final String TAG = "RxJava2";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_number_filter);

        // Generates random numbers from 0 up to 100
        Random random = new Random();
        Observable<Integer> randomNumbers = Observable
                .interval(200, TimeUnit.MILLISECONDS)
                .take(20)
                .map(lng -> random.nextInt(10));

        randomNumbers.subscribe(integer -> Log.d(TAG, String.valueOf(integer)));

        // Count

        randomNumbers
                .count()
                .subscribe(sum -> Log.d(TAG, "Wylosowano " + sum + " liczb"));

        // Filter

        randomNumbers
                .distinct()
                .filter( x -> x < 5)
                .toSortedList()
                .subscribe(result -> Log.d(TAG, "Mniejsze od 5 to: " + result));


        // toMultimap

        randomNumbers
                .distinct()
                .toMultimap(i -> (i % 2 == 0) ? "even" : "odd")
                .subscribe(result -> Log.d(TAG, String.valueOf(result)));

        // Avg

        MathObservable
                .averageFloat(randomNumbers)
                .subscribe(avg -> Log.d(TAG, "Srednia to: " + avg));

        // Sum

        MathObservable
                .sumInt(randomNumbers)
                .subscribe(sum -> Log.d(TAG, "Suma to: " + sum));

    }

}
