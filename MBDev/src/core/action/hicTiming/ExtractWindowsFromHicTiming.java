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
package core.action.hicTiming;

import java.io.File;

import core.action.file.LineFileReader;
import core.chromosome.ProjectChromosome;
import core.fileLine.HicTimingLine;
import core.list.ChromosomeArrayListOfLists;
import core.list.ChromosomeListOfLists;
import core.list.IntArrayAsIntegerList;


/**
 * @author Nicolas Fourel
 * @version 0.1
 */
public class ExtractWindowsFromHicTiming extends LineFileReader<HicTimingLine> {

	/** Generated default serial version ID */
	private static final long serialVersionUID = 4495558610088027673L;

	private final ProjectChromosome projectChromosome;		// The instance of the chromosome project.
	private final ChromosomeListOfLists<Integer> fullList;	// The list of windows.


	/**
	 * Constructor of {@link ExtractWindowsFromHicTiming}
	 * @param inputFile the input file
	 */
	public ExtractWindowsFromHicTiming(File inputFile) {
		super(inputFile, new HicTimingLine(null));
		actionName = "Extract all HiC position from the file to a list.";
		fullList = new ChromosomeArrayListOfLists<>();

		projectChromosome = ProjectChromosome.getInstance();
		int chromosomeNumber = projectChromosome.getChromosomeList().size();
		for (int i = 0; i < chromosomeNumber; i++) {
			fullList.add(new IntArrayAsIntegerList());
		}
	}


	@Override
	protected void processCurrentLine() {
		fullList.add(projectChromosome.get(line.getFirstChromosome()), line.getFirstPosition());
		fullList.add(projectChromosome.get(line.getSecondChromosome()), line.getSecondPosition());
	}


	/**
	 * @return the list
	 */
	public ChromosomeListOfLists<Integer> getList() {
		return fullList;
	}


	@Override
	protected void doFirst() {}


	@Override
	protected void doAtTheEnd() {
		int chromosomeNumber = projectChromosome.getChromosomeList().size();
		for (int i = 0; i < chromosomeNumber; i++) {
			((IntArrayAsIntegerList) fullList.get(i)).sort();
		}
	}

}
