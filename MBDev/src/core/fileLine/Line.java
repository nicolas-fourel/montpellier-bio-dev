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
package core.fileLine;

import core.util.Utils;


/**
 * @author Nicolas Fourel
 * @version 0.1
 */
public abstract class Line {

	protected final int columnNumber;	// The number of column of the line.
	protected boolean isValid;			// Says if the line is valid or not.
	protected String[] elements;		// The content of the line organized in an array.


	/**
	 * Constructor of {@link Line}
	 * @param line a line from a file
	 */
	public Line (int columnNumber, String line) {
		this.columnNumber = columnNumber;
		initialize(line);
	}


	/**
	 * Initialize the object with a line
	 * @param line a line from a file
	 */
	public void initialize (String line) {
		if (line == null) {							// if null
			elements = null;						// there is no element and it is the last line
		} else if (line.isEmpty()) {				// if empty: bad reading behavior
			elements = new String[0];				// we filter setting the elements as "empty" in order to be skipped in the process
		} else {									// the line matches the requirements
			elements = Utils.splitWithTab(line);	// we split with tabulations
		}

		if ((elements != null) && (elements.length == columnNumber)) {
			isValid = true;
		} else {
			isValid = false;
		}
	}


	/**
	 * @return the isValid
	 */
	public boolean isValid() {
		return isValid;
	}


	/**
	 * @return the line as it appears in the file
	 */
	public String getLine () {
		String line = null;
		if (isValid) {
			line = "";
			for (int i = 0; i < elements.length; i++) {
				line += elements[i];
				if (i < (elements.length - 1)) {
					line += "\t";
				}
			}
		}
		return line;
	}


	/**
	 * @return the elements
	 */
	public String[] getElements() {
		return elements;
	}

}
