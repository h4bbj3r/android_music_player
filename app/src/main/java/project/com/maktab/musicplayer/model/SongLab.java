package project.com.maktab.musicplayer.model;

import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import project.com.maktab.musicplayer.R;

public class SongLab {
    private static SongLab mInstance;
    private List<Song> mSongList;


    public List<Song> getSongList() {
        return mSongList;
    }

    private SongLab() {
        mSongList = new ArrayList<>();
    }

    public static SongLab getInstance() {
        if (mInstance == null)
            mInstance = new SongLab();
        return mInstance;
    }

    public String getArt(Activity activity, Long albumId) {
        Cursor cursor = activity.getContentResolver().query(MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI,
                new String[]{MediaStore.Audio.Albums._ID, MediaStore.Audio.Albums.ALBUM_ART},
                MediaStore.Audio.Albums._ID + "=?",
                new String[]{String.valueOf(albumId)},
                null);

        if (cursor.moveToFirst()) {
            String path = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM_ART));
            // do whatever you need to do
            return path;
        }
        return null;
    }


    public void init(Activity activity) {
        final Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        final String[] cursor_cols = {MediaStore.Audio.Media._ID,
                MediaStore.Audio.Media.ARTIST, MediaStore.Audio.Media.ALBUM,
                MediaStore.Audio.Media.TITLE, MediaStore.Audio.Media.DATA,
                MediaStore.Audio.Media.ALBUM_ID,
                MediaStore.Audio.Media.DURATION};
        final String where = MediaStore.Audio.Media.IS_MUSIC + "!=0";
        final Cursor cursor = activity.getContentResolver().query(uri,
                null, where, null, null);

        if(cursor==null)return;

        while (cursor.moveToNext()) {
            String artist = cursor.getString(cursor
                    .getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST));
            String album = cursor.getString(cursor
                    .getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM));
            String track = cursor.getString(cursor
                    .getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE));
            String data = cursor.getString(cursor
                    .getColumnIndexOrThrow(MediaStore.Audio.Media.DATA));
            Long albumId = cursor.getLong(cursor
                    .getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM_ID));

            int duration = cursor.getInt(cursor
                    .getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION));

            Uri sArtworkUri = Uri
                    .parse("content://media/external/audio/albumart");
            Uri albumArtUri = ContentUris.withAppendedId(sArtworkUri, albumId);

            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(
                        activity.getContentResolver(), albumArtUri);
                if (bitmap != null)
                    bitmap = Bitmap.createScaledBitmap(bitmap, 80, 80, true);

            } catch (FileNotFoundException exception) {
                exception.printStackTrace();
              /*  bitmap = BitmapFactory.decodeResource(context.getResources(),
                        R.drawable.audio_file);*/
            } catch (IOException e) {

                e.printStackTrace();
            }

            Song song = new Song();
            song.setArtist(artist);
            song.setBitmap(bitmap);
            song.setTrack(track);
            song.setData(data);
            song.setDuration(duration);
            song.setBitmap(bitmap);

            mSongList.add(song);

        }

    }
}
