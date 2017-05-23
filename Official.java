/**
* @author: Shruthi s3613612
*/
package ozlympicgames;

class Official extends Person
{
	/* constructor */
	Official(String id, String type, String name, int age, String state)
	{
		super(id, type, name, age, state);
	}
        
        public String toString()
        {
            return super.toString();
        }
        
        public Official getReference()
        {
            return this;
        }
        
            
        public int compareTo(Official that)
        {
            return super.compareTo(that);
        }
        
        public String getId()
	{
		return super.getId();
	}
} 
	/** 
	* @param: game Object
	* @return: none
	* @function: to summarizes the game
	*/
       
