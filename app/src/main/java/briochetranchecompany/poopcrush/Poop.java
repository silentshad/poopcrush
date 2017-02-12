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
    TYPE type;

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
        offset = 0f;
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

