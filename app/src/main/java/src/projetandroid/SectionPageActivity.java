package src.projetandroid;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;

import java.util.ArrayList;

import model.DBHelper;

public class SectionPageActivity extends Activity {

    TextView phraseView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_section_page);

        phraseView = (TextView) findViewById(R.id.phraseView);

        Bundle bun = getIntent().getExtras();
        int idPage = bun.getInt("idPage");

        String nomPage = bun.getString("nomPage");

        setTitle(nomPage);

        GridLayout gl = (GridLayout) findViewById(R.id.gridLayoutSectionPage);

        DBHelper db = new DBHelper(this);

        Cursor buttons = db.selectBoutonQuery(idPage);
        buttons.moveToFirst();
        while(!buttons.isAfterLast()) {

            Button b = new Button(this);
            String nomBouton = buttons.getString(buttons.getColumnIndex("NOMBOUTON"));
            b.setId( Integer.valueOf(buttons.getString(buttons.getColumnIndex("IDPAGE"))) );
            b.setText( nomBouton );
            b.setPadding(20,20,20,20);
            b.setOnClickListener(saveWord);

            gl.addView(b);

            buttons.moveToNext();
        }
        buttons.close();
    }

    private View.OnClickListener saveWord = new View.OnClickListener()
    {
        public void onClick(View v)
        {
            Button button = (Button) v;

            ContentActivity.getPhrase().add((String) button.getText());

            finish();
        }
    };

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

    @Override
    protected void onResume()
    {
        super.onResume();
        String str = "";


        for(String e: ContentActivity.getPhrase())
            str += e + " ";

        if (str != "")
            phraseView.setText(str);
    }
}
