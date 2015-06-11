//********************************************************
//SpellCheck Class. Compares PD entries to hashTable --
//Manipulates non-contained words
//
//Braden Katzman bmk2137
//********************************************************

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;


public class SpellCheck
{
	//reference to hashTable
	QuadraticProbingHashTable ht = new QuadraticProbingHashTable();
	
	//reference to lineNumber
	int lineNumber = 0;

	//empty constructor
	public SpellCheck()
	{

	}

	//takes bigDictionary file and hashes it into hashTable, line at a time
	public void bigDictionaryToHashTable(File bigDictionary) 
			throws FileNotFoundException
	{
		Scanner scannerBD = new Scanner(bigDictionary);
		
		//reads through dictionary file
		while(scannerBD.hasNextLine())
		{
			//unique lines in dictionary file
			String s = scannerBD.nextLine();
			
			//insert string s into hash table
			ht.insert(s);
		}
	}

	//checks if line in PD is in hashTable, if not it calls 3 methods
	public void personalDictionarySpellChecker(File personalDictionary)
			throws FileNotFoundException
	{
		Scanner scannerPD = new Scanner(personalDictionary);

		//reads PD line by line
		while (scannerPD.hasNextLine())
		{
			//increment lineNumber
			lineNumber++;
			
			//unique lines(words) in personal dictionary file
			String word = scannerPD.nextLine();
			
			//changes word to lower case, in order to ignore case
			word.toLowerCase();

			if (ht.contains(word))
			{
				; //if contains, do nothing
			}
			else
			{
				//print mispelled word with line number
				System.out.println(word + " - mispelled word at line: "
				+ lineNumber);
				
				//3 manipulations of non-contained words
				addOneCharacter(word);
				removeOneCharacter(word);
				exchangeAdjacentLetters(word);
			}
		}
	}

	private void exchangeAdjacentLetters(String word)
	{
		//holds explicit parameter in order to print with reference
		String temp0 = word;
		
		//to keep track of suggestions contained in table after manipulation
		int counter = 0;
		
		//copies explicit parameter to character array
		char[] c = word.toCharArray();
		
		//switches adjacent letters one at a time
		for(int i = 0; i < c.length-1; i++)
		{
			//saves adjacent letters as temporary characters
			char temp = c[i];
			char next = c[i+1];
			
			//switches temporary characters
			c[i] = next;
			c[i+1] = temp;
			
			//allows for access of value in c, not address
			temp0 = String.copyValueOf(c);
			
			//checks if new word is in hashTable
			if(ht.contains(word))
			{
				System.out.println(word + " - by switchingÊÊ'" + next + 
				"' and '" + temp + "' you could obtain:  " + word);
				
				//increment counter if suggestion is found
				counter++;
				
				//if not at end of word, revert to original word
				if (i < c.length)
				{
					c[i] = temp;
					c[i+1] = next;
				}
			}
			else
			{
				//if word isn't contained, switch letters back, index + 1
				c[i] = temp;
				c[i+1] = next;
			}

		}
		
		//if suggestions weren't obtained
		if (counter == 0)
		{
			System.out.println("No suggestions were found by exchanging "
					+ "adjacent letters in word: " + word);
		}
	}

	private void removeOneCharacter(String s) 
	{
		//to keep track of suggestions contained in table after manipulation
		int counter = 0;
		
		//traverses through word, deleting one character at a time
		for(int i=0; i<s.length(); i++)
		{
			//temporary word so original word isn't lost upon deletion
			String temp = s;

			//deletes character in temporary string
			temp = deleteCharAt(temp,i);

			//if temp word is contained, print found word
			if(ht.contains(temp))
			{
				System.out.print(s + " - minus a letter, could be: " + temp);
				System.out.println("");
				
				//increment counter if suggestion is found
				counter++;
			}
		}
		
		//if suggestions weren't obtained
		if (counter == 0)
		{
			System.out.println("No suggestions were found by removing "
					+ "characters on word: " + s);
		}
	}

	public void addOneCharacter(String word)
	{
		//to keep track of suggestions contained in table after manipulation
		int counter = 0;
		
		char[] alphabet ={'a','b','c','d','e','f','g','h','i','j','k',
		'l','m','n','o','p','q','r','s','t','u','v','w','x','y','z', '\''};


		//traverses through word, adding every letter and calling contains
		for (int i = 0; i < word.length(); i++)
		{
			for (int j = 0; j < alphabet.length; j++)
			{
				//word will added characters
				String newWord;
				
				//if addition is to be done to begininng of word
				if (i == 0)
				{
					newWord = alphabet[j] + word;
				}
				//if addition is to be done to end of word
				else if(i == word.length() - 1)
				{
					newWord = word + alphabet[j];
				}
				//if addition is to be done in the middle of word
				else
				{
					newWord = word.substring(0,i) + alphabet[j] 
							+ word.substring(i, word.length());
				}
				
				//if new word is in hash table
				if (ht.contains(newWord))
				{
					System.out.println(word + " - by adding a character, "
					+ "you could obtain: " + newWord);
					
					//increment counter if suggestion is found
					counter++;
				}

			}//end inner for

		}//end outer for
		
		//if suggestions weren't obtained
		if (counter == 0)
		{
			System.out.println("No suggestions were found by adding"
			+ " characters to word: " + word);
		}
	}
	
	private static String deleteCharAt(String s, int pos) 
	{
		//removes character at index of pos parameter
		return s.substring(0, pos) + s.substring(pos + 1);
	}
}