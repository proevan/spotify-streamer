package com.proevan.spotifystreamer.presenter.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.orhanobut.logger.Logger;
import com.proevan.spotifystreamer.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import kaaes.spotify.webapi.android.models.Image;
import kaaes.spotify.webapi.android.models.Track;

public class TrackListAdapter extends RecyclerView.Adapter<TrackListAdapter.ViewHolder> {

    private List<Track> mItems;
    private AdapterView.OnItemClickListener mOnItemClickListener;

    public TrackListAdapter() {
        mItems = new ArrayList<>();
    }

    public Track getItem(int position) {
        return mItems.get(position);
    }

    public void removeAll() {
        mItems.clear();
        notifyDataSetChanged();
    }

    public void addAll(List<Track> items) {
        mItems.addAll(items);
        notifyDataSetChanged();
    }

    public void addItem(int position, Track item) {
        if (position > mItems.size()) return;

        mItems.add(position, item);
        notifyItemInserted(position);
    }

    public void removeItem(int position) {
        if (position >= mItems.size()) return;

        mItems.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public TrackListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.track_list_item, parent, false);
        ViewHolder vh = new ViewHolder(v, this);

        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Track track = mItems.get(position);
        holder.albumNameTextView.setText(track.name);
        holder.trackNameTextView.setText(track.album.name);
        loadTrackFirstImage(holder.imageView, track.album.images);
    }

    private void loadTrackFirstImage(ImageView imageView, List<Image> images) {
        if (images.size() == 0) {
            imageView.setImageResource(R.drawable.spotify_placeholder);
            return;
        }

        int imageLoadIndex = 0;
        try {
            imageLoadIndex = getSecondLastIndex(images);
        } catch (IndexOutOfBoundsException e) {
            Logger.v(e.getMessage());
        }
        imageView.setImageDrawable(null);
        Picasso.with(imageView.getContext())
                .load(images.get(imageLoadIndex).url)
                .fit().centerCrop()
                .into(imageView);
    }

    private int getSecondLastIndex(List list) throws IndexOutOfBoundsException {
        int resultIndex = list.size() - 2;
        if (resultIndex < 0)
            throw new IndexOutOfBoundsException("List size is less then 2");

        return resultIndex;
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    private void onItemHolderClick(ViewHolder viewHolder) {
        if (mOnItemClickListener != null) {
            mOnItemClickListener.onItemClick(null, viewHolder.itemView,
                    viewHolder.getAdapterPosition(), viewHolder.getItemId());
        }
    }

    public void setOnItemClickListener(AdapterView.OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @InjectView(R.id.image)
        ImageView imageView;
        @InjectView(R.id.track_name)
        TextView trackNameTextView;
        @InjectView(R.id.album_name)
        TextView albumNameTextView;

        private TrackListAdapter mAdapter;

        public ViewHolder(View v, TrackListAdapter adapter) {
            super(v);
            mAdapter = adapter;
            v.setOnClickListener(this);
            ButterKnife.inject(this, v);
        }

        @Override
        public void onClick(View view) {
            mAdapter.onItemHolderClick(this);
        }
    }
}
