package com.example.android.rxbasicsteps.Operators;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.android.rxbasicsteps.R;

import rx.Observable;
import rx.Subscriber;
import rx.observables.MathObservable;

public class SumOperatorActivity extends AppCompatActivity {

    private static final String TAG = SumOperatorActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sum_operator);

        Integer[] numbers = {5, 101, 404, 22, 3, 1024, 65};
        Observable<Integer> integerObservable = Observable.from(numbers);

        MathObservable
                .sumInteger(integerObservable)
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Integer integer) {
                        Log.d(TAG, "Sum value: " + integer);
                    }
                });
    }
}
