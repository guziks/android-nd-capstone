package ua.com.elius.familycart;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ToBuyListFragment extends Fragment implements OnStartDragListener {

    private RecyclerView mRecyclerView;
    private ListAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ItemTouchHelper mItemTouchHelper;

    public ToBuyListFragment() {
        // Required empty public constructor
    }

    public static ToBuyListFragment newInstance() {
        return new ToBuyListFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_to_buy_list, container, false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.to_by_recycler_view);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new ListAdapter(getDummyDataset(), this);
        mRecyclerView.setAdapter(mAdapter);

        ItemTouchHelper.Callback itemTouchHelperCallback = new ListItemTouchHelperCallback(mAdapter,
                ListViewHolder.MODE_BOUGHT,
                ListViewHolder.MODE_WONT_BUY);
        mItemTouchHelper = new ItemTouchHelper(itemTouchHelperCallback);
        mItemTouchHelper.attachToRecyclerView(mRecyclerView);

        return view;
    }

    private String[][] getDummyDataset() {
        String[][] dataset = new String[][]{
                {"Milk", "1 bottle", "2.5% or 3% fat"},
                {"Butter", "250 g", "That one in blue wrapper"},
                {"Bread", "2x", ""},
                {"Banana", "1.5 kg", "Don't take too large"},
                {"Orange", "1 kg", ""},
                {"Apples", "2 kg", "Take red and yellow"},
        };

        return dataset;
    }

    @Override
    public void onStartDrag(RecyclerView.ViewHolder viewHolder) {
        mItemTouchHelper.startDrag(viewHolder);
    }
}
