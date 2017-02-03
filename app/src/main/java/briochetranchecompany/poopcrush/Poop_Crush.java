package briochetranchecompany.poopcrush;


import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;


public class Poop_Crush extends Activity implements View.OnTouchListener{

    private static final String TAG = "poopcrush";
    View my_view;

    public void onCreate(Bundle savedInstanceState)
    {
     super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_running);
        my_view = findViewById(R.id.board_view);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return my_view.onTouchEvent(event);
    }
}
