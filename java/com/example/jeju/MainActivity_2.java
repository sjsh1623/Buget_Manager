package com.example.jeju;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;

public class MainActivity_2 extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private Contract mData;
    private DrawerLayout drawer;
    private SQLiteDatabase mDatabase;
    private Adapter mAdapter;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_2);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        drawer.addDrawerListener(toggle);
        toggle.syncState();
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        DBHelper dbHelper = new DBHelper(this);
        mDatabase = dbHelper.getWritableDatabase();
        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new Adapter(this, getAllItems());
        recyclerView.setAdapter(mAdapter);

        int sum = getAllColumn();
        TextView tv = (TextView) findViewById(R.id.total);
        tv.setText(String.valueOf(sum) + "원");
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.nav_overview:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new OverViewFragment()).commit();
                break;
            case R.id.nav_add:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new AddFragment()).commit();
                break;
            case R.id.nav_joo:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new JooFragment()).commit();
                break;
            case R.id.nav_choi:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ChoiFragment()).commit();
                break;
            case R.id.nav_lim:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new LimFragment()).commit();
                break;
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    private Cursor getAllItems() {
        return mDatabase.query(
                Contract.Entry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                Contract.Entry.COLUMN_TIMESTAMP + " DESC");
    }

    public int getAllColumn() {
        int sum = 0;
        DBHelper dbHelper = new DBHelper(this);
        mDatabase = dbHelper.getReadableDatabase();
        Cursor cursor = mDatabase.rawQuery("SELECT SUM(" + Contract.Entry.COLUMN_AMOUNT + ") as Total FROM " + Contract.Entry.TABLE_NAME, null);
        if (cursor.moveToFirst()) {
            sum = cursor.getInt(cursor.getColumnIndex("Total"));
        }
        return sum;
    }
}