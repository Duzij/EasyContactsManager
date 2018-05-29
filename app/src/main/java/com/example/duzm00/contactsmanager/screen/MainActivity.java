package com.example.duzm00.contactsmanager.screen;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.duzm00.contactsmanager.R;

public class MainActivity extends AppCompatActivity implements ContactListFragment.OnContactListFragmentIntegrationListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setUpListFragment();

    }

    private void setUpListFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frameContainer, new ContactListFragment())
                .commit();
    }

    @Override
    public void onCreateContact() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frameContainer, new AddContactFragment())
                .addToBackStack(null)
                .commit();
    }
}
