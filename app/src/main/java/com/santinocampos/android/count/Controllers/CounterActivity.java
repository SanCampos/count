package com.santinocampos.android.count.Controllers;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.santinocampos.android.count.Adapter.RecyclerViewCursorAdapter;
import com.santinocampos.android.count.Database.ItemCursorWrapper;
import com.santinocampos.android.count.Dialogs.AbstractDialog;
import com.santinocampos.android.count.Dialogs.ConfirmClearDialog;
import com.santinocampos.android.count.Dialogs.ConfirmExportDialog;
import com.santinocampos.android.count.Utils.Exporter;
import com.santinocampos.android.count.Listeners.DialogListener;
import com.santinocampos.android.count.Models.Accountant;
import com.santinocampos.android.count.Models.Item;
import com.santinocampos.android.count.R;
import com.santinocampos.android.count.Dialogs.AddItemDialog;
import com.santinocampos.android.count.Dialogs.AddMoneyDialog;

import java.util.List;

public class CounterActivity extends AppCompatActivity implements DialogListener {

    private static final String TOTAL_MONEY = "totalMoney";

    private static SharedPreferences mPreferences;

    private TextView mAllowanceTextView;
    private TextView mChangeTextView;

    private Accountant mAccountant;

    private RecyclerView mRecyclerView;
    private ItemAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_counter);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mAccountant = Accountant.get(this);

        mAllowanceTextView = (TextView) findViewById(R.id.text_view_allowance);
        mChangeTextView = (TextView) findViewById(R.id.text_view_change);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        addMoney(Double.longBitsToDouble(mPreferences.getLong(TOTAL_MONEY, 0)), true);

        startUI();
        startItemHelper();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_addItem);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startDialog(new AddItemDialog());
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();

        SharedPreferences.Editor editor = mPreferences.edit();
        editor.putLong(TOTAL_MONEY, Double.doubleToRawLongBits(mAccountant.getTotalMoney()));
        editor.apply();
    }

    private class ItemHolder extends RecyclerView.ViewHolder {
        private TextView mItemNameTextView;
        private TextView mItemInitialPriceTextView;
        private TextView mItemTotalPriceTextView;
        private TextView mItemCountTextView;
        private ImageView mItemTypeImageView;

        /**
         * HACKY V1 OF NEW ITEM VIEW, NOT FOR PRODUCTION
         **/
        public ItemHolder(View itemView) {
            super(itemView);
            mItemNameTextView = (TextView) itemView.findViewById(R.id.item_name_textView);
            mItemCountTextView = (TextView) itemView.findViewById(R.id.item_count_textView);
            mItemInitialPriceTextView = (TextView) itemView.findViewById(R.id.item_initialPrice_textView);
            mItemTotalPriceTextView = (TextView) itemView.findViewById(R.id.item_totalPrice_textView);
            mItemTypeImageView = (ImageView) itemView.findViewById(R.id.itemType_imageView);
        }

        public void bindItem(Item item) {
            mItemNameTextView.setText(item.getName());
            mItemCountTextView.setText("x" + Accountant.countOf(item));
            mItemInitialPriceTextView.setText(Accountant.individualPriceOf(item));
            mItemTotalPriceTextView.setText(Accountant.totalPriceOf(item));
            mItemTypeImageView.setImageDrawable(getDrawable(item.getItemType().getImageID()));
        }

        public String getItemName()  {
            return mItemNameTextView.getText().toString();
        }

        public String getItemPrice() {
            String price = mItemInitialPriceTextView.getText().toString();
            return price.substring(0, price.length() - 1);
        }
    }

    private class ItemAdapter extends RecyclerViewCursorAdapter<ItemHolder> {

        public ItemAdapter() {
            super();
        }

        public ItemHolder onCreateViewHolder(ViewGroup parent, int ViewType) {
            View view = getLayoutInflater().inflate(R.layout.list_item_item, parent, false);
            return new ItemHolder(view);
        }

        @Override
        public void onBindViewHolder(ItemHolder holder, Cursor cursor) {
            ItemCursorWrapper cw = (ItemCursorWrapper) cursor;
            holder.bindItem(cw.getItem());
        }
    }

    @Override
    public void addItem(Item item) {
        mAccountant.addItem(item);
        updateCursor();
        updateChange();
    }

    public void removeItem(String itemName, String itemPrice) {
        mAccountant.removeItem(itemName, itemPrice);
        updateCursor();
        updateChange();
    }

    @Override
    public void addMoney(double money, boolean isSet) {
        mAccountant.addMoney(money, isSet);
        updateMoney();
    }

    private void startUI() {
        updateMoney();
        if (mAdapter == null) {
            mAdapter = new ItemAdapter();
            mRecyclerView.setAdapter(mAdapter);
            updateCursor();
        }
    }

    private void startItemHelper() {
        ItemTouchHelper.SimpleCallback mSimpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                ItemHolder holder = (ItemHolder) viewHolder;
                removeItem(holder.getItemName(), holder.getItemPrice());
            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(mSimpleCallback);
        itemTouchHelper.attachToRecyclerView(mRecyclerView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_change, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();

        switch (itemId) {
            case R.id.action_add_money : startDialog(new AddMoneyDialog());
                break;
            case R.id.action_export : checkIfListIsEmptyToStart(new ConfirmExportDialog());
                break;
            case R.id.action_clear : checkIfListIsEmptyToStart(new ConfirmClearDialog());
                break;
            case R.id.action_settings : //startSettings();
                break;
        }
        return true;
    }

    /**
    public void startSettings() {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    } **/

    private void startDialog(AbstractDialog abstractDialog) {
        getSupportFragmentManager()
                .beginTransaction()
                .add(abstractDialog, abstractDialog.getTagString())
                .commit();
    }

    @Override
    public void clearList() {
        mAccountant.clearList();
        updateCursor();
        updateChange();
    }

    private void checkIfListIsEmptyToStart(AbstractDialog abstractDialog) {
        List<Item> list = mAccountant.getItemList();

        if (list.size() != 0) {
            startDialog(abstractDialog);
        } else Toast.makeText(CounterActivity.this, R.string.toast_error_empty_list, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void exportList()  {
        Exporter.exportItemList(mAccountant.getItemList(), this);
        Toast.makeText(CounterActivity.this, R.string.toast_success_export, Toast.LENGTH_LONG).show();
    }

    private void updateMoney() {
       mAllowanceTextView.setText(mAccountant.getTotalMoneyInformation());
       updateChange();
    }

    private void updateChange() {
        mChangeTextView.setText(mAccountant.getChange());
    }

    private void updateCursor() {
        mAdapter.swapCursor(mAccountant.querySortedItems(null, null));
    }
}
