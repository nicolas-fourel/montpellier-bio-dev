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
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import core.chromosome.Chromosome;
import core.chromosome.ProjectChromosome;


/**
 * This class represents a generic list organized by {@link Chromosome}
 * @param <T> type of the objects stored in the list
 * @author Julien Lajugie
 * @version 0.1
 */
public class ChromosomeArrayListOfLists<T> implements List<List<T>>, Cloneable, Serializable, ChromosomeListOfLists<T> {

	private static final long serialVersionUID = 3989560975472825193L; 	// generated ID
	private final ProjectChromosome projectChromosome; 					// Instance of the Chromosome Manager.
	private final List<List<T>> dataList;								// The lis of list of data.


	/**
	 * Constructor of {@link ChromosomeArrayListOfLists}.
	 */
	public ChromosomeArrayListOfLists() {
		super();
		this.projectChromosome = ProjectChromosome.getInstance();
		dataList = new ArrayList<List<T>>();
	}


	// Implentation of ChromosomeListOfLists<T>
	@Override
	public void add(Chromosome chromosome, T element) {
		get(projectChromosome.getIndex(chromosome)).add(element);
	}
	@Override
	public List<T> get(Chromosome chromosome) {
		return get(projectChromosome.getIndex(chromosome));
	}
	@Override
	public T get(int chromosomeIndex, int elementIndex) {
		return get(chromosomeIndex).get(elementIndex);
	}
	@Override
	public T get(Chromosome chromosome, int index) {
		return get(projectChromosome.getIndex(chromosome)).get(index);
	}
	@Override
	public void set(Chromosome chromosome, int index, T element) {
		get(projectChromosome.getIndex(chromosome)).set(index, element);
	}
	@Override
	public void set(Chromosome chromosome, List<T> list) {
		set(projectChromosome.getIndex(chromosome), list);
	}
	@Override
	public void set(int chromosomeIndex, int elementIndex, T element) {
		get(chromosomeIndex).set(elementIndex, element);
	}
	@Override
	public int size(int index) {
		return get(index).size();
	}
	@Override
	public int size(Chromosome chromosome) {
		return get(projectChromosome.getIndex(chromosome)).size();
	}


	// Implementation of List<List<T>>
	@Override
	public boolean add(List<T> e) {
		return dataList.add(e);
	}
	@Override
	public void add(int index, List<T> element) {
		dataList.add(index, element);
	}
	@Override
	public boolean addAll(Collection<? extends List<T>> c) {
		return dataList.addAll(c);
	}
	@Override
	public boolean addAll(int index, Collection<? extends List<T>> c) {
		return dataList.addAll(index, c);
	}
	@Override
	public void clear() {
		dataList.clear();
	}
	@Override
	public boolean contains(Object o) {
		return dataList.contains(o);
	}
	@Override
	public boolean containsAll(Collection<?> c) {
		return dataList.containsAll(c);
	}
	@Override
	public List<T> get(int index) {
		return dataList.get(index);
	}
	@Override
	public int indexOf(Object o) {
		return dataList.indexOf(o);
	}
	@Override
	public boolean isEmpty() {
		return dataList.isEmpty();
	}
	@Override
	public Iterator<List<T>> iterator() {
		return dataList.iterator();
	}
	@Override
	public int lastIndexOf(Object o) {
		return dataList.lastIndexOf(o);
	}
	@Override
	public ListIterator<List<T>> listIterator() {
		return dataList.listIterator();
	}
	@Override
	public ListIterator<List<T>> listIterator(int index) {
		return dataList.listIterator(index);
	}
	@Override
	public boolean remove(Object o) {
		return dataList.remove(o);
	}
	@Override
	public List<T> remove(int index) {
		return dataList.remove(index);
	}
	@Override
	public boolean removeAll(Collection<?> c) {
		return dataList.removeAll(c);
	}
	@Override
	public boolean retainAll(Collection<?> c) {
		return retainAll(c);
	}
	@Override
	public List<T> set(int index, List<T> element) {
		return dataList.set(index, element);
	}
	@Override
	public int size() {
		return dataList.size();
	}
	@Override
	public List<List<T>> subList(int fromIndex, int toIndex) {
		return dataList.subList(fromIndex, toIndex);
	}
	@Override
	public Object[] toArray() {
		return dataList.toArray();
	}
	@SuppressWarnings("hiding")
	@Override
	public <T> T[] toArray(T[] a) {
		return dataList.toArray(a);
	}
}
