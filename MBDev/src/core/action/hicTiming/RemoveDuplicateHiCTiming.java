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

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import core.action.file.LineFileReader;
import core.fileLine.HicTimingFullLine;


/**
 * @author Nicolas Fourel
 * @version 0.1
 */
public class RemoveDuplicateHiCTiming extends LineFileReader<HicTimingFullLine> {

	/** Generated default serial version ID */
	private static final long serialVersionUID = 4495558610088027673L;

	private final Map<String, Integer> idMap;	// The map of inserted lines.
	private final File outputFile;				// The output file.
	private boolean isReadyToWrite;				// Says if the output file is ready.
	private FileWriter fw;						// The file writer.
	private BufferedWriter out;					// The buffered writer.


	/**
	 * Constructor of {@link RemoveDuplicateHiCTiming}
	 * @param inputFile		the input file
	 * @param outputFile	the output file
	 */
	public RemoveDuplicateHiCTiming(File inputFile, File outputFile) {
		super(inputFile, new HicTimingFullLine(null));
		this.outputFile = outputFile;
		isReadyToWrite = false;
		actionName = "Remove duplicates according to the ID field.";
		idMap = new HashMap<>();
	}


	@Override
	protected void processCurrentLine() {
		if (isReadyToWrite) {
			String name = line.getName();
			Integer cpt = idMap.get(name);
			if (cpt == null) {
				String newLine = line.getLine();
				if (newLine != null) {
					idMap.put(name, 1);
					try {
						out.write(newLine + "\n");
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			} else {
				idMap.put(name, cpt + 1);
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

		int cpt01 = 0;
		int cpt02 = 0;
		int cpt03 = 0;
		int cptPlus = 0;
		List<String> set = new ArrayList<>(idMap.keySet());
		for (String key: set) {
			int value = idMap.get(key);
			switch (value) {
			case 1:
				cpt01++;
				break;
			case 2:
				cpt02++;
				break;
			case 3:
				cpt03++;
				break;
			default:
				cptPlus++;
				break;
			}
		}
		System.out.println("1: " + cpt01);
		System.out.println("2: " + cpt02);
		System.out.println("3: " + cpt03);
		System.out.println("+: " + cptPlus);
		System.out.println("total: " + idMap.size());
	}

}
