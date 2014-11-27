package src.projetandroid;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import model.DBHelper;


public class AjouterIconeActivity extends Activity {

    private EditText nomIconeEditText;
    private Spinner pageSelectionSpinner;
    private Button creerIconeButton;

    private DBHelper db;
    private ArrayList<String> idPages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajouter_icone);

        db = new DBHelper(this);

        nomIconeEditText = (EditText) findViewById(R.id.ajouterIconeEditText);
        pageSelectionSpinner = (Spinner) findViewById(R.id.pageSelectionSpinner);
        creerIconeButton = (Button) findViewById(R.id.creerIconeButton);

        ArrayList<String> listPages = new ArrayList<String>();
        idPages = new ArrayList<String>();

        Cursor pages = db.selectPageQuery();
        pages.moveToFirst();
        while(!pages.isAfterLast()) {

            ArrayList<String> page = new ArrayList<String>();
            idPages.add( pages.getString(pages.getColumnIndex("IDPAGE")) );
            listPages.add( pages.getString(pages.getColumnIndex("NOMPAGE")) );

            pages.moveToNext();
        }
        pages.close();

        //Objet permettant l'insertion de listes dans un spinner
        ArrayAdapter<String> adp= new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,listPages);
        adp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Insertion des pages dans le spinner
        pageSelectionSpinner.setAdapter(adp);

        creerIconeButton.setOnClickListener(creerIconeListener);
    }

    private View.OnClickListener creerIconeListener = new View.OnClickListener() {
        public void onClick(View v) {
            if ( !nomIconeEditText.equals("") ){
                int idPage = Integer.valueOf( idPages.get(pageSelectionSpinner.getSelectedItemPosition()) );
                db.insertBoutonQuery( idPage, ""+nomIconeEditText.getText() );

                finish();
            }
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_ajouter_icone, menu);
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
