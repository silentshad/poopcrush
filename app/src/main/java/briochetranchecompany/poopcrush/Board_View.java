package briochetranchecompany.poopcrush;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import static android.content.ContentValues.TAG;


/**
 * Created by silentshad on 01/02/17.
 */

public class Board_View extends View {


    Board board;
    int nb_touched_poop;
    int was_touchedX;
    int was_touchedY;
    Rect block;
    Drawable poop_png;
    Drawable[] poop_skins;
    Rect view_space;
    long score;
    long time;
    double time_since_played;

    boolean animation_in_progress;
    boolean game_paused;

    float offset_decrease;
    float scroll_speed = 2f; // nb of block by second or not but increasing it speed up the draw

    public Board_View(Context context , AttributeSet attrs)
    {

        super(context, attrs);
        time= System.currentTimeMillis();
        time_since_played=0;
        score = 0;
        offset_decrease =0;
        nb_touched_poop = 0;
        was_touchedX = 0;
        was_touchedY = 0;
        board = new Board();
        block = new Rect();
        view_space = new Rect();
        animation_in_progress =false;
        game_paused = false;

        Resources res = getResources();
        TypedArray poop_skin_xml =  res.obtainTypedArray(R.array.poop_skins);
        int count_skin = poop_skin_xml.length();


        poop_skins = new Drawable[count_skin];
        for (int i = 0 ; i< count_skin; i++)
        {
            poop_skins[i] = poop_skin_xml.getDrawable(i);
        }
        poop_skin_xml.recycle();

        board.grid[2][2] = new Poop(Poop.TYPE.NONE);
        board.grid[1][2] = new Poop(Poop.TYPE.NONE);
        board.grid[3][2] = new Poop(Poop.TYPE.NONE);

        invalidate();
    }

   public void onDraw(Canvas canvas) {


       super.onDraw(canvas);
       View game_layout = findViewById(R.id.board_view);
       float block_h = game_layout.getHeight() / (float) board.height;
       float block_w = game_layout.getWidth() / (float) board.width;


       if (!game_paused) {
           board_full_score_check();
           board.fall();
           board.fill();

           board.decrease_offset(offset_decrease);
           ((TextView) (((View) game_layout.getParent()).findViewById(R.id.score))).setText(score + "");

           boolean one_moving = false;

           for (int i = 0; i < board.width; i++) {
               for (int j = 0; j < board.height; j++) {
                   Poop current = board.get(i, j);
                   float offset = current.getOffset() * block_h;
                   float offsetH = current.getSwapH_offset() * block_w;
                   float offsetV = current.getSwapV_offset() * block_h;

                   block.left = (int) (i * block_w) + (int) offsetH;
                   block.top = (int) (block_h * j - offset + offsetV);
                   block.bottom = (int) (block.top + block_h);
                   block.right = (int) (block.left + block_w);

                   if (current.IsMoving())
                       one_moving = true;

                   if (current.type != Poop.TYPE.NONE) {
                       poop_png = poop_skins[current.skin];
                       poop_png.setBounds(block);
                       poop_png.draw(canvas);
                   }
               }
           }

           animation_in_progress = one_moving;
           if (animation_in_progress)
               time_since_played=0;

           update_time((0.001 * ((System.currentTimeMillis()) - time))  );
           Log.d(TAG, "time since played  " + time_since_played);
           if (!animation_in_progress && time_since_played > 5f) {
               Log.d(TAG, "yolo");
               boolean debug = board.possible_move();
               Log.d(TAG, "show debug" + debug);
               if (!debug) {
                   game_paused = true;
                   (((View)(game_layout.getParent())).findViewById(R.id.loosing_screen)).setVisibility(View.VISIBLE);
               }
           }
       }
       time = System.currentTimeMillis();
        invalidate();

    }

    private void update_time(double time_since_lastFrame)
    { // time since last frame is in seconds
        Log.d(TAG, "update_time: " + time_since_lastFrame);
        offset_decrease =  (float)(scroll_speed * time_since_lastFrame );
        time_since_played += time_since_lastFrame;
    }
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int desiredWidth = (int)(0.75f * (float)widthSize) ;
        int desiredHeight = (int) (1f * (float)heightSize);


        int width;
        int height;

        //Measure Width
        if (widthMode == MeasureSpec.EXACTLY) {
            //Must be this size
            width = widthSize;
        } else if (widthMode == MeasureSpec.AT_MOST) {
            //Can't be bigger than...
            width = Math.min(desiredWidth, widthSize);
        } else {
            //Be whatever you want
            width = desiredWidth;
        }

        //Measure Height
        if (heightMode == MeasureSpec.EXACTLY) {
            //Must be this size
            height = heightSize;
        } else if (heightMode == MeasureSpec.AT_MOST) {
            //Can't be bigger than...
            height = Math.min(desiredHeight, heightSize);
        } else {
            //Be whatever you want
            height = desiredHeight;
        }

        View game_layout =   findViewById(R.id.board_view);
        view_space.left = game_layout.getLeft();
        view_space.top = game_layout.getTop();
        view_space.bottom = view_space.top + height;
        view_space.right = view_space.left + width;
        //MUST CALL THIS
        setMeasuredDimension(width, height);

    }

    public boolean onTouchEvent( MotionEvent event)
    {

        int x=(int)event.getX();
        int y=(int)event.getY();

        int poop_touchedX = x/ (view_space.width()/board.width)  ;
        int poop_touchedY = y/ (view_space.height()/board.height);

        //Log.d(TAG, "touched poop" + poop_touchedX+"|" +poop_touchedY);


        if (!game_paused &&!animation_in_progress && board.isvalid(poop_touchedX, poop_touchedY)
                         && nb_touched_poop < 2 && event.getAction()==MotionEvent.ACTION_DOWN)
        {
            // a poop on the grid was touched and no poop was touched or only one
            nb_touched_poop++;

            if ( nb_touched_poop ==1)
            {
                was_touchedX = poop_touchedX;
                was_touchedY = poop_touchedY;
            }

            else
            {

                if (!( board.swapping(was_touchedX,was_touchedY,poop_touchedX,poop_touchedY) )) 
                {
                    // swapping was not valid
                    //therefore only the last click  count
                    nb_touched_poop = 1;
                    was_touchedX = poop_touchedX;
                    was_touchedY = poop_touchedY;
                } 
                else 
                {
                    nb_touched_poop = 0;
                    time_since_played = 0;
                    // swap happen so there is no selected poop
                }
            }
                return true;
        }
        return false;
    }

    public long board_full_score_check()
    {

        for (int i = 0 ; i< board.width; i++) {
            for (int j = 0; j < board.height; j++) {
                if (!board.get(i,j).IsMoving() && board.get(i,j).type != Poop.TYPE.NONE && board.get(i,j).type != Poop.TYPE.EMPTY) {
                    long this_score = board.score_and_destroy(i, j);
                    if (this_score != 0) {
                        score += this_score;
                    }
                }
            }
        }
        return score;
    }


}
