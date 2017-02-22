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
    private float swapH_offset;// number of block to travel during horizontal swap
    private float swapV_offset; //number of block to travel during horizontal swap
    public int frame ; // int represent the frame: 1 is basic, 2 is selected, 3 is winning


    TYPE type;



    public Poop( TYPE type)
    {
        this.type =type;
        offset = 0.f;
        swapH_offset = 0.f;
        swapV_offset = 0.f;
        frame = 0;
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


    public boolean IsMoving()
    {
        return swapH_offset !=0 || swapV_offset !=0 || offset !=0;
    }
    public boolean IsFalling()
    {
        return swapH_offset >0.1 || swapV_offset >0.1 || offset >0.1;
    }

    public void setSwapH_offset(float value )
    {
        if ( value > 1)
            swapH_offset = 1;
        else if (value < -1)
            swapH_offset = -1;
        else
            swapH_offset = value;
    }

    public float getSwapH_offset()
    {
        return  swapH_offset;}
    public float getSwapV_offset()
    {
        return  swapV_offset;}
    public void setSwapV_offset(float value )
    {
        if ( value > 1)
            swapV_offset = 1;
        else if (value < -1)
            swapV_offset = -1;
        else
            swapV_offset = value;
    }

    public boolean isMovable() {
        return moveable;
    }

    public long getPoint() {
        return point;
    }


    public int getLife() {
        return life;
    }

    public void setLife(int life) {
        this.life = (life >=0 ? life  : 0);
    }

    public float getOffset() {
        return offset;
    }

    public void setOffset(float offset) {
        this.offset = offset<0 ? 0 : offset;
    }

  }

