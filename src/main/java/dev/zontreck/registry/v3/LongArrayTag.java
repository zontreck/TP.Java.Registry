package dev.zontreck.registry.v3;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class LongArrayTag extends Tag implements List<Long> {
	List<Long> list;

	public LongArrayTag() {
		list = new ArrayList<>();
	}

	public LongArrayTag withAddValue(long val) {
		add(val);
		return this;
	}

	public LongArrayTag withRemoveValue(long val) {
		remove(val);
		return this;
	}

	@Override
	public Type getType() {
		return Type.LongArray;
	}

	@Override
	public String getCanonicalName() {
		return "TAG_LongArray";
	}

	@Override
	public void WriteValue(DataOutputStream dos) throws IOException {
		dos.writeInt(size());

		for (long L : this) {
			dos.writeLong(L);
		}
	}

	@Override
	public void ReadValue(DataInputStream dis) throws IOException {
		int count = dis.readInt();
		for (int i = 0; i < count; i++) {
			add(dis.readLong());
		}
	}

	@Override
	public String PrettyPrint(int indent, String name) {
		String builder = super.PrettyPrint(indent, name);
		builder += ": [";

		for (int i = 0; i < size(); i++) {
			builder += get(i);
			if (i + 1 >= size()) {
				builder += "]";
			} else
				builder += ",";
		}

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
	public Iterator<Long> iterator() {
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
	public boolean add(Long aLong) {
		return list.add(aLong);
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
	public boolean addAll(Collection<? extends Long> collection) {
		return list.addAll(collection);
	}

	@Override
	public boolean addAll(int i, Collection<? extends Long> collection) {
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
	public Long get(int i) {
		return list.get(i);
	}

	@Override
	public Long set(int i, Long aLong) {
		return list.set(i, aLong);
	}

	@Override
	public void add(int i, Long aLong) {
		list.add(i, aLong);
	}

	@Override
	public Long remove(int i) {
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
	public ListIterator<Long> listIterator() {
		return list.listIterator();
	}

	@Override
	public ListIterator<Long> listIterator(int i) {
		return list.listIterator(i);
	}

	@Override
	public List<Long> subList(int i, int i1) {
		return list.subList(i, i1);
	}
}
