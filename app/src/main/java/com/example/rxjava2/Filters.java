package com.example.rxjava2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.os.Bundle;
import android.util.Log;

import com.jakewharton.rxbinding3.view.RxView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;

public class Filters extends AppCompatActivity {

    private static final String TAG = "RxJava2";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filters);

        AppCompatButton publish = findViewById(R.id.pubButton);

        RxView.clicks(publish)
                .debounce(1, TimeUnit.SECONDS)
                .subscribe(click -> Log.d(TAG, "Clicked!"));

        List<String> users = new ArrayList<>();

        Observable.just("user1", "user2", "user1", "user3")
                .distinct()
                .subscribe(user -> users.add(user));

        Log.d(TAG, "Users: " + users.toString());

        Observable<Number> numbers = Observable.just(1, 4.0, 3, 2.71, 2f, 7);
        Observable<Integer> integers = numbers.ofType(Integer.class);

        integers.subscribe((Integer x) -> Log.d(TAG, "Integer: " + x));

        Observable.create(emitter -> {
            emitter.onNext(1);

            Thread.sleep(500);

            emitter.onNext(2);

            Thread.sleep(1700);

            emitter.onNext(3);

            emitter.onComplete();
        })
                .timeout(1, TimeUnit.SECONDS)
                .subscribe(item -> Log.d(TAG, "Next item: " + item),
                        error -> Log.e(TAG, error.getMessage()));

        Observable.range(0, 10)
                .filter(i -> i <= 5)
                .subscribe(item -> Log.d(TAG, "Smaller or equal than 5: " + item));
    }
    
}
