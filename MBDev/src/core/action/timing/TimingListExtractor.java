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
package core.action.timing;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import core.action.file.LineFileReader;
import core.fileLine.TimingDespratLine;
import core.fileLine.TimingGilbertLine;
import core.fileLine.TimingLine;


/**
 * @author Nicolas Fourel
 * @version 0.1
 */
public class TimingListExtractor extends LineFileReader<Void> {

	/** Generated default serial version ID */
	private static final long serialVersionUID = 4374073285163085004L;

	public static final int DESPRAT_FORMAT_INPUT = 0;		// Desprat input file format.
	public static final int GILBERT_FORMAT_INPUT = 1;		// Gilbert input file format.

	private final Map<String, List<TimingLine>> map;		// Map between chromosome names and their list of timing lines.

	private final TimingLine timingLine;	// Current timing line.
	private final int inputFormat;			// Input file format.


	/**
	 * Constructor of {@link TimingListExtractor}
	 * @param file			the input file
	 * @param inputFormat	the input format
	 */
	public TimingListExtractor(File file, int inputFormat) {
		super(file, null);
		map = new HashMap<>();
		this.inputFormat = inputFormat;
		timingLine = getNewLine(null);
		force = true;
		actionName = "Extract timing data to a list.";
	}


	@Override
	protected void processCurrentLine() {
		if (getCurrentLineNumber() > 3) {
			String chromosome = TimingLine.getChromosome(nativeLine);
			if (chromosome != null) {
				if (map.get(chromosome) == null) {
					map.put(chromosome, new ArrayList<TimingLine>());
				}

				TimingLine timingLine = getNewLine(nativeLine);
				if (timingLine.isValid()) {
					map.get(chromosome).add(timingLine);
				} else {
					System.err.println("Could not process the line " + getCurrentLineNumber() +". The line does not seem to be valid.");
				}
			}
			timingLine.initialize(nativeLine);
		}
	}


	/**
	 * Create the new {@link TimingLine} according to the input file format.
	 * @param line	the current line from the file
	 * @return	the right {@link TimingLine}
	 */
	private TimingLine getNewLine (String line) {
		if (inputFormat == DESPRAT_FORMAT_INPUT) {
			return new TimingDespratLine(line);
		}
		return new TimingGilbertLine(line);
	}


	/**
	 * @return the map between chromosome names and their list of timing lines
	 */
	public Map<String, List<TimingLine>> getMap() {
		return map;
	}


	@Override
	protected void doFirst() {}


	@Override
	protected void doAtTheEnd() {}

}
