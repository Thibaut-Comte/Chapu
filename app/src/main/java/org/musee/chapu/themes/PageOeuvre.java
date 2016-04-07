package org.musee.chapu.themes;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.musee.chapu.Accueil;
import org.musee.chapu.MainActivity;
import org.musee.chapu.Plan;
import com.example.musee.R;

public class PageOeuvre extends Activity implements View.OnClickListener {

    ImageView img;
    Button audio, stop;
    private MediaPlayer mp;
    TextView titre;
    int imgPos;

    // Tenir une référence à l'animateur actuel,
    // Sorte qu'il peut être interrompue à mi-chemin.
    private Animator mCurrentAnimator;
    // Le système de durée de temps d'animation "court", en millisecondes.  Ce
    // Durée est idéal pour les animations subtiles ou des animations qui se produisent
    // Très fréquemment.
    private int mShortAnimationDuration;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_oeuvre);
        setupActionBar();

        Typeface face = Typeface.createFromAsset(getAssets(), "Segoe_Print.ttf");

        img = (ImageView)findViewById(R.id.imgBio);
        titre = (TextView)findViewById(R.id.titre);

        Bundle bundle = getIntent().getExtras();
        imgPos = bundle.getInt("imgPos");

        audio=(Button)findViewById(R.id.audio);
        audio.setOnClickListener(this);
        audio.setTypeface(face);

        stop = (Button)findViewById(R.id.stop);
        stop.setOnClickListener(this);
        stop.setTypeface(face);

        img.setImageResource(mThumbIds[imgPos]);
        titre.setText(titres[imgPos]);
        titre.setTypeface(face);


        audio.setVisibility(View.INVISIBLE);
        stop.setVisibility(View.INVISIBLE);

        if(audioExist()){
            playTheSound(imgPos);
            stopTheSound();
            audio.setVisibility(View.VISIBLE);
        }


        final View thumb1View = findViewById(R.id.imgBio);
        thumb1View.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                zoomImageFromThumb(thumb1View, mThumbIds[imgPos]);
            }
        });

        mShortAnimationDuration = getResources().getInteger(
                android.R.integer.config_shortAnimTime);


    }

    private void zoomImageFromThumb(final View thumbView, int imageResId) {
        // If there's an animation in progress, cancel it
        // immediately and proceed with this one.
        if (mCurrentAnimator != null) {
            mCurrentAnimator.cancel();
        }

        // Load the high-resolution "zoomed-in" image.
        final ImageView expandedImageView = (ImageView) findViewById(
                R.id.expanded_image);
        expandedImageView.setImageResource(imageResId);

        // Calculate the starting and ending bounds for the zoomed-in image.
        // This step involves lots of math. Yay, math.
        final Rect startBounds = new Rect();
        final Rect finalBounds = new Rect();
        final Point globalOffset = new Point();

        // The start bounds are the global visible rectangle of the thumbnail,
        // and the final bounds are the global visible rectangle of the container
        // view. Also set the container view's offset as the origin for the
        // bounds, since that's the origin for the positioning animation
        // properties (X, Y).
        thumbView.getGlobalVisibleRect(startBounds);
        findViewById(R.id.cxw)
                .getGlobalVisibleRect(finalBounds, globalOffset);
        startBounds.offset(-globalOffset.x, -globalOffset.y);
        finalBounds.offset(-globalOffset.x, -globalOffset.y);

        // Adjust the start bounds to be the same aspect ratio as the final
        // bounds using the "center crop" technique. This prevents undesirable
        // stretching during the animation. Also calculate the start scaling
        // factor (the end scaling factor is always 1.0).
        float startScale;
        if ((float) finalBounds.width() / finalBounds.height()
                > (float) startBounds.width() / startBounds.height()) {
            // Extend start bounds horizontally
            startScale = (float) startBounds.height() / finalBounds.height();
            float startWidth = startScale * finalBounds.width();
            float deltaWidth = (startWidth - startBounds.width()) / 2;
            startBounds.left -= deltaWidth;
            startBounds.right += deltaWidth;
        } else {
            // Extend start bounds vertically
            startScale = (float) startBounds.width() / finalBounds.width();
            float startHeight = startScale * finalBounds.height();
            float deltaHeight = (startHeight - startBounds.height()) / 2;
            startBounds.top -= deltaHeight;
            startBounds.bottom += deltaHeight;
        }

        // Hide the thumbnail and show the zoomed-in view. When the animation
        // begins, it will position the zoomed-in view in the place of the
        // thumbnail.
        thumbView.setAlpha(0f);
        expandedImageView.setVisibility(View.VISIBLE);

        // Set the pivot point for SCALE_X and SCALE_Y transformations
        // to the top-left corner of the zoomed-in view (the default
        // is the center of the view).
        expandedImageView.setPivotX(0f);
        expandedImageView.setPivotY(0f);

        // Construct and run the parallel animation of the four translation and
        // scale properties (X, Y, SCALE_X, and SCALE_Y).
        AnimatorSet set = new AnimatorSet();
        set
                .play(ObjectAnimator.ofFloat(expandedImageView, View.X,
                        startBounds.left, finalBounds.left))
                .with(ObjectAnimator.ofFloat(expandedImageView, View.Y,
                        startBounds.top, finalBounds.top))
                .with(ObjectAnimator.ofFloat(expandedImageView, View.SCALE_X,
                        startScale, 1f)).with(ObjectAnimator.ofFloat(expandedImageView,
                View.SCALE_Y, startScale, 1f));
        set.setDuration(mShortAnimationDuration);
        set.setInterpolator(new DecelerateInterpolator());
        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mCurrentAnimator = null;
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                mCurrentAnimator = null;
            }
        });
        set.start();
        mCurrentAnimator = set;

        // Upon clicking the zoomed-in image, it should zoom back down
        // to the original bounds and show the thumbnail instead of
        // the expanded image.
        final float startScaleFinal = startScale;
        expandedImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mCurrentAnimator != null) {
                    mCurrentAnimator.cancel();
                }

                // Animate the four positioning/sizing properties in parallel,
                // back to their original values.
                AnimatorSet set = new AnimatorSet();
                set.play(ObjectAnimator
                        .ofFloat(expandedImageView, View.X, startBounds.left))
                        .with(ObjectAnimator
                                .ofFloat(expandedImageView,
                                        View.Y,startBounds.top))
                        .with(ObjectAnimator
                                .ofFloat(expandedImageView,
                                        View.SCALE_X, startScaleFinal))
                        .with(ObjectAnimator
                                .ofFloat(expandedImageView,
                                        View.SCALE_Y, startScaleFinal));
                set.setDuration(mShortAnimationDuration);
                set.setInterpolator(new DecelerateInterpolator());
                set.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        thumbView.setAlpha(1f);
                        expandedImageView.setVisibility(View.GONE);
                        mCurrentAnimator = null;
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {
                        thumbView.setAlpha(1f);
                        expandedImageView.setVisibility(View.GONE);
                        mCurrentAnimator = null;
                    }
                });
                set.start();
                mCurrentAnimator = set;
            }
        });
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
                if (audioExist()){
                    playTheSound(imgPos);
                    stopTheSound();
                }
                finish();
                return true;
            case R.id.accueil:
                //Appuyer sur le bouton Accueil
                Intent accueil = new Intent(PageOeuvre.this, Accueil.class);
                startActivity(accueil);
                finish();
                if (audioExist()){
                    playTheSound(imgPos);
                    stopTheSound();
                }
                return true;
            case R.id.profil:
                playTheSound(imgPos);
                stopTheSound();
                Intent profil = new Intent(this, MainActivity.class);
                startActivity(profil);
                return true;

            case R.id.plan:
                finish();
                Intent plan = new Intent(this, Plan.class);
                startActivity(plan);
                if (audioExist()){
                    playTheSound(imgPos);
                    stopTheSound();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    //Les primières images auront un son
    private boolean audioExist(){
        if (imgPos<sons.length) {
            return true;
        }
        return false;
    }


    // references to our images
    public Integer[] mThumbIds = {
            R.drawable.clytie, R.drawable.pluton,
            R.drawable.proserpine, R.drawable.image_4,
            R.drawable.image_5, R.drawable.image_6,
            R.drawable.image_7, R.drawable.image_8,
            R.drawable.image_13, R.drawable.clytie,
            R.drawable.proserpine, R.drawable.image_4,
            R.drawable.image_5, R.drawable.image_6,
            R.drawable.image_7, R.drawable.image_8,
            R.drawable.pluton, R.drawable.image_13
    };

    //Références aux titres
    private int[] titres = {
            R.string.titre1, R.string.titre2,
            R.string.titre3, R.string.titre4,
            R.string.titre5, R.string.titre6,
            R.string.titre7, R.string.titre8,
            R.string.titre9, R.string.titre1,
            R.string.titre3, R.string.titre4,
            R.string.titre5, R.string.titre6,
            R.string.titre7, R.string.titre8,
            R.string.titre2, R.string.titre9
    };

    //Référence aux sons
    private Integer[] sons = {
            R.raw.la_mort_de_la_nymphe_clythie, R.raw.pluton_et_proserpine,
            R.raw.pluton_et_proserpine,R.raw.jeanne_darc_ecoutant_ses_voix,
            R.raw.la_delivrance_de_melun, R.raw.bio,
            R.raw.la_reconnaissance, R.raw.le_christ_aux_anges,
            R.raw.lensemble_de_la_foi_le_courage_et_le_mgr_dupanloup, R.raw.cleobis_et_biton,
            R.raw.remerciements, R.raw.la_mort_de_la_nymphe_clythie,
            R.raw.pluton_et_proserpine, R.raw.pluton_et_proserpine,
            R.raw.jeanne_darc_ecoutant_ses_voix, R.raw.la_delivrance_de_melun,
            R.raw.bio, R.raw.la_reconnaissance,
            R.raw.le_christ_aux_anges, R.raw.lensemble_de_la_foi_le_courage_et_le_mgr_dupanloup,
            R.raw.cleobis_et_biton, R.raw.remerciements
    };

    //Va permettre de jouer le son
    protected void playTheSound(int position) {
        if (mp != null) {
            mp.reset();
            mp.release();
        }
        mp = MediaPlayer.create(this, sons[position]);
        mp.start();
    }

    //Va stopper le son en cours
    protected void stopTheSound(){
        mp.stop();
    }

    @Override
    public void onClick(View v) {

        //En appuyant sur le bouton audio. Le bouton audio va se masquer et le bouton stop s'affichera
        if (v == audio) {
            playTheSound(imgPos);
            stop.setVisibility(View.VISIBLE);
            audio.setVisibility(View.INVISIBLE);

            //Pareil que pour le bouton audio en inversant les rôles
        }

        if (v == stop) {
            playTheSound(imgPos);
            stopTheSound();
            stop.setVisibility(View.INVISIBLE);
            audio.setVisibility(View.VISIBLE);
        }
    }

    //Gestion du bouton retour de l'appareil
    public void onBackPressed(){
        //Gardera ses fonctions de base
        super.onBackPressed();

        //Executera une action en plus. Ici il lancera le son et le coupera automatiquement
        //Si nous ne faisons que le stop cela ne marchera que si le son est en cours
        if (audioExist()){
            playTheSound(imgPos);
            stopTheSound();
        }
    }
}