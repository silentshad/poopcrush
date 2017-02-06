package briochetranchecompany.poopcrush;

import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.util.ArrayList;
import java.util.Random;

import static android.content.ContentValues.TAG;

/**
 * Created by silentshad on 01/02/17.
 */

public class Board
{


    public Poop[][] grid; // grid wich will be displayed during the game
    Random rand; // generator of random int
    int width ;
    int height;
    int thresh = 3;
    int poop_count = 5;

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
                poop.skin = rand.nextInt(poop_count-1);
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

    public void get_neighbour(Poop poop, ArrayList<Poop> L) // add  unvisited neighbour to L
    {
        int x = poop.x;
        int y = poop.y;

        if(  isvalid(x-1,y) && !grid[x-1][y].visited && grid[x-1][y].skin == poop.skin   )
            L.add(grid[x-1][y]);

        if(  isvalid(x+1,y) && !grid[x+1][y].visited && grid[x+1][y].skin == poop.skin   )
            L.add(grid[x+1][y]);

        if(  isvalid(x,y-1) && !grid[x][y-1].visited && grid[x][y-1].skin == poop.skin   )
            L.add(grid[x][y-1]);

        if(  isvalid(x,y+1) && !grid[x][y+1].visited && grid[x][y+1].skin == poop.skin   )
            L.add(grid[x][y+1]);

    }

    public boolean score_point(Poop poop) // return 0 if not enough poop are touching
    {
        ArrayList<Poop> neigh_list = new ArrayList<>();
        get_neighbour( poop, neigh_list);
        int same_skin = 1;

        while( !neigh_list.isEmpty() )
        {
            int i=0;
            Poop current = neigh_list.get(i);
            get_neighbour(current, neigh_list);
            current.visited = true;
            same_skin++;

            i++;
        }
        if (same_skin >= thresh)
        {
            Log.d(TAG, "score_point: above thresh ");
            for (Poop p: neigh_list)
            {
                Poop temp_poop = new Poop(Poop.TYPE.EMPTY);
                temp_poop.skin = poop_count-1;
                grid[p.x][p.y] = temp_poop;
            }

            return true;
        }
        else
        {
            for (Poop p: neigh_list)
            {
               grid[p.x][p.y].visited = false;
            }
            return false;
        }
    }

}


