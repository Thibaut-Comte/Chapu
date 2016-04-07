package org.musee.chapu.themes;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import org.musee.chapu.Accueil;
import org.musee.chapu.MainActivity;
import org.musee.chapu.Plan;
import com.example.musee.R;

public class ImageOeuvre extends Activity implements View.OnClickListener {

    TextView titre;
    ImageButton oeuvre1, oeuvre2, oeuvre3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_oeuvre);
        setupActionBar();

        Bundle bundle = getIntent().getExtras();
        int id = bundle.getInt("id");

        TextView titre = (TextView)findViewById(R.id.titre);
        Typeface face = Typeface.createFromAsset(getAssets(), "Segoe_Print.ttf");
        titre.setTypeface(face);

        titre = (TextView)findViewById(R.id.titre);

        oeuvre1 = (ImageButton)findViewById(R.id.oeuvre1);
        oeuvre1.setOnClickListener(this);

        oeuvre2 = (ImageButton)findViewById(R.id.oeuvre2);
        oeuvre2.setOnClickListener(this);

        oeuvre3 = (ImageButton)findViewById(R.id.oeuvre3);
        oeuvre3.setOnClickListener(this);

        //On vérifie la valeur retourner par ListeTheme (0 = mythologie, 1 = portrait, 2 = grands hommes)
        if (id == 0){
            titre.setText(R.string.mytho);
            oeuvre1.setImageResource(R.drawable.icone_clytie);
            oeuvre2.setImageResource(R.drawable.icone_pluton);
            oeuvre3.setImageResource(R.drawable.icone_proserpine);
        } else if (id == 1) {
            titre.setText(R.string.portrait);
        } else if (id == 2) {
            titre.setText(R.string.grandsHommes);
        } else if (id == 3) {
            titre.setText(R.string.jeunesse);
        } else if (id == 4) {
            titre.setText(R.string.beaux_arts);
        } else if (id == 5) {
            titre.setText(R.string.commandes);
        }
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
                finish();
                Intent accueil = new Intent(this, Accueil.class);
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
        Bundle bundle = getIntent().getExtras();
        int id = bundle.getInt("id");
        Intent suite = new Intent(this, PageOeuvre.class);

        //Gestion du premier thème (la Mythologie)
        if (v==oeuvre1 && id == 0){
            suite.putExtra("imgPos", 0);
            startActivity(suite);
        } else if (v == oeuvre2 && id == 0){
            suite.putExtra("imgPos", 1);
            startActivity(suite);
        } else if (v == oeuvre3 && id == 0){
            suite.putExtra("imgPos", 2);
            startActivity(suite);
        }
        //Gestion du second thème (Le portrait)
        else if (v == oeuvre1 && id == 1){
            suite.putExtra("imgPos", 3);
            startActivity(suite);
        } else if (v == oeuvre2 && id == 1){
            suite.putExtra("imgPos", 4);
            startActivity(suite);
        } else if (v == oeuvre3 && id == 1){
            suite.putExtra("imgPos", 5);
            startActivity(suite);
        }
        //Gestion du troisième thème (Les grands hommes)
        else if (v == oeuvre1 && id == 2){
            suite.putExtra("imgPos", 6);
            startActivity(suite);
        } else if (v == oeuvre2 && id == 2){
            suite.putExtra("imgPos", 7);
            startActivity(suite);
        } else if (v == oeuvre3 && id == 2){
            suite.putExtra("imgPos", 8);
            startActivity(suite);
        }
        //Gestion du quatrième thème (Sa jeunesse)
        else if (v == oeuvre1 && id == 3){
            suite.putExtra("imgPos", 9);
            startActivity(suite);
        } else if (v == oeuvre2 && id == 3){
            suite.putExtra("imgPos", 10);
            startActivity(suite);
        } else if (v == oeuvre3 && id == 3){
            suite.putExtra("imgPos", 11);
            startActivity(suite);
        }
        //Gestion du cinquième thème (Les beaux arts)
        else if (v == oeuvre1 && id == 4){
            suite.putExtra("imgPos", 12);
            startActivity(suite);
        } else if (v == oeuvre2 && id == 4){
            suite.putExtra("imgPos", 13);
            startActivity(suite);
        } else if (v == oeuvre3 && id == 4){
            suite.putExtra("imgPos", 14);
            startActivity(suite);
        }
        //Gestion du sixième thème (Les commandes princières)
        else if (v == oeuvre1 && id == 5){
            suite.putExtra("imgPos", 15);
            startActivity(suite);
        } else if (v == oeuvre2 && id == 5){
            suite.putExtra("imgPos", 16);
            startActivity(suite);
        } else if (v == oeuvre3 && id == 5){
            suite.putExtra("imgPos", 17);
            startActivity(suite);
        }
    }
}
