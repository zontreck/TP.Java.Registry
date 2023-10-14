package dev.zontreck.registry.v3;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class IntArrayTag extends Tag implements List<Integer> {
	List<Integer> list;

	public IntArrayTag() {
		list = new ArrayList<>();
	}

	@Override
	public Type getType() {
		return Type.IntArray;
	}

	@Override
	public String getCanonicalName() {
		return "TAG_IntArray";
	}

	@Override
	public void WriteValue(DataOutputStream dos) throws IOException {
		dos.writeInt(size());
		for (int i : list) {
			dos.writeInt(i);
		}
	}

	@Override
	public void ReadValue(DataInputStream dis) throws IOException {
		int count = dis.readInt();
		for (int i = 0; i < count; i++) {
			list.add(dis.readInt());
		}
	}

	@Override
	public String PrettyPrint(int indent, String name) {
		String builder = super.PrettyPrint(indent, name);
		builder += ": [";

		for (int i = 0; i < size(); i++) {
			builder += list.get(i);
			if (i + 1 >= size()) {
				builder += "";
			} else
				builder += ",";
		}

		builder += "]";
		return builder;
	}

	@Override
	public int size() {
		return list.size();
	}

	@Override
	public boolean isEmpty() {
		return list.isEmpty();
	}

	@Override
	public boolean contains(Object o) {
		return list.contains(o);
	}

	@Override
	public Iterator<Integer> iterator() {
		return list.iterator();
	}

	@Override
	public Object[] toArray() {
		return list.toArray();
	}

	@Override
	public <T> T[] toArray(T[] ts) {
		return list.toArray(ts);
	}

	@Override
	public boolean add(Integer integer) {
		return list.add(integer);
	}

	@Override
	public boolean remove(Object o) {
		return list.remove(o);
	}

	@Override
	public boolean containsAll(Collection<?> collection) {
		return list.containsAll(collection);
	}

	@Override
	public boolean addAll(Collection<? extends Integer> collection) {
		return list.addAll(collection);
	}

	@Override
	public boolean addAll(int i, Collection<? extends Integer> collection) {
		return list.addAll(i, collection);
	}

	@Override
	public boolean removeAll(Collection<?> collection) {
		return list.removeAll(collection);
	}

	@Override
	public boolean retainAll(Collection<?> collection) {
		return list.retainAll(collection);
	}

	@Override
	public void clear() {
		list.clear();
	}

	@Override
	public Integer get(int i) {
		return list.get(i);
	}

	@Override
	public Integer set(int i, Integer integer) {
		return list.set(i, integer);
	}

	@Override
	public void add(int i, Integer integer) {
		list.add(i, integer);
	}

	@Override
	public Integer remove(int i) {
		return list.remove(i);
	}

	@Override
	public int indexOf(Object o) {
		return list.indexOf(o);
	}

	@Override
	public int lastIndexOf(Object o) {
		return list.lastIndexOf(o);
	}

	@Override
	public ListIterator<Integer> listIterator() {
		return list.listIterator();
	}

	@Override
	public ListIterator<Integer> listIterator(int i) {
		return list.listIterator(i);
	}

	@Override
	public List<Integer> subList(int i, int i1) {
		return list.subList(i, i1);
	}
}
