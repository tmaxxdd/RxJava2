package com.example.rxjava2;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class ComDisposable extends AppCompatActivity {

    private static final String TAG = "ComDisposable";

    // Tworzy kontener dla obiektow Disposable. Pozwala akumulowac wszystkich subskrybentow
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_composite_disposable);

        // Wersja bez lambdy

        Observable.just(1, 2, 3, 4, 5, 6)
                .subscribe(new Observer<Integer>() {

                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d(TAG, "onSubscribe");
                    }

                    @Override
                    public void onNext(Integer integer) {
                        Log.d(TAG, "onNext: " + integer);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete");
                    }

                });

        // Emituje sztywno zdefiniowany obiekt/elementy

        Disposable fromOneToSix = Observable.just(1, 2, 3, 4, 5, 6)
                .subscribe(
                        value -> Log.d(TAG, "onNext: " + value),  //lepiej nazywac zmiennymi niz 'onNext'!
                        error -> Log.d(TAG, error.getMessage())
                );

        compositeDisposable.add(fromOneToSix);

        // przy just mozna utworzyc w kolejnosci do 9 elementow
        //Observable.just("1", "A", 3.2, "def", 2, true, ...)


        // fromIterable pozwala utworzyc Observable z obiektow, ktore dziedzicza po Iterable

        List<Integer> list = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8));
        Observable<Integer> fromIterable = Observable.fromIterable(list);
        Disposable listPrinter = fromIterable.subscribe(item -> Log.d(TAG, String.valueOf(item)));

        compositeDisposable.add(listPrinter);


        // create - pozwala na emitowanie elementow po kolei jeden po drugim.

        Observable<String> getUserID = Observable.create( emitter -> {
                    emitter.onNext(getName());
                    emitter.onNext(String.valueOf(getSurname()));
                    emitter.onComplete();

                }
        );
        Disposable userPrinter = getUserID.subscribe(v -> Log.d(TAG, "User is: " + v));

        compositeDisposable.add(userPrinter);


        // range - pozwala wygenerowac Integer lub long z danego przedzialu

        Observable<Integer> arabicNumerals = Observable
                .range(0, 10);

        Disposable arab = arabicNumerals.subscribe(numeral -> Log.d(TAG, "next Numeral is: " + numeral));
        arab.dispose();

        // generate - tworzy synchroniczny generator wartosci

        int startValue = 1;
        int incrementValue = 1;
        Flowable<Object> incrementer = Flowable.generate(() -> startValue, (s, emitter) -> {
            int nextValue = s + incrementValue;
            emitter.onNext(nextValue);
            return nextValue;
        }); // limituje do 20 emisji

        Disposable generator = incrementer.subscribe(value -> Log.d(TAG, "Generated: " + value));

        compositeDisposable.add(generator);

    }

    @Override
    protected void onStop() {
        super.onStop();
        // Czysci kontener oraz wywoluje dispose() na wszystkich zrodlach
        compositeDisposable.clear();
    }

    String getName() {return "Tomek";}
    String getSurname(){ return "Kadziolka";}
}

