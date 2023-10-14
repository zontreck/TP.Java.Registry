package dev.zontreck.registry.v3;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.*;

public class ListTag extends Tag implements List<Tag> {
	private List<Tag> list;
	private Type subType = Type.End;
	public ListTag()
	{
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

		if(size()>0)
		{
			for (Tag T :
				this) {
				T.WriteValue(dos);
			}
		}
	}

	@Override
	public void ReadValue(DataInputStream dis) throws IOException {
		Type ltype = Type.valueOf(dis.readByte());
		int count = dis.readInt();
		if(count <= 0)
		{
			return;
		}else {
			subType = ltype;
			for(int i = 0; i < count; i++)
			{
				Tag inst = TagTypeRegistry.getInstanceOf(subType);
				inst.ReadValue(dis);

				add(inst);

			}
		}
	}

	@Override
	public int size() {
		return 0;
	}

	@Override
	public boolean isEmpty() {
		return false;
	}

	@Override
	public boolean contains(Object o) {
		return false;
	}

	@Override
	public Iterator<Tag> iterator() {
		return null;
	}

	@Override
	public Object[] toArray() {
		return new Object[0];
	}

	@Override
	public <T> T[] toArray(T[] ts) {
		return null;
	}

	@Override
	public boolean add(Tag tag) {
		return false;
	}

	@Override
	public boolean remove(Object o) {
		return false;
	}

	@Override
	public boolean containsAll(Collection<?> collection) {
		return false;
	}

	@Override
	public boolean addAll(Collection<? extends Tag> collection) {
		return false;
	}

	@Override
	public boolean addAll(int i, Collection<? extends Tag> collection) {
		return false;
	}

	@Override
	public boolean removeAll(Collection<?> collection) {
		return false;
	}

	@Override
	public boolean retainAll(Collection<?> collection) {
		return false;
	}

	@Override
	public void clear() {

	}

	@Override
	public Tag get(int i) {
		return null;
	}

	@Override
	public Tag set(int i, Tag tag) {
		return null;
	}

	@Override
	public void add(int i, Tag tag) {

	}

	@Override
	public Tag remove(int i) {
		return null;
	}

	@Override
	public int indexOf(Object o) {
		return 0;
	}

	@Override
	public int lastIndexOf(Object o) {
		return 0;
	}

	@Override
	public ListIterator<Tag> listIterator() {
		return null;
	}

	@Override
	public ListIterator<Tag> listIterator(int i) {
		return null;
	}

	@Override
	public List<Tag> subList(int i, int i1) {
		return null;
	}
}
