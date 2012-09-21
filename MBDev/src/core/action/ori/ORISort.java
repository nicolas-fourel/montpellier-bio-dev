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
package core.action.ori;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

import core.action.Action;
import core.chromosome.ProjectChromosome;
import core.comparator.ORILineComparator;
import core.fileLine.ORILine;
import core.list.ChromosomeListOfLists;


/**
 * @author Nicolas Fourel
 * @version 0.1
 */
public class ORISort extends Action {

	private static final long serialVersionUID = 8108449200040584474L;

	private final File outputFile;		// The output file.
	private boolean isReadyToWrite;		// Says if the output file is ready.
	private FileWriter fw;				// The file writer.
	private BufferedWriter out;			// The buffered writer.

	private final ChromosomeListOfLists<ORILine> list; // The list of Ori lines to sort.


	/**
	 * Constructor of {@link ORISort}
	 * @param list			the list of Ori lines to sort
	 * @param outputFile	the output file
	 */
	public ORISort(ChromosomeListOfLists<ORILine> list, File outputFile) {
		this.list = list;
		this.outputFile = outputFile;
		isReadyToWrite = false;
		actionName = "Sort ORI list and write it.";
	}


	@Override
	protected Object compute() {
		if (isReadyToWrite) {
			int chromosomeNumber = ProjectChromosome.getInstance().getChromosomeList().size();
			for (int i = 0; i < chromosomeNumber; i++) {
				Collections.sort(list.get(i), new ORILineComparator());
			}

			try {

				for (int i = 0; i < chromosomeNumber; i++) {
					List<ORILine> currentList = list.get(i);
					int size = currentList.size();
					for (int j = 0; j < size; j++) {
						out.write(currentList.get(i).getLine());
						if ((i == (chromosomeNumber - 1)) && (j == (size - 1))) {
							// DO NOTHING
						} else {
							out.write("\n");
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}


	@Override
	protected void doFirst() {
		try {
			fw = new FileWriter(outputFile);
			out = new BufferedWriter(fw);
			isReadyToWrite = true;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	@Override
	protected void doAtTheEnd() {
		if (out != null) {
			try {
				out.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		if (fw != null) {
			try {
				fw.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
