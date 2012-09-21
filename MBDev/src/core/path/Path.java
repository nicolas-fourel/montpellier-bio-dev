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
package core.path;

/**
 * @author Nicolas Fourel
 * @version 0.1
 */
public class Path {

	public static final int NICOLAS = 0;	// NICOLAS user variable.
	public static final int ROMAIN = 1;		// ROMAIN user variable.

	public static final int HOME = 10;		// HOME location.
	public static final int WORK = 11;		// WORK location.


	public static int USER = NICOLAS;		// Current user.
	public static int LOCATION = WORK;		// Current location.


	/**
	 * Builds the right path of a file
	 * @param fileName a name of a file
	 * @return	the right path of a file
	 */
	public static String getPath (String fileName) {
		String path = null;

		if (USER == NICOLAS) {
			if (LOCATION == HOME) {
				path = FilePath.NICOLAS_HOME_PATH.toString() + fileName;
			} else if (LOCATION == WORK) {
				path = FilePath.NICOLAS_WORK_PATH.toString() + fileName;
			}
		}

		return path;
	}

}
