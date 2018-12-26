package com.example.android.rxbasicsteps.observers;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.android.rxbasicsteps.R;
import com.example.android.rxbasicsteps.observers.model.Note;

import io.reactivex.Completable;
import io.reactivex.CompletableEmitter;
import io.reactivex.CompletableObserver;
import io.reactivex.CompletableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class CompletableObserverActivity extends AppCompatActivity {

    private static final String TAG = CompletableObserverActivity.class.getSimpleName();
    Disposable disposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_completable_observer);

        Note note = new Note(1, "Do homework");
        Completable completableObservable = updateNote(note);
        CompletableObserver completableObserver = getCompletableObserver();

        completableObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(completableObserver);
    }


    private CompletableObserver getCompletableObserver() {
        return new CompletableObserver() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.d(TAG, "onSubscribe");
                disposable = d;
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "onComplete: Note updated successfully!");
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, "onError: " + e.getMessage());
            }
        };
    }

    // Update some data on server - no data is emitted by Completable
    private Completable updateNote(Note note) {
        return Completable.create(new CompletableOnSubscribe() {
            @Override
            public void subscribe(CompletableEmitter emitter) throws Exception {
                if(!emitter.isDisposed()) {
                    Thread.sleep(1000);
                    emitter.onComplete();
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposable.dispose();
    }
}
