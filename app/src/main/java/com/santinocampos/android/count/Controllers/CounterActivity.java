package com.santinocampos.android.count.Controllers;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.os.Bundle;
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

import com.santinocampos.android.count.Dialogs.AbstractDialog;
import com.santinocampos.android.count.Dialogs.ConfirmClearDialog;
import com.santinocampos.android.count.Dialogs.ConfirmClearMoneyDialog;
import com.santinocampos.android.count.Dialogs.ConfirmExportDialog;
import com.santinocampos.android.count.ItemType.ItemType;
import com.santinocampos.android.count.Settings.SettingsActivity;
import com.santinocampos.android.count.ListManipulation.ListSender;
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

    private RecyclerView       mRecyclerView;
    private ItemAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_counter);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mAccountant = new Accountant(this);

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
        mPreferences.edit()
                    .putLong(TOTAL_MONEY, Double.doubleToLongBits(mAccountant.getTotalMoney()))
                    .apply();
    }

    private class ItemHolder extends RecyclerView.ViewHolder {
        private TextView mItemNameTextView;
        private TextView mItemInitialPriceTextView;
        private TextView mItemTotalPriceTextView;
        private TextView mItemCountTextView;
        private TextView mItemTypeIntTextViewNOTVISIBLE;
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
            mItemTypeIntTextViewNOTVISIBLE = (TextView) itemView.findViewById(R.id.item_typeInt_textView_INVISIBLE);
        }

        public void bindItem(Item item) {
            mItemNameTextView.setText(item.getName());
            mItemCountTextView.setText(getString(R.string.text_item_count_string, mAccountant.countOf(item)));
            mItemInitialPriceTextView.setText(mAccountant.individualPriceOf(item));
            mItemTotalPriceTextView.setText(mAccountant.totalPriceOf(item));
            mItemTypeIntTextViewNOTVISIBLE.setText(String.valueOf(item.getItemTypeInt()));
            if (Build.VERSION.SDK_INT < 21) {
                mItemTypeImageView.setImageDrawable(getResources().getDrawable(ItemType.getImageIdOf(item.getItemTypeInt()))); //Fix this shit
            } else {
                mItemTypeImageView.setImageDrawable(getResources().getDrawable(ItemType.getImageIdOf(item.getItemTypeInt()), getTheme()));
            }
        }

        public int getItemTypeInt() {
            return Integer.parseInt(mItemTypeIntTextViewNOTVISIBLE.getText().toString());
        }

        public String getItemName()  {
            return mItemNameTextView.getText().toString();
        }

        public int getItemCount() {
            return Integer.parseInt(mItemCountTextView.getText().toString().replace("x", ""));
        }

        public String getItemPrice() {
            String price = mItemInitialPriceTextView.getText().toString();
            price = price.replaceAll("[^0-9]", ""); //REGEX MAKES YOUR PROBLEMS EASIER TO SOLVE, USE THIS
            return String.valueOf(Double.parseDouble(price)); //This is how you fix bugs, highest layer of abstraction first.
        }

        public Item getItem() {
            return new Item(getItemName(),  Double.parseDouble(getItemPrice()), getItemCount(), getItemTypeInt());
        }

    }

    private class ItemAdapter extends RecyclerView.Adapter<ItemHolder> {

        public ItemAdapter() {
            super();
        }

        public ItemHolder onCreateViewHolder(ViewGroup parent, int ViewType) {
            View view = getLayoutInflater().inflate(R.layout.list_item_item, parent, false);
            return new ItemHolder(view);
        }

        @Override
        public void onBindViewHolder(ItemHolder holder, int position) {
            holder.bindItem(mAccountant.getItemList().get(position));
        }

        @Override
        public int getItemCount() {
            return mAccountant.getItemList().size();
        }
    }

    @Override
    public void addItem(Item item) {
        mAdapter.notifyItemInserted(mAccountant.addItem(item));
        updateChange();
    }

    public void removeItem(Item removedItem, int position) {
        mAccountant.removeItem(removedItem);
        mAdapter.notifyItemRemoved(position);
        updateChange();
    }

    @Override
    public void addMoney(double money, boolean isSet) {
        mAccountant.addMoney(money, isSet);
        updateMoneyDetails();
    }

    private void startUI() {
        if (mAdapter == null) {
            mAdapter = new ItemAdapter();
            mRecyclerView.setAdapter(mAdapter);
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
                removeItem(holder.getItem(), viewHolder.getAdapterPosition());
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
            case R.id.action_clear_money : checkIfNoMoneyToStart(new ConfirmClearMoneyDialog());
                break;
            case R.id.action_clear_items: checkIfListIsEmptyToStart(new ConfirmClearDialog());
                break;
            case R.id.action_settings : startSettings();
                break;
        }
        return true;
    }

    private void checkIfNoMoneyToStart(ConfirmClearMoneyDialog confirmClearMoneyDialog) {
        if (mAccountant.getTotalMoney() != 0) {
            startDialog(confirmClearMoneyDialog);
        } else Toast.makeText(this, R.string.toast_no_money, Toast.LENGTH_SHORT).show();
    }

    public void clearMoney() {
        mAccountant.clearMoney();
        updateMoneyDetails();
    }

    public void startSettings() {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

    private void startDialog(AbstractDialog abstractDialog) {
        getSupportFragmentManager()
                .beginTransaction()
                .add(abstractDialog, abstractDialog.getTagString())
                .commit();
    }

    @Override
    public void clearList() {
        mAccountant.clearList();
        mAdapter.notifyDataSetChanged();
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
        ListSender.exportItemList(mAccountant.getItemList(), this, mAccountant);
    }

    private void updateMoneyDetails() {
       mAllowanceTextView.setText(mAccountant.getTotalMoneyInformation());
       updateChange();
    }

    private void updateChange() {
        mChangeTextView.setText(mAccountant.getChange());
    }
}
