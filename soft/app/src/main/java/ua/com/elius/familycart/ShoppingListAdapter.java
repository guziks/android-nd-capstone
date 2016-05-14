package ua.com.elius.familycart;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;

public class ShoppingListAdapter extends RecyclerView.Adapter<ShoppingListAdapter.ViewHolder>
        implements ListItemTouchHelperAdapter {

    private static final String TAG = "ShoppingListAdapter";

    private ArrayList<String[]> mDataset;

    public ShoppingListAdapter(String[][] dataset) {
        mDataset = new ArrayList<>();
        Collections.addAll(mDataset, dataset);
    }

    @Override
    public ShoppingListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.shopping_list_item, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.mTitle.setText(mDataset.get(position)[0]);
        holder.mQuantity.setText(mDataset.get(position)[1]);
        holder.mDescription.setText(mDataset.get(position)[2]);
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    @Override
    public void onItemMove(int fromPosition, int toPosition) {
        Log.i(TAG, "onItemMove");
        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                Collections.swap(mDataset, i, i + 1);
                Log.i(TAG, "swap");
            }
        } else {
            for (int i = fromPosition; i > toPosition; i--) {
                Collections.swap(mDataset, i, i - 1);
                Log.i(TAG, "swap");
            }
        }
        notifyItemMoved(fromPosition, toPosition);
    }

    @Override
    public void onItemDismiss(int position) {
        Log.i(TAG, "onItemDismiss");
        mDataset.remove(position);
        notifyItemRemoved(position);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mTitle;
        public TextView mQuantity;
        public TextView mDescription;

        public ViewHolder(View v) {
            super(v);
            mTitle = (TextView) v.findViewById(R.id.item_title);
            mQuantity = (TextView) v.findViewById(R.id.item_quantity);
            mDescription = (TextView) v.findViewById(R.id.item_description);
        }
    }
}
