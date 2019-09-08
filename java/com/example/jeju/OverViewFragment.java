package com.example.jeju;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class OverViewFragment extends Fragment {

    private SQLiteDatabase mDatabase;
    private Adapter mAdapter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_overview, container, false);

        int sum = getAllColumn();
        TextView tv = (TextView) view.findViewById(R.id.total);
        tv.setText(String.valueOf(sum) + "원");

        DBHelper dbHelper = new DBHelper(getActivity());
        mDatabase = dbHelper.getWritableDatabase();

        RecyclerView recyclerView = view.findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new Adapter(getActivity(), getAllItems());
        recyclerView.setAdapter(mAdapter);
        return view;



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
        DBHelper dbHelper = new DBHelper(getActivity());
        mDatabase = dbHelper.getReadableDatabase();
        Cursor cursor = mDatabase.rawQuery("SELECT SUM(" + Contract.Entry.COLUMN_AMOUNT + ") as Total FROM " + Contract.Entry.TABLE_NAME, null);
        if(cursor.moveToFirst())
        {
            sum = cursor.getInt(cursor.getColumnIndex("Total"));
        }
        return sum;
    }


}