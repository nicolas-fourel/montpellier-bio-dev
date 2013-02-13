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

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import core.action.file.LineFileReader;
import core.chromosome.ProjectChromosome;
import core.fileLine.ORILine;
import core.list.file.ORILineFile;


/**
 * @author Nicolas Fourel
 * @version 0.1
 */
public class ORIListExtractor extends LineFileReader<ORILine> {

	/** Generated default serial version ID */
	private static final long serialVersionUID = 4374073285163085004L;

	private final ProjectChromosome projectChromosome;		// The instance of the chromosome project.
	private final List<ORILineFile> list;
	private final ORILine currentLine;

	/**
	 * Constructor of {@link ORIListExtractor}
	 * @param file the input file
	 */
	public ORIListExtractor(File file) {
		super(file, new ORILine(null));
		actionName = "Extract ORI data to a list.";
		list = new ArrayList<>();
		currentLine = new ORILine();
		projectChromosome = ProjectChromosome.getInstance();
		int chromosomeNumber = projectChromosome.getChromosomeList().size();
		for (int i = 0; i < chromosomeNumber; i++) {
			list.add(new ORILineFile());
		}
	}


	@Override
	protected void processCurrentLine() {
		currentLine.initialize(nativeLine);
		list.get(projectChromosome.getIndex(currentLine.getChromosome())).add(currentLine);
	}


	/**
	 * @return the list
	 */
	public List<ORILineFile> getList() {
		return list;
	}


	@Override
	protected void doFirst() {}


	@Override
	protected void doAtTheEnd() {}

}
