package project.com.maktab.musicplayer;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import project.com.maktab.musicplayer.model.SongLab;

public class ViewPagerActivity extends AppCompatActivity {
    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private ViewPagerAdapter mViewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager);
        mViewPager = findViewById(R.id.main_view_pager);
        mTabLayout = findViewById(R.id.main_tab_layout);
        mViewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mViewPagerAdapter);
        SongLab.getInstance().init(this);

        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setTabMode(TabLayout.MODE_FIXED);

    }

    private class ViewPagerAdapter extends FragmentStatePagerAdapter {

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            switch (i) {
                case 0:
                    return SongsRecyclerFragment.newInstance("all",0l);
                case 1:
                    return AlbumRecyclerFragment.newInstance();
                case 2:
                    return ArtistRecyclerFragment.newInstance();
                    default:
                        return null;
            }

        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public int getItemPosition(@NonNull Object object) {
            return POSITION_NONE;
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return getString(R.string.songs);
                case 1:
                    return getString(R.string.album);
                case 2:
                    return getString(R.string.artists);
                default:
                    return super.getPageTitle(position);
            }
        }
    }


}
