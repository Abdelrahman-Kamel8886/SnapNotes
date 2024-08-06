package com.abdok.snapnotes.Ui.Note;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.Toast;

import com.abdok.snapnotes.Enums.Align;
import com.abdok.snapnotes.Enums.ConfirmationState;
import com.abdok.snapnotes.Models.NoteModel;
import com.abdok.snapnotes.Models.StyleModel;
import com.abdok.snapnotes.R;
import com.abdok.snapnotes.Utils.Consts;
import com.abdok.snapnotes.Utils.SharedModel;
import com.abdok.snapnotes.databinding.FragmentNoteBinding;

public class NoteFragment extends Fragment {

    FragmentNoteBinding binding;
    NoteViewModel viewModel ;
    StyleModel style ;
    boolean flag = false ;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_note, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = FragmentNoteBinding.bind(view);
        viewModel = new ViewModelProvider(this).get(NoteViewModel.class);

        if(SharedModel.isEditNoteState()){
            binding.contentEdit.setText(SharedModel.getSelectedNote().getContent());
            binding.headerEdit.setText(SharedModel.getSelectedNote().getHeader());
            style = SharedModel.getSelectedNote().getStyle();

        }
        else{
            style = new StyleModel();
        }
        binding.fontSizeSlider.setProgress(getProgressForSize(style.getFontSize()));
        checkStyle();
        onClicks();
    }

    private void onClicks(){
        binding.backBtn.setOnClickListener(v -> {
            requireActivity().onBackPressed();
        });

        binding.saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validation();
            }
        });
        binding.bold.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (style.isBold()){
                    style.setBold(false);

                }
                else{
                    style.setBold(true);
                }
                checkStyle();
            }
        });
        binding.underlined.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (style.isUnderline()){
                    style.setUnderline(false);

                }
                else{
                    style.setUnderline(true);
                }
                checkStyle();
            }
        });

        binding.italic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (style.isItalic()){
                    style.setItalic(false);

                }
                else{
                    style.setItalic(true);
                }
                checkStyle();
            }
        });

        binding.alignLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                style.setAlign(Align.LEFT);
                checkStyle();
            }
        });

        binding.alignRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                style.setAlign(Align.RIGHT);
                checkStyle();
            }
        });
        binding.alignCenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                style.setAlign(Align.CENTER);
                checkStyle();
            }
        });

        binding.font.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag = !flag;
                checkStyle();
            }
        });
        binding.fontSizeSlider.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int newSize = Consts.TEXT_SIZES[progress];
                style.setFontSize(newSize);
                applyStyle();

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

    }

    private void validation(){
        String content = binding.contentEdit.getText().toString();
        String header = binding.headerEdit.getText().toString();
        long now = System.currentTimeMillis();

        if (header.isEmpty()){
            binding.headerEdit.setError("Required");
        }
        else if (content.isEmpty()) {
            binding.contentEdit.setError("Required");
        }
        else{
            if (SharedModel.isEditNoteState()){
                updateNote(new NoteModel(header,content,now,style));
            }
            else {
                insertNote(new NoteModel(header,content,now,style));
            }

        }
    }

    private void checkStyle(){
        int tintColor1 = requireActivity().getColor( R.color.orange); // Replace with your color
        ColorStateList list1 = ColorStateList.valueOf(tintColor1);

        int tintColor2 = requireActivity().getColor( R.color.black); // Replace with your color
        ColorStateList list2 = ColorStateList.valueOf(tintColor2);

        if (style.isBold()){
            binding.bold.setBackgroundTintList(list1);
        }
        else {
            binding.bold.setBackgroundTintList(list2);
        }
        if (style.isUnderline()){
            binding.underlined.setBackgroundTintList(list1);
        }
        else {
            binding.underlined.setBackgroundTintList(list2);
        }
        if (style.isItalic()){
            binding.italic.setBackgroundTintList(list1);
        }
        else {
            binding.italic.setBackgroundTintList(list2);
        }

        if (style.getAlign().equals(Align.LEFT)){
            binding.alignLeft.setBackgroundTintList(list1);
            binding.alignCenter.setBackgroundTintList(list2);
            binding.alignRight.setBackgroundTintList(list2);
        }
        else if (style.getAlign().equals(Align.RIGHT)){
            binding.alignLeft.setBackgroundTintList(list2);
            binding.alignCenter.setBackgroundTintList(list2);
            binding.alignRight.setBackgroundTintList(list1);
        }
        else{
            binding.alignLeft.setBackgroundTintList(list2);
            binding.alignCenter.setBackgroundTintList(list1);
            binding.alignRight.setBackgroundTintList(list2);
        }
        if (flag){
            binding.font.setBackgroundTintList(list1);
            binding.fontLayout.setVisibility(View.VISIBLE);
        }
        else{
            binding.font.setBackgroundTintList(list2);
            binding.fontLayout.setVisibility(View.GONE);
        }

        applyStyle();
    }

    private void insertNote(NoteModel note){
        viewModel.InsertNote(note);
        viewModel.getConfirmationState().observe(getViewLifecycleOwner(), new Observer<ConfirmationState>() {
            @Override
            public void onChanged(ConfirmationState confirmationState) {
                if (confirmationState.equals(ConfirmationState.SUCCESS)){
                    navigate();
                }
            }
        });
    }

    private void updateNote(NoteModel note){
        note.setId(SharedModel.getSelectedNote().getId());
        viewModel.updateNote(note);
        viewModel.getConfirmationState().observe(getViewLifecycleOwner(), new Observer<ConfirmationState>() {
            @Override
            public void onChanged(ConfirmationState confirmationState) {
                if (confirmationState.equals(ConfirmationState.SUCCESS)){
                    navigate();
                }
            }
        });

    }
    private void navigate(){
        requireActivity().onBackPressed();
    }

    private void applyStyle(){
        String text = binding.contentEdit.getText().toString();
        SpannableString spannableString = new SpannableString(text);
        int typefaceStyle;

        if (style.isBold() && style.isItalic()) {
            typefaceStyle = Typeface.BOLD_ITALIC;
        } else if (style.isBold()) {
            typefaceStyle = Typeface.BOLD;
        } else if (style.isItalic()) {
            typefaceStyle = Typeface.ITALIC;
        } else {
            typefaceStyle = Typeface.NORMAL;
        }

        binding.contentEdit.setTypeface(null, typefaceStyle);

        if (style.isUnderline()){
            spannableString.setSpan(new UnderlineSpan(), 0, text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        else{
            UnderlineSpan[] underlines = spannableString.getSpans(0, text.length(), UnderlineSpan.class);
            for (UnderlineSpan span : underlines) {
                spannableString.removeSpan(span);
            }
        }
        binding.contentEdit.setText(spannableString);

        if (style.getAlign().equals(Align.LEFT)){
            binding.contentEdit.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
        }
        else if (style.getAlign().equals(Align.RIGHT)){
            binding.contentEdit.setGravity(Gravity.RIGHT | Gravity.CENTER_VERTICAL);
        }
        else {
            binding.contentEdit.setGravity(Gravity.CENTER);
        }
        binding.contentEdit.setTextSize(style.getFontSize());

    }
    private int getProgressForSize(int size) {
        for (int i = 0; i < Consts.TEXT_SIZES.length; i++) {
            if (Consts.TEXT_SIZES[i] == size) {
                return i;
            }
        }
        return 0; // Default progress if size is not found
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}