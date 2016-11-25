package com.santinocampos.android.count.Controllers;

import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.santinocampos.android.count.Listeners.DialogListener;
import com.santinocampos.android.count.Models.Accountant;
import com.santinocampos.android.count.Models.Item;
import com.santinocampos.android.count.R;
import com.santinocampos.android.count.Dialogs.AddItemFragment;
import com.santinocampos.android.count.Dialogs.AddMoneyFragment;
import com.santinocampos.android.count.ViewFragments.ChangeFragment;
import com.santinocampos.android.count.ViewFragments.ItemListFragment;
import com.santinocampos.android.count.ViewFragments.WalletFragment;

public class CounterActivity extends AppCompatActivity implements WalletFragment.Callbacks, DialogListener {

    private final static String DIALOG_ADD_ITEM = "DialogAddItem";
    private final static String DIALOG_ADD_MONEY = "DialogAddMoney";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_counter);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (savedInstanceState == null) {
            FragmentManager fm = getSupportFragmentManager();

            WalletFragment walletFragment = new WalletFragment();
            ItemListFragment itemListFragment = new ItemListFragment();
            ChangeFragment changeFragment = new ChangeFragment();

            FragmentTransaction ft = fm.beginTransaction();
            ft.add(R.id.fragment_container_first, walletFragment);
            ft.add(R.id.fragment_container_second, itemListFragment);
            ft.add(R.id.fragment_container_third, changeFragment);
            ft.commit();
        }
    }

    @Override
    public void addItem() {
        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction().add(new AddItemFragment(), DIALOG_ADD_ITEM).commit();
    }

    @Override
    public void addMoney(double money) {
        Accountant.get(this).addMoney(money);
        updateUI();
    }

    @Override
    public void addMoney() {
        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction().add(new AddMoneyFragment(), DIALOG_ADD_MONEY).commit();
    }

    private void updateUI() {
        WalletFragment wf = (WalletFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_container_first);
        ItemListFragment ilf = (ItemListFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_container_second);
        ChangeFragment cf = (ChangeFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_container_third);
        wf.update();
        ilf.update();
        cf.update();
    }

    @Override
    public void addItem(Item item, int count) {
        Accountant.get(this).addItem(item, count);
        updateUI();
    }
}
