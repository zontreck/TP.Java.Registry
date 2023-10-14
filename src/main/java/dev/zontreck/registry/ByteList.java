package dev.zontreck.registry;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class ByteList extends Entry implements List<Byte> {
	public ByteList(String name) {
		super(EntryType.ByteArray, name);
		EncodeDescription = false;
	}

	List<Byte> list = new ArrayList<>();

	@Override
	public void readValue(DataInputStream stream) throws IOException {
		int count = stream.readInt();
		for (int i = 0; i < count; i++) {
			list.add(stream.readByte());
		}
	}

	@Override
	public void Write(DataOutputStream stream) throws IOException {
		super.Write(stream);

		stream.write(size());
		for (byte b : list) {
			stream.write(b);
		}
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
	public Iterator<Byte> iterator() {
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
	public boolean add(Byte b) {
		return list.add(b);
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
	public boolean addAll(Collection<? extends Byte> collection) {
		return list.addAll(collection);
	}

	@Override
	public boolean addAll(int i, Collection<? extends Byte> collection) {
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
	public Byte get(int i) {
		return list.get(i);
	}

	@Override
	public Byte set(int i, Byte b) {
		return list.set(i, b);
	}

	@Override
	public void add(int i, Byte b) {
		list.add(i, b);
	}

	@Override
	public Byte remove(int i) {
		return list.remove(i);
	}

	@Override
	public int indexOf(Object o) {
		return list.indexOf(o);
	}

	@Override
	public int lastIndexOf(Object o) {
		return 0;
	}

	@Override
	public ListIterator<Byte> listIterator() {
		return list.listIterator();
	}

	@Override
	public ListIterator<Byte> listIterator(int i) {
		return list.listIterator(i);
	}

	@Override
	public List<Byte> subList(int i, int i1) {
		return list.subList(i, i1);
	}
}
