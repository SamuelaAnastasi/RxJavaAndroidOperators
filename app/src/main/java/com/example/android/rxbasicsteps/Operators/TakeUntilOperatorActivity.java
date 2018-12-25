package com.example.android.rxbasicsteps.Operators;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.android.rxbasicsteps.R;

import io.reactivex.Observable;
import io.reactivex.functions.Predicate;
import io.reactivex.observers.DisposableObserver;

public class TakeUntilOperatorActivity extends AppCompatActivity {
    private static final String TAG = TakeUntilOperatorActivity.class.getSimpleName();

    @SuppressLint("CheckResult")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_until_operator);

        Observable.just(1, 2, 3, 4, 5, 6, 7)
                .takeUntil(new Predicate<Integer>() {
                    @Override
                    public boolean test(Integer integer) throws Exception {
                        return integer == 5;
                    }
                })
                .subscribeWith(new DisposableObserver<Integer>() {
                    @Override
                    public void onNext(Integer integer) {
                        Log.d(TAG, "onNext: " + integer);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "All items emitted!");
                    }
                });
    }
}
