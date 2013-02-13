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

import core.genome.GenomePosition;
import core.util.Utils;


/**
 * @author Nicolas Fourel
 * @version 0.1
 */
public class HicTimingLine extends Line {

	public static final String EXTENSION = "txt";


	/**
	 * Constructor of {@link HicTimingLine}
	 */
	public HicTimingLine () {
		super(6, null);
	}


	/**
	 * Constructor of {@link HicTimingLine}
	 * @param line a line from a file
	 */
	public HicTimingLine (String line) {
		super(6, line);
	}


	/**
	 * Initialize the object with a line
	 * @param line a line from a file
	 */
	public void initializeFromFullHiCTimingLine (String line) {
		if (line == null) {							// if null
			elements = null;						// there is no element and it is the last line
		} else if (line.isEmpty()) {				// if empty: bad reading behavior
			elements = new String[0];				// we filter setting the elements as "empty" in order to be skipped in the process
		} else {									// the line matches the requirements
			String[] subElements = Utils.splitWithTab(line);	// we split with tabulations
			initializeFromArray(subElements);
		}

		finalizeInitialization();
	}


	public void initializeFromArray (String[] subElements) {
		if (subElements == null) {
			elements = null;
		} else {
			elements = new String[columnNumber];
			int index = 0;
			//for (int i = 0; i < subElements.length; i++) {
			for (int i = 0; i < subElements.length; i++) {
				if ((i != 0) & (i != 3) & (i != 7)) {
					elements[index] = subElements[i];
					index++;
				}
			}
		}

		finalizeInitialization();
	}


	private void finalizeInitialization () {
		if ((elements != null) && (elements.length == columnNumber)) {
			isValid = true;
		} else {
			isValid = false;
		}
	}

	/**
	 * @return the first chromosome name
	 */
	public String getFirstChromosome () {
		return elements[0];
	}


	/**
	 * @return the first position
	 */
	public Integer getFirstPosition () {
		return Utils.getIntegerFromString(elements[1]);
	}


	/**
	 * @return the first timing
	 */
	public String getFirstTiming () {
		return elements[2];
	}


	/**
	 * @return the second chromosome name
	 */
	public String getSecondChromosome () {
		return elements[3];
	}


	/**
	 * @return the second position
	 */
	public Integer getSecondPosition () {
		return Utils.getIntegerFromString(elements[4]);
	}


	/**
	 * @return the second timing
	 */
	public String getSecondTiming () {
		return elements[5];
	}


	/**
	 * @return the first genome position
	 */
	public GenomePosition getFirstGenomePosition () {
		return new GenomePosition(getFirstChromosome(), getFirstPosition());
	}


	/**
	 * @return the second genome position
	 */
	public GenomePosition getSecondGenomePosition () {
		return new GenomePosition(getSecondChromosome(), getSecondPosition());
	}
}
