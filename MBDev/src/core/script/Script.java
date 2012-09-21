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
package core.script;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import core.action.hic.ExtractChromosomeRegion;
import core.action.hic.MergeHiCWithTiming;
import core.action.hicTiming.CleanHiCTiming;
import core.action.hicTiming.ExtractWindowsFromHicTiming;
import core.action.hicTiming.RemoveClosePositionHiCTiming;
import core.action.hicTiming.RemoveDuplicateHiCTiming;
import core.action.hicTimingOri.MergeHiCORIToChromosomeMap;
import core.action.hicTimingOri.MergeHiCTimingORIToFile;
import core.action.ori.ORIListExtractor;
import core.action.ori.ORIRenamer;
import core.action.ori.ORISort;
import core.action.timing.TimingListExtractor;
import core.chromosome.ProjectChromosome;
import core.fileLine.ORILine;
import core.fileLine.TimingLine;
import core.list.ChromosomeListOfLists;

public class Script {


	/**
	 * Split a HiC file regarding the two chromosomes of a line: inter or intra
	 * @param inputPath		the input file path
	 * @param outputPath	the output file path
	 * @param extractOption	inter or intra chromosome (see {@link ExtractChromosomeRegion})
	 */
	public static void splitHiC(String inputPath, String outputPath, int extractOption) {
		// Input file
		File inputFile = new File(inputPath);

		// Output file
		File outputFile = new File(outputPath);

		// Perform the action
		ExtractChromosomeRegion action = new ExtractChromosomeRegion(inputFile,	outputFile, extractOption);
		action.actionPerformed(null);
	}


	/**
	 * Merge HiC data with Timing data
	 * @param timingInputPath		the input path of the timing file
	 * @param hicInputPath			the input path of the HiC file
	 * @param hicTimingOutputPath	the output path of the HiC/timing file
	 * @param extractOption			Desprat or Gilbert (see {@link TimingListExtractor})
	 */
	public static void mergeHiCWithFilTiming(String timingInputPath, String hicInputPath, String hicTimingOutputPath, int extractOption) {
		// Timing action
		File timingInputFile = new File(timingInputPath);
		TimingListExtractor action = new TimingListExtractor(timingInputFile, extractOption);
		action.actionPerformed(null);

		// Get the timing information
		Map<String, List<TimingLine>> map = action.getMap();

		// HiC Action
		File hicInputFile = new File(hicInputPath);
		File hicOutputFile = new File(hicTimingOutputPath);
		MergeHiCWithTiming hicAction = new MergeHiCWithTiming(hicInputFile, hicOutputFile, map);
		hicAction.actionPerformed(null);
	}


	/**
	 * Remove duplicated lines in HiC/Timing file
	 * @param inputPath		the input file path
	 * @param outputPath	the output file path
	 */
	public static void removeHiCTimingDuplicate(String inputPath, String outputPath) {
		File inputFile = new File(inputPath);
		File outputFile = new File(outputPath);
		RemoveDuplicateHiCTiming action = new RemoveDuplicateHiCTiming(inputFile, outputFile);
		action.actionPerformed(null);
	}


	/**
	 * Cleans up a HiC/Timing file (delete useless columns)
	 * @param inputPath		the input file path
	 * @param outputPath	the output file path
	 */
	public static void cleanUpHiCTimingFile(String inputPath, String outputPath) {
		File inputFile = new File(inputPath);
		File outputFile = new File(outputPath);
		CleanHiCTiming action = new CleanHiCTiming(inputFile, outputFile);
		action.actionPerformed(null);
	}


	/**
	 * Remove HiC/Timing lines where positions are two close (make sens for HiC/Timing intra chromosome files)
	 * @param inputPath		the input file path
	 * @param outputPath	the output file path
	 */
	public static void removeCloseIntraLines(String inputPath, String outputPath) {
		File inputFile = new File(inputPath);
		File outputFile = new File(outputPath);
		RemoveClosePositionHiCTiming action = new RemoveClosePositionHiCTiming(inputFile, outputFile, 100000);
		action.actionPerformed(null);
	}


