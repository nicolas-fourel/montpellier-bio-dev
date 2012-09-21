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
package core.comparator;

import java.util.Comparator;

import core.genome.GenomePosition;


/**
 * @author Nicolas Fourel
 * @version 0.1
 */
public class GenomePositionComparator implements Comparator<GenomePosition> {


	@Override
	public int compare(GenomePosition o1, GenomePosition o2) {
		ChromosomeComparator chromosomeComparator = new ChromosomeComparator();
		int compare = chromosomeComparator.compare(o1.getChromosome(), o2.getChromosome());
		
		if (compare == 0) {
			compare = Integer.compare(o1.getPosition(), o2.getPosition());
		}
		
		return compare;
	}
	
}
