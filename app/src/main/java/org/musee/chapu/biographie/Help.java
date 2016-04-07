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

public class Help extends Activity implements View.OnClickListener {

    Button retour;
    TextView help, help2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        setupActionBar();

        Typeface face = Typeface.createFromAsset(getAssets(), "Segoe_Print.ttf");


        retour = (Button)findViewById(R.id.retour);
        retour.setOnClickListener(this);
        retour.setTypeface(face);

        help = (TextView)findViewById(R.id.help_texte);
        help.setText(R.string.help);
        help.setTypeface(face);

        help2 = (TextView)findViewById(R.id.help_texte2);
        help2.setText(R.string.help2);
        help2.setTypeface(face);

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
        if (v==retour){
            finish();
        }
    }
}
