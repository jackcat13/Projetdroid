package src.projetandroid;

import android.app.Activity;
import android.app.AlertDialog;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.GridLayout;

import model.DBHelper;


public class SectionPageActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_section_page);

        Bundle bun = getIntent().getExtras();
        int idPage = bun.getInt("idPage");
        String nomPage = bun.getString("nomPage");

        setTitle(nomPage);

        GridLayout gl = (GridLayout) findViewById(R.id.gridLayoutContent);

        DBHelper db = new DBHelper(this);

        int i = 0;
        Cursor buttons = db.selectBoutonQuery(idPage);
        buttons.moveToFirst();
        while(!buttons.isAfterLast()) {

            Button b = new Button(this);
            String nomBouton = buttons.getString(buttons.getColumnIndex("NOMBOUTON"));
            b.setId( Integer.valueOf(buttons.getString(buttons.getColumnIndex("IDBOUTON"))) );
            b.setText( nomBouton );
            b.setPadding(20,20,20,20);

            gl.addView(b);

            buttons.moveToNext();
        }
        buttons.close();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_section_page, menu);
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
