import processing.core.PImage;

import java.util.List;

public class Actor extends NonStatic
{
    private int rate;

    public Actor(String name, Point position, int Rate, List<PImage> pImages)
    {
        super(name,position,pImages);
        rate = Rate;
    }

    public int getRate()
    {
        return rate;
    }
}
