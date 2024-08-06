package com.abdok.snapnotes.Ui.Note;

import android.app.Application;

import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.abdok.snapnotes.Enums.ConfirmationState;
import com.abdok.snapnotes.Local.NoteDatabase;
import com.abdok.snapnotes.Models.NoteModel;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class NoteViewModel extends AndroidViewModel {

    private MutableLiveData<ConfirmationState> confirmationState = new MutableLiveData<>();

    private NoteDatabase noteDatabase ;

    public MutableLiveData<ConfirmationState> getConfirmationState() {
        return confirmationState;
    }

    public NoteViewModel(@NonNull Application application) {
        super(application);
        noteDatabase = NoteDatabase.getDatabase(application);
    }


    public void InsertNote(NoteModel note) {
        noteDatabase.noteDao().insert(note)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        confirmationState.setValue(ConfirmationState.SUCCESS);
                    }

                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                        confirmationState.setValue(ConfirmationState.FAILED);
                    }
                });
    }

    public void updateNote(NoteModel note){
        noteDatabase.noteDao().update(note)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        confirmationState.setValue(ConfirmationState.SUCCESS);
                    }

                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                        confirmationState.setValue(ConfirmationState.FAILED);
                    }
                });
    }

}