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
public class PairLine extends Line {

	public static final String EXTENSION = "pair";


	/**
	 * Constructor of {@link PairLine}
	 * @param line a line from a file
	 */
	public PairLine (String line) {
		super(11, line);
	}


	/**
	 * @return the IMAGE_ID
	 */
	public String getImageID () {
		return elements[0];
	}


	/**
	 * @return the GENE_EXPR_OPTION
	 */
	public String getGeneExprOption () {
		return elements[1];
	}


	/**
	 * @return the SEQ_ID
	 */
	public String getSeqID () {
		return elements[2];
	}


	/**
	 * @return the PROBE_ID
	 */
	public String getProbeID () {
		return elements[3];
	}


	/**
	 * @return the POSITION
	 */
	public Integer getPosition () {
		return Utils.getIntegerFromString(elements[4]);
	}


	/**
	 * @return the X
	 */
	public Integer getX () {
		return Utils.getIntegerFromString(elements[5]);
	}


	/**
	 * @return the Y
	 */
	public Integer getY () {
		return Utils.getIntegerFromString(elements[6]);
	}


	/**
	 * @return the MATCH_INDEX
	 */
	public Integer getMatchIndex () {
		return Utils.getIntegerFromString(elements[7]);
	}


	/**
	 * @return the SEQ_URL
	 */
	public String getSeqUrl () {
		return elements[8];
	}


	/**
	 * @return the PM
	 */
	public Double getPM () {
		return Utils.getDoubleFromString(elements[9]);
	}


	/**
	 * @return the MM
	 */
	public Double getMM () {
		return Utils.getDoubleFromString(elements[10]);
	}

}
