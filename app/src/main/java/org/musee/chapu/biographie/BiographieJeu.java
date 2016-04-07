package org.musee.chapu.biographie;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import org.musee.chapu.Accueil;
import org.musee.chapu.MainActivity;
import org.musee.chapu.Plan;
import com.example.musee.R;

public class BiographieJeu extends Activity implements View.OnClickListener {

    Button oreilles, mains, nez, yeux, retour, help, help2;
    ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_biographie_jeu);
        setupActionBar();

        Typeface face = Typeface.createFromAsset(getAssets(), "Segoe_Print.ttf");

        img = (ImageView)findViewById(R.id.img);
        img.setOnClickListener(this);

        retour = (Button)findViewById(R.id.retour);
        retour.setOnClickListener(this);
        retour.setTypeface(face);

        help = (Button)findViewById(R.id.help);
        help.setOnClickListener(this);
        help.setTypeface(face);

        help2 = (Button)findViewById(R.id.help2);
        help2.setOnClickListener(this);

        oreilles = (Button)findViewById(R.id.oreilles);
        oreilles.setOnClickListener(this);

        mains = (Button)findViewById(R.id.mains);
        mains.setOnClickListener(this);

        nez = (Button)findViewById(R.id.nez);
        nez.setOnClickListener(this);

        yeux = (Button)findViewById(R.id.yeux);
        yeux.setOnClickListener(this);

        oreilles.setBackgroundColor(Color.TRANSPARENT);
        mains.setBackgroundColor(Color.TRANSPARENT);
        nez.setBackgroundColor(Color.TRANSPARENT);
        yeux.setBackgroundColor(Color.TRANSPARENT);

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
            case R.id.plan:
                Intent plan = new Intent(this, Plan.class);
                startActivity(plan);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onClick(View v) {
        Intent detail = new Intent(this, Details.class);

        if (v==oreilles){
            detail.putExtra("detail", 0);
            startActivity(detail);
        } else if (v==mains){
            detail.putExtra("detail", 1);
            startActivity(detail);
        } else if (v==nez){
            detail.putExtra("detail", 2);
            startActivity(detail);
        } else if (v==yeux){
            detail.putExtra("detail", 3);
            startActivity(detail);
        } else if (v==retour){
            finish();
        } else if (v==help || v==help2){
            Intent help = new Intent(this, Help.class);
            startActivity(help);
        } else if (v==img){
            Toast.makeText(this, "Dommage, essayez encore", Toast.LENGTH_SHORT).show();
        }
    }
}
