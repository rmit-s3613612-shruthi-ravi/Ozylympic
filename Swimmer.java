/**
* @author: Neha
*/

package ozlympicgames;
import java.util.Random;

class Swimmer extends Athlete
{
	/* constructor */
	Swimmer(String id, String type, String name, int age, String state)
	{
		super(id, type, name, age, state);
	}
        
        public Swimmer getReference()
        {
            return this;
        }
        public String getId()
	{
		return super.getId();
	}
        
        public String toString()
        {
            return super.toString();
        }
        
        public int compareTo(Swimmer that)
        {
            return super.compareTo(that);
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
		super.setFinishTime(rand.nextInt(101) + 100);
	}
}
