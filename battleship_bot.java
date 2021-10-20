import battleship.Player;
import battleship.Maps.mapStates;
import java.util.Random;
import java.util.Set;
import java.util.HashSet;

public class TestPlayer extends Player {
  public static Random rand = new Random();
  Set<Integer> empty_space = populateEmptySpace();

  public static int GRID_WIDTH = 10;
  public static int GRID_LENGTH = 10;

  public Boolean focus = false;
  public int last_move_x = 0;
  public int last_move_y = 0;

  public int[][] look_directions = { { 0, -1 }, { -1, 0 }, { 1, 0 }, { 0, 1 } };

  public void calcMove() {
    // Update Map
    battleship.Maps.mapStates[][] map = getShadowMap();
    updateEmptySpaces(map);

    int y = 0;
    int x = 0;

    // Check if last fire was a hit
    if (map[last_move_x][last_move_y] == battleship.Maps.mapStates.HIT) {
      focus = True;
    }


    // If the last fire was a hit, try to sink the rest of the ship
    if (focus) {
      for (int[] look : look_directions) {
        // TODO: Loop through squares in this look direction
        // can be either up, down, left, or right
        // and find next EMPTY square.
        // If square is a miss, continue
      }

    } 
    // If not, generate next move randomly
    else {
      int move = generateMove(empty_space);
      y = (int) Math.ceil(move / 10.0) - 1;
      x = (int) move - (y * 10) - 1;
    }

    // Submit move
    last_move_x = x;
    last_move_y = y;
    submitMove(x, y);
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
    for (Integer num : map) {
      if (random-- == 0) {
        return num;
      }
    }
    return -1;
  }

  public Set<Integer> updateEmptySpaces(Set<Integer> map) {
    for (int y = 0; y < map.length; y++) {
      for (int x = 0; x < map[0].length; x++) {
        if (map[x][y] == battleship.Maps.mapStates.MISS) {
          empty_space.remove(coordsToSpace(x, y));
        }
      }
    }
    return map;
  }

  public Set<Integer> populateEmptySpace() {
    Set<Integer> nums = new HashSet<Integer>();
    for (int i = 1; i <= 100; i++) {
      nums.add(i);
    }
    return nums;
  }
}
