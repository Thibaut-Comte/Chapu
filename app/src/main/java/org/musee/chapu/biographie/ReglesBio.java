package org.musee.chapu.biographie;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.musee.chapu.Accueil;
import org.musee.chapu.MainActivity;
import org.musee.chapu.Plan;
import com.example.musee.R;


public class ReglesBio extends Activity implements View.OnClickListener {

    TextView regle1, regle2, regle3, titre;
    Button jouer, sauter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regles_bio);
        setupActionBar();

        Typeface face = Typeface.createFromAsset(getAssets(), "Segoe_Print.ttf");

        regle1 = (TextView) findViewById(R.id.regle1);
        regle1.setText(R.string.regle1);

        regle2 = (TextView) findViewById(R.id.regle2);
        regle2.setText(R.string.regle2);

        regle3 = (TextView) findViewById(R.id.regle3);
        regle3.setText(R.string.regle3);


        titre = (TextView) findViewById(R.id.titre);

        regle1.setTypeface(face);
        regle2.setTypeface(face);
        regle3.setTypeface(face);
        titre.setTypeface(face);

        jouer = (Button) findViewById(R.id.jouer);
        jouer.setOnClickListener(this);
        jouer.setTypeface(face);

        sauter = (Button)findViewById(R.id.sauter);
        sauter.setOnClickListener(this);
        sauter.setTypeface(face);
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
        if (v==jouer){
            Intent bio = new Intent (this, BiographieJeu.class);
            startActivity(bio);
        } else if (v==sauter){
            Intent sauter = new Intent(this, Biographie.class);
            startActivity(sauter);
        }
    }
}
