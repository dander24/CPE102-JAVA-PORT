public class OccupancyGrid
{
    //occupancy values
    private final int EMPTY = 0, GATHERER = 1, GENERATOR = 2, RESOURCE = 3;

    private int width, height;

    private Entity[][] grid;

    public OccupancyGrid(int Height, int Width, Entity OccupancyVal)
    {
        grid = new Entity[Height][Width];

        for (int i = 0; i < Height; i++)
        {
            for (int j = 0; j < Width; j++)
            {
                grid[i][j] = OccupancyVal;
            }
        }
    }

    public void setCell(Point point, Entity value)
    {
        grid[point.getY()][point.getX()] = value;
    }

    public Entity getCell(Point point)
    {
        return grid[point.getY()][point.getX()];
    }

}
