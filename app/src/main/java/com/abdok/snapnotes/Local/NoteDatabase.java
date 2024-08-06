package com.abdok.snapnotes.Local;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.abdok.snapnotes.Models.NoteModel;
import com.abdok.snapnotes.Utils.Converters;

@Database(entities = {NoteModel.class}, version = 1)
@TypeConverters({Converters.class})
public abstract class NoteDatabase extends RoomDatabase {

    public abstract NoteDao noteDao();

    private static volatile NoteDatabase INSTANCE;

    public static NoteDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (NoteDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    NoteDatabase.class, "note_database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
