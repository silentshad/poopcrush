package briochetranchecompany.poopcrush;

import android.graphics.Bitmap;



/**
 * Created by silentshad on 01/02/17.
 */

public class Poop {

    public enum  TYPE {
        BASIC ,
        EXPLOSIVE ,
        NONE ,
        EMPTY

    }

    public int skin; // skin of the poop
    private int life; // number of explosion needed
    private long point; // for the score
    public boolean gravity; // if the poop can fall
    private  boolean moveable; // can be moved
    private float offset; // number of poop.height  the block need to fall
    private float swapH_offset;
    private float swapV_offset;
    private  boolean moving;
    TYPE type;

    public boolean getMoving()
    {
     return swapH_offset !=0 && swapV_offset !=0 && offset !=0;
    }

    public void setSwapH_offset(float value , int width)
    {
        if ( value > width-1)
            swapH_offset = width-1;
        else if (value < 0)
            swapH_offset = 0;
        else
            swapH_offset = value;
    }

    public float getSwapH_offset()
    {
        return  swapH_offset;}
    public float getSwapV_offset()
    {
        return  swapV_offset;}
    public void setSwapV_offset(float value , int height)
    {
        if ( value > height-1)
            swapH_offset = height-1;
        else if (value < 0)
            swapH_offset = 0;
        else
            swapH_offset = value;
    }

    public boolean isMoveable() {
        return moveable;
    }

    public long getPoint() {
        return point;
    }


    public int getLife() {
        return life;
    }

    public void setLife(int life) {
        this.life = life >=0 ? life  : 0;
    }

    public float getOffset() {
        return offset;
    }

    public void setOffset(float offset) {
        this.offset = offset<0 ? 0 : offset;
    }


    public Poop( TYPE type)
    {
        this.type =type;
        offset = 0.f;
        swapH_offset = 0.f;
        swapV_offset = 0.f;
        switch (type)
        {
            case BASIC:
                //  the skin need to be attributed after depending on which poop we want
                gravity = true;
                life = 1;
                point = 50000;
                moveable = true;
                break;
            case EXPLOSIVE:
                //  the skin need to be attributed after depending on which poop we want
                gravity = true;
                life = 1;
                point = 5000000;
                moveable = true;
                break;
            case NONE:
                gravity = false;
                moveable = false;
                break;
            case EMPTY:
                gravity = false;
                moveable = false;
                break;

        }

    }

  }

