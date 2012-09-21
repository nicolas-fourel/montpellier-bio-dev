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
package core.action.file;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import core.util.Utils;


/**
 * @author Nicolas Fourel
 * @version 0.1
 */
public class FileExtractor {

	private final FileReader reader;	// The file reader.
	private int lineLimit;				// The limit number of line to read (infinite = -1).
	protected int currentLineNumber;	// The current line number.


	/**
	 * Constructor of {@link FileExtractor}
	 * @param file
	 */
	public FileExtractor (FileReader reader) {
		this.reader = reader;
		this.lineLimit = -1;
	}


	/**
	 * Read the file
	 */
	public void compute() {
		if (Utils.isValidFile(reader.getFile())) {
			try{
				// Open the input streams
				FileInputStream fstream = new FileInputStream(reader.getFile());
				DataInputStream in = new DataInputStream(fstream);
				InputStreamReader isr = new InputStreamReader(in);
				BufferedReader br = new BufferedReader(isr);


				//Read the file line by line
				currentLineNumber = 0;
				String line;
				while (((line = br.readLine()) != null) && isLineLimitValid(currentLineNumber))   {
					currentLineNumber++;
					reader.processLine(line);
				}


				//Close the input streams
				br.close();
				isr.close();
				in.close();
				fstream.close();
			}catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			String error = "The file is not valid.\n";
			error += "Given file: " + reader.getFile();
			System.err.println(error);
		}
	}


	/**
	 * @param lineNumber the current number of processed line
	 * @return true if number of processed line has not reached the limit yet, false if it does
	 */
	private boolean isLineLimitValid (int lineNumber) {
		if (lineLimit == -1) {
			return true;
		}

		if (lineNumber < lineLimit) {
			return true;
		}

		return false;
	}


	/**
	 * @param limit number of line to process
	 */
	public void setLimit (int limit) {
		lineLimit = limit;
	}


	/**
	 * @return the currentLineNumber
	 */
	public int getCurrentLineNumber() {
		return currentLineNumber;
	}

}
