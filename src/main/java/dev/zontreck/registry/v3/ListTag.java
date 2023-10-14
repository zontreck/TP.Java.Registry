package dev.zontreck.registry.v3;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class ListTag extends Tag implements List<Tag> {
	private List<Tag> list;
	private Type subType = Type.End;

	public ListTag() {
		list = new ArrayList<>();
	}

	@Override
	public Type getType() {
		return Type.List;
	}

	@Override
	public String getCanonicalName() {
		return "TAG_List";
	}

	@Override
	public void WriteValue(DataOutputStream dos) throws IOException {
		dos.writeByte(subType.value);
		dos.writeInt(size());

		if (size() > 0) {
			for (Tag T : this) {
				T.WriteValue(dos);
			}
		}
	}

	@Override
	public void ReadValue(DataInputStream dis) throws IOException {
		Type ltype = Type.valueOf(dis.readByte());
		int count = dis.readInt();
		if (count <= 0) {
			return;
		} else {
			subType = ltype;
			for (int i = 0; i < count; i++) {
				Tag inst = TagTypeRegistry.getInstanceOf(subType);
				inst.ReadValue(dis);

				add(inst);

			}
		}
	}

	@Override
	public String PrettyPrint(int indent) {
		String builder = super.PrettyPrint(indent);
		builder += ": [\n";

		for (Tag T : this) {
			builder += T.PrettyPrint(indent + 1) + ",\n";
		}

		builder += MakeIndent(indent) + "]";

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
	public Iterator<Tag> iterator() {
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
	public boolean add(Tag tag) {
		if (tag.getType() == subType || subType == Type.End) {
			subType = tag.getType();
			return list.add(tag);

		}

		return false;
	}

	@Override
	public boolean remove(Object o) {
		boolean result = list.remove(o);

		if (size() <= 0) {
			subType = Type.End;
		}
		return result;
	}

	@Override
	public boolean containsAll(Collection<?> collection) {
		return list.containsAll(collection);
	}

	@Override
	public boolean addAll(Collection<? extends Tag> collection) {
		return list.addAll(collection);
	}

	@Override
	public boolean addAll(int i, Collection<? extends Tag> collection) {
		return list.addAll(i, collection);
	}

	@Override
	public boolean removeAll(Collection<?> collection) {
		boolean result = list.removeAll(collection);
		if (size() <= 0) {
			subType = Type.End;
		}
		return result;
	}

	@Override
	public boolean retainAll(Collection<?> collection) {
		return list.retainAll(collection);
	}

	@Override
	public void clear() {
		subType = Type.End;
		list.clear();
	}

	@Override
	public Tag get(int i) {
		return list.get(i);
	}

	@Override
	public Tag set(int i, Tag tag) {
		if (subType == tag.getType() || subType == Type.End) {
			subType = tag.getType();
			return list.set(i, tag);
		}
		return null;
	}

	@Override
	public void add(int i, Tag tag) {

		if (subType == tag.getType() || subType == Type.End) {
			subType = tag.getType();
			list.add(i, tag);
		}
	}

	@Override
	public Tag remove(int i) {
		Tag result = list.remove(i);
		if (size() <= 0) {
			subType = Type.End;
		}
		return result;
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
	public ListIterator<Tag> listIterator() {
		return list.listIterator();
	}

	@Override
	public ListIterator<Tag> listIterator(int i) {
		return list.listIterator(i);
	}

	@Override
	public List<Tag> subList(int i, int i1) {
		return list.subList(i, i1);
	}
}
