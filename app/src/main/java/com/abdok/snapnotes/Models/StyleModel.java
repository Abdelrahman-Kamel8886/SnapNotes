package com.abdok.snapnotes.Models;

import com.abdok.snapnotes.Enums.Align;

public class StyleModel {

    Align align ;
    boolean bold ;
    boolean underline ;
    boolean italic ;
    int fontSize;

    public StyleModel() {
        this.align = Align.LEFT;
        this.bold = false;
        this.underline = false;
        this.italic = false;
        this.fontSize = 14;
    }

    public StyleModel(Align align, boolean bold, boolean underline, boolean italic, int fontSize) {
        this.align = align;
        this.bold = bold;
        this.underline = underline;
        this.italic = italic;
        this.fontSize = fontSize;
    }

    public int getFontSize() {
        return fontSize;
    }

    public void setFontSize(int fontSize) {
        this.fontSize = fontSize;
    }

    public Align getAlign() {
        return align;
    }

    public void setAlign(Align align) {
        this.align = align;
    }

    public boolean isBold() {
        return bold;
    }

    public void setBold(boolean bold) {
        this.bold = bold;
    }

    public boolean isUnderline() {
        return underline;
    }

    public void setUnderline(boolean underline) {
        this.underline = underline;
    }

    public boolean isItalic() {
        return italic;
    }

    public void setItalic(boolean italic) {
        this.italic = italic;
    }
}
