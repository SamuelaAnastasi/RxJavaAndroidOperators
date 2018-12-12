package com.example.android.rxbasicsteps.Operators;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.android.rxbasicsteps.R;

import rx.Subscriber;
import rx.observables.MathObservable;
import rx.Observable;


public class MaxOperatorActivity extends AppCompatActivity {
    private static final String TAG = MaxOperatorActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_max_operator);

        Integer[] numbers = {5, 101, 404, 22, 3, 1024, 65};

        Observable<Integer> observable = Observable.from(numbers);

        MathObservable
                .max(observable)
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Integer integer) {
                        Log.d(TAG, "Max value: " + integer);
                    }
                });

        Observable<Float> floatObservable = Observable.just(10.5f, 11.5f, 14.5f);
        MathObservable.max(floatObservable)
                .subscribe(new Subscriber<Float>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Float aFloat) {
                        Log.d(TAG, "Max of 10.5f, 11.5f, 14.5f: " + aFloat);
                    }
                });

        Observable<Double> doubleObservable = Observable.just(13.5D, 45.3D, 33.6D);
        MathObservable.max(doubleObservable).subscribe(new Subscriber<Double>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Double aDouble) {
                Log.d(TAG, "Max of 13.5D, 45.3D, 33.6D: " + aDouble);
            }
        });
    }
}
