package ua.com.elius.familycart;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ua.com.elius.familycart.data.item.ItemColumns;
import ua.com.elius.familycart.data.item.ItemCursor;
import ua.com.elius.familycart.data.item.List;
import ua.com.elius.familycart.list.ListAdapter;
import ua.com.elius.familycart.list.ListItemTouchHelperCallback;
import ua.com.elius.familycart.list.OnStartDragListener;


public class BoughtListFragment extends Fragment implements OnStartDragListener,
        LoaderManager.LoaderCallbacks<Cursor> {

    private static final String TAG = "BoughtListFragment";

    private RecyclerView mRecyclerView;
    private ListAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ItemTouchHelper mItemTouchHelper;

    private static final int BOUGHT_LOADER = 3;

    public BoughtListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        getLoaderManager().initLoader(BOUGHT_LOADER, null, this);
        super.onActivityCreated(savedInstanceState);
    }

    public static BoughtListFragment newInstance() {
        return new BoughtListFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bought_list, container, false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.bought_recycler_view);

        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new ListAdapter(getContext(), List.BOUGHT, this);
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
                new String[]{String.valueOf(List.BOUGHT.ordinal())},
                null);
    }

    @Override
    public void onLoadFinished(android.support.v4.content.Loader<Cursor> loader, Cursor data) {
        mAdapter.changeCursor(new ItemCursor(data));
    }

    @Override
    public void onLoaderReset(android.support.v4.content.Loader<Cursor> loader) {
        mAdapter.swapCursor(null);
    }
}
