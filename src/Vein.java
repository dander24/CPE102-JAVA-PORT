public class Vein extends ActorDist
{
    public Vein(String name, Point position, int rate, int distance)
    {
        super(name,  position, rate, distance);
    }

    public String getSelfString()
    {
        return "vein" + getName() + Integer.toString(getPosition().getX()) + Integer.toString(getPosition().getY())
                + Integer.toString(getRate()) + Integer.toString(getResourceDistance());
    }
}
