package com.example.rxjava2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import java.util.Objects;

import io.reactivex.Observable;

public class Transformators extends AppCompatActivity {

    private static final String TAG = "RxJava2";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transformators);

        // cast, groupBy, scan, window, flatMap

        // Cast - rzutuje elementy na dany typ. Trzeba przefiltrowac dla danego typu

        Observable.just(1, 2.0, 3f, 5.65)
                .filter((Number n) -> n instanceof Integer)
                .cast(Integer.class)
                .subscribe(numb -> Log.d(TAG, String.valueOf(numb)));

        // groupBy - pozwala grupowac elementy na podstawie danego warunku

        Observable.just("Ala", "Ania", "Beata", "Kamila", "Katarzyna")
                .groupBy(name -> name.charAt(0))
                .concatMapSingle(Observable::toList) // podobne do flatMap, pozwala uzywac funkcji jak na observable
                .subscribe(group -> Log.d(TAG, String.valueOf(group)));

        // Scan - Pozwala zaaplikowac funkcje do elementow. Pamieta poprzedni wynik

        Observable.just(1, 2, 3, 4, 5)
                .scan(1, (result, i) -> result * i)
                .subscribe(result -> Log.d(TAG, "Silnia: " + result));

        // Window - pozwala grupowac elementy w 'okna' Observable lub Flowable

        Observable.range(0, 10)
                .window(2, 4) // Grupuje po 2 elementy co 4
                .flatMapSingle(it -> it.toSortedList())
                .subscribe(window -> Log.d(TAG, String.valueOf(window)));

        // flatMap - dzieli pojedynczy Observable na wiele Observables
        // dziala asynchronicznie i uzywane jest tam, gdzie mapujemy innym observable

        Observable.just("user1", "user2", "user3")
                .flatMap(user -> getUserDetails(user))
                .subscribe(item -> Log.d(TAG, String.valueOf(item)));

    }

    private Observable getUserDetails(String name) {
        return Observable.just(name).map(user -> user + " + jakies istotne informacje z DB");
    }
}
