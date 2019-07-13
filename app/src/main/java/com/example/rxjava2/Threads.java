package com.example.rxjava2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;

import android.os.Bundle;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class Threads extends AppCompatActivity {
    private AppCompatImageView imgv1;
    private Disposable imageLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_threads);

        // Example loading a bitmap
        imgv1 = findViewById(R.id.image1);

    }

    @Override
    protected void onStart() {
        super.onStart();

        imageLoader = Observable.create(emitter -> {

                    emitter.onNext(R.drawable.thumb_1080_720);

                    Thread.sleep(2000); // Symulacja pobierania danych

                    emitter.onNext(R.drawable.image_1080_720);

                    emitter.onComplete();

                }
        ).subscribeOn(Schedulers.io()) // Operacje powyzej wykonaja sie w tle
                .observeOn(AndroidSchedulers.mainThread()) // Zmiana zdjecia wykona sie w watku glownym
                .subscribe(img -> imgv1.setImageResource((Integer) img));

    }

    @Override
    protected void onPause() {
        super.onPause();
        imageLoader.dispose();
    }
}
