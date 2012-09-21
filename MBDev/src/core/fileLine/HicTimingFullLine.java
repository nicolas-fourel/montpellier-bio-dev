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
public class HicTimingFullLine extends Line {

	public static final String EXTENSION = "txt";


	/**
	 * Constructor of {@link HicTimingFullLine}
	 * 
	 * @param line
	 *            a line from a file
	 */
	public HicTimingFullLine(String line) {
		super(9, line);
	}


	/**
	 * @return the line as it appears in the file
	 */
	public String getLineNoName() {
		String line = null;
		if (isValid) {
			line = "";
			for (int i = 1; i < elements.length; i++) {
				line += elements[i];
				if (i < (elements.length - 1)) {
					line += "\t";
				}
			}
		}
		return line;
	}


	/**
	 * @return the name
	 */
	public String getName() {
		return elements[0];
	}


	/**
	 * @return the first chromosome name
	 */
	public String getFirstChromosome() {
		return elements[1];
	}


	/**
	 * @return the first position
	 */
	public Integer getFirstPosition() {
		return Utils.getIntegerFromString(elements[2]);
	}


	/**
	 * @return the first strand
	 */
	public String getFirstStrand() {
		return elements[3];
	}


	/**
	 * @return the first timing
	 */
	public String getFirstTiming() {
		return elements[4];
	}


	/**
	 * @return the second chromosome name
	 */
	public String getSecondChromosome() {
		return elements[5];
	}


	/**
	 * @return the second position
	 */
	public Integer getSecondPosition() {
		return Utils.getIntegerFromString(elements[6]);
	}


	/**
	 * @return the second strand
	 */
	public String getSecondStrand() {
		return elements[7];
	}


	/**
	 * @return the second timing
	 */
	public String getSecondTiming() {
		return elements[8];
	}

}
