package com.example.tiroberita.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.tiroberita.R;
import com.example.tiroberita.model.PostModel;
import com.example.tiroberita.ui.ItemClickListener;

import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {
    private Context context;
    private List<PostModel> postModelList;
    private ItemClickListener itemClickListener;


    public NewsAdapter(Context context, List<PostModel> postModelList) {
        this.context = context;
        this.postModelList = postModelList;
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public NewsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_news_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsAdapter.ViewHolder holder, int position) {
        holder.tvTitle.setText(postModelList.get(holder.getAdapterPosition()).getTitle());
        holder.tvDesc.setText(postModelList.get(holder.getAdapterPosition()).getDescription());


        String date = postModelList.get(holder.getAdapterPosition()).getPubDate().substring(0, 10);
        String timeStamp = postModelList.get(holder.getAdapterPosition()).getPubDate().substring(11, 19);

        holder.tvDate.setText(date + " | " + timeStamp);

        Glide.with(context).load(postModelList.get(holder.getAdapterPosition()).getThumbnail())
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .fitCenter()
                .centerCrop()
                .dontAnimate()
                .into(holder.ivThumbnail);


    }

    @Override
    public int getItemCount() {
        return postModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvTitle, tvDesc, tvDate;
        private ImageView ivThumbnail;
        ImageButton btnShare;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvDate = itemView.findViewById(R.id.tvDate);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvDesc = itemView.findViewById(R.id.tvDesc);
            ivThumbnail = itemView.findViewById(R.id.ivThumbnail);
            btnShare = itemView.findViewById(R.id.btnShare);



            btnShare.setOnClickListener(view -> {
                if (itemClickListener != null) {
                    itemClickListener.onItemClickListener("share", postModelList.get(getAdapterPosition()));
                }
            });


        }
    }
}
