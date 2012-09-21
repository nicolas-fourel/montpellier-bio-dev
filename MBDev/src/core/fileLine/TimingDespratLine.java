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
public class TimingDespratLine extends TimingLine {

	public static final String EXTENSION = "txt";


	/**
	 * Constructor of {@link TimingDespratLine}
	 * @param line a line from a file
	 */
	public TimingDespratLine (String line) {
		super(4, line);
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
	public Integer getStart() {
		return Utils.getIntegerFromString(elements[1]);
	}


	/**
	 * @return the stop position
	 */
	public Integer getStop() {
		return Utils.getIntegerFromString(elements[2]);
	}


	/**
	 * @return the coefficient
	 */
	public Double getValue () {
		return Utils.getDoubleFromString(elements[3]);
	}


	@Override
	public Integer getPosition() {
		return getStart() + ((getStop() - getStart()) / 2);
	}


	@Override
	public Double getCoeff() {
		return getValue();
	}

}
