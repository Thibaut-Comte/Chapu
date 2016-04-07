package org.musee.chapu.biographie;

import android.content.Intent;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import org.musee.chapu.Accueil;
import org.musee.chapu.MainActivity;
import org.musee.chapu.Plan;
import com.example.musee.R;

public class Details extends Activity implements View.OnClickListener {

    Button retour;
    MediaPlayer mp;
    RelativeLayout relative;
    int detail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bio_details);
        setupActionBar();

        Bundle bundle = getIntent().getExtras();
        detail = bundle.getInt("detail");

        Typeface face = Typeface.createFromAsset(getAssets(), "Segoe_Print.ttf");

        relative = (RelativeLayout)findViewById(R.id.relative);

        retour = (Button)findViewById(R.id.retour);
        retour.setOnClickListener(this);
        retour.setTypeface(face);

        playTheSound(sons[detail]);

        if (detail == 0) {
            relative.setBackgroundResource(R.drawable.paris19);

        } else if (detail == 1) {
            relative.setBackgroundResource(R.drawable.henridorleans);

        } else if (detail == 2) {
            relative.setBackgroundResource(R.drawable.beauxarts);

        } else if (detail == 3) {
            relative.setBackgroundResource(R.drawable.jeanne);

        }

    }

    //Création du menu
    public boolean onCreateOptionsMenu(Menu menu) {
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
                playTheSound(sons[detail]);
                stopTheSound();
                finish();
                return true;
            case R.id.accueil:
                //Appuyer sur le bouton Accueil
                playTheSound(sons[detail]);
                stopTheSound();
                Intent accueil = new Intent(this, Accueil.class);
                startActivity(accueil);
                return true;
            case R.id.profil:
                playTheSound(sons[detail]);
                stopTheSound();
                Intent profil = new Intent(this, MainActivity.class);
                startActivity(profil);
                return true;
            case R.id.plan:
                playTheSound(sons[detail]);
                stopTheSound();
                Intent plan = new Intent(this, Plan.class);
                startActivity(plan);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public Integer[] sons = {
            R.raw.test_son, R.raw.le_christ_aux_anges,
            R.raw.la_delivrance_de_melun, R.raw.jeanne_darc_ecoutant_ses_voix
    };

    //Va permettre de jouer le son
    protected void playTheSound(int son) {
        if (mp != null) {
            mp.reset();
            mp.release();
        }
        mp = MediaPlayer.create(this, son);
        mp.start();
    }

    //Va stopper le son en cours
    protected void stopTheSound(){
        mp.stop();
    }


    @Override
    public void onClick(View v) {
        if (v==retour){
            playTheSound(sons[detail]);
            stopTheSound();
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        playTheSound(sons[detail]);
        stopTheSound();
        super.onBackPressed();
    }
}
