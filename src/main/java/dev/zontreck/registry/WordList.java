package dev.zontreck.registry;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class WordList extends Entry implements List<String> {
	public WordList(String name) {
		super(EntryType.WordArray, name);
		EncodeDescription = false;
	}

	List<String> list = new ArrayList<>();

	@Override
	public void readValue(DataInputStream stream) throws IOException {
		int count = stream.readInt();

		for (int i = 0; i < count; i++) {
			list.add(stream.readUTF());
		}
	}

	@Override
	public void Write(DataOutputStream stream) throws IOException {
		super.Write(stream);
		stream.writeInt(size());
		for (String s : list) {
			stream.writeUTF(s);
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
	public Iterator<String> iterator() {
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
	public boolean add(String s) {
		return list.add(s);
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
	public boolean addAll(Collection<? extends String> collection) {
		return list.addAll(collection);
	}

	@Override
	public boolean addAll(int i, Collection<? extends String> collection) {
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
	public String get(int i) {
		return list.get(i);
	}

	@Override
	public String set(int i, String s) {
		return list.set(i, s);
	}

	@Override
	public void add(int i, String s) {
		list.add(i, s);
	}

	@Override
	public String remove(int i) {
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
	public ListIterator<String> listIterator() {
		return list.listIterator();
	}

	@Override
	public ListIterator<String> listIterator(int i) {
		return list.listIterator(i);
	}

	@Override
	public List<String> subList(int i, int i1) {
		return list.subList(i, i1);
	}
}
