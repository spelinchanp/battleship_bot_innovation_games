import battleship.Player;
import battleship.Maps.mapStates;
import java.util.Random;
import java.util.Set;
import java.util.HashSet;

public class TestPlayer extends Player {
  public static Random rand = new Random();
  Set<Integer> empty_space = populateEmptySpace();
  
  public int GRID_WIDTH = 10;
  public int GRID_LENGTH = 10;
  
  public Boolean focus = false;
  public int focus_x = 0;
  public int focus_y = 0;

  public int[][] look_directions = {{0, -1},
                                    {-1, 0},
                                    {1, 0},
                                    {0, 1}};

	public void calcMove() {
    	// Loop through map and update "empty_spaces"
    	// list, removing any spaces that are sunk, or misses
    	// if place is a hit, try to find the rest of the ship
    	battleship.Maps.mapStates[][] map = getShadowMap();
    	for (int y = 0; y < map.length; y++) {
        for (int x = 0; x < map[0].length; x++) {
          if (map[x][y] == battleship.Maps.mapStates.MISS ||
            	map[x][y] == battleship.Maps.mapStates.SUNK) {
            empty_space.remove(coordsToSpace(x,y));
          }
          else if (map[x][y] == battleship.Maps.mapStates.HIT) {
            focus = true;
            focus_x = x;
            focus_y = y;
          }
        }
      }
    
    	int y = 0;
    	int x = 0;
    
    	if (focus) {
		    for (int[] look : look_directions) {
            // TODO: Loop through squares in this look direction
            // can be either up, down, left, or right
            // and find next EMPTY square. 
            // If square is a miss, continue
          }
          
          
        }
      }
    	else {
        // Generate next move randomly
				int move = generateMove(empty_space);
    		y = (int)Math.ceil(move/10.0)-1;
    		x = (int)move - (y*10) - 1;
      }
    	// Submit move
      submitMove(x,y);
	}
  
  // Convert x and y coordinates
  // to a space on the board
  public int coordsToSpace(int x, int y) {
    return 6;
  }
  
  // Check if coordinates are on the board,
  // ex: 11, 5 returns false
  public boolean checkSquareValid(int x, int y) {
    return (!(x > grid_width-1 || 
    x < 0 ||
    y > grid_length-1 ||
    y < 0))
  }

  public int generateMove(Set<Integer> map) {
     	int random = rand.nextInt(map.size());
    	for(Integer num : map) {
        if (random-- == 0) {
            return num;
        }
    	}
    	return -1;
  }
  
  public Set<Integer> populateEmptySpace() {
    Set<Integer> nums = new HashSet<Integer>();
    for (int i = 1; i <= 100; i++) {
      nums.add(i);
    }
    return nums;
  }
}
