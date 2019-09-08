package com.example.jeju;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
    private Context mContext;
    private Cursor mCursor;

    public Adapter(FragmentActivity context, Cursor cursor) {
        mContext = context;
        mCursor = cursor;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView locationText;
        public TextView itemText;
        public TextView amountText;

        public ViewHolder(View itemView) {
            super(itemView);

            amountText = itemView.findViewById(R.id.textview_amount);
            itemText = itemView.findViewById(R.id.textview_item);
            locationText = itemView.findViewById(R.id.textview_location);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (!mCursor.moveToPosition(position)) {
            return;
        }

        String location = mCursor.getString(mCursor.getColumnIndex(Contract.Entry.COLUMN_LOCATION));
        String item = mCursor.getString(mCursor.getColumnIndex(Contract.Entry.COLUMN_ITEM));
        int amount = mCursor.getInt(mCursor.getColumnIndex(Contract.Entry.COLUMN_AMOUNT));
        long id = mCursor.getLong(mCursor.getColumnIndex(Contract.Entry._ID));

        holder.locationText.setText(location);
        holder.itemText.setText(item);
        holder.amountText.setText(String.valueOf(amount));
        holder.itemView.setTag(id);
    }

    @Override
    public int getItemCount() {

        return mCursor.getCount();
    }

    public void swapCursor(Cursor newCursor) {
        if (mCursor != null) {
            mCursor.close();
        }

        mCursor = newCursor;

        if (newCursor != null) {
            notifyDataSetChanged();
        }
    }
}
