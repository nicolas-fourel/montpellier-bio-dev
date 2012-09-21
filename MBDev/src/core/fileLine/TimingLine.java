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
public abstract class TimingLine extends Line {

	public static final String EXTENSION = "txt";


	/**
	 * Constructor of {@link TimingLine}
	 * @param line a line from a file
	 */
	public TimingLine (int columnNumber, String line) {
		super(columnNumber, line);
	}


	/**
	 * @return the position
	 */
	public abstract Integer getPosition ();


	/**
	 * @return the coefficient
	 */
	public abstract Double getCoeff ();


	public static String getChromosome (String line) {
		String result = null;
		if (line != null) {
			String start = line.substring(0, 5);
			int firstInt = Utils.getFirstIntegerOffset(start, 2);
			if (firstInt == -1) {
				result = "chr" + start.charAt(3);
			} else {
				Integer chrom = Utils.getFullIntegerPart(start, firstInt);
				if (chrom != null) {
					result = "chr" + chrom;
				}
			}
		}
		return result;
	}

}
