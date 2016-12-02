package com.santinocampos.android.count.Controllers;

import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.santinocampos.android.count.Listeners.DialogListener;
import com.santinocampos.android.count.Models.Accountant;
import com.santinocampos.android.count.Models.Item;
import com.santinocampos.android.count.R;
import com.santinocampos.android.count.Dialogs.AddItemDialog;
import com.santinocampos.android.count.Dialogs.AddMoneyDialog;
import com.santinocampos.android.count.ViewFragments.ItemListFragment;

public class CounterActivity extends AppCompatActivity implements DialogListener {

    private final static String DIALOG_ADD_ITEM = "DialogAddItem";
    private final static String DIALOG_ADD_MONEY = "DialogAddMoney";

    private Button mWalletButton;
    private Button mChangeButton;

    private Accountant mAccountant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_counter);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (savedInstanceState == null) {
            FragmentManager fm = getSupportFragmentManager();

            ItemListFragment itemListFragment = new ItemListFragment();

            fm.beginTransaction()
                    .add(R.id.fragment_container_second, itemListFragment)
                    .commit();
        }
        mAccountant = Accountant.get(this);

        mWalletButton =  (Button) findViewById(R.id.wallet_totalMoney);
        mChangeButton =  (Button) findViewById(R.id.wallet_totalChange);

        updateUI();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_addItem);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startDialog(new AddItemDialog());
            }
        });
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
        ItemListFragment ilf = (ItemListFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_container_second);
        ilf.update();

        mWalletButton.setText(String.valueOf(Math.round(mAccountant.getTotalMoney())));
        mChangeButton.setText(String.valueOf(Math.round(mAccountant.getChange())));
    }
}
