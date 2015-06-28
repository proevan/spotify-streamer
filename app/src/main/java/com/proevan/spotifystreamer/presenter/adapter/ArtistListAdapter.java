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
import kaaes.spotify.webapi.android.models.Artist;
import kaaes.spotify.webapi.android.models.Image;

public class ArtistListAdapter extends RecyclerView.Adapter<ArtistListAdapter.ViewHolder> {

    private List<Artist> mItems;
    private AdapterView.OnItemClickListener mOnItemClickListener;
    private boolean mIsChoiceMode = false;
    private int mSelectedPosition;

    public ArtistListAdapter() {
        mItems = new ArrayList<>();
    }

    public void setChoiceMode(boolean isChoiceMode) {
        mIsChoiceMode = isChoiceMode;
    }

    public Artist getItem(int position) {
        return mItems.get(position);
    }

    public void removeAll() {
        mSelectedPosition = -1;
        mItems.clear();
        notifyDataSetChanged();
    }

    public void addAll(List<Artist> items) {
        mItems.addAll(items);
        notifyDataSetChanged();
    }

    @Override
    public ArtistListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.artist_list_item, parent, false);
        ViewHolder vh = new ViewHolder(v, this);

        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Artist artist = mItems.get(position);
        if (mIsChoiceMode) {
            if (position == mSelectedPosition)
                holder.setActivated(true);
            else
                holder.setActivated(false);
        }
        holder.nameTextView.setText(artist.name);
        loadArtistFirstImage(holder.imageView, artist.images);
    }

    private void loadArtistFirstImage(ImageView imageView, List<Image> images) {
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
        if (mIsChoiceMode) {
            if (mSelectedPosition != -1) {
                notifyItemChanged(mSelectedPosition);
            }
            viewHolder.setActivated(true);
            mSelectedPosition = viewHolder.getAdapterPosition();
        }
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
        @InjectView(R.id.name)
        TextView nameTextView;

        private View mItemView;
        private ArtistListAdapter mAdapter;

        public ViewHolder(View v, ArtistListAdapter adapter) {
            super(v);
            mItemView = v;
            mAdapter = adapter;
            v.setOnClickListener(this);
            ButterKnife.inject(this, v);
        }

        @Override
        public void onClick(View view) {
            mAdapter.onItemHolderClick(this);
        }

        public void setActivated(boolean isActivated) {
            mItemView.setActivated(isActivated);
        }
    }
}
