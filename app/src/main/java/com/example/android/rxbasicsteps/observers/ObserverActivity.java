package com.example.android.rxbasicsteps.observers;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.android.rxbasicsteps.R;
import com.example.android.rxbasicsteps.observers.model.Note;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ObserverActivity extends AppCompatActivity {
    private static final String TAG = ObserverActivity.class.getSimpleName();
    Disposable disposable;

    @SuppressLint("CheckResult")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_observer);

        Observable<Note> notesObservable = getNotesObservable();
        Observer<Note> notesObserver = getNotesObserver();

        notesObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(notesObserver);
    }

    private Observer<Note> getNotesObserver() {
        return new Observer<Note>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.d(TAG, "onSubscribe");
                disposable = d;
            }

            @Override
            public void onNext(Note note) {
                Log.d(TAG, "onNext: " + note.getNote());
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, "onError: " + e.getMessage());
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "onComplete");
            }
        };
    }

    private Observable<Note> getNotesObservable() {
        List<Note> notes = prepareNotes();
        return Observable.create(new ObservableOnSubscribe<Note>() {
            @Override
            public void subscribe(ObservableEmitter<Note> emitter) throws Exception {
                for (Note note : notes) {
                    if (!emitter.isDisposed()) {
                        emitter.onNext(note);
                    }
                }
                if (!emitter.isDisposed()) {
                    emitter.onComplete();
                }
            }
        });
    }

    private List<Note> prepareNotes() {
        List<Note> notes = new ArrayList<>();
        notes.add(new Note(1, "Buy tooth paste!"));
        notes.add(new Note(2, "Call brother!"));
        notes.add(new Note(3, "Watch Star Trek tonight!"));
        notes.add(new Note(4, "Pay gas bill!"));
        return notes;
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposable.dispose();
    }
}
