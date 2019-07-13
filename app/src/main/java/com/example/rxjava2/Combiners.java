package com.example.rxjava2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;

public class Combiners extends AppCompatActivity {

    private static final String TAG = "RxJava2";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_combiners);


        // Merge - pozwala laczyc wiele obiektow Observable w jeden

        Observable.just(1, 2, 3)
                .mergeWith(Observable.range(4, 7))
                .subscribe(item -> Log.d(TAG, "Item: " + item));

        // Zip - pozwala laczyc elementy z dwoch lub wiecej Observable za pomoca specjalnej funkcji

        Observable even = Observable.range(0, 10).filter(i -> i % 2 == 0);
        Observable odd = Observable.range(0, 10).filter(i -> i % 2 != 0);

        even.zipWith(odd, (e, o) -> o + " jest wieksze od " + e)
                .subscribe(item -> Log.d(TAG, String.valueOf(item)));

        // startWith - pozwala dodac na poczatek wartosc od ktorej zacznie emitowac

        Observable.just("ma", "kota")
                .startWith("Ala")
                .subscribe(item -> Log.d(TAG, item));

    }
}
