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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import core.action.Action;
import core.chromosome.ProjectChromosome;
import core.fileLine.ORILine;
import core.list.ChromosomeListOfLists;
import core.list.IntArrayAsIntegerList;


/**
 * @author Nicolas Fourel
 * @version 0.1
 */
public class MergeHiCORIToChromosomeMap extends Action {

	/** Generated default serial version ID */
	private static final long serialVersionUID = 4495558610088027673L;

	private final ProjectChromosome projectChromosome;			// The instance of the chromosome project.
	private final List<Map<Integer, List<ORILine>>> fullList;	// The list between Ori and HiC.

	private final ChromosomeListOfLists<ORILine> oriList;		// The Ori list.
	private final ChromosomeListOfLists<Integer> hicList;		// The HiC list.
	private final int length;


	/**
	 * Constructor of {@link MergeHiCORIToChromosomeMap}
	 * @param oriList	the Ori list
	 * @param hicList	the HiC list
	 * @param threshold	the length threshold (no threshold: -1)
	 */
	public MergeHiCORIToChromosomeMap(ChromosomeListOfLists<ORILine> oriList, ChromosomeListOfLists<Integer> hicList, int threshold) {
		actionName = "Merge HiC with ORI information to a map.";
		this.oriList = oriList;
		this.hicList = hicList;
		length = threshold;
		fullList = new ArrayList<>();

		projectChromosome = ProjectChromosome.getInstance();
		int chromosomeNumber = projectChromosome.getChromosomeList().size();
		for (int i = 0; i < chromosomeNumber; i++) {
			fullList.add(new HashMap<Integer, List<ORILine>>());
		}
	}


	@Override
	protected Object compute() {
		for (List<ORILine> currentORIList : oriList) {
			for (ORILine oriLine : currentORIList) {
				int chromosomeIndex = projectChromosome.getIndex(oriLine
						.getChromosome());
				IntArrayAsIntegerList currentHICList = (IntArrayAsIntegerList) hicList.get(chromosomeIndex);
				int hicIndex = currentHICList.getClosestIndex(oriLine.getStartPosition());
				int hicValue = currentHICList.get(hicIndex);

				Map<Integer, List<ORILine>> currentMap = fullList.get(chromosomeIndex);
				if (currentMap.get(hicValue) == null) {
					currentMap.put(hicValue, new ArrayList<ORILine>());
				}

				if (canBeInserted(hicValue, oriLine.getStartPosition())) {
					currentMap.get(hicValue).add(oriLine);
				}
			}
		}
		return null;
	}


	/**
	 * Check if the ORI can be related to the HiC position
	 * @param hicPosition	the HiC position
	 * @param oriPosition	the Ori position
	 * @return	true if the position can be inserted, false otherwise
	 */
	private boolean canBeInserted (int hicPosition, int oriPosition) {
		if (length == -1) {
			return true;
		}

		int diff = Math.abs(hicPosition - oriPosition);
		if (diff < length) {
			return true;
		}

		return false;
	}


	/**
	 * @return the list
	 */
	public List<Map<Integer, List<ORILine>>> getList() {
		return fullList;
	}


	@Override
	protected void doFirst() {}


	@Override
	protected void doAtTheEnd() {}

}
