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
package core.action.hicTimingOri;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import core.action.file.LineFileReader;
import core.chromosome.ProjectChromosome;
import core.fileLine.HicTimingLine;
import core.fileLine.ORILine;

/**
 * @author Nicolas Fourel
 * @version 0.1
 */
public class MergeHiCTimingORIToFile extends LineFileReader<HicTimingLine> {

	/** Generated default serial version ID */
	private static final long serialVersionUID = 4495558610088027673L;

	public static final int NUMBER = 0;
	public static final int NAMES = 1;

	private final int insertOption;
	private final boolean clean;

	private final File outputFile;
	private final ProjectChromosome projectChromosome; // The instance of the chromosome project.
	private final List<Map<Integer, List<ORILine>>> map; // The list between Ori and HiC.

	private final Map<Integer, List<Integer>> report; // The statistics report.

	private boolean isReadyToWrite; // Says if the output file is ready.
	private FileWriter fw; // The file writer.
	private BufferedWriter out; // The buffered writer.

	/**
	 * Constructor of {@link MergeHiCTimingORIToFile}
	 * 
	 * @param inputFile the input file
	 * @param outputFile the output file
	 * @param map the list between Ori and HiC
	 * @param insertOption  the Ori insert option (number or names)
	 * @param clean true if the file has to be clean (no lines without Ori match), false to insert everything
	 */
	public MergeHiCTimingORIToFile(File inputFile, File outputFile, List<Map<Integer, List<ORILine>>> map, int insertOption, boolean clean) {
		super(inputFile, new HicTimingLine(null));
		this.outputFile = outputFile;
		this.map = map;
		this.insertOption = insertOption;
		this.clean = clean;
		this.report = new HashMap<>();
		projectChromosome = ProjectChromosome.getInstance();
		isReadyToWrite = false;
		actionName = "Merge HiC/Timing with ORI information to a file.";
	}

	@Override
	protected void processCurrentLine() {
		if (isReadyToWrite) {
			String firstValue;
			String secondValue;

			// Get first value
			firstValue = getValue(line.getFirstChromosome(), line.getFirstPosition());

			// Get second value
			secondValue = getValue(line.getSecondChromosome(), line.getSecondPosition());

			if (canBeInserted(firstValue, secondValue)) {
				// Get the line
				String s = buildLine(line, firstValue, secondValue);

				if (s != null) {
					try {
						out.write(s + "\n");
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	/**
	 * Look for the value to insert in the final line
	 * @param chromosome a chromosome
	 * @param position a position on the chromosome
	 * @return the value to insert (number/list of ORI)
	 */
	private String getValue(String chromosome, int position) {
		int chromosomeIndex = projectChromosome.getIndex(chromosome);
		List<ORILine> currentList = map.get(chromosomeIndex).get(position);
		String value = null;
		if (currentList == null) {
			if (insertOption == NUMBER) {
				value = "0";
			} else if (insertOption == NAMES) {
				value = "-";
			}
		} else {
			if (insertOption == NUMBER) {
				value = "" + currentList.size();
			} else if (insertOption == NAMES) {
				value = getIDString(currentList);
			}
		}
		updateReport(currentList);
		return value;
	}

	/**
	 * @param currentList list of ORI
	 * @return the list of ORI names
	 */
	private String getIDString(List<ORILine> currentList) {
		String names = "";
		int size = currentList.size();
		if (size == 0) {
			names = "-";
		} else {
			for (int i = 0; i < size; i++) {
				names += currentList.get(i).getElements()[3];
				if (i < (size - 1)) {
					names += ";";
				}
			}
		}
		return names;
	}

	/**
	 * Update the report
	 * @param list list of ORI
	 */
	private void updateReport(List<ORILine> list) {
		int oriNumber = 0;
		if (list != null) {
			oriNumber = list.size();
		}
		if (report.get(oriNumber) == null) {
			report.put(oriNumber, new ArrayList<Integer>());
		}
		report.get(oriNumber).add(getCurrentLineNumber());
	}

	/**
	 * @return the report as a string
	 */
	private String getReport() {
		String info = "";
		List<Integer> oriNumbers = new ArrayList<>(report.keySet());
		Collections.sort(oriNumbers);
		for (Integer oriNumber : oriNumbers) {
			List<Integer> lines = report.get(oriNumber);
			List<Integer> noDupLines = getNoDuplicateLines(lines);
			int numberEvent = lines.size();
			int numberLines = noDupLines.size();

			if (oriNumber == 0) {
				info += oriNumber + " ORI found " + numberEvent + " times in " + numberLines + " lines";
			} else {
				info += oriNumber + " ORI(s) found " + numberEvent + " time(s) in " + numberLines + " line(s):";
				int length = 20;
				if (numberLines < length) {
					length = numberLines;
				}
				int lastLine = -1;
				for (int i = 0; i < length; i++) {
					int currentLine = noDupLines.get(i);
					if (lastLine != currentLine) {
						info += currentLine;
					}
					lastLine = currentLine;
					if (i < (length - 1)) {
						info += ",";
					}
				}
			}
			info += "\n";
		}
		return info;
	}

	private List<Integer> getNoDuplicateLines(List<Integer> lines) {
		List<Integer> result = new ArrayList<>();
		result.add(lines.get(0));
		int previousLine = lines.get(0);
		int currentLine = previousLine;
		for (int i = 1; i < lines.size(); i++) {
			currentLine = lines.get(i);
			if (previousLine != currentLine) {
				result.add(currentLine);
			}
			previousLine = currentLine;
		}

		return result;
	}


	/**
	 * If the clean option is activated, this method will reject all lines not related to Ori.
	 * @param firstValue	the first value to insert
	 * @param secondValue	the second value to insert
	 * @return	true if the values are not null, false otherwise (always true if clean is not activated)
	 */
	private boolean canBeInserted (String firstValue, String secondValue) {
		if (clean) {
			if ((firstValue.equals("0") && secondValue.equals("0")) || (firstValue.equals("-") && secondValue.equals("-"))) {
				return false;
			}
		}
		return true;
	}



	/**
	 * Create the line to insert
	 * 
	 * @param line the native line
	 * @param firstValue the first names to insert
	 * @param secondValue the second names to insert
	 * @return the line to insert
	 */
	private String buildLine(HicTimingLine line, String firstValue, String secondValue) {
		String s = "";
		s += line.getFirstChromosome() + "\t";
		s += line.getFirstPosition() + "\t";
		s += line.getFirstTiming() + "\t";
		s += firstValue + "\t";
		s += line.getSecondChromosome() + "\t";
		s += line.getSecondPosition() + "\t";
		s += line.getSecondTiming() + "\t";
		s += secondValue;
		return s;
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
		System.out.println("Report:");
		System.out.println(getReport());
	}

}
