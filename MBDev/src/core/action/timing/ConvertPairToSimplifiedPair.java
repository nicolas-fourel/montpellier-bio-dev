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

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import core.action.file.LineFileReader;
import core.fileLine.PairLine;


/**
 * @author Nicolas Fourel
 * @version 0.1
 */
public class ConvertPairToSimplifiedPair extends LineFileReader<PairLine> {

	/** Generated default serial version ID */
	private static final long serialVersionUID = 4495558610088027673L;

	private static final boolean SKIP_RANDOM = true;

	private final File outputFile;		// The output file.

	private boolean isReadyToWrite;		// Says if the output file is ready.
	private FileWriter fw;				// The file writer.
	private BufferedWriter out;			// The buffered writer.


	/**
	 * Constructor of {@link ConvertPairToSimplifiedPair}
	 * @param inputFile		the input file
	 * @param outputFile	the output file
	 */
	public ConvertPairToSimplifiedPair(File inputFile, File outputFile) {
		super(inputFile, new PairLine(null));
		this.outputFile = outputFile;
		isReadyToWrite = false;
		actionName = "Convert pair file to simplified pair file.";
	}


	@Override
	protected void processCurrentLine() {
		if (isReadyToWrite) {
			boolean isValid = true;
			if (SKIP_RANDOM) {
				if (line.getGeneExprOption().equals("RANDOM")) {
					isValid = false;
				}
			}
			if (isValid) {
				String newLine;
				if (getCurrentLineNumber() == 1) {
					newLine = getSimplifiedPairFileHeader();
				} else {
					newLine = getSimplifiedPairLine(line);
				}
				try {
					out.write(newLine + "\n");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}


	/**
	 * @return the header of a simplified pair file
	 */
	private String getSimplifiedPairFileHeader () {
		String info = "";
		info += "SEQ_ID\t";
		info += "PROBE_ID\t";
		info += "POSITION\t";
		info += "PM";
		return info;
	}


	/**
	 * @param line the original pair line
	 * @return the simplified pair line
	 */
	private String getSimplifiedPairLine (PairLine line) {
		String info = "";
		info += line.getSeqID() + "\t";
		info += line.getProbeID() + "\t";
		info += line.getPosition() + "\t";
		info += line.getPM();
		return info;
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
