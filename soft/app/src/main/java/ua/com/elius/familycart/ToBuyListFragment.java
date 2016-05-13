package ua.com.elius.familycart;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ToBuyListFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    public ToBuyListFragment() {
        // Required empty public constructor
    }

    public static ToBuyListFragment newInstance() {
        return new ToBuyListFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_to_buy_list, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.to_by_recycler_view);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new ShoppingListAdapter(getDummyDataset());
        mRecyclerView.setAdapter(mAdapter);
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
}
