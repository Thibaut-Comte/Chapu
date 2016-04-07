package org.musee.chapu;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.musee.R;

public class Plan extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan);
        setupActionBar();
    }

    //Création du menu
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    private void setupActionBar () {
        getActionBar().setDisplayHomeAsUpEnabled(true);
    }

    //Gestion du menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                //Appuyer sur le bouton Retour du menu
                finish();
                return true;
            case R.id.accueil:
                //Appuyer sur le bouton Accueil
                Intent accueil = new Intent(this, Accueil.class);
                startActivity(accueil);
                return true;
            case R.id.profil:
                Intent profil = new Intent(this, MainActivity.class);
                startActivity(profil);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}