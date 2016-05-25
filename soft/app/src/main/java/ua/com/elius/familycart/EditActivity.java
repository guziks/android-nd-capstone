package ua.com.elius.familycart;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import ua.com.elius.familycart.data.item.ItemContentValues;
import ua.com.elius.familycart.data.item.List;
import ua.com.elius.familycart.list.FetchMaxCustomOrderAsyncTask;
import ua.com.elius.familycart.list.InsertItemAsyncTask;
import ua.com.elius.familycart.list.ListViewHolder;
import ua.com.elius.familycart.list.UpdateItemAsyncTask;

public class EditActivity extends AppCompatActivity {

    private static final String TAG = "EditActivity";

    public static final String ACTION_EDIT = "ua.com.elius.familycart.action.EDIT";

    private EditText mTitleView;
    private EditText mQuantityView;
    private EditText mDescriptionView;
    private boolean mEdit;
    private Bundle mInbox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close_white);
        }

        mTitleView = (EditText) findViewById(R.id.title_edit_text);
        mQuantityView = (EditText) findViewById(R.id.quantity_edit_text);
        mDescriptionView = (EditText) findViewById(R.id.description_edit_text);

        mEdit = ACTION_EDIT.equals(getIntent().getAction());

        if (mEdit) {
            mTitleView.setText(getIntent().getStringExtra(ListViewHolder.EXTRA_TITLE));
            mQuantityView.setText(getIntent().getStringExtra(ListViewHolder.EXTRA_QUANTITY));
            mDescriptionView.setText(getIntent().getStringExtra(ListViewHolder.EXTRA_DESCRIPTION));
        }

        mInbox = new Bundle();
        new FetchMaxCustomOrderAsyncTask(this, List.TO_BUY, mInbox).execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edit, menu);
        return true;
    }

    public void save(MenuItem menuItem) {
        if (!mInbox.getBoolean(FetchMaxCustomOrderAsyncTask.VALUE_RECEIVED)) {
            Log.i(TAG, "Custom order maximum values not received, abort saving");
            return;
        }

        ItemContentValues item = new ItemContentValues();
        long timestamp = System.currentTimeMillis();

        item.putTitle(mTitleView.getText().toString());
        item.putQuantity(mQuantityView.getText().toString());
        item.putDescription(mDescriptionView.getText().toString());
        item.putList(List.TO_BUY);

        if (mEdit) {
            item.putTimeModified(timestamp);
            new UpdateItemAsyncTask(this, item.values(), getIntent().getData()).execute();
        } else {
            // create new item
            int maxOrder = mInbox.getInt(FetchMaxCustomOrderAsyncTask.MAX_ORDER);
            item.putCustomOrder(++maxOrder);
            item.putTimeCreated(timestamp);
            item.putTimeModified(timestamp);
            new InsertItemAsyncTask(this).execute(item.values());
        }
        finish();
    }
}
