package briochetranchecompany.poopcrush;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

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
}
