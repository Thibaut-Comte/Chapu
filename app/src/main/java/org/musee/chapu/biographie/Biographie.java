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

public class Biographie extends Activity implements View.OnClickListener {

    Button ba, vie, cp, jeanne;
    TextView titre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_biographie);
        setupActionBar();

        titre = (TextView)findViewById(R.id.titre);
        Typeface face = Typeface.createFromAsset(getAssets(), "Segoe_Print.ttf");
        titre.setTypeface(face);

        ba = (Button)findViewById(R.id.ba);
        ba.setOnClickListener(this);
        ba.setTypeface(face);

        vie = (Button)findViewById(R.id.vie);
        vie.setOnClickListener(this);
        vie.setTypeface(face);

        cp = (Button)findViewById(R.id.cp);
        cp.setOnClickListener(this);
        cp.setTypeface(face);

        jeanne = (Button)findViewById(R.id.btJeanne);
        jeanne.setOnClickListener(this);
        jeanne.setTypeface(face);
    }

    //Création du menu
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    private void setupActionBar() {
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
                finish();
                Intent accueil = new Intent(Biographie.this, Accueil.class);
                startActivity(accueil);
                return true;
            case R.id.profil:
                Intent profil = new Intent(this, MainActivity.class);
                startActivity(profil);
                return true;
            case R.id.plan:
                finish();
                Intent plan = new Intent(this, Plan.class);
                startActivity(plan);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    public void onClick(View v) {
        Intent details = new Intent(this, Details.class);
        //Envoi à la page détail un id qui permettra de savoir quoi afficher
        if (v==ba){
            details.putExtra("detail", 0);
            startActivity(details);
        } else if (v == vie) {
            details.putExtra("detail", 1);
            startActivity(details);
        } else if (v == cp) {
            details.putExtra("detail", 2);
            startActivity(details);
        } else if (v == jeanne) {
            details.putExtra("detail", 3);
            startActivity(details);
        }
    }
}
