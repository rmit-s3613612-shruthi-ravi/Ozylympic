/**
* @author: Shruthi s3613612
*/
package ozlympicgames;
import java.util.Random;

class Cyclist extends Athlete
{
	/** constructor */
	Cyclist(String id, String type, String name, int age, String state)
	{
		super(id, type, name, age, state);
	}
        
        public Cyclist getReference()
        {
            return this;
        }
        
        public String toString()
        {
            return super.toString();
        }
        
        public String getId()
	{
		return super.getId();
	}
        
        public int compareTo(Cyclist that)
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
		super.setFinishTime(rand.nextInt(301) + 500);
	}
}
