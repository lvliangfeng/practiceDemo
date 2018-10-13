package com.zhongyujiaoyu.swiprefreshlayout.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.zhongyujiaoyu.swiprefreshlayout.R;

/**
 * Created by Administrator on 2018/10/11.
 */

public class LoadWrapperAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private RecyclerView.Adapter<RecyclerView.ViewHolder> mAdapter;

    private final int TYPE_ITEM = 1;
    private final int TYPE_FOOTER = 2;

    public final int LOADING = 1;
    public final int LOADING_COMPLETE = 2;
    public final int LOADING_END = 3;
    private int mLoadingStatus = 2;

    private View mLoadingView;
    private View mLoadingEndView;
    private int mLoadingViewHeight = 0;

    private CommonAdapter.OnItemClickListener mOnItemClickListener;

    public LoadWrapperAdapter(RecyclerView.Adapter<RecyclerView.ViewHolder> adapter) {
        this.mAdapter = adapter;
    }

    @Override
    public int getItemViewType(int position) {
        if (position + 1 == getItemCount()) {
            return TYPE_FOOTER;
        } else {
            return TYPE_ITEM;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        this.mContext = parent.getContext();
        if (viewType == TYPE_FOOTER) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.layout_loading_footer, parent, false);
            return new LoadWrapperViewHolder(view);
        } else {
            return mAdapter.onCreateViewHolder(parent, viewType);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof LoadWrapperViewHolder) {
            LoadWrapperViewHolder loadWrapperViewHolder = (LoadWrapperViewHolder) holder;
            loadWrapperViewHolder.rlLoadingFooter.removeAllViews();
            if (mLoadingViewHeight > 0) {
                RelativeLayout.LayoutParams params =
                        (RelativeLayout.LayoutParams) loadWrapperViewHolder.rlLoadingFooter.getLayoutParams();
                params.height = mLoadingViewHeight;
                loadWrapperViewHolder.rlLoadingFooter.setLayoutParams(params);
            }

            switch (mLoadingStatus) {
                case LOADING:
                    if (mLoadingView != null) {
                        //自定义加载布局
                        loadWrapperViewHolder.rlLoadingFooter.addView(mLoadingView);
                    } else {
                        //默认加载布局
                        View view = View.inflate(mContext, R.layout.layout_loading_loading, null);
                        loadWrapperViewHolder.rlLoadingFooter.addView(view);
                    }
                    break;
                case LOADING_COMPLETE:

                    break;
                case LOADING_END:
                    if (mLoadingEndView != null) {
                        loadWrapperViewHolder.rlLoadingFooter.addView(mLoadingEndView);
                    } else {
                        View view = View.inflate(mContext, R.layout.layout_loading_end, null);
                        loadWrapperViewHolder.rlLoadingFooter.addView(view);
                    }
                    break;
            }
        } else {
            mAdapter.onBindViewHolder(holder, position);
        }
    }

    @Override
    public int getItemCount() {
        return mAdapter.getItemCount() + 1;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();

        // GridLayoutManager
        if (manager instanceof GridLayoutManager) {
            final GridLayoutManager gridManager = ((GridLayoutManager) manager);
            gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    // If the current position is footer view, then the item occupy two cells,
                    // Normal item occupy a cell.
                    return getItemViewType(position) == TYPE_FOOTER ? gridManager.getSpanCount() : 1;
                }
            });
        }
    }

    private class LoadWrapperViewHolder extends RecyclerView.ViewHolder {

        RelativeLayout rlLoadingFooter;

        public LoadWrapperViewHolder(View itemView) {
            super(itemView);
            rlLoadingFooter = itemView.findViewById(R.id.loading_footer);
        }
    }

    public void setLoadStatus(int loadingStatus) {
        this.mLoadingStatus = loadingStatus;
        notifyDataSetChanged();
    }

    public void setLoadingView(View view) {
        this.mLoadingEndView = view;
    }

    public void setLoadingEndView(View view) {
        this.mLoadingEndView = view;
    }

    public void setLoadingViewHeight(int height) {
        this.mLoadingViewHeight = height;
    }

    public void setOnItemClickListener(CommonAdapter.OnItemClickListener onItemClickListener) {
        ((CommonAdapter) mAdapter).setOnItemClickListener(onItemClickListener);
    }
}
