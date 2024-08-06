package com.abdok.snapnotes.Ui.Home;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.abdok.snapnotes.Enums.ConfirmationState;
import com.abdok.snapnotes.Local.NoteDatabase;
import com.abdok.snapnotes.Models.NoteModel;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class HomeViewModel extends AndroidViewModel {

    private MutableLiveData<List<NoteModel>> list = new MutableLiveData<>();
    private MutableLiveData<ConfirmationState> deletionState = new MutableLiveData<>();
    private NoteDatabase noteDatabase ;

    public MutableLiveData<List<NoteModel>> getList() {
        return list;
    }

    public MutableLiveData<ConfirmationState> getDeletionState() {
        return deletionState;
    }

    public HomeViewModel(@NonNull Application application) {
        super(application);
        noteDatabase = NoteDatabase.getDatabase(application);
    }

    public void getAllNotes(){
        noteDatabase.noteDao().getAllNotes()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<NoteModel>>() {
                    @Override
                    public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@io.reactivex.rxjava3.annotations.NonNull List<NoteModel> noteModels) {
                        list.setValue(noteModels);
                    }

                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {

                    }
                });

    }
    public void deleteNote(NoteModel noteModel){
        noteDatabase.noteDao().delete(noteModel)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        deletionState.setValue(ConfirmationState.SUCCESS);
                    }

                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                        deletionState.setValue(ConfirmationState.FAILED);
                    }
                });

    }
}
