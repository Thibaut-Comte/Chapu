package org.musee.chapu;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.musee.R;

public class MainActivity extends Activity implements View.OnClickListener {

	Button btDecouverte, btExperte;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		TextView titre = (TextView)findViewById(R.id.titre);
		Typeface face = Typeface.createFromAsset(getAssets(), "Segoe_Print.ttf");
		titre.setTypeface(face);

		btDecouverte = (Button)findViewById(R.id.btDecouverte);
		btDecouverte.setOnClickListener(this);
		btDecouverte.setTypeface(face);

		btExperte = (Button)findViewById(R.id.btExperte);
		btExperte.setOnClickListener(this);
		btExperte.setTypeface(face);


	}

	@Override
	public void onClick(View v) {
		Intent id = new Intent(this, SplashScreen.class);

		if(v == btDecouverte){
			id.putExtra("id", 1);
			startActivity(id);
		}
		if (v==btExperte){
			id.putExtra("id", 2);
			startActivity(id);
		}

	}
}
