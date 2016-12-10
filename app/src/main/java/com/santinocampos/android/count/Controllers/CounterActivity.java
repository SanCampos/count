package com.santinocampos.android.count.Controllers;

import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.santinocampos.android.count.Listeners.DialogListener;
import com.santinocampos.android.count.Models.Accountant;
import com.santinocampos.android.count.Models.Item;
import com.santinocampos.android.count.R;
import com.santinocampos.android.count.Dialogs.AddItemDialog;
import com.santinocampos.android.count.Dialogs.AddMoneyDialog;
import com.santinocampos.android.count.Utils.MoneyUtils;

import java.util.ArrayList;
import java.util.Map;

public class CounterActivity extends AppCompatActivity implements DialogListener {

    private final static String DIALOG_ADD_ITEM = "DialogAddItem";
    private final static String DIALOG_ADD_MONEY = "DialogAddMoney";

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
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mAccountant = Accountant.get(this);

        mWalletButton =  (Button) findViewById(R.id.wallet_totalMoney);
        mChangeButton =  (Button) findViewById(R.id.wallet_totalChange);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        updateUI();

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

        /** HACKY V1 OF NEW ITEM VIEW, NOT FOR PRODUCTION **/
        public ItemHolder(View itemView) {
            super(itemView);
            mItemNameTextView = (TextView) itemView.findViewById(R.id.item_name_textView);
            mItemCountTextView = (TextView) itemView.findViewById(R.id.item_count_textView);
            mItemInitialPriceTextView = (TextView) itemView.findViewById(R.id.item_initialPrice_textView);
            mItemTotalPriceTextView = (TextView) itemView.findViewById(R.id.item_totalPrice_textView);
        }

        public void bindItem(Item item, int count) {
            mItemNameTextView.setText(item.getName());
            mItemCountTextView.setText('x' + String.valueOf(count));
            mItemInitialPriceTextView.setText(MoneyUtils.prep(item.getPrice()));
            mItemTotalPriceTextView.setText(MoneyUtils.prep(item.getPrice() * count));
        }
    }

    private class ItemAdapter extends RecyclerView.Adapter<ItemHolder> {
        private Map<Item, Integer> mItemList;

        public ItemAdapter(Map<Item, Integer> itemList) {
            mItemList = itemList;
        }

        public ItemHolder onCreateViewHolder(ViewGroup parent, int ViewType) {
            View view = getLayoutInflater().inflate(R.layout.list_item_item, parent, false);
            return new ItemHolder(view);
        }

        public void onBindViewHolder(ItemHolder holder, int position) {
            Item item = new ArrayList<>(mItemList.keySet()).get(position);
            int count = mItemList.get(item);
            holder.bindItem(item, count);
        }

        public int getItemCount() {
            return mItemList.size();
        }
    }

    private void startDialog(DialogFragment df) {
        String tag = df.getClass() == AddItemDialog.class ? DIALOG_ADD_ITEM : DIALOG_ADD_MONEY;
        getSupportFragmentManager().beginTransaction().add(df, tag).commit();
    }

    @Override
    public void addItem(Item item, int count) {
        mAccountant.addItem(item, count);
        updateUI();
    }

    @Override
    public void addMoney(double money, boolean isSet) {
        mAccountant.addMoney(money, isSet);
        updateUI();
    }

    private void updateUI() {
        mWalletButton.setText(MoneyUtils.prep(mAccountant.getTotalMoney()));
        mChangeButton.setText(MoneyUtils.prep(mAccountant.getChange()));

        if (mAdapter == null) {
            mAdapter = new ItemAdapter(mAccountant.getItemList());
            mRecyclerView.setAdapter(mAdapter);
        } else mAdapter.notifyDataSetChanged();
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
            case R.id.action_export : //export();
                break;
        }
        return true;
    }
}
