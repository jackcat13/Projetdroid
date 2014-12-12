package src.projetandroid;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import model.DBHelper;


public class ModifierIconeActivity extends Activity {

    private EditText modifNomIconeEditText;
    private Button modifIconeButton;
    private Button supprimerIconeButton;

    private int idIcone;
    private DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modifier_icone);

        setTitle("Modification icône");

        Bundle b = getIntent().getExtras();
        idIcone = b.getInt("idIcone");

        modifNomIconeEditText = (EditText) findViewById(R.id.modifNomIconeEditText);
        modifIconeButton = (Button) findViewById(R.id.modifIconeButton);
        supprimerIconeButton = (Button) findViewById(R.id.supprimerIconeButton);

        db = new DBHelper(this);

        Cursor unBouton = db.selectUnBoutonQuery(idIcone);
        unBouton.moveToFirst();
        modifNomIconeEditText.setHint( unBouton.getString(unBouton.getColumnIndex("NOMBOUTON")) );

        modifIconeButton.setOnClickListener(modifIconeListener);
        supprimerIconeButton.setOnClickListener(supprimerIconeListener);
    }

    private View.OnClickListener modifIconeListener = new View.OnClickListener() {
        public void onClick(View v) {

            if ( !modifNomIconeEditText.equals("") ) {
                db.updateBoutonQuery(idIcone, modifNomIconeEditText.getText() + "");
                Intent intentView = new Intent(ModifierIconeActivity.this, ViewIconesActivity.class);
                startActivity(intentView);
                finish();
            }
        }
    };

    private View.OnClickListener supprimerIconeListener = new View.OnClickListener() {
        public void onClick(View v) {

            db.deleteBoutonQuery(idIcone);
            Intent intentView = new Intent(ModifierIconeActivity.this, ViewIconesActivity.class);
            startActivity(intentView);
            finish();
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_modifier_icone, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onBackPressed(){
        Intent intentView = new Intent(this, ViewIconesActivity.class);
        startActivity(intentView);
        finish();
    }
}
