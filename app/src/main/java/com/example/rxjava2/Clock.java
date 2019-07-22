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

    private Disposable timer;
    private CompositeDisposable compositeDisposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clock);

        compositeDisposable = new CompositeDisposable();

        AppCompatTextView timeView = findViewById(R.id.time);
        AppCompatButton start = findViewById(R.id.start);
        AppCompatButton stop = findViewById(R.id.stop);

        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");

        Disposable starter = RxView.clicks(start).subscribe(click -> timer = getTimer(timeView, sdf));
        Disposable stoper = RxView.clicks(stop).subscribe(click -> stopTimer(timeView));

        compositeDisposable.add(starter);
        compositeDisposable.add(stoper);
        compositeDisposable.add(timer);
    }

    private void stopTimer(AppCompatTextView timeView) {
        if (timer != null) {
            timer.dispose();
        }
        timeView.setText("Stopped");
    }

    @Override
    protected void onPause() {
        super.onPause();
        compositeDisposable.clear();
    }

    private Disposable getTimer(AppCompatTextView timeView, SimpleDateFormat sdf) {
        return Observable.timer(1, TimeUnit.SECONDS)
                .map(lng -> getCurrentTime(sdf))
                .repeat()
                .subscribeOn(Schedulers.single())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(timeView::setText);
    }

    private String getCurrentTime(SimpleDateFormat simpleDateFormat) {
        return simpleDateFormat.format(new Date());
    }

}
