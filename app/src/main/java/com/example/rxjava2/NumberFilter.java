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

        Random random = new Random();
        Observable<Integer> randomGenerator = Observable.fromCallable(() -> random.nextInt(10))
                .repeat()
                .take(20);

        randomGenerator.subscribe(randInt -> Log.d(TAG, "Random: " + randInt));

        randomGenerator.count().subscribe(count -> Log.d(TAG, "Generated: " + count + " numbers"));

        randomGenerator
                .distinct()
                .toMultimap(i -> (i % 2 == 0) ? "even" : "odd")
                .subscribe(result -> Log.d(TAG, String.valueOf(result)));

        MathObservable.averageFloat(randomGenerator)
                .subscribe(avg -> Log.d(TAG, "The average is: " + avg));

        MathObservable.sumInt(randomGenerator)
                .subscribe(sum -> Log.d(TAG, "The sum: " + sum));
    }
    
}
