package ua.com.elius.familycart;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import ua.com.elius.familycart.data.item.ItemColumns;
import ua.com.elius.familycart.data.item.ItemContentValues;
import ua.com.elius.familycart.data.item.List;

public class EditActivity extends AppCompatActivity {

    private static final String TAG = "EditActivity";

    public static final String ACTION_EDIT = "intent.action.EDIT";

    EditText mTitle;
    EditText mQuantity;
    EditText mDescription;
    boolean mEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close_white);
        }

        mTitle = (EditText) findViewById(R.id.title_edit_text);
        mQuantity = (EditText) findViewById(R.id.quantity_edit_text);
        mDescription = (EditText) findViewById(R.id.description_edit_text);

        mEdit = ACTION_EDIT.equals(getIntent().getAction());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edit, menu);
        return true;
    }

    public void save(MenuItem menuItem) {
        if (mEdit) {
            // TODO edit
        } else {
            // create new item
            ItemContentValues item = new ItemContentValues();
            item.putTitle(mTitle.getText().toString());
            item.putQuantity(mQuantity.getText().toString());
            item.putDescription(mDescription.getText().toString());
            item.putList(List.TO_BUY);
            item.putCustomOrder(0); // TODO put highest value
            long timestamp = System.currentTimeMillis();
            item.putTimeCreated(timestamp);
            item.putTimeModified(timestamp);

            getContentResolver().insert(ItemColumns.CONTENT_URI, item.values());

            Log.i(TAG, "Save item: " + item.values().toString());

            setResult(RESULT_OK);
        }
        finish();
    }
}
