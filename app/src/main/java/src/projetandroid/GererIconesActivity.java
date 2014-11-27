package src.projetandroid;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class GererIconesActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gerer_icones);

        Button ajouterIconeButton = (Button) findViewById(R.id.ajouterIconeButton);
        Button modifierIconeButton = (Button) findViewById(R.id.modifierIconeButton);
        Button supprimerIconeButton = (Button) findViewById(R.id.supprimerIconeButton);

        ajouterIconeButton.setOnClickListener(ajouterIconeListener);
        modifierIconeButton.setOnClickListener(modifierIconeListener);
        supprimerIconeButton.setOnClickListener(supprimerIconeListener);
    }

    private View.OnClickListener ajouterIconeListener = new View.OnClickListener() {
        public void onClick(View v) {
            Intent intentAjouterIcone = new Intent(GererIconesActivity.this, AjouterIconeActivity.class);
            startActivity(intentAjouterIcone);
        }
    };

    private View.OnClickListener modifierIconeListener = new View.OnClickListener() {
        public void onClick(View v) {
            Intent intentModifierIcone = new Intent(GererIconesActivity.this, ModifierIconeActivity.class);
            startActivity(intentModifierIcone);
        }
    };

    private View.OnClickListener supprimerIconeListener = new View.OnClickListener() {
        public void onClick(View v) {
            Intent intentSupprimerIcone = new Intent(GererIconesActivity.this, SupprimerIconeActivity.class);
            startActivity(intentSupprimerIcone);
        }
    };


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_gerer_icones, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        /*if (id == R.id.action_settings) {
            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }
}
