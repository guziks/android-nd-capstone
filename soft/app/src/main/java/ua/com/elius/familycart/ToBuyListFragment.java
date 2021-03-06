package ua.com.elius.familycart;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ua.com.elius.familycart.data.item.ItemColumns;
import ua.com.elius.familycart.data.item.ItemCursor;
import ua.com.elius.familycart.data.item.List;
import ua.com.elius.familycart.data.person.PersonColumns;
import ua.com.elius.familycart.data.person.PersonCursor;
import ua.com.elius.familycart.list.ListAdapter;
import ua.com.elius.familycart.list.ListItemTouchHelperCallback;
import ua.com.elius.familycart.list.ListViewHolder;
import ua.com.elius.familycart.list.OnStartDragListener;

public class ToBuyListFragment extends Fragment implements OnStartDragListener,
        LoaderManager.LoaderCallbacks<Cursor>{

    private static final String TAG = "ToBuyListFragment";

    private RecyclerView mRecyclerView;
    private ListAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ItemTouchHelper mItemTouchHelper;

    private static final int TO_BUY_LOADER = 1;

    public ToBuyListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        getLoaderManager().initLoader(TO_BUY_LOADER, null, this);
        super.onActivityCreated(savedInstanceState);
    }

    public static ToBuyListFragment newInstance() {
        return new ToBuyListFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_to_buy_list, container, false);

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newItem();
            }
        });

        mRecyclerView = (RecyclerView) view.findViewById(R.id.to_by_recycler_view);

        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new ListAdapter(getContext(), List.TO_BUY, this);
        mRecyclerView.setAdapter(mAdapter);

        ItemTouchHelper.Callback itemTouchHelperCallback = new ListItemTouchHelperCallback(mAdapter);
        mItemTouchHelper = new ItemTouchHelper(itemTouchHelperCallback);
        mItemTouchHelper.attachToRecyclerView(mRecyclerView);

        return view;
    }

    @Override
    public void onStartDrag(RecyclerView.ViewHolder viewHolder) {
        mItemTouchHelper.startDrag(viewHolder);
    }

    @Override
    public android.support.v4.content.Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(getContext(),
                ItemColumns.CONTENT_URI,
                ItemColumns.ALL_COLUMNS,
                ItemColumns.LIST + " = ?",
                new String[]{String.valueOf(List.TO_BUY.ordinal())},
                ItemColumns.CUSTOM_ORDER + " DESC");
    }

    @Override
    public void onLoadFinished(android.support.v4.content.Loader<Cursor> loader, Cursor data) {
        mAdapter.changeCursor(new ItemCursor(data));
    }

    @Override
    public void onLoaderReset(android.support.v4.content.Loader<Cursor> loader) {
        mAdapter.swapCursor(null);
    }

    public void newItem() {
        Intent intent = new Intent(getContext(), EditActivity.class);
        startActivity(intent);
    }
}
