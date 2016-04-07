package org.musee.chapu;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.musee.R;

import org.musee.chapu.biographie.ReglesBio;
import org.musee.chapu.scan.IntentIntegrator;
import org.musee.chapu.scan.IntentResult;
import org.musee.chapu.themes.ListeThemes;
import org.musee.chapu.themes.ListeThemesExpert;
import org.musee.chapu.themes.PageOeuvre;

public class Accueil extends Activity implements View.OnClickListener {

    ImageButton btScan, btListe, btBio, btJeanne;
    TextView mode;
    int id;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Récuperation du Layout
        setContentView(R.layout.activity_accueil);
        setupActionBar();

        //Récupération de l'id de visite (1 = découverte, 2 = experte) afin de toujours savoir quoi afficher
        id = SplashScreen.id;


        mode = (TextView)findViewById(R.id.mode);

        // Récupération du bouton "Scan" du layout courant
        btScan = (ImageButton)findViewById(R.id.BtScan);
        // On définit l'evenement click sur le bouton
        btScan.setOnClickListener(this);

        // Récupération du bouton "Liste" du layout courant
        btListe = (ImageButton)findViewById(R.id.BtListe);
        // On définit l'evenement click sur le bouton
        btListe.setOnClickListener(this);

        //Récupération du bouton "bio" du Layout courant
        btBio = (ImageButton)findViewById(R.id.bio);
        //On définit l'évenement click sur le bouton
        btBio.setOnClickListener(this);

        btJeanne = (ImageButton)findViewById(R.id.jeanne);
        btJeanne.setOnClickListener(this);

        if (id == 1) {
            mode.setText("Mode découverte");
            btScan.setImageResource(R.drawable.icone_code_decouverte);
            btBio.setImageResource(R.drawable.icone_bio_decouverte);
        } else if (id == 2) {
            mode.setText("Mode expert");
            btScan.setImageResource(R.drawable.icone_code_experte);
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

    /*
     * Gestion du résultat du scan
     */
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch (requestCode)
        {
            case IntentIntegrator.REQUEST_CODE:
                if (resultCode == RESULT_OK)
                {
                    IntentResult scanResult = IntentIntegrator.parseActivityResult
                            (
                                    requestCode,
                                    resultCode,
                                    data
                            );

                    if (scanResult != null) {
                        String out = scanResult.getContents();

                        if (out != null) {

                            // Envoi du resultat et affichage de la page details
                            Intent page = new Intent(Accueil.this, PageOeuvre.class);

                            int scan;

                            try {
                                scan = Integer.parseInt(out);
                            }
                            catch (Exception e){
                                Toast.makeText(this, "Qr Code non valide, ressayez.", Toast.LENGTH_SHORT).show();
                                break;
                            }

                            if (id == 1) {
                                //Gestion mode découverte
                                if (scan > 0 && scan <= 8) {
                                    Toast.makeText(this, "Désolé, ceci n'est pas ton Qr Code.", Toast.LENGTH_SHORT).show();
                                    break;
                                } else if (scan >= 100 && scan <=118) { // Si le code scanner n'est pas le bon
                                    page.putExtra("imgPos", scan - 100);
                                    startActivity(page);
                                } else { //Si le résultat est un int mais pas de la bonne valeur
                                    Toast.makeText(this, "Œuvre non existante", Toast.LENGTH_SHORT).show();
                                    break;
                                }

                            } else if (id == 2) {
                                //Gestion du mode expert
                                if (scan >= 0 && scan <= 17) {
                                    page.putExtra("imgPos", scan);
                                    startActivity(page);
                                } else if (scan > 100 && scan <= 118) { // Si le code scanner n'est pas le bon
                                    Toast.makeText(this, "Désolé, ceci n'est pas ton Qr Code.", Toast.LENGTH_SHORT).show();
                                    break;
                                }
                                else { //Si le résultat est un int mais pas de la bonne valeur
                                    Toast.makeText(this, "Œuvre non existante", Toast.LENGTH_SHORT).show();
                                    break;
                                }
                            }
                        }
                    }

                }
        }
    }


    public void setId(int id) {
        this.id = id;
    }

    @Override
    public void onClick(View v) {
        if (v == btBio){
            Intent bio = new Intent(Accueil.this, ReglesBio.class);
            bio.putExtra("id", id);
            startActivity(bio);
        }
        else if (v == btListe){
            if (id == 1){
                Intent liste = new Intent(Accueil.this, ListeThemes.class);
                liste.putExtra("id", id);
                startActivity(liste);
            } else if (id == 2){
                Intent liste = new Intent(Accueil.this, ListeThemesExpert.class);
                liste.putExtra("id", id);
                startActivity(liste);
            }
        }
        else if (v == btScan){
            // Initialisation du scan
            IntentIntegrator.initiateScan(Accueil.this);
        } else if (v == btJeanne){
            Toast.makeText(this, "Section à venir !", Toast.LENGTH_SHORT).show();
        }

    }

    //Gestion du bouton retour de l'appareil
    public void onBackPressed(){
        Intent profil = new Intent(this, MainActivity.class);
        startActivity(profil);
    }

}