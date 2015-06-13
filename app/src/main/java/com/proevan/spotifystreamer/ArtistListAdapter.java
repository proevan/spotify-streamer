package com.proevan.spotifystreamer;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import kaaes.spotify.webapi.android.models.Artist;
import kaaes.spotify.webapi.android.models.Image;

public class ArtistListAdapter extends RecyclerView.Adapter<ArtistListAdapter.ViewHolder> {

    private Context mContext;
    private List<Artist> mArtists;
    private AdapterView.OnItemClickListener mOnItemClickListener;

    public ArtistListAdapter(Context context, List<Artist> artists) {
        mContext = context;
        mArtists = artists;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @InjectView(R.id.image)
        ImageView imageView;
        @InjectView(R.id.name)
        TextView nameTextView;

        private ArtistListAdapter mAdapter;

        public ViewHolder(View v, ArtistListAdapter adapter) {
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
        Artist artist = mArtists.get(position);
        holder.nameTextView.setText(artist.name);
        loadArtistFirstImage(holder.imageView, artist.images);
    }

    private void loadArtistFirstImage(ImageView imageView, List<Image> images) {
        if (images.size() > 0) {
            imageView.setImageDrawable(null);
            Picasso.with(mContext)
                    .load(images.get(0).url)
                    .fit().centerCrop()
                    .into(imageView);
        } else
            imageView.setImageResource(R.drawable.spotify_placeholder);
    }

    @Override
    public int getItemCount() {
        return mArtists.size();
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
}
