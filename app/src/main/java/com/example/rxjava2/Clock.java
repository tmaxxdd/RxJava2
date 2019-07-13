package com.example.rxjava2;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class Clock extends AppCompatActivity {

    Disposable disposable = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clock);

        TextView timeView = findViewById(R.id.time);
        AppCompatButton stop = findViewById(R.id.stop);
        AppCompatButton start = findViewById(R.id.start);


        start.setOnClickListener(click -> disposable = startTimer(timeView));

        stop.setOnClickListener(click -> {
            if (disposable != null) {
                disposable.dispose();
            }
            timeView.setText("Stopped");
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (disposable != null) {
            disposable.dispose();
        }
    }

    private Disposable startTimer(TextView textView) {
        return Observable.timer(1000, TimeUnit.MILLISECONDS)
                .map(o -> getCurrentTime())
                .repeat() // important to show more that once
                .subscribeOn(Schedulers.single())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(textView::setText);
    }

    public String getCurrentTime() {
        return new SimpleDateFormat("HH:mm:ss").format(new Date());
    }
}
