package com.donzasto.myfirstapplication;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.content.CursorLoader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

//TODO Получить данные с БД и засунуть в фрагмент
public class MainActivity extends AppCompatActivity {

    public static final String LOGS = "myLogs";

    private final Uri URI = Uri.parse("content://com.donzasto.myfirstapplication.DataProvider/mytable");

    private boolean twoPane;


    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.list);

for(int i = 0; i < 5; i++) {
    ContentValues contentValues = new ContentValues();
    contentValues.put("title", "pumpurum" + i);
    contentValues.put("content", "paramparam" + i);
    getContentResolver().insert(URI, contentValues);
}
        Cursor cursor = new CursorLoader(this, URI, null, null, null, null).loadInBackground();

        ArrayList arrayList = new ArrayList();
        if (cursor.moveToFirst()) {
            do {
                arrayList.add(cursor.getString(1));
            } while (cursor.moveToNext());
        }
        recyclerView.setAdapter(new SimpleItemRecyclerViewAdapter(arrayList));

        if (findViewById(R.id.list_container) != null) {
            twoPane = true;
        }
    }

//    @Override
//    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
//        Log.d(LOGS, "onCreateLoader");
//        return new CursorLoader(this, URI, null, null, null, null);
//    }
//
//    @Override
//    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
//        Log.d(LOGS, "onLoadFinished");
//        ArrayList<String> arrayList = new ArrayList<>();
//
//        if (data.moveToFirst()){
//            do{
//                arrayList.add(data.getString(1));
//                Log.d(LOGS, "cursor " + data.getString(1));
//            }while (data.moveToNext());
//        }
//
//        recyclerView.setAdapter(new SimpleItemRecyclerViewAdapter(arrayList));
//        try {
//            TimeUnit.SECONDS.sleep(7);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Override
//    public void onLoaderReset(Loader<Cursor> loader) {
//        Log.d(LOGS, "onLoaderReset");
//    }


    public class SimpleItemRecyclerViewAdapter extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

        ArrayList arrayList = new ArrayList();

        SimpleItemRecyclerViewAdapter(ArrayList arrayList) {
            this.arrayList = arrayList;
            //           Log.d(LOGS, arrayList.get(0).toString(), null);
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            Log.d(LOGS, "onCreateViewHolder", null);
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, final int position) {
            Log.d(LOGS, "onBindViewHolder", null);
            holder.mTextView.setText(arrayList.get(position).toString());
            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (twoPane) {
                        DetailFragment fragment = new DetailFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.list_container, fragment).commit();
                    } else {
                        Context context = view.getContext();
                        Intent intent = new Intent(context, DetailActivity.class);
                        context.startActivity(intent);
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            Log.d(LOGS, "getItemCount", null);
            return arrayList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            public final View mView;
            public final TextView mTextView;

            public ViewHolder(View itemView) {
                super(itemView);
                Log.d(LOGS, "ViewHolder", null);
                mView = itemView;
                mTextView = (TextView) itemView.findViewById(R.id.mTextView);
                registerForContextMenu(mTextView);
            }
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(0, 1, 0, "Delete");
    }

}