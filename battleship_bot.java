import battleship.Player;
import battleship.Maps.mapStates;
import java.util.Random;
import java.util.Set;
import java.util.HashSet;

public class TestPlayer extends Player {
  public static Random rand = new Random();
  Set<Integer> empty_space = populateEmptySpace();

  public static int GRID_WIDTH = 10;
  public static int GRID_HEIGHT = 10;

  public Boolean focus = false;
  public int last_move_x = 0;
  public int last_move_y = 0;

  public int[][] look_directions = { { 0, 1 }, { 0, -1 }, { -1, 0 }, { 1, 0 } };
  public int focus_x = 0;
  public int focus_y = 0;

  public void calcMove() {
    //Update Map
    battleship.Maps.mapStates[][] map = getShadowMap();
    updateEmptySpaces(map);

    int y = 0;
    int x = 0;

    // Check if last fire was a hit, 
    // Set focus to true if not already focused
    if (map[last_move_x][last_move_y] == battleship.Maps.mapStates.HIT && !focus) {
      focus = true;
      focus_x = last_move_x;
      focus_y = last_move_y;
    }
    // Check if focus is a SUNK
    if (map[focus_x][focus_y] == battleship.Maps.mapStates.SUNK && focus) {
      focus_x = 0;
      focus_y = 0;
      focus = false;
    }

    // If the last fire was a hit, try to sink the rest of the ship
    if (focus) {
      for (int i = 0; i < look_directions.length; i++) {
        int look_x = focus_x;
        int look_y = focus_y;

				boolean empty = false;
        while (!empty) {
          look_x += look_directions[i][0];
          look_y += look_directions[i][1];
          
          if (!checkSquareValid(look_x, look_y)) {
            break;
          }
          else if (map[look_x][look_y] == battleship.Maps.mapStates.MISS) {
            break;
          }
          else if (map[look_x][look_y] == battleship.Maps.mapStates.EMPTY) {
            empty = true;
            x = look_x;
            y = look_y;
          }
        }
        // Program found an empty space to hit, break out of the for loop
        if (empty) {
          break;
        }

      }
    }
    // If not, generate next move randomly
    else {
      double move = (double)generateMove(empty_space);
      y = (int) Math.ceil(move / GRID_WIDTH) - 1;
      x = (int) move - (y * GRID_WIDTH) - 1;
    }

    // Submit move
    last_move_x = x;
    last_move_y = y;
    submitMove(x, y);
  }

  // Convert x and y coordinates
  // to a space on the board
  public int coordsToSpace(int x, int y) {
    return (((y * 10) + (x)));
  }

  // Check if coordinates are on the board,
  // ex: 11, 5 returns false
  public boolean checkSquareValid(int x, int y) {
    return (!(x > GRID_WIDTH - 1 || x < 0 || y > GRID_HEIGHT - 1 || y < 0));
  }

  // Generate move randomly from the set 
  // of empty spaces
  public int generateMove(Set<Integer> map) {
    int random = rand.nextInt(map.size());
    for (Integer num : map) {
      if (random-- == 0) {
        return num;
      }
    }
    return -1;
  }

  // Helper function that prints the shadow map
  public void printShadowMap(battleship.Maps.mapStates[][] map) {
    for (int y = 0; y < map.length; y++) {
      for (int x = 0; x < map[0].length; x++) {
        System.out.println(map[x][y].name());
      }
    }
  }

  // Update the empty_space set
  public void updateEmptySpaces(battleship.Maps.mapStates[][] map) {
    for (int y = 0; y < map.length; y++) {
      for (int x = 0; x < map[0].length; x++) {
        if (map[x][y] == battleship.Maps.mapStates.MISS ||
            map[x][y] == battleship.Maps.mapStates.SUNK) {
          empty_space.remove(coordsToSpace(x, y));
        }
      }
    }
  }

  // Populate the empty_space set
  public Set<Integer> populateEmptySpace() {
    Set<Integer> nums = new HashSet<Integer>();
    for (int i = 1; i <= 100; i++) {
      nums.add(i);
    }
    return nums;
  }
}
