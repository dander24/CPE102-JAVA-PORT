import java.util.ArrayList;

public class WorldModel
{
    private final int BLOB_RATE_SCALE = 4,
                      BLOB_ANIMATION_RATE_SCALE = 50,
                      BLOB_ANIMATION_MIN = 1,
                      BLOB_ANIMATION_MAX = 3,
                      ORE_CORRUPT_MIN = 20000,
                      ORE_CORRUPT_MAX = 30000,

                       QUAKE_STEPS = 10,
                      QUAKE_DURATION = 1100,
                      QUAKE_ANIMATION_RATE = 100,

                     VEIN_SPAWN_DELAY = 500,
                     VEIN_RATE_MIN = 8000,
                     VEIN_RATE_MAX = 17000;

    private ArrayList<Entity> entities;
    private OccupancyGrid background, occupancy;
    private int numRows, numCols;

    //OCC GRID is now x,y
    //so like normal

    public WorldModel(int rows, int cols, Background _background)
    {
        background = new OccupancyGrid(numRows,numCols, _background);
        numRows = rows;
        numCols = cols;
        occupancy = new OccupancyGrid(numRows,numCols, null);
        entities = new ArrayList<>();
    }

    test

}
