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
    public int x;
    public int y;
    public boolean visited;

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




    public Poop( TYPE type)
    {
        switch (type)
        {
            case BASIC:
                //  the skin need to be attributed after depending on which poop we want
                gravity = true;
                life = 1;
                point = 5000000;
                moveable = true;
                break;
            case EXPLOSIVE:
                //  the skin need to be attributed after depending on which poop we want
                gravity = true;
                life = 1;
                point = 500000000;
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

