package com.example.rxjava2;

import android.os.Bundle;

import com.androidhuman.rxfirebase2.database.RxFirebaseDatabase;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.jakewharton.rxbinding3.view.RxView;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.CompletableObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class Firebase extends AppCompatActivity {

    private static final String TAG = "RxJava2";
    private List<String> items = new ArrayList<>();
    private ArrayAdapter<String> adapter;
    private DatabaseReference database;
    private Disposable fabClick;
    private Disposable firebase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firebase);

        initLV();

        FloatingActionButton fab = findViewById(R.id.fab);
        fabClick = RxView.clicks(fab)
                .subscribe(click -> addNewItem());

        // Path
        database = FirebaseDatabase
                .getInstance()
                .getReference();

        // Get keys
        firebase = RxFirebaseDatabase
                .dataChanges(database)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        data -> updateItems(data.getChildren()),
                        error -> Log.e(TAG, error.getMessage())
                );

    }

    @Override
    protected void onPause() {
        super.onPause();
        fabClick.dispose();
        firebase.dispose();
    }

    private void addNewItem() {
        RxFirebaseDatabase
                .setValue(database.child(String.valueOf(items.size() + 1)), "New value")
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        Toast.makeText(Firebase.this, "Dodano wartosc", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(Firebase.this, "Blad podczas dodawania!", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void initLV() {
        ListView listView = findViewById(R.id.fb_list);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, items);
        listView.setAdapter(adapter);
    }

    private void updateItems(Iterable<DataSnapshot> children) {
        items.clear();
        for (DataSnapshot data : children) {
            items.add(data.getKey() + " => " + data.getValue());
        }
        adapter.notifyDataSetChanged();
    }

}
