import java.io.*;

class FileTest
{
	public static void main(String args[])throws Exception
	{
/*		File f= new File("FileTest.java");
		try
		{
			String s=f.getAbsolutePath();
			int l=s.lastIndexOf("\\");

			System.out.println(s.substring(0,l));
			System.out.println(f.getParent());
		/*if(f.createNewFile())
		System.out.println("File created successfuly");
		else
		System.out.println("File not created ");
	}
	catch(Exception e)
	{
		System.out.println(e);
	}
	}*/



	 try
	 {
	FileOutputStream fout = new FileOutputStream("c:\\a\\b\\asd.txt");
	System.out.println("asdf");
	}
	 catch(Exception e)
	 {
		 System.out.println(e);
	 }

	//Runtime t = Runtime.getRuntime();
	//t.exec("notepafd");

}
}