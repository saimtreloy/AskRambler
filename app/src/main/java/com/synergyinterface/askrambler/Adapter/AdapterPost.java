package com.synergyinterface.askrambler.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.synergyinterface.askrambler.Model.ModelPost;
import com.synergyinterface.askrambler.R;

import java.util.ArrayList;

/**
 * Created by Android on 8/6/2017.
 */

public class AdapterPost extends RecyclerView.Adapter<AdapterPost.PostViewHolder>{

    ArrayList<ModelPost> adapterList = new ArrayList<>();
    Context mContext;

    public AdapterPost(ArrayList<ModelPost> adapterList) {
        this.adapterList = adapterList;
    }

    public AdapterPost(ArrayList<ModelPost> adapterList, Context mContext) {
        this.adapterList = adapterList;
        this.mContext = mContext;
    }

    @Override
    public PostViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_all_post, parent, false);
        PostViewHolder postViewHolder = new PostViewHolder(view);
        return postViewHolder;
    }

    @Override
    public void onBindViewHolder(PostViewHolder holder, int position) {
        holder.txtToWhere.setText(adapterList.get(position).getTo_where());
        holder.txtToDate.setText(adapterList.get(position).getTo_date());
        Glide.with(holder.txtToWhere.getContext())
                .load(adapterList.get(position).getUser_photo())
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        return false;
                    }
                })
                .into(holder.imgAllPostList);
    }

    @Override
    public int getItemCount() {
        return adapterList.size();
    }

    public class PostViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView imgAllPostList;
        TextView txtToDate, txtToWhere;

        public PostViewHolder(View itemView) {
            super(itemView);

            imgAllPostList = (ImageView) itemView.findViewById(R.id.imgAllPostList);
            txtToWhere = (TextView) itemView.findViewById(R.id.txtToWhere);
            txtToDate = (TextView) itemView.findViewById(R.id.txtToDate);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

        }
    }
}
