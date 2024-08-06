package com.abdok.snapnotes.Utils;

import com.abdok.snapnotes.Models.NoteModel;

public class SharedModel {


    private static boolean editNoteState ;
    private static NoteModel selectedNote ;

    public static boolean isEditNoteState() {
        return editNoteState;
    }

    public static void setEditNoteState(boolean editNoteState) {
        SharedModel.editNoteState = editNoteState;
    }

    public static NoteModel getSelectedNote() {
        return selectedNote;
    }

    public static void setSelectedNote(NoteModel selectedNote) {
        SharedModel.selectedNote = selectedNote;
    }
}
