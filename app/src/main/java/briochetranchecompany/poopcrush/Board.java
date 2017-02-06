package briochetranchecompany.poopcrush;

import android.content.res.Resources;
import android.graphics.BitmapFactory;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by silentshad on 01/02/17.
 */

public class Board
{


    public Poop[][] grid; // grid wich will be displayed during the game
    Random rand; // generator of random int
    int width ;
    int height;

    public Board()//int width, int height )
    {
        rand = new Random();
        this.width = 4;
        this.height = 5;

        grid = new Poop[width][height];
        for (int i =0 ; i<width; i++)
        {
            for (int j = 0; j<height; j++)
            {

                Poop poop = new Poop(Poop.TYPE.BASIC);
                // give the skin
                poop.skin = (rand.nextInt(4)) %4;
                grid[i][j] = poop ;

            }
        }
    }

    public boolean isvalid(int x, int y)
    {
        return ( x<width && x>= 0 && y<height && y >=0);
    }

    public boolean swapping(int x1, int y1, int x2 , int y2)
    {
        Poop poop1 = grid[x1][y1];
        Poop poop2 = grid[x2][y2];
        if ( poop1.isMoveable() && poop2.isMoveable())
        {
            int a = x1-x2;
            int b = y1-y2;

            if( (Math.abs(a) == 1 && b==0) || (Math.abs(b) == 1 && a==0))
            {
                grid[x1][y1] = poop2;
                grid[x2][y2] = poop1;
                return true;
            }
            return  false;
        }
        return false;

    }

    public ArrayList<Poop> get_neighbour(int x,int y)

}