	/**
	 * Sort a ORI file
	 * @param inputPath		the input file path
	 * @param outputPath	the output file path
	 */
	public static void sortORIFile(String inputPath, String outputPath) {
		File inputFile = new File(inputPath);
		ORIListExtractor oriExtract = new ORIListExtractor(inputFile);
		oriExtract.actionPerformed(null);
		ChromosomeListOfLists<ORILine> list = oriExtract.getList();

		File outputFile = new File(outputPath);
		ORISort oriSort = new ORISort(list, outputFile);
		oriSort.actionPerformed(null);
	}


	/**
	 * Rename a ORI file, put a ID code on each line (eg: AAAAA, AAAAB, AAAAC .... ZZZZZ)
	 * @param inputPath		the input file path
	 * @param outputPath	the output file path
	 */
	public static void renameORIFile (String inputPath, String outputPath) {
		File inputFile = new File(inputPath);
		ORIListExtractor oriExtract = new ORIListExtractor(inputFile);
		oriExtract.actionPerformed(null);
		ChromosomeListOfLists<ORILine> list = oriExtract.getList();

		File outputFile = new File(outputPath);
		ORIRenamer oriRename = new ORIRenamer(outputFile, list);
		oriRename.actionPerformed(null);
	}


	/**
	 * Merge HiC/Timing data with Ori data
	 * @param oriInputPath				the Ori input file
	 * @param hicTimingInputPath		the HiC/Timing input file
	 * @param hicTimingOriOutputFile	the HiC/Timing/Ori output file
	 * @param threshold	the length threshold (no threshold: -1)
	 */
	public static void mergeHiCTimingWithORI(String oriInputPath, String hicTimingInputPath, String hicTimingOriOutputFile, int threshold) {
		File oriInputFile = new File(oriInputPath);
		ORIListExtractor oriExtract = new ORIListExtractor(oriInputFile);
		oriExtract.actionPerformed(null);
		ChromosomeListOfLists<ORILine> oriList = oriExtract.getList();

		File inputFile = new File(hicTimingInputPath);
		ExtractWindowsFromHicTiming hicExtract = new ExtractWindowsFromHicTiming(inputFile);
		hicExtract.actionPerformed(null);
		ChromosomeListOfLists<Integer> hicList = hicExtract.getList();

		MergeHiCORIToChromosomeMap mergeMap = new MergeHiCORIToChromosomeMap(oriList, hicList, threshold);
		mergeMap.actionPerformed(null);
		List<Map<Integer, List<ORILine>>> map = mergeMap.getList();

		File outputFile = new File(hicTimingOriOutputFile);
		MergeHiCTimingORIToFile mergeFile = new MergeHiCTimingORIToFile(inputFile, outputFile, map);
		mergeFile.actionPerformed(null);

		int totalORI = 0;
		int totalHiC = 0;
		int totalMap = 0;
		int totalMapORI = 0;
		int chromosomeNumber = ProjectChromosome.getInstance()
				.getChromosomeList().size();
		for (int i = 0; i < chromosomeNumber; i++) {
			System.out.println("Chromosome " + i + ":");
			System.out.println("ORI: " + oriList.get(i).size());
			System.out.println("HiC: " + hicList.get(i).size());
			System.out.println("Map: " + map.get(i).size());

			totalORI += oriList.get(i).size();
			totalHiC += hicList.get(i).size();
			totalMap += map.get(i).size();
			List<Integer> listHiCPos = new ArrayList<>(map.get(i).keySet());
			for (Integer key : listHiCPos) {
				List<ORILine> current = map.get(i).get(key);
				totalMapORI += current.size();
			}

		}
		System.out.println("Total ORI: " + totalORI);
		System.out.println("Total HiC: " + totalHiC);
		System.out.println("Total map: " + totalMap);
		System.out.println("Total ORI from map: " + totalMapORI);

		/*
		 * File outputFile = new File(
		 * "O:\\Montpellier\\GSE35156_GSM862723_hESC_HindIII_HiC.nodup.summary.inter.timing.nodup.clean.ori.txt"
		 * ); MergeHiCTimingWithORI mergeORI = new
		 * MergeHiCTimingWithORI(inputFile, outputFile, oriList);
		 * mergeORI.actionPerformed(null);
		 */
	}
}
