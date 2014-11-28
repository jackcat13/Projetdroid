package src.projetandroid;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import model.DBHelper;


public class ModifierIconeActivity extends Activity {

    private EditText modifNomIconeEditText;
    private Spinner modifPageIconeSpinner;
    private Button creerIconeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modifier_icone);

        Bundle b = getIntent().getExtras();
        int idPage = b.getInt("idIcone");

        modifNomIconeEditText = (EditText) findViewById(R.id.modifNomIconeEditText);
        creerIconeButton = (Button) findViewById(R.id.creerIconeButton);

        DBHelper db = new DBHelper(this);

        Cursor unBouton = db.selectUnBoutonQuery(idPage);
        unBouton.moveToFirst();
        modifNomIconeEditText.setHint( unBouton.getString(unBouton.getColumnIndex("NOMBOUTON")) );



    }


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
}
