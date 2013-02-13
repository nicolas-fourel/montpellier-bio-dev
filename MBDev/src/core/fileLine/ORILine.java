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

import core.genome.GenomeWindow;
import core.util.Utils;


/**
 * @author Nicolas Fourel
 * @version 0.1
 */
public class ORILine extends Line {

	public static final String EXTENSION = "txt";


	/**
	 * Constructor of {@link ORILine}
	 */
	public ORILine () {
		super(4, null);
	}


	/**
	 * Constructor of {@link ORILine}
	 * @param line a line from a file
	 */
	public ORILine (String line) {
		super(4, line);
	}


	@Override
	public void initialize (String line) {
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
			if (subElements.length > 2) {
				elements = subElements;
			} else {
				elements = null;
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
	 * @return the chromosome name
	 */
	public String getChromosome () {
		return elements[0];
	}


	/**
	 * @return the start position
	 */
	public Integer getStartPosition () {
		return Utils.getIntegerFromString(elements[1]);
	}


	/**
	 * @return the start position
	 */
	public Integer getStopPosition () {
		return Utils.getIntegerFromString(elements[2]);
	}


	/**
	 * @return the genome window
	 */
	public GenomeWindow getGenomeWindow () {
		return new GenomeWindow(getChromosome(), getStartPosition(), getStopPosition());
	}

}
