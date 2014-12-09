package src.projetandroid;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Locale;

import model.DBHelper;

public class SectionPageActivity extends Activity {

    static TextView phraseView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_section_page);

        phraseView = (TextView) findViewById(R.id.phraseView);
        phraseView.setOnClickListener(speak);

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

        Button bResetPhrase = (Button) findViewById(R.id.resetPhraseButton);
        bResetPhrase.setOnClickListener(resetPhrase);

        Button bDelLastWord = (Button) findViewById(R.id.delLastWordButton);
        bDelLastWord.setOnClickListener(delLastWord);
    }

    private View.OnClickListener saveWord = new View.OnClickListener()
    {
        public void onClick(View v)
        {
            Button button = (Button) v;

            ContentActivity.getPhrase().add(button);

            finish();
        }
    };

    static private View.OnClickListener speak = new View.OnClickListener()
    {
        @TargetApi(Build.VERSION_CODES.LOLLIPOP)
        public void onClick(View v)
        {
            String phrase = "";

            for(Button e: ContentActivity.getPhrase())
                phrase += e.getText() + " ";

            if (ContentActivity.mTts.isSpeaking())
                ContentActivity.mTts.stop();

            ContentActivity.mTts.speak(phrase, TextToSpeech.QUEUE_ADD, null);

        }
    };

    static private View.OnClickListener resetPhrase = new View.OnClickListener()
    {
        @TargetApi(Build.VERSION_CODES.LOLLIPOP)
        public void onClick(View v)
        {
            ContentActivity.setPhrase(new ArrayList<Button>());
            phraseView.setText("");
        }
    };

    private View.OnClickListener delLastWord = new View.OnClickListener()
    {
        @TargetApi(Build.VERSION_CODES.LOLLIPOP)
        public void onClick(View v)
        {
            if (ContentActivity.getPhrase().size() > 0)
            {
                ContentActivity.getPhrase().remove(ContentActivity.getPhrase().size() - 1);
                setTextPhrase();
            }
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
        setTextPhrase();
    }

    public void setTextPhrase()
    {
        String str = "";


        for(Button e: ContentActivity.getPhrase())
            str += e.getText() + " ";

        if (str != "")
            phraseView.setText(str);
    }
}
