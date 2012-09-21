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
public enum FileName {

	// ORI files
	ORI_SORTED_RENAMED ("GSM927236_hESC_H9_ori.csv.sorted.renamed.txt"),
	ORI_SORTED ("GSM927236_hESC_H9_ori.csv.sorted.txt"),

	// HiC files
	HIC_INTER ("GSE35156_GSM862723_hESC_HindIII_HiC.nodup.summary.inter.txt"),
	HIC_INTRA ("GSE35156_GSM862723_hESC_HindIII_HiC.nodup.summary.intra.txt"),

	// HiC/Timing files - Desprat
	HIC_INTER_TIMING_DESPRAT_NODUP_CLEAN ("GSE35156_GSM862723_hESC_HindIII_HiC.nodup.summary.inter.timing-desprat.nodup.clean.txt"),
	HIC_INTRA_TIMING_DESPRAT_REMOVAL_INF_100KB_NODUP_CLEAN ("GSE35156_GSM862723_hESC_HindIII_HiC.nodup.summary.intra.timing-desprat.removal-inf-100kb.nodup.clean.txt"),
	HIC_INTRA_TIMING_DESPRAT ("GSE35156_GSM862723_hESC_HindIII_HiC.nodup.summary.intra.timing-desprat.txt"),

	// HiC/Timing files - Gilbert
	HIC_INTER_TIMING_GILBERT_NODUP_CLEAN ("GSE35156_GSM862723_hESC_HindIII_HiC.nodup.summary.inter.timing-gilbert.nodup.clean.txt"),
	HIC_INTRA_TIMING_GILBERT_REMOVAL_INF_100KB_NODUP_CLEAN ("GSE35156_GSM862723_hESC_HindIII_HiC.nodup.summary.intra.timing-gilbert.removal-inf-100kb.nodup.clean.txt"),
	HIC_INTRA_TIMING_GILBERT ("GSE35156_GSM862723_hESC_HindIII_HiC.nodup.summary.intra.timing-gilbert.txt"),

	// HiC/Timing/Ori files - Gilbert
	HIC_INTER_TIMING_GILBERT_NODUP_CLEAN_ORI_NUMBER ("GSE35156_GSM862723_hESC_HindIII_HiC.nodup.summary.inter.timing-gilbert.nodup.clean.ori-number.txt"),

	// Timing files
	TIMING_FILE_01 ("GSM500936_294986_H9hESC_532.spair"),
	TIMING_FILE_02 ("GSM500936_294986_H9hESC_635.spair");


	private final String name;


	/**
	 * Private constructor. Creates an instance of {@link FileName}
	 * @param name
	 */
	private FileName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return name;
	}


	/**
	 * @return the full path of the file
	 */
	public String toPath () {
		return Path.getPath(name);
	}

}
