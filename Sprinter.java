/**
* @author: Shruthi s3613612
*/
package ozlympicgames;
import java.util.Random;

class Sprinter extends Athlete
{
	/* constructor */
	Sprinter(String id, String type, String name, int age, String state)
	{
		super(id, type, name, age, state);
	}
        
        public Sprinter getReference()
        {
            return this;
        }
        
         public String toString()
        {
            return super.toString();
        }
        
        public int compareTo(Sprinter that)
        {
            return super.compareTo(that);
        }
        
        public String getId()
	{
		return super.getId();
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
		super.setFinishTime(rand.nextInt(11) + 10);
	}
}
