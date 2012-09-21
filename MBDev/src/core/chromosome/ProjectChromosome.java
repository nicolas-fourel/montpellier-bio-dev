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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;


/**
 * The ChromosomeManager class provides tools to load and access and list of {@link Chromosome}.
 * This class follows the design pattern <i>Singleton</i>
 * @author Nicolas Fourel
 * @version 0.1
 */
public final class ProjectChromosome implements Iterable<Chromosome> {

	private static	ProjectChromosome	instance = null;		// unique instance of the singleton
	private		 	Map<String, Integer> 		chromosomeHash;			// Hashtable indexed by chromosome name
	private			List<Chromosome> 			chromosomeList;			// List of chromosome
	private			Chromosome					currentChromosome;		// Current chromosome in the genome window (uses for multi genome project)
	private			long						genomeLength;			// Total length of the genome


	/**
	 * @return an instance of a {@link ProjectChromosome}.
	 * Makes sure that there is only one unique instance as specified in the singleton pattern
	 */
	public static ProjectChromosome getInstance() {
		if (instance == null) {
			synchronized(ProjectChromosome.class) {
				if (instance == null) {
					instance = new ProjectChromosome();
				}
			}
		}
		return instance;
	}


	/**
	 * Constructor of {@link ProjectChromosome}.
	 */
	private ProjectChromosome() {
		initializeHg19();
	}


	/**
	 * Initialize the chromosome list with Hg19
	 */
	private void initializeHg19 () {
		List<Chromosome> chromosomeList = new ArrayList<>();
		chromosomeList.add(new Chromosome("chr1", 249250621));
		chromosomeList.add(new Chromosome("chr2", 243199373));
		chromosomeList.add(new Chromosome("chr3", 198022430));
		chromosomeList.add(new Chromosome("chr4", 191154276));
		chromosomeList.add(new Chromosome("chr5", 180915260));
		chromosomeList.add(new Chromosome("chr6", 171115067));
		chromosomeList.add(new Chromosome("chr7", 159138663));
		chromosomeList.add(new Chromosome("chr8", 146364022));
		chromosomeList.add(new Chromosome("chr9", 141213431));
		chromosomeList.add(new Chromosome("chr10", 135534747));
		chromosomeList.add(new Chromosome("chr11", 135006516));
		chromosomeList.add(new Chromosome("chr12", 133851895));
		chromosomeList.add(new Chromosome("chr13", 115169878));
		chromosomeList.add(new Chromosome("chr14", 107349540));
		chromosomeList.add(new Chromosome("chr15", 102531392));
		chromosomeList.add(new Chromosome("chr16", 90354753));
		chromosomeList.add(new Chromosome("chr17", 81195210));
		chromosomeList.add(new Chromosome("chr18", 78077248));
		chromosomeList.add(new Chromosome("chr19", 59128983));
		chromosomeList.add(new Chromosome("chr20", 63025520));
		chromosomeList.add(new Chromosome("chr21", 48129895));
		chromosomeList.add(new Chromosome("chr22", 51304566));
		chromosomeList.add(new Chromosome("chrX", 155270560));
		chromosomeList.add(new Chromosome("chrY", 59373566));
		chromosomeList.add(new Chromosome("chrM", 0));
		setChromosomeList(chromosomeList);
	}


	/**
	 * Set the current {@link ProjectChromosome} using another instance of {@link ProjectChromosome}
	 * Used for the unserialization.
	 * @param project the instance of {@link ProjectChromosome} to use
	 */
	protected void setProjectChromosome (ProjectChromosome project) {
		setChromosomeList(project.getChromosomeList());
		setCurrentChromosome(project.getCurrentChromosome());
	}


	/**
	 * @param chromosomeList the chromosomeList to set
	 */
	public void setChromosomeList(List<Chromosome> chromosomeList) {
		this.chromosomeList = chromosomeList;
		createChromosomeHash();
		genomeLengthCalculation();
	}


	/**
	 * @param chromosomeList the chromosomeList to set
	 */
	public void updateChromosomeLength (List<Chromosome> chromosomeList) {
		for (Chromosome current: chromosomeList) {
			Chromosome existingChromosome = get(current.getName());
			if (existingChromosome != null) {
				existingChromosome.setLength(current.getLength());
			}
		}
		genomeLengthCalculation();
	}


