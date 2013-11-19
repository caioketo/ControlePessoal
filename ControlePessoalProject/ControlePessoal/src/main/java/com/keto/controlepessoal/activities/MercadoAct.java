package com.keto.controlepessoal.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.keto.controlepessoal.R;
import com.keto.controlepessoal.activities.fragments.ComprasListFragment;
import com.keto.controlepessoal.activities.fragments.LocaisListFragment;
import com.keto.controlepessoal.activities.fragments.ProdutoListFragment;

public class MercadoAct extends ActionBarActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    private int SelectedItem = 1;
    private Fragment CurrFrag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produtos);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        SelectedItem = position + 1;
        if (position == 0) {
            CurrFrag = ProdutoListFragment.newInstance(position + 1);
        }
        else if (position == 1) {
            CurrFrag = ComprasListFragment.newInstance(position + 1);
        }
        else if (position == 2) {
            CurrFrag = LocaisListFragment.newInstance(position + 1);
        }
        fragmentManager.beginTransaction()
                .replace(R.id.container, CurrFrag)
                .commit();
    }

    public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                mTitle = getString(R.string.title_produtos);
                break;
            case 2:
                mTitle = getString(R.string.title_compras);
                break;
            case 3:
                mTitle = getString(R.string.title_locais);
                break;
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            CurrFrag.onCreateOptionsMenu(menu, getMenuInflater());
            //getMenuInflater().inflate(R.menu.compras, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return CurrFrag.onOptionsItemSelected(item);
    }

}
