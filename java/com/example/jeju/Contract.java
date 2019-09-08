package com.example.jeju;

import android.content.Context;
import android.provider.BaseColumns;

    public class Contract {

    Contract(Context context) {
    }

    public static final class Entry implements BaseColumns {
        public static final String TABLE_NAME = "List";
        public static final String COLUMN_LOCATION = "location";
        public static final String COLUMN_ITEM = "item";
        public static final String COLUMN_AMOUNT = "amount";
        public static final String COLUMN_TIMESTAMP = "timestamp";
    }
}