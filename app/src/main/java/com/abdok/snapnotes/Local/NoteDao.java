package com.abdok.snapnotes.Local;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import com.abdok.snapnotes.Models.NoteModel;
import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

@Dao
public interface NoteDao {

    @Insert
    Completable insert(NoteModel note);

    @Update
    Completable update(NoteModel note);

    @Delete
    Completable delete(NoteModel note);


    @Query("SELECT * FROM notes")
    Single<List<NoteModel>> getAllNotes();



}