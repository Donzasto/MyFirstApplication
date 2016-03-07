package com.donzasto.myfirstapplication;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.CursorLoader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class DetailFragment extends Fragment {

    private final Uri URI = Uri.parse("content://com.donzasto.myfirstapplication.DataProvider/mytable");
    Cursor cursor;
    int arg;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cursor = new CursorLoader(getContext(), URI, null, null, null, null).loadInBackground();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail, container, false);
        cursor.moveToFirst();
        Log.d(MainActivity.LOGS, "whhhaat " + cursor.getCount());
        ((TextView)view.findViewById(R.id.tvContent)).setText(cursor.getString(2));
        return view;
    }
}
