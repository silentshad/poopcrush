package briochetranchecompany.poopcrush;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceView;
import android.view.View;
import android.widget.RelativeLayout;


/**
 * Created by silentshad on 01/02/17.
 */

public class Board_View extends View{

    Poop[][] grid;
    Board board;

    private static final String TAG = "MyActivity";
    Bitmap poop_png;
    Rect test;

    public Board_View(Context context , AttributeSet attrs)
    {

        super(context, attrs);
        poop_png = BitmapFactory.decodeResource(getResources(), R.drawable.beard) ;
        test = new Rect();
        /* A COMPLETER */

    }

   public void onDraw(Canvas canvas)
    {


        super.onDraw(canvas);
        View game_layout =   findViewById(R.id.board_view);
        float block_h = game_layout.getHeight() /  8.f; //board.height;
        float block_w = game_layout.getWidth() / 8.f; //board.width;

        for (int i= 0 ; i<game_layout.getWidth(); i+= block_w )
        {
            for (int j= 0 ; j< game_layout.getHeight(); j+= block_h )
            {
                test.left = i;
                test.top = j;
                test.bottom = j +(int) block_h;
                test.right = i + (int) block_w;
                canvas.drawBitmap(poop_png, null, test,null)
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

        //MUST CALL THIS
        setMeasuredDimension(width, height);

    }
}
