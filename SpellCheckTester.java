//********************************************************
//Tester class for SpellCheck class
//Braden Katzman bmk2137
//********************************************************

import java.io.File;
import java.io.FileNotFoundException;


public class SpellCheckTester 
{

	public static void main(String[] args)
	{
		//if command line arguments contains 2 arguments
		if (args.length == 2)
		{	
			try
			{
				//reference to spellCheck class
				SpellCheck spellCheck = new SpellCheck();
				
				//creates two files based on args. First is BD, second is PD
				File bigDictionary = new File (args[0]);
				File personalDictionary = new File (args[1]);
				
				//hashes BD into the table
				spellCheck.bigDictionaryToHashTable(bigDictionary);
				
				//checks PD entries with hashtable
				spellCheck.personalDictionarySpellChecker(personalDictionary);
			}
			catch (FileNotFoundException e)
			{
				e.printStackTrace();
			}
			
		}
		else
		{
			//if exactly two arguments are not supplied
			System.out.println("Incorrect argument(s) inputted, rerun program "
					+ "with correct arguments: two files - first is big"
					+ " dictionary, second is personal dictionary");
			System.exit(0);
		}

	}

}
