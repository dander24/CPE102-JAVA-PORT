public class Ore extends Actor
{
    public Ore(String name, Point position, int Rate)
    {
        super(name, position,Rate);
    }

    public String getSelfString()
    {
        return "ore" + getName() + Integer.toString(getPosition().getX()) + Integer.toString(getPosition().getY())
                + Integer.toString(getRate());
    }
}
