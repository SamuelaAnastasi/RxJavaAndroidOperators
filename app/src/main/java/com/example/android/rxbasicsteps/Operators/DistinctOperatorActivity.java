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
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class DistinctOperatorActivity extends AppCompatActivity {

    private static final String TAG = DistinctOperatorActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_distinct_operator);

        Observable<Integer> integerObservable = Observable.just(10, 3, 10, 200, 34, 45, 34, 56, 56);
        integerObservable
                .distinct()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Integer integer) {
                        Log.d(TAG, "onNext: " + integer);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

        Observable<Note> observable = getNotesObservables();
        DisposableObserver<Note> disposableObserver = getDisposableObserver();
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .distinct()
                .subscribeWith(disposableObserver);
    }

    private DisposableObserver<Note> getDisposableObserver() {
        return new DisposableObserver<Note>() {
            @Override
            public void onNext(Note note) {
                Log.d(TAG, "onNext: " + note.getNote());
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {
                Log.d(TAG, "onComplete");
            }
        };
    }

    private Observable<Note> getNotesObservables() {
        final List<Note> notes = prepareNotesList();

        return Observable.create(new ObservableOnSubscribe<Note>() {
            @Override
            public void subscribe(ObservableEmitter<Note> emitter) throws Exception {
                for (Note note : notes) {
                    if (!emitter.isDisposed()) {
                        emitter.onNext(note);
                    }
                }

                if ( !emitter.isDisposed()) {
                    emitter.onComplete();
                }
            }
        });
    }

    private List<Note> prepareNotesList () {
        List<Note> notes = new ArrayList<>();
        notes.add(new Note(1, "Buy tooth paste!"));
        notes.add(new Note(2, "Call brother!"));
        notes.add(new Note(3, "Call brother!"));
        notes.add(new Note(4, "Pay power bill!"));
        notes.add(new Note(5, "Watch Narcos tonight!"));
        notes.add(new Note(6, "Buy tooth paste!"));
        notes.add(new Note(7, "Pay power bill!"));
        return notes;
    }

    private class Note {
        private int id;
        private String note;

        public Note(int id, String note) {
            this.id = id;
            this.note = note;
        }

        public int getId() {
            return id;
        }

        public String getNote() {
            return note;
        }

        public void setId(int id) {
            this.id = id;
        }

        public void setNote(String note) {
            this.note = note;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }

            if (!(obj instanceof Note)) {
                return false;
            }

            return note.equalsIgnoreCase(((Note) obj).getNote());
        }

        @Override
        public int hashCode() {
            int hash = 3;
            hash = 53 * hash + (this.note != null ? this.note.hashCode() : 0);
            return hash;
        }
    }
}
