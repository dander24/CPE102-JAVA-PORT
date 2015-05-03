public class OccupancyGrid
{
    //occupancy values
    private final int EMPTY = 0, GATHERER = 1, GENERATOR = 2, RESOURCE = 3;

    private int width, height;

    private Entity[][] grid;

    public OccupancyGrid(int Width, int Height, Entity OccupancyVal)
    {
        grid = new Entity[Width][Height];

        for (int i = 0; i < Width; i++)
        {
            for (int j = 0; j < Height; j++)
            {
                grid[i][j] = OccupancyVal;
            }
        }
    }

    public void setCell(Point point, Entity value)
    {
        grid[point.getX()][point.getY()] = value;
    }

    public Entity getCell(Point point)
    {
        return grid[point.getX()][point.getY()];
    }

}
