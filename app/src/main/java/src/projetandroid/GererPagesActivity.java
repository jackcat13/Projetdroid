package src.projetandroid;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class GererPagesActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gerer_pages);

        Button ajouterPageButton = (Button) findViewById(R.id.ajouterPageButton);
        Button modifierPageButton = (Button) findViewById(R.id.modifierPageButton);
        Button supprimerPageButton = (Button) findViewById(R.id.supprimerPageButton);

        ajouterPageButton.setOnClickListener(ajouterPageListener);
        modifierPageButton.setOnClickListener(modifierPageListener);
        supprimerPageButton.setOnClickListener(supprimerPageListener);
    }

    private View.OnClickListener ajouterPageListener = new View.OnClickListener() {
        public void onClick(View v) {
                Intent intentGererPages = new Intent(GererPagesActivity.this, AjouterPageActivity.class);
                startActivity(intentGererPages);
            }
        };

        private View.OnClickListener modifierPageListener = new View.OnClickListener() {
            public void onClick(View v) {
                Intent intentGererPages = new Intent(GererPagesActivity.this, ModifierPageActivity.class);
                startActivity(intentGererPages);
            }
        };

        private View.OnClickListener supprimerPageListener = new View.OnClickListener() {
            public void onClick(View v) {
                Intent intentGererPages = new Intent(GererPagesActivity.this, SupprimerPageActivity.class);
                startActivity(intentGererPages);
            }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_gerer_pages, menu);
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
