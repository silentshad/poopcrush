package briochetranchecompany.poopcrush;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.EventLog;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;
import android.widget.RelativeLayout;

import java.lang.reflect.Array;

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

    public Board_View(Context context , AttributeSet attrs)
    {

        super(context, attrs);
        nb_touched_poop =0;
        was_touchedX =0;
        was_touchedY = 0;
        board = new Board();
        block = new Rect();
        view_space = new Rect();
        Resources res = getResources();
        TypedArray poop_skin_xml =  res.obtainTypedArray(R.array.poop_skins);
        int count_skin = poop_skin_xml.length();


        poop_skins = new Drawable[count_skin];
        for (int i = 0 ; i< count_skin; i++)
        {
            poop_skins[i] = poop_skin_xml.getDrawable(i);
        }
        poop_skin_xml.recycle();
    }

   public void onDraw(Canvas canvas)
    {


        super.onDraw(canvas);
        View game_layout =   findViewById(R.id.board_view);
        float block_h = game_layout.getHeight() /  (float)board.height;
        float block_w = game_layout.getWidth() /(float)board.width;

        for (int i= 0 ; i<game_layout.getWidth(); i+= block_w )
        {
            for (int j= 0 ; j< game_layout.getHeight(); j+= block_h )
            {
                block.left = i;
                block.top = j;
                block.bottom = j +(int) block_h;
                block.right = i + (int) block_w;
                poop_png = poop_skins[ board.grid[(int) (i/block_w)][(int) (j/block_h)].skin];
                poop_png.setBounds(block);
                poop_png.draw(canvas);
;            }
        }
    }


    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int desiredWidth = (int)(0.8f * (float)widthSize) ;
        int desiredHeight = (int) (0.6f * (float)heightSize);


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


        if ( board.isvalid(poop_touchedX, poop_touchedY)  && nb_touched_poop < 2
                                                    && event.getAction()==MotionEvent.ACTION_DOWN)
        {
            // a poop on the grid was touched and no poop was touched or only one
            nb_touched_poop++;
            Log.d(TAG, "nb touched : "+nb_touched_poop);

            if ( nb_touched_poop ==1)
            {
                was_touchedX = poop_touchedX;
                was_touchedY = poop_touchedY;
            }

            else
            {
                if (!( board.swapping(was_touchedX,was_touchedY,poop_touchedX,poop_touchedY) )) {
                    // swapping was not valid
                    //therefore only the last click  count
                    nb_touched_poop = 1;
                    was_touchedX = poop_touchedX;
                    was_touchedY = poop_touchedY;
                } else {

                    board.score_point(was_touchedX, was_touchedY);
                    board.score_point(poop_touchedX, poop_touchedY);
                    nb_touched_poop = 0;
                    invalidate();
                    // swap happen so there is no selected poop
                }
            }
                return true;
        }

        return false;
    }
}
