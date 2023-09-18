package com.example.tiroberita.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.tiroberita.R;
import com.example.tiroberita.model.SavePostModel;

import java.util.List;

public class SavePostAdapter extends RecyclerView.Adapter<SavePostAdapter.ViewHolder> {
    private Context context;
    private List<SavePostModel> savePostModelList;

    public SavePostAdapter(Context context, List<SavePostModel> savePostModelList) {
        this.context = context;
        this.savePostModelList = savePostModelList;
    }

    @NonNull
    @Override
    public SavePostAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_save_post_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SavePostAdapter.ViewHolder holder, int position) {
        String date = savePostModelList.get(holder.getAdapterPosition()).getPubDate().substring(0, 10);
        String timeStamp = savePostModelList.get(holder.getAdapterPosition()).getPubDate().substring(11, 19);
        holder.tvRedactionName.setText(savePostModelList.get(holder.getAdapterPosition()).getRedaction_name());
        holder.tvTitle.setText(savePostModelList.get(holder.getAdapterPosition()).getTitle());
        holder.tvDesc.setText(savePostModelList.get(holder.getAdapterPosition()).getDescription());

        holder.tvDate.setText(date + " | " + timeStamp);

        Glide.with(context).load(savePostModelList.get(holder.getAdapterPosition()).getThumbnail())
                .override(300, 300)
                .skipMemoryCache(true)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.ivThumbnail);

    }

    @Override
    public int getItemCount() {
        return savePostModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvTitle, tvDesc, tvRedactionName, tvDate;
        ImageView ivThumbnail;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvDate = itemView.findViewById(R.id.tvDate);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvDesc = itemView.findViewById(R.id.tvDesc);
            ivThumbnail = itemView.findViewById(R.id.ivThumbnail);
            tvRedactionName = itemView.findViewById(R.id.tvRedactionName);


        }
    }
}
