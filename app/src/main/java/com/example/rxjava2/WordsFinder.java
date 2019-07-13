package com.example.rxjava2;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;

import com.jakewharton.rxbinding3.widget.RxTextView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class WordsFinder extends AppCompatActivity {

    private static final String TAG = "RxJava2";

    private Observable<String> loremStream;
    private List<String> items = new ArrayList<>();
    private ArrayAdapter<String> adapter;
    private Disposable search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_words_finder);

        String[] lorem = getResources().getString(R.string.ipsum).split(" ");
        loremStream = Observable.fromArray(lorem);

        configureLV();

        AppCompatEditText searchField = findViewById(R.id.search);
        search = RxTextView.textChanges(searchField)
                .debounce(300, TimeUnit.MILLISECONDS)
                .flatMapSingle(this::findMatchingWords)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(output -> {
                    updateItems(output);
                    Log.d(TAG, "new items: " + output);
                });

    }

    @Override
    protected void onPause() {
        super.onPause();
        search.dispose();
    }

    private Single<List<String>> findMatchingWords(CharSequence input) {
        return loremStream
                .filter(item -> item.startsWith(input.toString()))
                .distinct()
                .map(word -> word.replace(".", "").replace(",",""))
                .toSortedList();
    }

    private void configureLV() {

        ListView listView = findViewById(R.id.list_view);
        adapter = new ArrayAdapter<>(
                this.getApplicationContext(),
                android.R.layout.simple_list_item_1,
                items);
        listView.setAdapter(adapter);

    }

    private void updateItems(Object newItems) {
        items.clear();
        items.addAll((Collection<? extends String>) newItems);
        adapter.notifyDataSetChanged();
    }

}
