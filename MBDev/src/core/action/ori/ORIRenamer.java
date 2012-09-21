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
import java.util.ArrayList;
import java.util.List;

import core.action.Action;
import core.chromosome.Chromosome;
import core.chromosome.ProjectChromosome;
import core.counter.ChromosomeCounter;
import core.fileLine.ORILine;
import core.list.ChromosomeListOfLists;


/**
 * @author Nicolas Fourel
 * @version 0.1
 */
public class ORIRenamer extends Action {

	/** Generated default serial version ID */
	private static final long serialVersionUID = 4495558610088027673L;

	private final File outputFile;							// The output file.
	private final ChromosomeListOfLists<ORILine> oriList;	// The list of Ori lines.
	private final ChromosomeCounter counter;				// The instance of the chromosome counter.

	private List<ORILine> insertedORI;	// The list of inserted Ori
	private FileWriter fw;				// The file writer.
	private BufferedWriter out;			// The buffered writer.


	/**
	 * Constructor of {@link ORIRenamer}
	 * @param outputFile	the output file
	 * @param list			the list of Ori lines
	 */
	public ORIRenamer(File outputFile, ChromosomeListOfLists<ORILine> list) {
		this.outputFile = outputFile;
		oriList = list;
		insertedORI = new ArrayList<>();
		counter = ChromosomeCounter.getInstance();
		actionName = "ORI line renamer (and delete duplicates).";
	}


	@Override
	protected Object compute() {

		for (Chromosome chromosome: ProjectChromosome.getInstance()) {
			insertedORI = new ArrayList<>();
			List<ORILine> currentList = oriList.get(chromosome);

			int cpt = 0;

			for (ORILine currentLine: currentList) {
				cpt++;
				if (cpt > 500) {
					insertedORI = new ArrayList<>();
					cpt = 0;
				}
				if (!insertedORI.contains(currentLine)) {
					String line = buildLine(currentLine, counter.getCurrent());
					counter.incrementCounter();
					insertedORI.add(currentLine);
					try {
						out.write(line + "\n");
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}

		return null;
	}


	/**
	 * Create the line to insert
	 * @param line	the Ori line
	 * @param name	the name to use
	 * @return	the line to insert
	 */
	private String buildLine (ORILine line, String name) {
		String current = "";
		current += line.getChromosome() + "\t";
		current += line.getStartPosition() + "\t";
		current += line.getStopPosition() + "\t";
		current += name + "\t";
		current += line.getElements()[3] + "\t";
		current += line.getElements()[5];
		return current;
	}


	@Override
	protected void doFirst() {
		try {
			fw = new FileWriter(outputFile);
			out = new BufferedWriter(fw);
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
