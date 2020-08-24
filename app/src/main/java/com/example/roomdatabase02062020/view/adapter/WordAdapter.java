package com.example.roomdatabase02062020.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.RecyclerView;

import com.example.roomdatabase02062020.R;
import com.example.roomdatabase02062020.interfaces.OnItemRemoveListener;
import com.example.roomdatabase02062020.interfaces.OnItemToggleListener;
import com.example.roomdatabase02062020.model.entity.WordEnity;

import java.util.ArrayList;
import java.util.List;

public class WordAdapter extends RecyclerView.Adapter<WordAdapter.WordHolder> {

    List<WordEnity> mArrWords;
    Context mContext;
    OnItemToggleListener mOnItemToggleListener;
    OnItemRemoveListener mOnItemRemoveListener;
    MutableLiveData<List<WordEnity>> mWordFilter;

    public WordAdapter(List<WordEnity> mArrWords) {
        this.mArrWords = new ArrayList<>();
        this.mArrWords.addAll(mArrWords);
        mWordFilter = new MutableLiveData<>();
    }

    @NonNull
    @Override
    public WordHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View view = layoutInflater.inflate(R.layout.layout_item_word, parent, false);
        return new WordHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WordHolder holder, int position) {
        WordEnity word = mArrWords.get(position);
        if (word.getIsmemorized() != 0) {
            holder.mTvVn.setText(mContext.getResources().getString(R.string.text_vn_empty));
            holder.mBtnMemorized.setText(mContext.getResources().getString(R.string.text_forgot));
            holder.mBtnMemorized.setBackground(mContext.getDrawable(R.drawable.box_corner_green));
        } else {
            holder.mTvVn.setText(word.getVn());
            holder.mBtnMemorized.setText(mContext.getResources().getString(R.string.text_memorized));
            holder.mBtnMemorized.setBackground(mContext.getDrawable(R.drawable.box_corner_red));
        }
        holder.mTvEn.setText(word.getEn());
    }

    @Override
    public int getItemCount() {
        return mArrWords != null ? mArrWords.size() : 0;
    }

    class WordHolder extends RecyclerView.ViewHolder {
        TextView mTvEn, mTvVn;
        Button mBtnMemorized, mBtnRemove;

        public WordHolder(@NonNull View itemView) {
            super(itemView);
            mTvVn = itemView.findViewById(R.id.textViewVn);
            mTvEn = itemView.findViewById(R.id.textViewEn);
            mBtnMemorized = itemView.findViewById(R.id.buttonMemorized);
            mBtnRemove = itemView.findViewById(R.id.buttonRemove);

            mBtnMemorized.setOnClickListener(view -> mOnItemToggleListener.setOnToggle(getAdapterPosition()));
            mBtnRemove.setOnClickListener(view -> mOnItemRemoveListener.setOnRemove(getAdapterPosition()));
        }
    }

    public void setAllWord(List<WordEnity> wordEniTies) {
        if (mArrWords.size() > 0) {
            mArrWords.clear();
        }
        mArrWords.addAll(wordEniTies);
        notifyDataSetChanged();
    }

    public List<WordEnity> getData() {
        return mArrWords;
    }

    public void setOnToggleWord(OnItemToggleListener onToggleWord) {
        this.mOnItemToggleListener = onToggleWord;
    }

    public void setOnRemoveWord(OnItemRemoveListener onRemoveWord) {
        this.mOnItemRemoveListener = onRemoveWord;
    }
}
