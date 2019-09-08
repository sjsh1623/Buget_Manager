package com.example.jeju;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ChoiFragment extends Fragment {


    private SQLiteDatabase mDatabase;
    private SQLiteDatabase Database;
    private Adapter mAdapter;
    private EditText mEditTextLocation;
    private EditText mEditTextItem;
    private EditText mEditTextAmount;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_choi, container, false);

        DBHelper_Choi dbHelper = new DBHelper_Choi(getActivity());
        mDatabase = dbHelper.getWritableDatabase();

        RecyclerView recyclerView = view.findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new Adapter(getActivity(), getAllItems());
        recyclerView.setAdapter(mAdapter);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                removeItem((long) viewHolder.itemView.getTag());
            }
        }).attachToRecyclerView(recyclerView);

        mEditTextLocation = view.findViewById(R.id.location);
        mEditTextItem = view.findViewById(R.id.item);
        mEditTextAmount = view.findViewById(R.id.amount);

        Button buttonAdd = view.findViewById(R.id.button);

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addItem();
            }
        });

        int group = getAllColumn() /3;
        TextView all = (TextView)view.findViewById(R.id.group);
        all.setText(String.valueOf(group)+ "원");

        int indivisual = getAllColumnforChoi();
        TextView indiv = (TextView)view.findViewById(R.id.indivisual);
        indiv.setText(String.valueOf(indivisual)+ "원");

        int result = group + indivisual;
        TextView res = (TextView)view.findViewById(R.id.result);
        res.setText(String.valueOf(result)+ "원");


        int hb = 130000; //회비
        TextView hbb = (TextView)view.findViewById(R.id.hb);
        hbb.setText(String.valueOf(hb) + "원");

        int sub = hb - result;
        TextView subt = (TextView)view.findViewById(R.id.subtract);
        subt.setText(String.valueOf(sub) +  "원");

        return view;
    }

    private void addItem() {


        String amount = mEditTextAmount.getText().toString();
        String location = mEditTextLocation.getText().toString();
        String item = mEditTextItem.getText().toString();
        ContentValues cv = new ContentValues();
        cv.put(Contract.Entry.COLUMN_LOCATION, location);
        cv.put(Contract.Entry.COLUMN_AMOUNT, amount);
        cv.put(Contract.Entry.COLUMN_ITEM, item);

        mDatabase.insert(Contract.Entry.TABLE_NAME, null, cv);
        mAdapter.swapCursor(getAllItems());

        mEditTextLocation.getText().clear();
        mEditTextAmount.getText().clear();
        mEditTextItem.getText().clear();
    }

    private void removeItem(long id) {
        mDatabase.delete(Contract.Entry.TABLE_NAME,
                Contract.Entry._ID + "=" + id, null);
        mAdapter.swapCursor(getAllItems());
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
        Database = dbHelper.getReadableDatabase();
        Cursor cursor = Database.rawQuery("SELECT SUM(" + Contract.Entry.COLUMN_AMOUNT + ") as Total FROM " + Contract.Entry.TABLE_NAME, null);
        if (cursor.moveToFirst()) {
            sum = cursor.getInt(cursor.getColumnIndex("Total"));
        }
        return sum;
    }
    public int getAllColumnforChoi() {
        int sum = 0;
        DBHelper_Choi dbHelper = new DBHelper_Choi(getActivity());
        mDatabase = dbHelper.getReadableDatabase();
        Cursor cursor = mDatabase.rawQuery("SELECT SUM(" + Contract.Entry.COLUMN_AMOUNT + ") as Total FROM " + Contract.Entry.TABLE_NAME, null);
        if(cursor.moveToFirst())
        {
            sum = cursor.getInt(cursor.getColumnIndex("Total"));
        }
        return sum;
    }
}


