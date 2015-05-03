public class Actor extends NonStatic
{
    private int rate;

    public Actor(String name, Point position, int Rate)
    {
        super(name,position);
        rate = Rate;
    }

    public int getRate()
    {
        return rate;
    }
}
