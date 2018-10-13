package com.zhongyujiaoyu.swiprefreshlayout.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zhongyujiaoyu.swiprefreshlayout.R;

import java.util.List;

/**
 * Created by Administrator on 2018/10/11.
 */

public class CommonAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<String> mData;
    private OnItemClickListener mOnItemClickListener;

    public CommonAdapter(List<String> mData) {
        this.mData = mData;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_common, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final RecyclerViewHolder recyclerViewHolder = (RecyclerViewHolder) holder;
        recyclerViewHolder.tvCommon.setText(mData.get(position));

        if (mOnItemClickListener != null) {
            recyclerViewHolder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onItemClick(recyclerViewHolder.cardView, recyclerViewHolder.getAdapterPosition());
                }
            });
            recyclerViewHolder.tvCommon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onItemClick(recyclerViewHolder.tvCommon, recyclerViewHolder.getAdapterPosition());
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    private class RecyclerViewHolder extends RecyclerView.ViewHolder {

        TextView tvCommon;
        CardView cardView;

        public RecyclerViewHolder(View itemView) {
            super(itemView);
            tvCommon = itemView.findViewById(R.id.textview_common);
            cardView = itemView.findViewById(R.id.cardview);
        }
    }

    public interface OnItemClickListener {
        public void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }
}
