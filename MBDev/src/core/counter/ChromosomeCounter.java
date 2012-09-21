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
package core.counter;

import core.chromosome.ProjectChromosome;


/**
 * @author Nicolas Fourel
 * @version 0.1
 */
public class ChromosomeCounter {

	private static ChromosomeCounter instance = null;		// unique instance of the singleton
	private int counter;									// The counter.


	/**
	 * @return an instance of a {@link ChromosomeCounter}.
	 * Makes sure that there is only one unique instance as specified in the singleton pattern
	 */
	public static ChromosomeCounter getInstance() {
		if (instance == null) {
			synchronized(ProjectChromosome.class) {
				if (instance == null) {
					instance = new ChromosomeCounter();
				}
			}
		}
		return instance;
	}


	/**
	 * Constructor of {@link ChromosomeCounter}.
	 */
	private ChromosomeCounter() {
		counter = 0;
	}


	/**
	 * Increment the counter for the given chromosome
	 * @param chromosome
	 */
	public void incrementCounter () {
		counter++;
	}


	/**
	 * Get the current name associated to the counter of the given chromosome
	 * @param chromosome a chromosome
	 * @return	the ID name for the current counter
	 */
	public String getCurrent () {
		int code = counter;

		if (code > 11881376) {
			return "ZZZZZ";
		}

		int[] modulos = new int[5];

		for (int i = 4; i >= 0 ; i--) {
			int modulo = code % 26;
			code /= 26;
			modulos[i] = modulo;
		}

		String pattern = "";
		for (int i = 0; i < modulos.length; i ++) {
			pattern += (char)(modulos[i] + 65);
		}

		return pattern;
	}

}
