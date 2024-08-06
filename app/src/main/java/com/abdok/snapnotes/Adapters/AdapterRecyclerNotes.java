package com.abdok.snapnotes.Adapters;



import android.content.res.TypedArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.abdok.snapnotes.Models.NoteModel;
import com.abdok.snapnotes.R;
import com.abdok.snapnotes.Utils.TimeUtils;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;
import java.util.Random;

public class AdapterRecyclerNotes extends RecyclerView.Adapter<AdapterRecyclerNotes.Holder> {


    ArrayList<NoteModel> list = new ArrayList<>();

    private OnItemClick onItemClick ;

    private static final int VIEW_TYPE_GRID = 0;
    private static final int VIEW_TYPE_LINEAR = 1;
    private boolean isGridLayout;


    public void setGridLayout(boolean gridLayout) {
        isGridLayout = gridLayout;
    }

    public void setOnItemClick (OnItemClick onItemClick){
        this.onItemClick = onItemClick;
    }

    public void setList(ArrayList<NoteModel> list) {
        this.list = list;
    }

    // Constructor
    public AdapterRecyclerNotes(boolean isGridLayout) {
        this.isGridLayout = isGridLayout;
    }

    @Override
    public int getItemViewType(int position) {
        return isGridLayout ? VIEW_TYPE_GRID : VIEW_TYPE_LINEAR;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;

        if (viewType == VIEW_TYPE_GRID) {
             view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_note2, parent, false);
        } else {
             view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_note, parent, false);
        }

        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {

        holder.title.setText(list.get(position).getHeader());
        holder.content.setText(list.get(position).getContent());
        holder.time.setText(TimeUtils.getRelativeTime(list.get(position).getTimestamp()));

    }

    @Override
    public int getItemCount() {
        return list==null?0:list.size();
    }

    public class Holder extends RecyclerView.ViewHolder {

        TextView title , content , time;
        ConstraintLayout card;
        public Holder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            content = itemView.findViewById(R.id.content);
            time = itemView.findViewById(R.id.time);
            card = itemView.findViewById(R.id.card);





            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClick != null){
                        onItemClick.OnClick(list.get(getLayoutPosition()));
                    }
                }
            });
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (onItemClick != null){
                        onItemClick.OnLongClick(list.get(getLayoutPosition()));
                    }
                    return false;
                }
            });



        }
    }

    public interface OnItemClick{

        void OnClick(NoteModel noteModel);
        void OnLongClick(NoteModel noteModel);

    }

}
