package com.abdok.snapnotes.Utils;

import androidx.room.TypeConverter;

import com.abdok.snapnotes.Models.StyleModel;
import com.google.gson.Gson;

public class Converters {

    @TypeConverter
    public static String fromStyleModel(StyleModel style) {
        return new Gson().toJson(style);
    }

    @TypeConverter
    public static StyleModel toStyleModel(String styleJson) {
        return new Gson().fromJson(styleJson, StyleModel.class);
    }
}