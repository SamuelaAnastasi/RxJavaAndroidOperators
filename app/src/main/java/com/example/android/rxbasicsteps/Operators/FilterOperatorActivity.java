package com.example.android.rxbasicsteps.Operators;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;


import com.example.android.rxbasicsteps.R;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Predicate;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class FilterOperatorActivity extends AppCompatActivity {
    private static final String TAG = FilterOperatorActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter_operator);

        Observable.just(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
                .filter(new Predicate<Integer>() {
                    @Override
                    public boolean test(Integer integer) throws Exception {
                        return integer %2 == 0;
                    }
                }).subscribe(new DisposableObserver<Integer>() {
            @Override
            public void onNext(Integer integer) {
                Log.d(TAG, "Even number: " + integer);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });

        Observable<User> usersObservable = getUsersObservable();
        usersObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .filter(new Predicate<User>() {
                    @Override
                    public boolean test(User user) throws Exception {
                        return user.getGender().equalsIgnoreCase("female");
                    }
                })
                .subscribeWith(new DisposableObserver<User>() {
                    @Override
                    public void onNext(User user) {
                        Log.d(TAG, user.getName() + ", " + user.getGender());
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private Observable<User> getUsersObservable() {
        String[] maleUsers = {"Mark", "John", "Trump", "Obama"};
        String[] femaleUsers = {"Lucy", "Scarlett", "April"};

        List<User> users = new ArrayList<>();

        for(String name : maleUsers) {
            User user = new User();
            user.setName(name);
            user.setGender("male");
            users.add(user);
        }

        for (String name : femaleUsers) {
            User user = new User();
            user.setName(name);
            user.setGender("female");
            users.add(user);
        }

        return Observable.create(new ObservableOnSubscribe<User>() {

            @Override
            public void subscribe(ObservableEmitter<User> emitter) throws Exception {
                for(User user : users) {
                    if (!emitter.isDisposed()) {
                        emitter.onNext(user);
                    } else {
                        emitter.onComplete();
                    }
                }
            }
        }).subscribeOn(Schedulers.io());
    }

    private class User {
        private String name;
        private String gender;

        public String getName() {
            return name;
        }

        public String getGender() {
            return gender;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }
    }
}
