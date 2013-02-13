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
package main;

import core.action.hicTimingOri.MergeHiCTimingORIToFile;
import core.path.FileName;
import core.path.Path;
import core.script.Script;

/**
 * @author Nicolas Fourel
 * @version 0.1
 */
public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// Set file location
		Path.USER = Path.NICOLAS;
		Path.LOCATION = Path.WORK;


		Script.mergeHiCTimingWithORI(FileName.ORI_SORTED_RENAMED.toPath(), FileName.HIC_INTER_TIMING_GILBERT_NODUP_CLEAN.toPath(), Path.getPath("GSE35156_GSM862723_hESC_HindIII_HiC.nodup.summary.inter.timing-gilbert.nodup.clean.ori-names-all-clean.txt"), -1, MergeHiCTimingORIToFile.NAMES, true);
		Script.mergeHiCTimingWithORI(FileName.ORI_SORTED_RENAMED.toPath(), FileName.HIC_INTER_TIMING_GILBERT_NODUP_CLEAN.toPath(), Path.getPath("GSE35156_GSM862723_hESC_HindIII_HiC.nodup.summary.inter.timing-gilbert.nodup.clean.ori-names-inf-2kb-clean.txt"), 2000, MergeHiCTimingORIToFile.NAMES, true);
		Script.mergeHiCTimingWithORI(FileName.ORI_SORTED_RENAMED.toPath(), FileName.HIC_INTER_TIMING_GILBERT_NODUP_CLEAN.toPath(), Path.getPath("GSE35156_GSM862723_hESC_HindIII_HiC.nodup.summary.inter.timing-gilbert.nodup.clean.ori-names-inf-5kb-clean.txt"), 5000, MergeHiCTimingORIToFile.NAMES, true);

		Script.mergeHiCTimingWithORI(FileName.ORI_SORTED_RENAMED.toPath(), FileName.HIC_INTER_TIMING_GILBERT_NODUP_CLEAN.toPath(), Path.getPath("GSE35156_GSM862723_hESC_HindIII_HiC.nodup.summary.inter.timing-gilbert.nodup.clean.ori-number-all-clean.txt"), -1, MergeHiCTimingORIToFile.NUMBER, true);
		Script.mergeHiCTimingWithORI(FileName.ORI_SORTED_RENAMED.toPath(), FileName.HIC_INTER_TIMING_GILBERT_NODUP_CLEAN.toPath(), Path.getPath("GSE35156_GSM862723_hESC_HindIII_HiC.nodup.summary.inter.timing-gilbert.nodup.clean.ori-number-inf-2kb-clean.txt"), 2000, MergeHiCTimingORIToFile.NUMBER, true);
		Script.mergeHiCTimingWithORI(FileName.ORI_SORTED_RENAMED.toPath(), FileName.HIC_INTER_TIMING_GILBERT_NODUP_CLEAN.toPath(), Path.getPath("GSE35156_GSM862723_hESC_HindIII_HiC.nodup.summary.inter.timing-gilbert.nodup.clean.ori-number-inf-5kb-clean.txt"), 5000, MergeHiCTimingORIToFile.NUMBER, true);



		Script.mergeHiCTimingWithORI(FileName.ORI_SORTED_RENAMED.toPath(), FileName.HIC_INTRA_TIMING_GILBERT_REMOVAL_INF_100KB_NODUP_CLEAN.toPath(), Path.getPath("GSE35156_GSM862723_hESC_HindIII_HiC.nodup.summary.intra.timing-gilbert.removal-inf-100kb.nodup.clean.ori-names-all-clean.txt"), -1, MergeHiCTimingORIToFile.NAMES, true);
		Script.mergeHiCTimingWithORI(FileName.ORI_SORTED_RENAMED.toPath(), FileName.HIC_INTRA_TIMING_GILBERT_REMOVAL_INF_100KB_NODUP_CLEAN.toPath(), Path.getPath("GSE35156_GSM862723_hESC_HindIII_HiC.nodup.summary.intra.timing-gilbert.removal-inf-100kb.nodup.clean.ori-names-inf-2kb-clean.txt"), 2000, MergeHiCTimingORIToFile.NAMES, true);
		Script.mergeHiCTimingWithORI(FileName.ORI_SORTED_RENAMED.toPath(), FileName.HIC_INTRA_TIMING_GILBERT_REMOVAL_INF_100KB_NODUP_CLEAN.toPath(), Path.getPath("GSE35156_GSM862723_hESC_HindIII_HiC.nodup.summary.intra.timing-gilbert.removal-inf-100kb.nodup.clean.ori-names-inf-5kb-clean.txt"), 5000, MergeHiCTimingORIToFile.NAMES, true);

		Script.mergeHiCTimingWithORI(FileName.ORI_SORTED_RENAMED.toPath(), FileName.HIC_INTRA_TIMING_GILBERT_REMOVAL_INF_100KB_NODUP_CLEAN.toPath(), Path.getPath("GSE35156_GSM862723_hESC_HindIII_HiC.nodup.summary.intra.timing-gilbert.removal-inf-100kb.nodup.clean.ori-number-all-clean.txt"), -1, MergeHiCTimingORIToFile.NUMBER, true);
		Script.mergeHiCTimingWithORI(FileName.ORI_SORTED_RENAMED.toPath(), FileName.HIC_INTRA_TIMING_GILBERT_REMOVAL_INF_100KB_NODUP_CLEAN.toPath(), Path.getPath("GSE35156_GSM862723_hESC_HindIII_HiC.nodup.summary.intra.timing-gilbert.removal-inf-100kb.nodup.clean.ori-number-inf-2kb-clean.txt"), 2000, MergeHiCTimingORIToFile.NUMBER, true);
		Script.mergeHiCTimingWithORI(FileName.ORI_SORTED_RENAMED.toPath(), FileName.HIC_INTRA_TIMING_GILBERT_REMOVAL_INF_100KB_NODUP_CLEAN.toPath(), Path.getPath("GSE35156_GSM862723_hESC_HindIII_HiC.nodup.summary.intra.timing-gilbert.removal-inf-100kb.nodup.clean.ori-number-inf-5kb-clean.txt"), 5000, MergeHiCTimingORIToFile.NUMBER, true);
	}

}
