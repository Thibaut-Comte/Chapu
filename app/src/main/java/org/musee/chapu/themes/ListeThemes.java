package org.musee.chapu.themes;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import org.musee.chapu.Accueil;
import org.musee.chapu.MainActivity;
import org.musee.chapu.Plan;
import com.example.musee.R;

public class ListeThemes extends Activity implements View.OnClickListener {

    ImageButton mythologie, portrait, grandsHommes;
    int id, idHome;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.liste_themes);
        setupActionBar();

        TextView titre = (TextView)findViewById(R.id.titre);
        Typeface face = Typeface.createFromAsset(getAssets(), "Segoe_Print.ttf");
        titre.setTypeface(face);

        mythologie = (ImageButton)findViewById(R.id.mythologie);
        mythologie.setOnClickListener(this);

        portrait = (ImageButton)findViewById(R.id.portrait);
        portrait.setOnClickListener(this);

        grandsHommes = (ImageButton)findViewById(R.id.grandsHommes);
        grandsHommes.setOnClickListener(this);
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
                Bundle bundle = getIntent().getExtras();
                id = bundle.getInt("id");
                Intent accueil = new Intent(this, Accueil.class);
                accueil.putExtra("id", id);
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
        Bundle bundle = getIntent().getExtras();
        idHome = bundle.getInt("id");
        if (v==mythologie){
            Intent mythologie = new Intent(this, ImageOeuvre.class);
            mythologie.putExtra("id", 0);
            mythologie.putExtra("idHome", idHome);
            startActivity(mythologie);
        }
        else if (v==portrait){
            Intent portrait = new Intent(this, ImageOeuvre.class);
            portrait.putExtra("id", 1);
            portrait.putExtra("idHome", idHome);
            startActivity(portrait);
        }
        else if (v==grandsHommes){
            Intent grandsHommes = new Intent(this, ImageOeuvre.class);
            grandsHommes.putExtra("id", 2);
            grandsHommes.putExtra("idHome", idHome);
            startActivity(grandsHommes);
        }

    }
}