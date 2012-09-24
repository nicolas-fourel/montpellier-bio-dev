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
public enum FilePath {

	NICOLAS_HOME_PATH ("O:\\Montpellier\\"),
	NICOLAS_WORK_PATH ("D:\\Romain\\files\\"),
	ROMAIN_HOME_PATH ("C:\\Users\\Romain\\Documents\\RD_Nico_HiC");


	private final String name;


	/**
	 * Private constructor. Creates an instance of {@link FilePath}
	 * @param name
	 */
	private FilePath(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return name;
	}

}
