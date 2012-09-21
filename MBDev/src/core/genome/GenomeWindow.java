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
package core.genome;

/**
 * @author Nicolas Fourel
 * @version 0.1
 */
public class GenomeWindow {

	private String 	chromosome;	// The chromosome name.
	private int 	start;		// The start position on the chromosome.
	private int 	stop;		// The stop position on the chromosome.


	/**
	 * Constructor of {@link GenomeWindow}
	 * @param chromosome 	a chromosome name
	 * @param start			a start position on the chromosome
	 * @param stop			a stop position on the chromosome
	 */
	public GenomeWindow (String chromosome, int start, int stop) {
		initialize(chromosome, start, stop);
	}


	/**
	 * Initialize the {@link GenomeWindow}
	 * @param chromosome 	a chromosome name
	 * @param start			a start position on the chromosome
	 * @param stop			a stop position on the chromosome
	 */
	public void initialize (String chromosome, int start, int stop) {
		this.chromosome = chromosome;
		this.start = start;
		this.stop = stop;
	}


	/**
	 * @return the chromosome
	 */
	public String getChromosome() {
		return chromosome;
	}


	/**
	 * @return the start
	 */
	public int getStart() {
		return start;
	}


	/**
	 * @return the stop
	 */
	public int getStop() {
		return stop;
	}

}
