package briochetranchecompany.poopcrush;

import android.content.Intent;
import android.graphics.Color;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Menu_activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_activity);


    }


    public void Start_game(View view)
    {
        Intent intent = new Intent(this,Poop_Crush.class);
        startActivity(intent);

    }

    public void Quit(View view)
    {
        finish();
    }
}
