package com.example.safedom.AdminP;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.example.safedom.AdminP.AdminActivity;
import com.example.safedom.InfoActivity;
import com.example.safedom.R;
import com.example.safedom.Tabs.TabCasas;
import com.example.safedom.Tabs.TabUsers;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class VistaAdmin extends AppCompatActivity {
    private String[] nombres = new String[]{"CASAS", " USUARIOS"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vista_admin);

        ViewPager2 viewPager = findViewById(R.id.viewpager);
        viewPager.setAdapter(new MiPagerAdapter(this));
        TabLayout tabs = findViewById(R.id.tabs);
        new TabLayoutMediator(tabs, viewPager,
                new TabLayoutMediator.TabConfigurationStrategy() {
                    @Override
                    public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                        tab.setText(nombres[position]);
                    }
                }
        ).attach();


    }

    public class MiPagerAdapter extends FragmentStateAdapter {
        public MiPagerAdapter(FragmentActivity activity) {
            super(activity);
        }

        @Override
        public int getItemCount() {
            return 2;
        }

        @Override
        @NonNull
        public Fragment createFragment(int position) {
            switch (position) {
                case 0:
                    return new TabCasas();
                case 1:
                    return new TabUsers();
            }
            return null;
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.menu_usuario) {
            Intent intent = new Intent(this, AdminActivity.class);
            startActivity(intent);
        }
        if (id == R.id.info) {
            Intent intent = new Intent(this, InfoActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

}
