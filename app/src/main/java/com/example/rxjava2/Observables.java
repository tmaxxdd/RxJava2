package com.example.rxjava2;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.MaybeObserver;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;

public class Observables extends AppCompatActivity {

    private static final String TAG = "Observables";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_observables);

        //TODO Observable, Flowable, Single, Maybe, Completable

        // Observable - emituje 0 lub wiecej elementow. Konczy sukcesem lub bledem

        Observable
                .just("a", "b", "c")
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(String s) {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

        // Flowable - emituje 0 lub wiecej elementow. Wspiera backpressure -
        // czyli pozwala kontrolowac jak szybko emitowane sa elementy

        // Backpressure - sytuacja w ktorej observable generuje dane szybciej niz
        // observer jest w stanie je skonsumowac

        Flowable
                .range(0, 100)
                .buffer(10) // Robi paczki danych po 10
                .delay(3, TimeUnit.SECONDS) // Zaczyna wysylac po 3 sec
                .subscribe(next -> Log.d(TAG, "Pack: " + next));

        // Single emituje 1 element lub blad. Imituje wywolanie funkcji
        // Zwykle uzywa sie tego do requestow API/HTTP np poprzez Retrofita

        Single<List<String>> namesSingle = Single.just(getNames());
        namesSingle.subscribe(new SingleObserver<List<String>>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onSuccess(List<String> names) {
                Log.d(TAG, "List = " + names);
            }

            @Override
            public void onError(Throwable e) {

            }
        });

        // Maybe - Zwraca obiekt lub nie, lub blad posiada metode onComplete, ktora
        // wskazuje na brak obiektu. Imituje Optional w javie.

        // Optional - zabezpieczenie przed NullPointerException

        Maybe.just("Rxjava").subscribe(new MaybeObserver<String>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onSuccess(String s) {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });

        // Completable - Skupiony na tym czy czynnosc sie powiodla czy nie

        Runnable runnable = () -> Log.d(TAG, "Hello World!");

        Completable completable = Completable.fromRunnable(runnable);

        completable.subscribe(() -> Log.d(TAG, "Done"), error -> error.printStackTrace());

    }

    private List<String> getNames() {
        List<String> names = new ArrayList<>();
        names.add("Bartek");
        names.add("Ala");
        names.add("Ola");

        return names;
    }
}
