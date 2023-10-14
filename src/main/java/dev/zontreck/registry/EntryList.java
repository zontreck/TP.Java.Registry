package dev.zontreck.registry;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;

import dev.zontreck.eventsbus.Bus;
import dev.zontreck.registry.events.RegistryEntryAddedEvent;
import dev.zontreck.registry.events.RegistryEntryRemovedEvent;

public class EntryList<T extends Entry> extends Entry implements List<T> {
	private List<T> value = new ArrayList<>();
	private EntryType type;

	public EntryList(String name) {
		super(EntryType.Array, name);
		Description = "Array";
	}

	@Override
	public void readValue(DataInputStream stream) throws IOException {
		type = EntryType.values()[stream.readByte()];

		if (type == EntryType.Empty) {
			return;
		}

		int count = stream.readInt();
		for (int i = 0; i < count; i++) {
			Entry subEntry = Entry.Read(stream, MyRoot, true, type, !EncodeDescription);
			value.add((T) subEntry);
			subEntry.Parent = this;
		}
	}

	@Override
	public void Write(DataOutputStream stream) throws IOException {
		super.Write(stream);

		if (!value.isEmpty()) {
			type = value.get(0).Type;
		}

		stream.writeByte(type.byteValue());

		if (type == EntryType.Empty) {
			return;
		}

		stream.writeInt(value.size());
		for (T entry : value) {
			entry.Write(stream);
		}
	}

	public void updateParents() {
		for (Entry entry : value) {
			entry.Parent = this;
		}
	}

	@Override
	public String PrettyPrint(int indent) {
		StringBuilder str = new StringBuilder(super.PrettyPrint(indent) + " [ " + value.size() + " ]\n");
		for (Entry entry : value) {
			str.append(entry.PrettyPrint(indent + 4)).append("\n");
		}
		return str.toString();
	}

	@Override
	public int size() {
		return value.size();
	}

	@Override
	public boolean isEmpty() {
		return value.isEmpty();
	}

	@Override
	public boolean contains(Object o) {
		return value.contains(o);
	}

	@Override
	public Iterator<T> iterator() {
		return value.iterator();
	}

	@Override
	public Object[] toArray() {
		return value.toArray();
	}

	@Override
	public <T1> T1[] toArray(T1[] a) {
		return value.toArray(a);
	}

	@Override
	public boolean add(T entry) {
		try {
			if (Bus.Post(new RegistryEntryAddedEvent(entry, getEntryPath() + "/" + entry.Name))) {
				return false;
			} else {
				entry.Parent = this;
				value.add(entry);
				updateRoots();
				return true;
			}
		} catch (InvocationTargetException e) {
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}

	public void updateRoots() {
		return;
	}

	@Override
	public boolean remove(Object o) {
		try {
			if (Bus.Post(new RegistryEntryRemovedEvent((Entry) o, ((Entry) o).getEntryPath(), this))) {
				return false;
			} else {
				Entry entry = (Entry) o;
				entry.Parent = null;
				entry.MyRoot = null;
				boolean ret = value.remove(o);
				updateRoots();
				return ret;
			}
		} catch (InvocationTargetException e) {
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		return value.containsAll(c);
	}

	@Override
	public boolean addAll(Collection<? extends T> c) {
		boolean modified = false;
		for (T entry : c) {
			if (add(entry)) {
				modified = true;
			}
		}
		return modified;
	}

	@Override
	public boolean addAll(int index, Collection<? extends T> c) {
		return value.addAll(index, c);
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		boolean modified = false;
		for (Object o : c) {
			if (remove(o)) {
				modified = true;
			}
		}
		return modified;
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		return value.retainAll(c);
	}

	@Override
	public void clear() {
		for (Entry item : value) {
			item.Parent = null;
			item.MyRoot = null;
		}
		value.clear();
	}

	@Override
	public T get(int index) {
		return value.get(index);
	}

	@Override
	public T set(int index, T element) {
		return value.set(index, element);
	}

	@Override
	public void add(int index, T element) {
		try {
			if (Bus.Post(new RegistryEntryAddedEvent(element, getEntryPath() + "/" + element.Name))) {
				return;
			} else {
				element.Parent = this;
				value.add(index, element);
				updateRoots();
			}
		} catch (InvocationTargetException e) {
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public T remove(int index) {
		Entry item = value.get(index);
		try {
			if (Bus.Post(new RegistryEntryRemovedEvent(item, item.getEntryPath(), this))) {
				return null;
			} else {
				item.Parent = null;
				item.MyRoot = null;
				updateRoots();
				return (T) item;
			}
		} catch (InvocationTargetException e) {
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public int indexOf(Object o) {
		return value.indexOf(o);
	}

	@Override
	public int lastIndexOf(Object o) {
		return value.lastIndexOf(o);
	}

	@Override
	public ListIterator<T> listIterator() {
		return value.listIterator();
	}

	@Override
	public ListIterator<T> listIterator(int index) {
		return value.listIterator(index);
	}

	@Override
	public List<T> subList(int fromIndex, int toIndex) {
		return value.subList(fromIndex, toIndex);
	}

	public T getNamed(String name) {
		for (T entry : value) {
			if (entry.Name.equals(name)) {
				return entry;
			}
		}
		throw new NoSuchElementException("Entry not found");
	}

	public void replaceEntries(Entry replaceWith) {
		if (replaceWith instanceof Key key) {
			value.addAll((Collection<? extends T>) key);
			updateRoots();
		}
	}

	public boolean HasNamedKey(String name) {
		return value.stream().anyMatch(entry -> entry.Name.equals(name));
	}
}
