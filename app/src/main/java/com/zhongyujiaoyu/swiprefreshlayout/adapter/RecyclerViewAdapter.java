package com.zhongyujiaoyu.swiprefreshlayout.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.zhongyujiaoyu.swiprefreshlayout.R;
import com.zhongyujiaoyu.swiprefreshlayout.bean.Book;

import java.util.List;

/**
 * Created by Administrator on 2018/10/9.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private List<Book> bookList;
    private OnItemClickListener mOnItemClickListener;
    public static final int TYPE_ITEM = 0;
    public static final int TYPE_FOOTER = 1;

    public static final int PULLUP_LOAD = 0;
    public static final int LOADING = 1;
    public static final int NO_LOAD = 2;

    private int mLoadMoreStatus = 0;

    public static class ItemViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView textView;

        public ItemViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image_view);
            textView = itemView.findViewById(R.id.text_view_content);
        }
    }

    public static class FooterViewHolder extends RecyclerView.ViewHolder {

        LinearLayout layoutLoad;
        TextView textViewLoad;
        ProgressBar progressBar;

        public FooterViewHolder(View itemView) {
            super(itemView);
            textViewLoad = itemView.findViewById(R.id.text_view_loading);
            progressBar = itemView.findViewById(R.id.progress_bar);
            layoutLoad = itemView.findViewById(R.id.layout_load);
        }
    }

    public RecyclerViewAdapter(Context context, List<Book> bookList) {
        this.mContext = context;
        this.bookList = bookList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (viewType == TYPE_ITEM) {
            View view = LayoutInflater.from(mContext)
                    .inflate(R.layout.recycler_view_item, parent, false);
            ItemViewHolder holder = new ItemViewHolder(view);
            return holder;
        } else if (viewType == TYPE_FOOTER) {
            View view = LayoutInflater.from(mContext)
                    .inflate(R.layout.layout_loading, parent, false);
            ItemViewHolder holder = new ItemViewHolder(view);
            return holder;
        }

        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {

        if (holder instanceof ItemViewHolder) {
            ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
            if (position < getItemCount()-1) {
                Book book = bookList.get(position);
                itemViewHolder.textView.setText(book.getName());
                itemViewHolder.imageView.setImageResource(book.getIcon());
                if (mOnItemClickListener != null) {
                    itemViewHolder.imageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mOnItemClickListener.onClick(position);
                        }
                    });
                    itemViewHolder.textView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mOnItemClickListener.onClick(position);
                        }
                    });
                    itemViewHolder.imageView.setOnLongClickListener(new View.OnLongClickListener() {
                        @Override
                        public boolean onLongClick(View v) {
                            mOnItemClickListener.onLongClick(position);
                            return false;
                        }
                    });
                    itemViewHolder.textView.setOnLongClickListener(new View.OnLongClickListener() {
                        @Override
                        public boolean onLongClick(View v) {
                            mOnItemClickListener.onLongClick(position);
                            return false;
                        }
                    });
                }
            }
        } else if (holder instanceof FooterViewHolder) {
            FooterViewHolder footerViewHolder = (FooterViewHolder) holder;

            switch (mLoadMoreStatus) {
                case PULLUP_LOAD:
                    footerViewHolder.textViewLoad.setText("上拉加载更多...");
                    break;
                case LOADING:
                    footerViewHolder.textViewLoad.setText("正在加载更多...");
                    break;
                case NO_LOAD:
                    footerViewHolder.layoutLoad.setVisibility(View.GONE);
                    break;
            }
        }
    }

//    @Override
//    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
//        Book book = bookList.get(position);
//        holder.textView.setText(book.getName());
//        holder.imageView.setImageResource(book.getIcon());
//        if (mOnItemClickListener != null) {
//            holder.imageView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    mOnItemClickListener.onClick(position);
//                }
//            });
//            holder.textView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    mOnItemClickListener.onClick(position);
//                }
//            });
//            holder.imageView.setOnLongClickListener(new View.OnLongClickListener() {
//                @Override
//                public boolean onLongClick(View v) {
//                    mOnItemClickListener.onLongClick(position);
//                    return false;
//                }
//            });
//            holder.textView.setOnLongClickListener(new View.OnLongClickListener() {
//                @Override
//                public boolean onLongClick(View v) {
//                    mOnItemClickListener.onLongClick(position);
//                    return false;
//                }
//            });
//        }
//    }

    @Override
    public int getItemCount() {
        return bookList.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position + 1 == getItemCount()) {
            return TYPE_FOOTER;
        } else {
            return TYPE_ITEM;
        }
    }

    public void removeData(int position) {
        bookList.remove(position);
        notifyItemRemoved(position);
    }

    public void addHeaderItem(List<Book> headItems) {
        bookList.addAll(0, headItems);
        notifyDataSetChanged();
    }

    public void addFooterItem(List<Book> footItems) {
        bookList.addAll(footItems);
        notifyDataSetChanged();
    }

    public void changeStatus(int status) {
        mLoadMoreStatus = status;
        notifyDataSetChanged();
    }


    public interface OnItemClickListener {
        public void onClick(int position);

        public void onLongClick(int positon);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

}
