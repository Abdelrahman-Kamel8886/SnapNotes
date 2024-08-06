package com.abdok.snapnotes.Ui.Home;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.SearchView;
import android.widget.Toast;

import com.abdok.snapnotes.Adapters.AdapterRecyclerNotes;
import com.abdok.snapnotes.Enums.ConfirmationState;
import com.abdok.snapnotes.Models.NoteModel;
import com.abdok.snapnotes.R;
import com.abdok.snapnotes.Ui.Note.NoteFragment;
import com.abdok.snapnotes.Utils.SharedModel;
import com.abdok.snapnotes.databinding.FragmentHomeBinding;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment {

    FragmentHomeBinding binding;
    HomeViewModel viewModel;
    AdapterRecyclerNotes adpter;

    private List<NoteModel> dataList = new ArrayList<>();
    private List<NoteModel> filteredDataList = new ArrayList<>();

    SharedPreferences.Editor layoutStyleEditor;
    SharedPreferences sharedPreferences;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = FragmentHomeBinding.bind(view);
        viewModel = new ViewModelProvider(this).get(HomeViewModel.class);



        sharedPreferences = requireActivity().getSharedPreferences("app_prefs", Context.MODE_PRIVATE);
        layoutStyleEditor = sharedPreferences.edit();
        adpter = new AdapterRecyclerNotes(isGridLayout());


        applyLayout();
        getNotes();

    }


    private void getNotes(){
        viewModel.getAllNotes();
        viewModel.getList().observe(getViewLifecycleOwner(), new Observer<List<NoteModel>>() {
            @Override
            public void onChanged(List<NoteModel> noteModels) {
                if (noteModels.size() == 0){
                    binding.emptyLayout.setVisibility(View.VISIBLE);
                }
                else {
                    dataList.clear();
                    filteredDataList.clear();
                    dataList.addAll(noteModels);
                    filteredDataList.addAll(dataList);
                    binding.emptyLayout.setVisibility(View.GONE);
                    showNotes();
                }

            }
        });
        onClicks();
    }
    private void onClicks(){
        binding.addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedModel.setEditNoteState(false);
                SharedModel.setSelectedNote(null);
                navigate();
            }
        });
        adpter.setOnItemClick(new AdapterRecyclerNotes.OnItemClick() {
            @Override
            public void OnClick(NoteModel noteModel) {
                SharedModel.setEditNoteState(true);
                SharedModel.setSelectedNote(noteModel);
                navigate();
            }

            @Override
            public void OnLongClick(NoteModel noteModel) {
                showDeleteDialog(noteModel);
            }
        });


        binding.searchBtn.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    binding.title.setVisibility(View.GONE);
                }
                else{
                    binding.title.setVisibility(View.VISIBLE);
                }
            }
        });

        binding.viewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               if (isGridLayout()){
                   binding.viewBtn.setBackground(requireActivity().getResources().getDrawable(R.drawable.baseline_grid_view_24));
                   layoutStyleEditor.putBoolean("is_grid_layout", false);
                   layoutStyleEditor.apply();
                   applyLayout();
                   toggle();
               }
               else{
                   binding.viewBtn.setBackground(requireActivity().getResources().getDrawable(R.drawable.sharp_view_list_24));
                   layoutStyleEditor.putBoolean("is_grid_layout", true);
                   layoutStyleEditor.apply();
                   applyLayout();
                   toggle();
               }
            }
        });

        binding.searchBtn.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filter(newText);
                return true;
            }
        });



    }
    private void showNotes(){
        adpter.setList((ArrayList<NoteModel>) filteredDataList);
        binding.recyclerHistory.setAdapter(adpter);

    }

    private void navigate(){
        requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container , new NoteFragment() , "note")
                .addToBackStack("note")
                .commit();
    }

    private void filter(String query) {
        filteredDataList.clear();
        if (query.isEmpty()) {
            filteredDataList.addAll(dataList);
        } else {
            String lowerCaseQuery = query.toLowerCase();
            for (NoteModel item : dataList) {
                if (item.getContent().toLowerCase().contains(lowerCaseQuery)||item.getHeader().toLowerCase().contains(lowerCaseQuery)) {
                    filteredDataList.add(item);
                }
            }
        }
        adpter.notifyDataSetChanged();
    }

    private boolean isGridLayout(){
        return sharedPreferences.getBoolean("is_grid_layout", false);
    }

    private void applyLayout(){
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        if (isGridLayout()){
            binding.viewBtn.setBackground(requireActivity().getResources().getDrawable(R.drawable.sharp_view_list_24));
            binding.recyclerHistory.setLayoutManager(gridLayoutManager);
        }
        else {

            binding.viewBtn.setBackground(requireActivity().getResources().getDrawable(R.drawable.baseline_grid_view_24));
            binding.recyclerHistory.setLayoutManager(linearLayoutManager);
        }
    }

    private void toggle(){
        adpter.setGridLayout(isGridLayout());
        adpter.notifyDataSetChanged();
        binding.recyclerHistory.setAdapter(adpter);
    }

    private void showDeleteDialog(NoteModel model) {
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.delete_dialog, null);


        Button deleteButton = dialogView.findViewById(R.id.dialog_delete);
        Button cancelButton = dialogView.findViewById(R.id.dialog_cancel);

        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity(),R.style.TransparentDialog);
        builder.setView(dialogView);

        // Handle button clicks
        final AlertDialog dialog = builder.create();

        deleteButton.setOnClickListener(v -> {
            deleteItem(model);
            dialog.dismiss();
        });

        cancelButton.setOnClickListener(v -> {
            dialog.dismiss();
        });

        // Show the dialog
        dialog.show();
    }

    private void deleteItem(NoteModel model){
        viewModel.deleteNote(model);
        viewModel.getDeletionState().observe(getViewLifecycleOwner(), new Observer<ConfirmationState>() {
            @Override
            public void onChanged(ConfirmationState confirmationState) {
                if (confirmationState.equals(ConfirmationState.SUCCESS)){
                    getNotes();
                }
            }
        });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}