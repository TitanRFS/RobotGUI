

public class VDM {
	public static boolean implies (boolean a, boolean b)
	{
		return ((!a)||b);
	}
	
	static void preTest(boolean expr) throws VDMException2
	{
		if(expr == false)
		
			throw new VDMException2();
	
	}
	
	static void invTest(InvariantCheck i) throws VDMException1
	{
		if ( !i.inv())
			throw new VDMException1();
	}
}