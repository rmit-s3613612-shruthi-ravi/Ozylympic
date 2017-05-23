/**
* @author: Neha
*/
package ozlympicgames;
import java.util.Random;

class SuperAthlete extends Athlete
{
	private String playingAs;
	/* Constructor */
	SuperAthlete(String id, String type, String name, int age, String state)
	{
		super(id, type, name, age, state);
	}
        
        public SuperAthlete getReference()
        {
            return this;
        }
        
        public String toString()
        {
            return super.toString();
        }
        
        public int compareTo(SuperAthlete that)
        {
            return super.compareTo(that);
        }
        
        public String getId()
	{
		return super.getId();
	}
        
	/* Accessors and mutators */
	public void setPlayingAs(String playingAs)
	{
		this.playingAs = playingAs;
	}
	public String getPlayingAs()
	{
		return playingAs;
	}
	/** 
	* @param: none
	* @return: none
	* @function: to generate finish time
	*/
	public void compete()
	{
		super.compete();
		Random rand = new Random();
                if(playingAs.equals("Sprinter"))
                {
                        super.setFinishTime(rand.nextInt(11) + 10);
                }
                else if(playingAs.equals("Swimmer"))
                {
                        super.setFinishTime(rand.nextInt(101) + 100);
                }
                else if(playingAs.equals("Cyclist"))
                {
                        super.setFinishTime(rand.nextInt(301) + 500);
                }
	}
}