	/**
	 * Creates the chromosome hash.
	 * Replaces the existing one.
	 */
	private void createChromosomeHash () {
		// Creates the chromosome names list
		List<String> chromosomeNames = new ArrayList<String>();
		for (Chromosome chromosome: chromosomeList) {
			chromosomeNames.add(chromosome.getName().toLowerCase());
		}

		// Creates the chromosome/index mapping
		chromosomeHash = new HashMap<String, Integer>();
		int cpt = 0;
		for (String s: chromosomeNames) {
			chromosomeHash.put(s, cpt);
			cpt++;
		}
	}


	/**
	 * Compute the size of the genome.
	 */
	private void genomeLengthCalculation () {
		genomeLength = 0;
		for (Chromosome chromosome: chromosomeList) {
			genomeLength += chromosome.getLength();
		}
	}


	/**
	 * @return the length of the genome in bp
	 */
	public long getGenomeLength() {
		return genomeLength;
	}


	/**
	 * @param index index of a {@link Chromosome}
	 * @return the first chromosome with the specified index
	 * @throws InvalidChromosomeException
	 */
	public Chromosome get(int index) {
		if (index < chromosomeList.size()) {
			return chromosomeList.get(index);
		}
		return null;
	}


	/**
	 * @param chromosomeName name of a {@link Chromosome}
	 * @return the first chromosome having the specified name
	 * @throws InvalidChromosomeException
	 */
	public Chromosome get(String chromosomeName) {
		// we put the chromosome name in lower case to avoid problems related to case sensitivity
		chromosomeName = chromosomeName.toLowerCase();

		// we get the index of associated to the chromosome name
		Integer index = chromosomeHash.get(chromosomeName);

		// if the index (thus the chromosome) exists, we can return the chromosome
		if (index != null) {
			return chromosomeList.get(index);
		}

		return null;
	}


	/**
	 * @param chromosome a {@link Chromosome}
	 * @return the index of the specified chromosome
	 * @throws InvalidChromosomeException
	 */
	public short getIndex(Chromosome chromosome) {
		return getIndex(chromosome.getName());
	}


	/**
	 * @param chromosomeName name of a chromosome.
	 * @return the index of the first chromosome having the specified name
	 * @throws InvalidChromosomeException
	 */
	public short getIndex(String chromosomeName) {
		// we put the chromosome name in lower case to avoid problems related to case sensitivity
		chromosomeName = chromosomeName.toLowerCase();

		// we get the index of associated to the chromosome name
		short index = (short)chromosomeHash.get(chromosomeName).intValue();

		return index;	// the index is returned
	}


	/**
	 * @return the size of the list of chromosome (ie: the number of chromosomes)
	 */
	public int size() {
		return chromosomeList.size();
	}


	/**
	 * @return an array containing all the chromosomes of the manager
	 */
	public Chromosome[] toArray() {
		// Initializes the chromosome array
		Chromosome[] returnArray = new Chromosome[chromosomeList.size()];

		// Fills the chromosome array
		returnArray = chromosomeList.toArray(returnArray);

		// Returns the array
		return returnArray;
	}


	/**
	 * @return the chromosomeList
	 */
	public List<Chromosome> getChromosomeList() {
		return chromosomeList;
	}


	/**
	 * @return the currentChromosome
	 */
	public Chromosome getCurrentChromosome() {
		if (currentChromosome == null) {
			return get(0);
		}
		return currentChromosome;
	}


	/**
	 * @param currentChromosome the currentChromosome to set
	 */
	public void setCurrentChromosome(Chromosome currentChromosome) {
		this.currentChromosome = currentChromosome;
	}


	@Override
	/**
	 * Constructor for chromosome manager iterator.
	 */
	public Iterator<Chromosome> iterator() {
		return new ChromosomeManagerIterator();
	}


	/**
	 * Iterator for chromosome manager.
	 * @author Julien Lajugie
	 * @author Nicolas Fourel
	 */
	private class ChromosomeManagerIterator implements Iterator<Chromosome> {

		private int currentIndex = 0;

		@Override
		public boolean hasNext() {
			if (currentIndex < chromosomeHash.size()) {
				return true;
			} else {
				return false;
			}
		}


		@Override
		public Chromosome next() throws NoSuchElementException {
			for (Chromosome chromosome: chromosomeList){
				// we put the chromosome name in lower case to avoid problems related to case sensitivity
				if (chromosomeHash.get(chromosome.getName().toLowerCase()) == currentIndex) {
					currentIndex++;
					return chromosome;
				}
			}
			throw new NoSuchElementException();
		}


		@Override
		public void remove() throws UnsupportedOperationException {
			throw new UnsupportedOperationException();
		}
	}
}
