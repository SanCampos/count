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
import com.santinocampos.android.count.Views.AddItemFragment;
import com.santinocampos.android.count.Views.ChangeFragment;
import com.santinocampos.android.count.Views.ItemListFragment;
import com.santinocampos.android.count.Views.WalletFragment;

public class CounterActivity extends AppCompatActivity implements WalletFragment.Callbacks, DialogListener {

    private final static String DIALOG_ADD = "DialogAdd";

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
        fm.beginTransaction().add(new AddItemFragment(), DIALOG_ADD).commit();
    }

    @Override
    public void addMoney(double money) {
        Accountant.get(this).addMoney(money);
    }

    private void updateUI() {
        ItemListFragment ilf = (ItemListFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_container_second);
        ChangeFragment cf = (ChangeFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_container_third);
        ilf.updateUI();
        cf.setChange();
    }

    @Override
    public void addItem(Item item, int count) {
        Accountant.get(this).addItem(item, count);
        updateUI();
    }
}
