import processing.core.PApplet;
import processing.core.PImage;

public class WorldView
{
    private int viewRows, viewCols, tileWidth, tileHeight, numRows, numCols, drawWidth, drawHeight, drawX, drawY;
    private WorldModel world;
    private PApplet parent;

    public WorldView(PApplet parentApp, int viewCol, int viewRow, WorldModel worldMod, int tileWid, int tileHei,
                     int screenWidth, int screenHeight)
    {
        world = worldMod;
        tileWidth = tileWid;
        tileHeight = tileHei;
        viewCols = viewCol;
        viewRows = viewRow;
        numRows = world.getNumRows();
        numCols = world.getNumCols();
        drawWidth = screenWidth / tileWidth;
        drawHeight = screenHeight / tileHeight;
        drawX = 0;
        drawY = 0;
        parent = parentApp;

    }

    public  void updateView()
    {
        drawBackground();
        drawEntities();
    }

    private void drawEntities()
    {
        for (int i = drawY; i < 16; i++) {
            for (int j = drawX; j < 16; j++)
            {
            Entity next = world.getTileOccupant(new Point(j,i));
                if (next != null)
                {
                    parent.image(next.getImage(), j * tileWidth, i * tileHeight);
                }
            }

        }
    }

    private void drawBackground()
    {
        for (int i = drawY; i < drawHeight; i++) {
            for (int j = drawX; j < drawWidth; j++)
            {
                Entity next = world.getBackground(new Point(j, i));
                if (next != null)
                {
                    parent.image(next.getImage(), j * tileWidth, i * tileHeight);
                }
            }

        }
    }

    public void shiftView(int deltaX, int deltaY)
    {
        if (deltaX != 0)
        {
            if (drawX + deltaX < numRows && drawX + deltaX >= 0)
            {
                drawX += deltaX;
            }

        }

        if (deltaY != 0)
        {
            if (drawY + deltaY < numCols && drawY + deltaY >= 0)
            {
                drawY += deltaY;
            }

        }
    }


}
