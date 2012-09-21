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
package core.action.hic;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import core.action.file.LineFileReader;
import core.fileLine.HicLine;
import core.fileLine.TimingLine;


/**
 * @author Nicolas Fourel
 * @version 0.1
 */
public class MergeHiCWithTiming extends LineFileReader<HicLine> {

	/** Generated default serial version ID */
	private static final long serialVersionUID = 4495558610088027673L;

	private final File outputFile;						// The output file.
	private final Map<String, List<TimingLine>> map;	// The map between chromosome (key) and the list of timing line (value).

	private boolean isReadyToWrite;						// Says if the output file is ready.
	private FileWriter fw;								// The file writer.
	private BufferedWriter out;							// The buffered writer.


	/**
	 * Constructor of {@link MergeHiCWithTiming}
	 * @param inputFile		the input file
	 * @param outputFile	the output file
	 * @param map			the map between chromosome (key) and the list of timing line (value)
	 */
	public MergeHiCWithTiming(File inputFile, File outputFile, Map<String, List<TimingLine>> map) {
		super(inputFile, new HicLine(null));
		this.outputFile = outputFile;
		isReadyToWrite = false;
		actionName = "Merge HiC data with Timing data.";
		this.map = map;
	}


	@Override
	protected void processCurrentLine() {
		if (isReadyToWrite) {
			boolean isValid = false;

			Double firstCoeff = getValue(map.get(line.getFirstChromosome()), line.getFirstPosition());
			Double secondCoeff = getValue(map.get(line.getSecondChromosome()), line.getSecondPosition());

			if ((firstCoeff != null) && (secondCoeff != null)) {
				isValid = true;
			}

			if (isValid) {
				String newLine = getLine(line, firstCoeff, secondCoeff);
				try {
					out.write(newLine + "\n");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
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


	/**
	 * Creates the line
	 * @param line			the HiC line
	 * @param firstCoeff	the first timing value
	 * @param secondCoeff	the second timing value
	 * @return the line to insert
	 */
	private String getLine (HicLine line, Double firstCoeff, Double secondCoeff) {
		String info = "";

		info += line.getName() + "\t";
		info += line.getFirstChromosome() + "\t";
		info += line.getFirstPosition() + "\t";
		info += line.getFirstStrand() + "\t";
		info += firstCoeff + "\t";
		info += line.getSecondChromosome() + "\t";
		info += line.getSecondPosition() + "\t";
		info += line.getSecondStrand() + "\t";
		info += secondCoeff;

		return info;
	}


	/**
	 * Get the closest timing value for a given position
	 * @param list		a list of timing lines
	 * @param position	a given position
	 * @return			the closest value associated to the position
	 */
	private Double getValue (List<TimingLine> list, int position) {
		if ((list != null) && (list.size() > 0)) {
			int index = findClosestIndex(list, position);
			return list.get(index).getCoeff();
		}
		return 0.0;
	}


	/**
	 * Get the closest index for a given position
	 * @param list		a list of timing lines
	 * @param position	a given position
	 * @return			the closes index in the list
	 */
	private int findClosestIndex (List<TimingLine> list, int position) {
		int index = findIndex(list, position, 0, list.size() - 1);
		int currentPosition = list.get(index).getPosition();
		if (position < currentPosition) {
			if (index > 0) {
				int previousIndex = index - 1;
				int previousPosition = list.get(previousIndex).getPosition();
				int diff = (currentPosition - position) - (position - previousPosition);

				if (diff > 0) {
					index = previousIndex;
				}
			}
		}

		return index;
	}


	/**
	 * Find the index for a given position, the one right after if not found
	 * @param list			a list of timing lines
	 * @param position		a given position
	 * @param indexStart	a start index
	 * @param indexStop		a stop index
	 * @return				the index for a given position, the one right after if not found
	 */
	private int findIndex (List<TimingLine> list, int position, int indexStart, int indexStop) {
		if (indexStart == indexStop) {
			return indexStart;
		} else {
			int middle = (indexStop - indexStart) / 2;
			int currentPosition = list.get(indexStart + middle).getPosition();

			if (currentPosition == position) {
				return indexStart + middle;
			} else if (position < currentPosition) {
				return findIndex(list, position, indexStart, indexStart + middle);
			} else {
				return findIndex(list, position, indexStart + middle + 1, indexStop);
			}
		}
	}

}
