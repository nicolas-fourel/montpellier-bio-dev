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

	private final File outputFile;
	private final ProjectChromosome projectChromosome;		// The instance of the chromosome project.
	private final List<Map<Integer, List<ORILine>>> map;	// The list between Ori and HiC.

	private final Map<Integer, Integer> report;				// The statistics report.

	private boolean isReadyToWrite;		// Says if the output file is ready.
	private FileWriter fw;				// The file writer.
	private BufferedWriter out;			// The buffered writer.


	/**
	 * Constructor of {@link MergeHiCTimingORIToFile}
	 * @param inputFile		the input file
	 * @param outputFile	the output file
	 * @param map			the list between Ori and HiC
	 */
	public MergeHiCTimingORIToFile(File inputFile, File outputFile, List<Map<Integer, List<ORILine>>> map, int insertOption) {
		super(inputFile, new HicTimingLine(null));
		this.outputFile = outputFile;
		this.map = map;
		this.insertOption = insertOption;
		this.report = new HashMap<>();
		projectChromosome = ProjectChromosome.getInstance();
		isReadyToWrite = false;
		actionName = "Merge HiC/Timing with ORI information to a file.";
	}


	@Override
	protected void processCurrentLine() {
		if (isReadyToWrite) {
			List<ORILine> currentList;
			int chromosomeIndex;
			int firstNumber = 0;
			int secondNumber = 0;
			String firstString = "-";
			String secondString = "-";

			// Get first number
			chromosomeIndex = projectChromosome.getIndex(line.getFirstChromosome());
			currentList = map.get(chromosomeIndex).get(line.getFirstPosition());

			if (currentList != null) {
				if (insertOption == NUMBER) {
					firstNumber = currentList.size();
					//updateORIFound(currentList);
				} else if (insertOption == NAMES) {
					firstString = getIDString(currentList);
				}
			}

			// Get second number
			chromosomeIndex = projectChromosome.getIndex(line.getSecondChromosome());
			currentList = map.get(chromosomeIndex).get(line.getSecondPosition());
			if (currentList != null) {
				if (insertOption == NUMBER) {
					secondNumber = currentList.size();
					//updateORIFound(currentList);
				} else if (insertOption == NAMES) {
					secondString = getIDString(currentList);
				}
			}

			String s = null;
			if (insertOption == NUMBER) {
				updateReport(firstNumber, secondNumber);
				s = buildLine(line, firstNumber, secondNumber);
			} else if (insertOption == NAMES) {
				s = buildLine(line, firstString, secondString);
			}

			if (s != null) {
				try {
					out.write(s + "\n");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}


	private String getIDString (List<ORILine> currentList) {
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
	 * Create the line to insert
	 * @param line			the native line
	 * @param firstNumber	the first number to insert
	 * @param secondNumber	the second number to insert
	 * @return	the line to insert
	 */
	private String buildLine (HicTimingLine line, int firstNumber, int secondNumber) {
		String s = "";
		s += line.getFirstChromosome() + "\t";
		s += line.getFirstPosition() + "\t";
		s += line.getFirstTiming() + "\t";
		s += firstNumber + "\t";
		s += line.getSecondChromosome() + "\t";
		s += line.getSecondPosition() + "\t";
		s += line.getSecondTiming() + "\t";
		s += secondNumber;
		return s;
	}


	/**
	 * Create the line to insert
	 * @param line			the native line
	 * @param firstNames	the first names to insert
	 * @param secondNames	the second names to insert
	 * @return	the line to insert
	 */
	private String buildLine (HicTimingLine line, String firstNames, String secondNames) {
		String s = "";
		s += line.getFirstChromosome() + "\t";
		s += line.getFirstPosition() + "\t";
		s += line.getFirstTiming() + "\t";
		s += firstNames + "\t";
		s += line.getSecondChromosome() + "\t";
		s += line.getSecondPosition() + "\t";
		s += line.getSecondTiming() + "\t";
		s += secondNames;
		return s;
	}


	/**
	 * Update the report for statistics
	 * @param firstNumber	the first number to insert
	 * @param secondNumber	the second number to insert
	 */
	private void updateReport (int firstNumber, int secondNumber) {
		updateReportNumber(firstNumber);
		updateReportNumber(secondNumber);
	}


	/**
	 * Update the report for statistics
	 * @param number a number to insert
	 */
	private void updateReportNumber (int number) {
		if (report.get(number) == null) {
			report.put(number, 1);
		} else {
			int cpt = report.get(number) + 1;
			report.put(number, cpt);
		}
	}


	/*private void updateORIFound (List<ORILine> list) {
		for (ORILine line: list) {
			if (oriFound.get(line) == null) {
				oriFound.put(line, 1);
			} else {
				int cpt = oriFound.get(line) + 1;
				oriFound.put(line, cpt);
			}
		}
	}*/


	/**
	 * @return the report
	 */
	private String getReportNumber () {
		List<Integer> numbers = new ArrayList<>(report.keySet());
		Collections.sort(numbers);

		int totalHiC = 0;
		int totalORI = 0;
		String info = "Report numbers:\n";
		for (int key: numbers) {
			totalHiC += report.get(key);
			totalORI += (key * report.get(key));
			info += "Number of " + key + ": " + report.get(key) + "\n";
		}
		info += "Total number of HiC found: " + totalHiC + "\n";
		info += "Total number of ORI found: " + totalORI;


		return info;
	}


	/*private void getDetailORIFound () {
		int count = 0;
		List<ORILine> list = new ArrayList<>(oriFound.keySet());
		for (ORILine line: list) {
			int cpt = oriFound.get(line);
			if (cpt > 1) {
				count++;
			}
		}
		System.out.println("Number of time ORI has been found more than once: " + count);
	}*/



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
		if (insertOption == NUMBER) {
			System.out.println(getReportNumber());
		}
		//getDetailORIFound();
	}

}
