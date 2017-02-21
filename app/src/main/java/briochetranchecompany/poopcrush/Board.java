package briochetranchecompany.poopcrush;

import android.nfc.Tag;
import android.util.Log;
import android.util.Pair;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by silentshad on 01/02/17.
 */

import static android.content.ContentValues.TAG;
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
        this.height = 6;

        grid = new Poop[width][height];
        for (int i =0 ; i<width; i++)
        {
            for (int j = 0; j<height; j++)
            {

                Poop poop = new Poop(Poop.TYPE.EMPTY);
                // give the skin
                poop.skin = poop_count-1;
                grid[i][j] = poop ;

            }
        }
        add_none();
    }

    public void add_none()
    {
        grid[2][2] = new Poop(Poop.TYPE.NONE);
    }

    public void reset()
    {
        for (int i =0 ; i<width; i++)
        {
            for (int j = 0; j<height; j++)
            {

                Poop poop = new Poop(Poop.TYPE.EMPTY);
                // give the skin
                poop.skin = poop_count-1;
                grid[i][j] = poop ;

            }
        }
        add_none();
    }

    public boolean isvalid(int x, int y)
    {
        return ( x<width && x>= 0 && y<height && y >=0);
    }

    public boolean swapping(int x1, int y1, int x2 , int y2)
    {
        //Log.d(TAG, "swapping: "+ x1 +"|"+y1 +"   and    "+ x2+"|"+y2);
        Poop poop1 = grid[x1][y1];
        Poop poop2 = grid[x2][y2];


        if ( poop1.isMovable() && poop2.isMovable() && !poop1.IsMoving() && !poop2.IsMoving())
        {
            int a = x1-x2;
            int b = y1-y2;

            if( (Math.abs(a) == 1 && b==0) ) // horrizontal swap
            {
                grid[x1][y1] = poop2;
                grid[x2][y2] = poop1;
                if (swifth_score_check(x1,y1) || swifth_score_check(x2,y2)) {
                    poop2.setSwapH_offset((float) -a);
                    poop1.setSwapH_offset((float) a);
                    return true;
                }
                else
                {
                    grid[x1][y1] = poop1;
                    grid[x2][y2] = poop2;
                    return  false;
                }
            }
            else if (Math.abs(b) == 1 && a==0) // vertical swap
            {
                grid[x1][y1] = poop2;
                grid[x2][y2] = poop1;
                if (swifth_score_check(x1,y1) || swifth_score_check(x2,y2)) {
                    poop2.setSwapV_offset((float) -b);
                    poop1.setSwapV_offset((float) b);
                    return true;
                }
                else {
                    grid[x1][y1] = poop1;
                    grid[x2][y2] = poop2;
                    return false;
                }
            }
            return  false;
        }
        return false;

    }

    public Poop get(int x, int y)
    {
        return grid[x][y];
    }



    public boolean get_neighbour(int x, int y, ArrayList<Pair<Integer,Integer>> L) // add  0neighbour to L
    {
        Poop poop = get(x, y);
        int sameH = 0;

        int i = 0; // line to the right;
        while (isvalid(x + i, y)&& get(x+i,y).type!= Poop.TYPE.NONE  && grid[x + i][y].skin == poop.skin && !get(x+i,y).IsMoving())
            i--;

        i++;
        while (isvalid(x + i, y)&& get(x+i,y).type!= Poop.TYPE.NONE  && grid[x + i][y].skin == poop.skin && !get(x+i,y).IsMoving()) {

            int sameV_above = 0;
            int sameV_below = 0;

            int j = 1; // line to the top
            while (isvalid(x+i, y - j) && get(x+i, y - j).type != Poop.TYPE.NONE && grid[x+i][y - j].skin == poop.skin && !get(x+i, y - j).IsMoving()) {
                sameV_above++;
                j++;
            }
            j = 1; // to the bottom
            while (isvalid(x+i, y + j) && get(x+i, y + j).type != Poop.TYPE.NONE && grid[x+i][y + j].skin == poop.skin && !get(x+i, y + j).IsMoving()) {
                sameV_below++;
                j++;
            }

            if( sameV_above+sameV_below >= thresh-1)
            {
                for (int k= y+ sameV_below ; k>=y- sameV_above ; k--)
                    L.add(new Pair<>(x+i, k));
            }

            sameH++;
            i++;
        }

        if( sameH >= thresh)
        {
            for (int k = 0 ; k < sameH ; k++)
                L.add(new Pair<>(x+k, y));
        }

        return L.size() >= thresh;

    }

    public long score_and_destroy(int x, int y) // return 0 if not enough poop are touching
    {
        ArrayList<Pair<Integer,Integer>> neigh_list = new ArrayList<>();


        boolean point_scored =get_neighbour(x, y, neigh_list);

        long score = 0;


            if (point_scored) {
                for (Pair<Integer, Integer> p : neigh_list) {
                    score += get(p.first, p.second).getPoint();
                    Poop temp_poop = new Poop(Poop.TYPE.EMPTY);
                    temp_poop.skin = poop_count - 1;
                    grid[p.first][p.second] = temp_poop;

                }
            }
            return score;

    }

    public boolean swifth_score_check (int x, int y)
    {
         ArrayList<Pair<Integer,Integer>> neigh_list = new ArrayList<>();

        return get_neighbour(x, y, neigh_list);

    }

    public boolean possible_move()
    {

        for (int i =0; i< width ; i++)
        {
            for (int j = 0; j<height; j++)
            {
                Pair<Integer,Integer> current = new Pair<>(i,j);

                Pair<Integer,Integer> left = new Pair<>(i-1,j);
                Pair<Integer,Integer> bot = new Pair<>(i,j+1);
                Pair<Integer,Integer> right = new Pair<>(i+1,j);
                Pair<Integer,Integer> top = new Pair<>(i,j-1);

                if (swap_is_possible(current,left) ||swap_is_possible(current,bot) ||
                            swap_is_possible(current,right) || swap_is_possible(current,top) )
                    return true;
            }
        }
        return false;
    }

    public boolean swap_is_possible(Pair<Integer,Integer> coordinate_p1 , Pair<Integer,Integer> coordinate_p2)
    {
        int x1= coordinate_p1.first;
        int y1= coordinate_p1.second;
        int x2= coordinate_p2.first;
        int y2= coordinate_p2.second;
        if (isvalid(x1,y1)&& isvalid(x2,y2) ){
            Poop poop1 = get(coordinate_p1.first,coordinate_p1.second);
            Poop poop2 = get(coordinate_p2.first,coordinate_p2.second);

            if ( poop1.isMovable() && poop2.isMovable() && !poop1.IsMoving() && !poop2.IsMoving()) {
                int a = x1 - x2;
                int b = y1 - y2;

                if ((Math.abs(a) == 1 && b == 0) || (Math.abs(b) == 1 && a == 0)) {
                    grid[x1][y1] = poop2;
                    grid[x2][y2] = poop1;
                    boolean possible = (swifth_score_check(x1, y1) || swifth_score_check(x2, y2));
                    grid[x1][y1] = poop1;
                    grid[x2][y2] = poop2;
                    return possible;
                }
                return false;
            }
        }
        return false;
    }

    public void defecate(int x, int y, float offset)
    {
        Poop poop = new Poop(Poop.TYPE.BASIC);
        // give the skin
        poop.skin = rand.nextInt(poop_count-1);
        poop.setOffset(offset);
        grid[x][y] = poop ;
    }


    public void fall()
    {

        for (int i = 0; i<width; i++) {
            for (int j = 0 ; j < height; j++) {

                if (get(i,j).gravity && !get(i,j).IsFalling())
                {
                    Poop current = get(i, j);
                    Poop temp_poop = new Poop(Poop.TYPE.EMPTY);
                    temp_poop.skin = poop_count - 1;

                    if ( isvalid(i,j+1)&&get(i,j+1).type == Poop.TYPE.EMPTY)
                    {
                        current.setOffset(1);
                        grid[i][j] = temp_poop;
                        grid[i][j + 1] = current;
                    }
                    else if ((isvalid(i + 1, j) && get(i + 1, j).type == Poop.TYPE.NONE) && (isvalid(i+1, j + 1) && get(i+1, j + 1).type == Poop.TYPE.EMPTY))
                    {// a none to the right and below the none is an empty
                        current.setSwapH_offset(-1);
                        current.setOffset(1);
                        grid[i][j] = temp_poop;
                        grid[i+1][j+1] = current;
                    }

                    else if ((isvalid(i - 1, j) && get(i - 1, j).type == Poop.TYPE.NONE) && (isvalid(i-1, j + 1) && get(i-1, j + 1).type == Poop.TYPE.EMPTY))
                    {// a none to the left and below the none is an empty
                        current.setSwapH_offset(1);
                        current.setOffset(1);
                        grid[i][j] =temp_poop;
                        grid[i - 1][j + 1] = current;
                    }
                    else if (isvalid(i, j + 1) && get(i, j + 1).type == Poop.TYPE.NONE) //there is a none below
                    {
                        if (isvalid(i - 1, j) && get(i - 1, j).type == Poop.TYPE.EMPTY && isvalid(i - 1, j + 1) && get(i - 1, j + 1).type == Poop.TYPE.EMPTY)
                        {//fall diagonally to the left
                            current.setSwapH_offset(1);
                            current.setOffset(1);
                            grid[i][j] = temp_poop;
                            grid[i - 1][j + 1] = current;
                        }
                        else if (isvalid(i + 1, j) && get(i + 1, j).type == Poop.TYPE.EMPTY && isvalid(i + 1, j + 1) && get(i + 1, j + 1).type == Poop.TYPE.EMPTY)
                        {//fall diagonally to the right
                            current.setSwapH_offset(-1);
                            current.setOffset(1);
                            grid[i][j] = temp_poop;
                            grid[i + 1][j + 1] = current;
                        }
                    }



                }// end if poop undergo gravity

            }
        }
    }

    public void  fill()
    {
        for (int i =0 ; i < width ;i++)
        {

            if( get(i,0).type == Poop.TYPE.EMPTY)
                    defecate(i, 0,1);
        }
    }

    public  void decrease_offset( float decrease)
    {
        for (int i = 0; i< width ; i++)
        {
            for (int j = 0 ; j< height ; j++)
            {
                Poop current = get(i,j);
                current.setOffset( current.getOffset() - decrease);

                float offsetV = current.getSwapV_offset();
                float offsetH = current.getSwapH_offset();

                if (offsetH > 0)
                    offsetH = (offsetH-decrease <0 ? 0 : offsetH-decrease);
                else
                    offsetH = ( offsetH+decrease >0 ? 0 : offsetH+decrease);

                if(offsetV > 0)
                    offsetV = ( offsetV-decrease <0 ? 0 : offsetV-decrease);
                else
                    offsetV = ( offsetV+decrease >0 ? 0 : offsetV+decrease);

                current.setSwapV_offset(offsetV);
                current.setSwapH_offset(offsetH);

            }
        }
    }


}


