public class ActorDist extends Actor
{
    private int resourceDistance;

    public ActorDist(String name, Point position, int rate, int distance)
    {
        super(name,position,rate);
        resourceDistance = distance;
    }

    public int getResourceDistance()
    {
        return resourceDistance;
    }
}
