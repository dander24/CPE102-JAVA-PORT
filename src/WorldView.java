import processing.core.PApplet;

public class WorldView {
    private int viewRows, viewCols, tileWidth, tileHeight, numRows, numCols, drawWidth, drawHeight, drawX, drawY;
    private WorldModel world;
    private PApplet parent;

    public WorldView(PApplet parentApp, int viewCol, int viewRow, WorldModel worldMod, int tileWid, int tileHei,
                     int screenWidth, int screenHeight) {
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

    public void updateView() {
        drawBackground();
        drawEntities();
        world.updateOnTime(System.currentTimeMillis());
    }


    private Point viewportToWorld(Point pt) {
        return new Point(pt.getX() + drawX, pt.getY() + drawY);
    }

    private void drawEntities() {
        for (int i = 0; i < drawHeight; i++) {
            for (int j = 0; j < drawWidth; j++) {
                Entity next = world.getTileOccupant(viewportToWorld(new Point(j, i)));
                if (next != null) {
                    parent.image(next.getImage(), j * tileWidth, i * tileHeight);
                }
            }

        }
    }

    private void drawBackground() {
        for (int i = 0; i < drawHeight; i++) {
            for (int j = 0; j < drawWidth; j++) {
                Entity next = world.getBackground(viewportToWorld(new Point(j, i)));
                if (next != null) {
                    parent.image(next.getImage(), j * tileWidth, i * tileHeight);
                }
            }

        }
    }

    public void shiftView(int deltaX, int deltaY) {
        drawX = clamp(drawX + deltaX, 0, numCols - drawWidth);
        drawY = clamp(drawY + deltaY, 0, numRows - drawHeight);
    }

    private int clamp(int i1, int i2, int i3) {
        return Math.min(i3, Math.max(i1, i2));
    }


}
