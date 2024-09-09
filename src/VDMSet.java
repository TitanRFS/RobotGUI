import java.util.*;
//https://setu677.medium.com/instantiating-interfaces-in-java-94a22dcf37f
//check int
//check same values for constuctor AND union
public class VDMSet  {
	private Vector<Object> theSet;
	

	//constructors
	
	VDMSet()  //constructs empty theSet (1)
	{
		theSet = new Vector<Object>();
	}
	
	VDMSet(Object[] o) //fills theSet with elements of Object array (2a)
	{
		theSet = new Vector<Object>();
		for(int i=0; i< o.length;i++)
		{
			if(!theSet.contains(o[i]))
				theSet.add(o[i]);
		}
		
	}
	VDMSet(int [] o)  //overloaded for int array (2b)
	{
		theSet = new Vector<Object>();
		for(int i=0; i< o.length;i++)
		{
			if(!theSet.contains(Integer.valueOf(o[i])))
				theSet.add(Integer.valueOf(o[i]));
		}
	}
	VDMSet(Object o)   //constructor for single Object (3a)
	{
		theSet = new Vector<Object>();
		theSet.add(o);
	
	}
	
	VDMSet(int o)   //overloaded constructor for single int (3b)
	{
		theSet = new Vector<Object>();
		theSet.add(Integer.valueOf(o));
	
	}
	VDMSet(int o1, int o2)  //constructor for sub range  (4)
	{
		theSet = new Vector<Object>();
		for(int i=o1; i<=o2;i++)
		{
			theSet.add(Integer.valueOf(i));
		}
	}
	
	//Method for constructing Object set with comprehension  (5a)
	static VDMSet setComp(Expression e, VDMSet other, Testable t)
	{
		VDMSet v = new VDMSet();
		for(int i =0; i < other.theSet.size(); i++)
		{
			if(t.test(other.theSet.get(i)))
			{
				if(!v.theSet.contains(other.theSet.get(i)))
					v.theSet.add(e.action(other.theSet.get(i)));
			}
		}
		return v;
	}
	//Method for constructing int  set  with comprehension  (5b) 
	 static VDMSet setComp(ExpressionInt e, VDMSet other, TestableInt t)
	{
		VDMSet v = new VDMSet();
		for(int i =0; i < other.theSet.size(); i++)
		{
			if(t.test((int)other.theSet.get(i)))
			{
				if(!v.theSet.contains((int)other.theSet.get(i)))
					v.theSet.add(Integer.valueOf(e.action((int)other.theSet.get(i))));
			}
		}
		
		return v;
	}
	 //cardinality
	public int card()
	{
		return theSet.size();
	}
	//contains + does not contain
	public boolean contains(Object o)
	{
		return(theSet.contains(o));
	}
	public boolean doesNotContain(Object o)
	{
		return(!theSet.contains(o));
	}
	
	//union, intersection,difference
	
	public VDMSet union(VDMSet o)
	{
		VDMSet v = new VDMSet();
		v.theSet.addAll(this.theSet);
		
		for(int i=0; i<o.theSet.size(); i++)
		{
			if(!v.theSet.contains(o.theSet.get(i)))
			{
				v.theSet.add(o.theSet.get(i));
			}
		}
		return v;
	}
	VDMSet intersection(VDMSet o) 
	{
		VDMSet v = new VDMSet();
		for(int i=0; i< this.theSet.size();i++)
		{
			if(o.theSet.contains(this.theSet.get(i)))
			{
				v.theSet.add(this.theSet.get(i));
			}
		}

		return v;		
	}
	
	VDMSet difference(VDMSet o) 
	{
		VDMSet v = new VDMSet();
		for(int i=0; i< this.theSet.size();i++)
		{
			if(!o.theSet.contains(this.theSet.get(i)))
			{
				v.theSet.add(this.theSet.get(i));
			}
		}

		return v;		
	}
	
    //subset, not subset
	public boolean isASubsetOf()
	{
		if(o.theSet.containsAll(this.theSet))
			return true;
		return false;

	}
	public boolean isNotASubsetOf(VDMSet o) 
	{
		if(!o.theSet.containsAll(this.theSet))
			return true;
		return false;
	}
	//proper subset, not proper subset
	public boolean isAProperSubsetOf(VDMSet o) 
	{
		if(o.theSet.containsAll(this.theSet))
		{
			if(o.theSet.size() == this.theSet.size())
				return false;
			else
				return true;
		}
		else
			return false;
	}
	
	public boolean isNotAProperSubsetOf(VDMSet o) 
	{
		if(o.theSet.containsAll(this.theSet))
		{
			if(o.theSet.size() == this.theSet.size())
				return true;
			else
				return false;
		}
		else
			return true;
	}
	//other
	public Enumeration getelements()
	{
		Enumeration enu = this.theSet.elements(); 
	    System.out.println("The enumeration of values are:"); 
  
        // Displaying the Enumeration 
        while (enu.hasMoreElements()) { 
            System.out.println(enu.nextElement()); 
        } 
        return enu;
	}
	
	public String toString()
	{
		String s= "";
		for(int i=0; i< this.theSet.size();i++)
		{
			s+=this.theSet.get(i).toString();
			s+=",";
		}
		return(s);
	}
	
	
}
