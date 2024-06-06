package com.example.newsapp.view;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.example.newsapp.databinding.ActivityMainBinding;
import com.example.newsapp.view.fragments.BookmarksFragment;
import com.example.newsapp.view.fragments.TopHeadlinesFragment;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

/**
 * @Author Gaurav Naresh Pandit
 * @Since 06/06/24
 **/
public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding activityMainBinding;
    private final String[] tabLabels = new String[]{"Top Headlines", "Bookmarks"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activityMainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(activityMainBinding.getRoot());


        ViewPager2 viewPager2 = activityMainBinding.viewPager;
        viewPager2.setAdapter(new ViewPagerAdapter(this));
        viewPager2.setUserInputEnabled(false);

        TabLayout tabLayout = activityMainBinding.tabLayout;
        tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#FFFFFF"));
        new TabLayoutMediator(tabLayout, viewPager2, (tab, i) -> tab.setText(tabLabels[i])).attach();

    }

    public static class ViewPagerAdapter extends FragmentStateAdapter {
        public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
            super(fragmentActivity);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            switch (position) {
                case 0:
                    return new TopHeadlinesFragment();
                case 1:
                    return new BookmarksFragment();
            }
            return new TopHeadlinesFragment();
        }

        @Override
        public int getItemCount() {
            return 2;
        }
    }

}