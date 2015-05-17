import processing.core.PApplet;
import processing.core.PImage;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Main extends PApplet
{
    private final boolean RUN_AFTER_LOAD = true;
    private final String IMAGE_LIST_FILE_NAME = "imagelist",
                        WORLD_FILE = "gaia.sav";
    private final int
    WORLD_WIDTH_SCALE = 2,
    WORLD_HEIGHT_SCALE = 2,
    SCREEN_WIDTH = 640,
    SCREEN_HEIGHT = 480,
    TILE_WIDTH = 32,
    TILE_HEIGHT = 32;

    private int numRows, numCols;
    private ImageStore imageStore;
    private Background defualtBackground;
    private Map<String, List<PImage>> imageMap;
    private WorldView view;

    @Override
    public void setup()
    {
        size(SCREEN_WIDTH,SCREEN_HEIGHT);
        ImageStore imageStore = new ImageStore();
        imageMap = imageStore.loadImages(IMAGE_LIST_FILE_NAME);
        numCols = SCREEN_WIDTH / (TILE_WIDTH * WORLD_HEIGHT_SCALE);
        numRows = SCREEN_HEIGHT / (TILE_HEIGHT * WORLD_HEIGHT_SCALE);
        defualtBackground = (Background)createDefaultBackground(imageMap.get(imageStore.getDEFAULT_IMAGE_NAME()));
        WorldModel world = new WorldModel(numRows,numCols,defualtBackground);
        view = new WorldView();

        loadWorld(world, imageMap, WORLD_FILE);
    }

    @Override
    public void draw()
    {
        view.updateView();
    }

    private Entity createDefaultBackground(List<PImage> image)
    {
        return new Background(imageStore.getDEFAULT_IMAGE_NAME(),image);
    }

    private void loadWorld(WorldModel world, Map<String, List<PImage>> images, String filename)
    {
        try
        {
            File file = new File(filename);
            SaveLoad worldLoader = new SaveLoad();
            worldLoader.loadWorld(world,images,file,RUN_AFTER_LOAD);
        }

        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

}
