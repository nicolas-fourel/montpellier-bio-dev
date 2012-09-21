/*******************************************************************************
 * Montpellier Biology Developpment
 * This project aims to create a tool to process and analyse data of a project in the field of Biology.
 * Copyright (C) 2012, Nicolas Fourel
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 * 
 * Authors:
 * 	Nicolas Fourel <nicolas.fourel@live.fr>
 * 	Romain Desprat <rdesprat@gmail.com>
 ******************************************************************************/
package core.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


/**
 * @author Nicolas Fourel
 * @version 0.1
 */
public class Utils {


	/**
	 * @param file a {@link File}
	 * @return the extension of the file (without the dot), null if none.
	 */
	final public static String getExtension(File file) {
		String fileName = file.getName();
		if (fileName == null) {
			return null;
		}
		int dotIndex =  fileName.lastIndexOf('.');
		if ((dotIndex > 0) && (dotIndex < (fileName.length() - 1))) {
			return fileName.substring(dotIndex + 1).toLowerCase().trim();
		} else {
			return null;
		}
	}


	/**
	 * @param file a {@link File}
	 * @return the name of a file without its extension
	 */
	public static String getFileNameWithoutExtension(File file) {
		String fileName = file.getName();
		int index = fileName.lastIndexOf('.');
		if ((index > 0) && (index <= (file.getName().length() - 2))) {
			return fileName.substring(0, index);
		} else {
			return fileName;
		}
	}


	/**
	 * @param file
	 * @return true if the file is valid, false otherwise
	 */
	public static boolean isValidFile (File file) {
		if (file != null) {
			if (file.exists()) {
				return true;
			}
		}
		return false;
	}


	/**
	 * Split a string using the char code of a character.
	 * @param s	the string to split
	 * @param c	the integer code of the character
	 * @return	an array containing the split string
	 */
	public static String[] split (String s, int c) {
		List<String> list = new ArrayList<String>();
		if (s != null) {
			int pos = 0, end;
			while ((end = s.indexOf(c, pos)) >= 0) {
				list.add(s.substring(pos, end));
				pos = end + 1;
			}
			list.add(s.substring(pos));
		}

		String[] result = new String[list.size()];
		for (int i = 0; i < list.size(); i++) {
			result[i] = list.get(i);
		}

		return result;
	}


	/**
	 * Split a string using the tabulation character.
	 * @param s	the string to split
	 * @return	an array containing the split string
	 */
	public static String[] splitWithTab (String s) {
		List<String> list = new ArrayList<String>();
		if (s != null) {
			int pos = 0, end;
			while ((end = s.indexOf("	", pos)) >= 0) {
				list.add(s.substring(pos, end));
				pos = end + 1;
			}
			list.add(s.substring(pos));
		}

		String[] result = new String[list.size()];
		for (int i = 0; i < list.size(); i++) {
			result[i] = list.get(i);
		}
		return result;
	}


	/**
	 * Convert a string in integer
	 * @param s the string to convert
	 * @return the integer, null if the string cannot be converted
	 */
	public static Integer getIntegerFromString (String s) {
		Integer result = null;
		if (s != null) {
			try {
				result = Integer.parseInt(s);
			} catch (Exception e) {}
		}
		return result;
	}


	/**
	 * Convert a string in double
	 * @param s the string to convert
	 * @return the double, null if the string cannot be converted
	 */
	public static Double getDoubleFromString (String s) {
		Double result = null;
		if (s != null) {
			try {
				result = Double.parseDouble(s);
			} catch (Exception e) {}
		}
		return result;
	}


	/**
	 * Show the content of an array
	 * @param array the array to show
	 */
	public static void showArray (Object[] array) {
		String info = "";
		if (array == null) {
			info = "The array is null";
		} else if (array.length == 0) {
			info = "The array is empty";
		} else {
			for (int i = 0; i < array.length; i++) {
				info += i + ": " + array[i];
				if (i < (array.length - 1)) {
					info += "\n";
				}
			}
		}
		System.out.println(info);
	}


	/**
	 * This method looks for the full integer part in a string from a start index.
	 * @param s		the string
	 * @param index	index of the first integer
	 * @return		the full integer starting at the index
	 */
	public static Integer getFullIntegerPart (String s, int index) {
		Integer result = null;									// Initialize the result to null
		int nextIndex = index + 1;								// Next index is initialized with index + 1
		while (nextIndex <= s.length()) {						// while the next index is shorter or equal to the string length
			String text = s.substring(index, nextIndex);		// gets the sub string from the string (index to next index)
			try {
				result = Integer.parseInt(text);				// tries to get the integer part
			} catch (Exception e) {								// if there is no integer part
				return result;									// we return result (that contains the previous integer part or null)
			}
			nextIndex++;										// if it worked, we keep looking in the string increasing the next index
		}
		return result;											// return the result of the scan
	}


	/**
	 * This method return the index of the first int found in a string, starting from the specified index position
	 * @param s			the string
	 * @param index		the index to start
	 * @return			the index of the first int found in the string after the specified start, -1 if not found
	 */
	public static int getFirstIntegerOffset (String s, int index) {
		for (int i = index; i < s.length(); i++) {
			int c = s.charAt(i);
			if ((c > 48) && (c <= 57)) {
				return i;
			}
		}
		return -1;
	}
}
