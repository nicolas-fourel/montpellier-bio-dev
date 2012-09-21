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
package core.chromosome;


/**
 * The Chromosome class represents a chromosome with a name and a length.
 * @author Nicolas Fourel
 * @version 0.1
 */
public final class Chromosome implements Cloneable {

	private final String name;	// Name of the chromosome.
	private int length;			// Length of the chromosome.


	/**
	 * Constructor. Creates an instance of a Chromosome.
	 * @param name Name of the chromosome.
	 * @param length Length of the chromosome.
	 */
	public Chromosome(String name, int length) {
		this.name = name;
		this.length = length;
	}


	/**
	 * @param length the length of a chromosome to set
	 */
	public void setLength(int length) {
		this.length = length;
	}


	/**
	 * @return the length of a chromosome
	 */
	public int getLength() {
		return length;
	}


	/**
	 * @return the name of a chromosome
	 */
	public String getName() {
		return name;
	}


	@Override
	public String toString() {
		return name;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Chromosome other = (Chromosome) obj;
		if (length != other.length) {
			return false;
		}
		if (name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!name.equals(other.name)) {
			return false;
		}
		return true;
	}


	/**
	 * The hashCode is computed only on the name
	 * because it's the only immutable field
	 */
	@Override
	public int hashCode(){
		return name.hashCode();
	}


	/**
	 * Returns true if the name of the current chromosome is equal to the specified string.
	 * Removes "chr" and "chromosome" before comparing if the string in parameter or if the
	 * chromosome name starts this way (ex: "chr1" becomes "1")
	 * @param otherChromoName
	 * @return true if equal, false otherwise
	 */
	public boolean hasSameNameAs(String otherChromoName) {
		// we remove "chr" or "chromosome" if the name of the current chromosome starts this way
		String chromoName = getName().trim();
		if ((chromoName.length() >= 10) && (chromoName.substring(0, 10).equalsIgnoreCase("chromosome"))) {
			chromoName = chromoName.substring(10);
		} else if ((chromoName.length() >= 3) && (chromoName.substring(0, 3).equalsIgnoreCase("chr"))) {
			chromoName = chromoName.substring(3);
		}
		// we remove "chr" or "chromosome" if the name of the other chromosome starts this way
		otherChromoName = otherChromoName.trim();
		if ((otherChromoName.length() >= 10) && (otherChromoName.substring(0, 10).equalsIgnoreCase("chromosome"))) {
			otherChromoName = otherChromoName.substring(10);
		} else if ((otherChromoName.length() >= 3) && (otherChromoName.substring(0, 3).equalsIgnoreCase("chr"))) {
			otherChromoName = otherChromoName.substring(3);
		}
		return chromoName.equalsIgnoreCase(otherChromoName);
	}


	/**
	 * Prints the chromosome information
	 */
	public void show () {
		String info = "";
		info += "Name: " + name + "; ";
		info += "Length: " + length;
		System.out.println(info);
	}
}
