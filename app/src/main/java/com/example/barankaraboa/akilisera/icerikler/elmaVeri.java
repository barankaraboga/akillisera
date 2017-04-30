package com.example.barankaraboa.akilisera.icerikler;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.example.barankaraboa.akilisera.Cicekler;
import com.example.barankaraboa.akilisera.MainActivity;
import com.example.barankaraboa.akilisera.R;

/**
 * Created by Baran on 7.05.2016.
 */
public class elmaVeri extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.elma);


    }


    @SuppressWarnings("StatementWithEmptyBody")

    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.anasayfa) {

            Intent i = new Intent(elmaVeri.this,MainActivity.class);
            startActivity(i);

        } else if (id == R.id.cicekler) {

            Intent i = new Intent(elmaVeri.this,Cicekler.class);
            startActivity(i);

        } else if (id == R.id.agaclar) {

        } else if (id == R.id.hakkimda) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
