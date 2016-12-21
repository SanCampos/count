package com.santinocampos.android.count.Controllers;

import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
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
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.santinocampos.android.count.Dialogs.ConfirmClearDialog;
import com.santinocampos.android.count.Dialogs.ConfirmExportDialog;
import com.santinocampos.android.count.Utils.Exporter;
import com.santinocampos.android.count.Listeners.DialogListener;
import com.santinocampos.android.count.Models.Accountant;
import com.santinocampos.android.count.Models.Item;
import com.santinocampos.android.count.R;
import com.santinocampos.android.count.Dialogs.AddItemDialog;
import com.santinocampos.android.count.Dialogs.AddMoneyDialog;
import com.santinocampos.android.count.Utils.MoneyUtils;

import java.util.List;

public class CounterActivity extends AppCompatActivity implements DialogListener {

    private final static String DIALOG_ADD_ITEM = "DialogAddItem";
    private final static String DIALOG_ADD_MONEY = "DialogAddMoney";
    private static final String DIALOG_EXPORT_LOG = "DialogExportLog";
    private static final String DIALOG_CLEAR_LIST = "DialogClearList";

    private Button mWalletButton;
    private Button mChangeButton;

    private Accountant mAccountant;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_counter);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mAccountant = Accountant.get(this);

        mWalletButton =  (Button) findViewById(R.id.wallet_totalMoney);
        mChangeButton =  (Button) findViewById(R.id.wallet_totalChange);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

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

    private class ItemHolder extends RecyclerView.ViewHolder {
        private TextView mItemNameTextView;
        private TextView mItemInitialPriceTextView;
        private TextView mItemTotalPriceTextView;
        private TextView mItemCountTextView;

        /**
         * HACKY V1 OF NEW ITEM VIEW, NOT FOR PRODUCTION
         **/
        public ItemHolder(View itemView) {
            super(itemView);
            mItemNameTextView = (TextView) itemView.findViewById(R.id.item_name_textView);
            mItemCountTextView = (TextView) itemView.findViewById(R.id.item_count_textView);
            mItemInitialPriceTextView = (TextView) itemView.findViewById(R.id.item_initialPrice_textView);
            mItemTotalPriceTextView = (TextView) itemView.findViewById(R.id.item_totalPrice_textView);
        }

        public void bindItem(Item item) {
            mItemNameTextView.setText(item.getName());
            mItemCountTextView.setText("x" + String.valueOf(mAccountant.countOf(item)));
            mItemInitialPriceTextView.setText(MoneyUtils.prep(mAccountant.individualPriceOf(item)));
            mItemTotalPriceTextView.setText(MoneyUtils.prep(mAccountant.totalPriceOf(item)));
        }
    }

    private class ItemAdapter extends RecyclerView.Adapter<ItemHolder> {
        private List<Item> mItemList;

        public ItemAdapter(List<Item> itemList) {
            mItemList = itemList;
        }

        public ItemHolder onCreateViewHolder(ViewGroup parent, int ViewType) {
            View view = getLayoutInflater().inflate(R.layout.list_item_item, parent, false);
            return new ItemHolder(view);
        }

        public void onBindViewHolder(ItemHolder holder, int position) {
            Item item = mItemList.get(position);
            holder.bindItem(item);
        }

        public int getItemCount() {
            return mItemList.size();
        }
    }

    private void startDialog(DialogFragment df) {
        String tag;

        if (df.getClass() == AddItemDialog.class)
            tag = DIALOG_ADD_ITEM;
        else if (df.getClass() == AddMoneyDialog.class)
            tag = DIALOG_ADD_MONEY;
        else if (df.getClass() == ConfirmExportDialog.class)
            tag = DIALOG_EXPORT_LOG;
        else
            tag = DIALOG_CLEAR_LIST;

        getSupportFragmentManager().beginTransaction().add(df, tag).commit();
    }

    @Override
    public void addItem(Item item, int count) {
        mAccountant.addItem(item, count);
        mAdapter.notifyDataSetChanged();
        mChangeButton.setText(mAccountant.getChange());
    }

    public void removeItem(int position) {
        mAccountant.removeItem(position);
        mAdapter.notifyItemRemoved(position);
        mChangeButton.setText(mAccountant.getChange());
    }

    @Override
    public void addMoney(double money, boolean isSet) {
        mAccountant.addMoney(money, isSet);
        updateMoney();
    }

    private void startUI() {
        updateMoney();
        if (mAdapter == null) {
            mAdapter = new ItemAdapter(mAccountant.getItemList());
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
                removeItem(viewHolder.getLayoutPosition());
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
            case R.id.action_export : export();
                break;
            case R.id.action_clear : startDialog(new ConfirmClearDialog());
        }
        return true;
    }

    @Override
    public void clearList() {
        mAccountant.clearList();
        mAdapter.notifyDataSetChanged();
        updateMoney();
    }

    private void export() {
        List<Item> list = mAccountant.getItemList();

        if (list.size() != 0) {
            startDialog(new ConfirmExportDialog());
        } else Toast.makeText(CounterActivity.this, R.string.toast_error_export, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void exportList()  {
        Exporter.exportItemList(mAccountant.getItemList(), this);
        Toast.makeText(CounterActivity.this, R.string.toast_success_export, Toast.LENGTH_LONG).show();
    }

    private void updateMoney() {
        mWalletButton.setText(mAccountant.getTotalMoney());
        mChangeButton.setText(mAccountant.getChange());
    }
}
