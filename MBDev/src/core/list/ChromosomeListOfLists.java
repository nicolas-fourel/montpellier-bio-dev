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
package core.list;

import java.io.Serializable;
import java.util.List;

import core.chromosome.Chromosome;


/**
 * This class represents a generic list organized by chromosome
 * @param <T> type of the objects stored in the list
 * @author Nicolas Fourel
 * @version 0.1
 */
public interface ChromosomeListOfLists<T> extends Cloneable, Serializable, List<List<T>> {


	/**
	 * Adds an element to the list of the specified chromosome
	 * @param String chromosome of the item
	 * @param element element to add
	 */
	public void add(Chromosome chromosome, T element);


	/**
	 * @param chromosome a chromosome
	 * @return the list associated to the specified chromosome
	 */
	public List<T> get(Chromosome chromosome);


	/**
	 * @param chromosome index of a chromosome
	 * @param index
	 * @return the data with the specified index on the specified chromosome
	 */
	public T get(Chromosome chromosome, int index);


	/**
	 * @param chromosomeIndex index of a chromosome
	 * @param elementIndex
	 * @return the data with the specified index on the specified chromosome
	 */
	public T get(int chromosomeIndex, int elementIndex);


	/**
	 * Sets the element on the specified index of the specified chromosome
	 * @param chromosome a chromosome
	 * @param index
	 * @param element element to set
	 */
	public void set(Chromosome chromosome, int index, T element);



	/**
	 * Sets the list of elements on the specified chromosome
	 * @param chromosome a chromosome
	 * @param list list to set
	 */
	public void set(Chromosome chromosome, List<T> list);


	/**
	 * Sets the element on the specified index of the specified chromosome
	 * @param chromosomeIndex
	 * @param elementIndex
	 * @param element value to set
	 */
	public void set(int chromosomeIndex, int elementIndex, T element);


	/**
	 * @param index index of a chromosome
	 * @return the size of the list for the specified chromosome
	 */
	public int size(int index);


	/**
	 * @param chromosome
	 * @return the size of the list for a specified chromosome
	 */
	public int size(Chromosome chromosome);
}
