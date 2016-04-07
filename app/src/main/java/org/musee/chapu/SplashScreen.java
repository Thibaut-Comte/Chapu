package org.musee.chapu;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.app.Activity;
import android.os.Handler;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.musee.R;

public class SplashScreen extends Activity {

    public static int id, init;
    TextView titre;
    ImageView fond;

    private static int SPLASH_TIME_OUT = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);

        //On va stocker la valeur de la visite pour pouvoir la récupérée à l'accueil à tout moment (grace au fait qu'elle est déclarée static)
        Bundle bundle = getIntent().getExtras();
        init = bundle.getInt("id");
        setId(init);
        Log.i("idClass", "" + id);

        titre = (TextView)findViewById(R.id.titre);
        fond = (ImageView)findViewById(R.id.fond);
        Typeface face = Typeface.createFromAsset(getAssets(), "Segoe_Print.ttf");

        titre.setTypeface(face);

        if (id == 1){
            titre.setText(R.string.welcome_decouv);
            fond.setImageResource(R.drawable.chapus_pense);
        } else if (id == 2){
            titre.setText(R.string.welcome_expert);
            fond.setImageResource(R.drawable.chapus_face);
        }


        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
                Intent i = new Intent(SplashScreen.this, Accueil.class);
                startActivity(i);

                // close this activity
                finish();
            }
        }, SPLASH_TIME_OUT);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}