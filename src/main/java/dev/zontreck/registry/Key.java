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

public class Key extends Entry implements List<Entry> {
    private List<Entry> _entries = new ArrayList<>();

    public Key(String name, Entry parent) {
        super(EntryType.Key, name);
        Parent = parent;
    }

    Key(String name) {
        super(EntryType.Key, name);
        Parent = null;
    }

    private Key() {
        super(EntryType.Root, "root");
    }

    @Override
    public int size() {
        return _entries.size();
    }

    @Override
    public boolean isEmpty() {
        return _entries.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return _entries.contains(o);
    }

    @Override
    public Iterator<Entry> iterator() {
        return _entries.iterator();
    }

    @Override
    public Object[] toArray() {
        return _entries.toArray();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return _entries.toArray(a);
    }

    @Override
    public boolean add(Entry entry) {
        try {
            if (Bus.Post(new RegistryEntryAddedEvent(entry, getEntryPath() + "/" + entry.Name))) {
                return false;
            } else {
                _entries.add(entry);
                entry.Parent = this;
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
        if (Type == EntryType.Root)
            setRoot(this);
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
                boolean ret = _entries.remove(o);
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
        return _entries.containsAll(c);
    }

    @Override
    public boolean addAll(Collection<? extends Entry> c) {
        boolean modified = false;
        for (Entry entry : c) {
            if (add(entry)) {
                modified = true;
            }
        }
        return modified;
    }

    @Override
    public boolean addAll(int index, Collection<? extends Entry> c) {
        return _entries.addAll(index, c);
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
        return _entries.retainAll(c);
    }

    @Override
    public void clear() {
        for (Entry item : _entries) {
            item.Parent = null;
            item.MyRoot = null;
        }
        _entries.clear();
    }

    @Override
    public Entry get(int index) {
        return _entries.get(index);
    }

    @Override
    public Entry set(int index, Entry element) {
        element.Parent = this;
        return _entries.set(index, element);
    }

    @Override
    public void add(int index, Entry element) {
        try {
            if (Bus.Post(new RegistryEntryAddedEvent(element, getEntryPath() + "/" + element.Name))) {
                return;
            } else {
                _entries.add(index, element);
                element.Parent = this;
                updateRoots();
            }
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Entry remove(int index) {
        Entry item = _entries.get(index);
        try {
            if (Bus.Post(new RegistryEntryRemovedEvent(item, item.getEntryPath(), this))) {
                return null;
            } else {
                item.Parent = null;
                item.MyRoot = null;
                updateRoots();
                return item;
            }
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int indexOf(Object o) {
        return _entries.indexOf(o);
    }

    @Override
    public int lastIndexOf(Object o) {
        return _entries.lastIndexOf(o);
    }

    @Override
    public ListIterator<Entry> listIterator() {
        return _entries.listIterator();
    }

    @Override
    public ListIterator<Entry> listIterator(int index) {
        return _entries.listIterator(index);
    }

    @Override
    public List<Entry> subList(int fromIndex, int toIndex) {
        return _entries.subList(fromIndex, toIndex);
    }

    public Entry getNamed(String name) {
        for (Entry entry : _entries) {
            if (entry.Name.equals(name)) {
                return entry;
            }
        }
        throw new NoSuchElementException("Entry not found");
    }

    @Override
    public void Write(DataOutputStream stream) throws IOException {
        super.Write(stream);
        stream.writeInt(_entries.size());
        for (Entry entry : _entries) {
            entry.Write(stream);
        }
    }

    @Override
    public void readValue(DataInputStream stream) throws IOException {
        int count = stream.readInt();
        for (int i = 0; i < count; i++) {
            Entry x = Entry.Read(stream, MyRoot, false, EntryType.Empty, !this.EncodeDescription);
            x.Parent = this;
            x.MyRoot = MyRoot;
            add(x);
        }
    }

    void replaceEntries(Entry replaceWith) {
        if (replaceWith instanceof Key key) {
            _entries.addAll(key);
            updateRoots();
        }
    }

    public void placeAtPath(String path, Entry toPlace) {
        if (Type != EntryType.Root) {
            toPlace.MyRoot = MyRoot;
            MyRoot.placeAtPath(path, toPlace);
            return;
        }
        String pth = path.substring(path.indexOf('/') + 1);

        Key e = this;
        while (!e.getEntryPath().equals(path)) {
            int inx = pth.indexOf("/");
            String nextEntryName = pth;
            if (inx != -1) {
                nextEntryName = pth.substring(0, pth.indexOf("/"));
                pth = pth.substring(pth.indexOf("/") + 1);
            } else {
                pth = "";
            }
            if (!e.HasNamedKey(nextEntryName)) {
                Key nxt = new Key(nextEntryName);
                e.add(nxt);
                e = nxt;
            } else {
                e = e.getNamed(nextEntryName).Key();
            }
        }
        e.add(toPlace);
    }

    public boolean HasNamedKey(String name) {
        return _entries.stream().anyMatch(x -> x.Name.equals(name));
    }

    @Override
    public String PrettyPrint(int indent) {
        StringBuilder line = new StringBuilder(super.PrettyPrint(indent));
        line.append(" [").append(_entries.size()).append("]\n");
        for (Entry x : _entries) {
            line.append(x.PrettyPrint(indent + 4)).append("\n");
        }
        return line.toString();
    }
}
