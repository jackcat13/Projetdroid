package src.projetandroid;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.speech.tts.*;

import java.util.ArrayList;
import java.util.Locale;

import model.DBHelper;


public class ContentActivity extends Activity {

    private static ArrayList<Button> phrase;
    static TextView phraseView;
    public static TextToSpeech mTts;

    @TargetApi(Build.VERSION_CODES.DONUT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setPhrase(new ArrayList<Button>());
        setContentView(R.layout.activity_content);

        phraseView = (TextView) findViewById(R.id.phraseView);
        phraseView.setOnClickListener(speak);

        mTts = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener()
        {
            @Override
            public void onInit(int status) {}
        });

        GridLayout gl = (GridLayout) findViewById(R.id.gridLayoutContent);

        DBHelper db = new DBHelper(this);

        int i = 0;
        Cursor buttons = db.selectPageQuery();
        buttons.moveToFirst();
        buttons.moveToNext();
        while(!buttons.isAfterLast()) {

            /*System.out.println(buttons.getString(buttons.getColumnIndex("IDBOUTON")));
            System.out.println(buttons.getString(buttons.getColumnIndex("NOMBOUTON")));*/

            Button b = new Button(this);
            String nomBouton = buttons.getString(buttons.getColumnIndex("NOMPAGE"));
            b.setId( Integer.valueOf(buttons.getString(buttons.getColumnIndex("IDPAGE"))) );
            b.setText( nomBouton );
            b.setPadding(20,20,20,20);
            b.setOnClickListener(pageAccess);

            gl.addView(b);

            buttons.moveToNext();
        }
        buttons.close();

        Button bDelLastWord = new Button(this);
        bDelLastWord.setId(R.id.delLastWordButton);
        bDelLastWord.setText("Supprimer le dernier mot");
        bDelLastWord.setOnClickListener(delLastWord);
        gl.addView(bDelLastWord);

        Button bResetPhrase = new Button(this);
        bResetPhrase.setId(R.id.resetPhraseButton);
        bResetPhrase.setText("Rénitialiser la phrase");
        bResetPhrase.setOnClickListener(resetPhrase);
        gl.addView(bResetPhrase);

        if (mTts.isLanguageAvailable(Locale.FRANCE) == TextToSpeech.LANG_COUNTRY_AVAILABLE)
        {
            mTts.setLanguage(Locale.FRANCE);
        }

        mTts.setSpeechRate((float) 0.80); // 1 est la valeur par défaut. Une valeur inférieure rendra l'énonciation plus lente, une valeur supérieure la rendra plus rapide.
        mTts.setPitch(1); // 1 est la valeur par défaut. Une valeur inférieure rendra l'énonciation plus grave, une valeur supérieure la rendra plus aigue.



        /*Cursor pages = db.selectPageQuery();
        pages.moveToFirst();
        while(!pages.isAfterLast()) {
            System.out.println(pages.getString(pages.getColumnIndex("IDPAGE")));
            System.out.println(pages.getString(pages.getColumnIndex("NOMPAGE")));
            pages.moveToNext();
        }
        pages.close();*/
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_content, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_administration) {
            Intent intentAdmin = new Intent(this, AdminActivity.class);
            startActivity(intentAdmin);
            finish();
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

    public View.OnClickListener pageAccess = new View.OnClickListener()
    {
        public void onClick(View v)
        {
            Intent newPage = new Intent(ContentActivity.this, SectionPageActivity.class);
            Bundle b = new Bundle();
            Button button = (Button) v;

            b.putInt("idPage", button.getId());
            b.putString("nomPage", button.getText() + "");
            newPage.putExtras(b);

            startActivity(newPage);
        }
    };

    static private View.OnClickListener speak = new View.OnClickListener()
    {
        @TargetApi(Build.VERSION_CODES.LOLLIPOP)
        public void onClick(View v)
        {
            String phrase = "";

            for(Button e: getPhrase())
                phrase += e.getText() + " ";

            if (mTts.isSpeaking())
                mTts.stop();

            mTts.speak(phrase, TextToSpeech.QUEUE_ADD, null);
        }
    };

    static private View.OnClickListener resetPhrase = new View.OnClickListener()
    {
        @TargetApi(Build.VERSION_CODES.LOLLIPOP)
        public void onClick(View v)
        {
            setPhrase(new ArrayList<Button>());
            phraseView.setText("");
        }
    };

    private View.OnClickListener delLastWord = new View.OnClickListener()
    {
        @TargetApi(Build.VERSION_CODES.LOLLIPOP)
        public void onClick(View v)
        {
            if (phrase.size() > 0)
            {
                phrase.remove(phrase.size() - 1);
                setTextPhrase();
            }
        }
    };

    public static ArrayList <Button> getPhrase()
    {
        return ContentActivity.phrase;
    }

    public static void setPhrase(ArrayList <Button> l)
    {
        ContentActivity.phrase = l;
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == 0x01)
        {
            if (resultCode == TextToSpeech.Engine.CHECK_VOICE_DATA_PASS)
            {
                // Succès, au moins un moteur de TTS à été trouvé, on l'instancie
                mTts = new TextToSpeech(this, (TextToSpeech.OnInitListener) this);
            }
            else
            {
                // Echec, aucun moteur n'a été trouvé, on propose à l'utilisateur d'en installer un depuis le Market
                Intent installIntent = new Intent();
                installIntent.setAction(TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA);
                startActivity(installIntent);
            }
        }

    }

    private void setTextPhrase()
    {
        String str = "";


        for(Button e: getPhrase())
            str += e.getText() + " ";

        if (str != "")
            phraseView.setText(str);
    }
}
