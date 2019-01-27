package project.com.maktab.musicplayer;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import project.com.maktab.musicplayer.model.Song;
import project.com.maktab.musicplayer.model.SongLab;

/**
 * A simple {@link Fragment} subclass.
 */
public class SongsRecyclerFragment extends Fragment {
    RecyclerView mSongsRv;
    private List<Song> mSongList;
    private RecyclerViewAdapter mAdapter;

    public static SongsRecyclerFragment newInstance() {

        Bundle args = new Bundle();

        SongsRecyclerFragment fragment = new SongsRecyclerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public SongsRecyclerFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSongList = SongLab.getInstance().getSongList();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.from(getActivity()).inflate(R.layout.recycler_view, container, false);
        mSongsRv = view.findViewById(R.id.recycler_view);

        mSongsRv.setLayoutManager(new LinearLayoutManager(getActivity()));

        mAdapter = new RecyclerViewAdapter(mSongList);
        mSongsRv.setAdapter(mAdapter);


        return view;
    }

    private class RecyclerViewHolder extends RecyclerView.ViewHolder {
        private ImageView mCoverIv;
        private TextView mSongTv;
        private TextView mArtistTv;
        private Song mSong;

        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            mCoverIv = itemView.findViewById(R.id.cover_image);
            mSongTv = itemView.findViewById(R.id.songs_name_tv);
            mArtistTv = itemView.findViewById(R.id.artist_name_tv);

        }

        public void bind(Song song) {
            mSong = song;
            mCoverIv.setImageBitmap(song.getBitmap());
            mSongTv.setText(song.getTrack());
            mArtistTv.setText(song.getArtist());
        }

    }


    private class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewHolder> {
        private List<Song> mSongList;

        public RecyclerViewAdapter(List<Song> songList) {
            mSongList = songList;
        }

        public void setSongList(List<Song> songList) {
            mSongList = songList;
        }

        @NonNull
        @Override
        public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.songs_list_item, viewGroup, false);
            RecyclerViewHolder viewHolder = new RecyclerViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerViewHolder recyclerViewHolder, int i) {
            Song song = mSongList.get(i);
            recyclerViewHolder.bind(song);
        }

        @Override
        public int getItemCount() {
            return mSongList.size();
        }
    }

}
