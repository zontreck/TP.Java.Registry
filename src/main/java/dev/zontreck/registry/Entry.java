package dev.zontreck.registry;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Stack;

public abstract class Entry {
    public EntryType Type = EntryType.Root;
    public String Name = "(noname)";
    public boolean EncodeDescription = true;
    public String Description = "";

    public String getEntryPath() {

        Stack<Entry> stack = new Stack<>();
        Entry current = this;
        while (current != null) {
            stack.push(current);
            current = current.Parent;
        }

        String path = "";
        while (!stack.isEmpty()) {
            String nxt = stack.pop().Name;

            path = path + "/" + nxt;
            if (path.startsWith("/"))
                path = path.substring(1);
        }

        return path;
    }

    void setRoot(Key root) {
        MyRoot = root;

        if (this instanceof Key) {
            for (Entry entry : ((Key) this)) {
                entry.setRoot(root);
            }
        }
    }

    public Entry Parent;

    public static final Key ROOT = new Key("root") {
        {
            Type = EntryType.Root;
        }
    };

    Entry() {
        Type = EntryType.Root;
        Name = "root";
        Description = "Registry Root";
        Parent = null;
    }

    Entry(EntryType type, String name) {
        Type = type;
        Name = name;
    }

    public static Entry getByPath(String path) {
        try {
            path = path.substring(path.indexOf('/') + 1);
            Entry retentry = null;
            Entry entry = ROOT;
            while (retentry == null) {
                int slash = path.indexOf("/");
                String nextEntry = "";
                if (slash != -1)
                    nextEntry = path.substring(0, slash);
                else {
                    nextEntry = path;
                    path = "";
                }

                if (entry instanceof Key && !nextEntry.isEmpty()) {
                    path = path.substring(slash + 1);
                    entry = ((Key) entry).getNamed(nextEntry);
                } else {
                    retentry = entry;
                }
            }
            return entry;
        } catch (Exception e) {
            return null;
        }
    }

    public Entry getAtPath(String path) {
        try {
            path = path.substring(path.indexOf('/') + 1);
            Entry retentry = null;
            Entry entry = MyRoot;
            while (retentry == null) {
                int slash = path.indexOf("/");
                String nextEntry = "";
                if (slash != -1)
                    nextEntry = path.substring(0, slash);
                else {
                    nextEntry = path;
                    path = "";
                }

                if (entry instanceof Key && !nextEntry.isEmpty()) {
                    path = path.substring(slash + 1);
                    entry = ((Key) entry).getNamed(nextEntry);
                } else {
                    retentry = entry;
                }
            }
            return entry;
        } catch (Exception e) {
            return null;
        }
    }

    public void Write(DataOutputStream stream) throws IOException {
        if (Parent instanceof EntryList)
            return;
        stream.write(Type.byteValue());
        stream.writeUTF(Name);

        if (Type == EntryType.Root) {
            stream.writeBoolean(EncodeDescription);
        }
        if (encodesDescription())
            stream.writeUTF(Description);
    }

    public boolean encodesDescription() {
        return MyRoot.EncodeDescription;
    }

    public static Entry Read(DataInputStream stream, Key root, boolean isList, EntryType listType, boolean skipEncDesc)
            throws IOException {
        Entry x = null;
        EntryType Type = listType;
        String Name = "";
        String Description = "";
        boolean EncodeDesc = true;
        if (!isList) {
            Type = EntryType.values()[stream.readByte()];
            Name = stream.readUTF();
            if (Type == EntryType.Root && !skipEncDesc) {
                EncodeDesc = stream.readBoolean();
            }
            if (skipEncDesc) {
                EncodeDesc = true;
            }
            if (root == null) {
                if (EncodeDesc)
                    Description = stream.readUTF();
            }
            if (root != null && root.EncodeDescription) {
                Description = stream.readUTF();
            }
        }

        switch (Type) {
            case Key:
                x = new Key(Name);
                break;
            case Word:
                x = new Word(Name, "");
                break;
            case Int16:
                x = new VInt16(Name, (short) 0);
                break;
            case Int32:
                x = new VInt32(Name, 0);
                break;
            case Int64:
                x = new VInt64(Name, 0);
                break;
            case Bool:
                x = new VBool(Name, false);
                break;
            case Byte:
                x = new VByte(Name, (byte) 0);
                break;
            case Root:
                x = new Key("root");
                break;
            case Array:
                x = new EntryList<>(Name);
                break;
            case WordArray:
                x = new WordList(Name);
                break;
            default:
                x = new BlankEntry(Name);
                break;
        }
        x.Type = Type;
        x.Description = Description;
        x.EncodeDescription = EncodeDesc;
        x.Parent = null;

        if (x.Type == EntryType.Root) {
            x.MyRoot = x.Key();
        }

        x.readValue(stream);
        return x;
    }

    public abstract void readValue(DataInputStream stream) throws IOException;

    private static Key getRoot(Entry entry) {
        Entry ent = entry;
        while (ent.Parent != null) {
            ent = ent.Parent;
        }
        return (Key) ent;
    }

    public Word Word() {
        return (Word) this;
    }

    public VInt16 Int16() {
        return (VInt16) this;
    }

    public VInt32 Int32() {
        return (VInt32) this;
    }

    public VInt64 Int64() {
        return (VInt64) this;
    }

    public VBool Bool() {
        return (VBool) this;
    }

    public Key Key() {
        return (Key) this;
    }

    public VByte Byte() {
        return (VByte) this;
    }

    public WordList WordList() {
        return (WordList) this;
    }

    public ByteList ByteList() {
        return (ByteList) this;
    }

    Key MyRoot;

    public String PrettyPrint(int indent) {
        return " ".repeat(indent) + "[" + Type + "] " + Name;
    }

    @Override
    public String toString() {
        return PrettyPrint(0);
    }

    public Entry deleteByPath(String path) {
        if (this instanceof Key) {
            Entry pth = getAtPath(path);
            Key parent = (Key) pth.Parent;
            parent.remove(pth);
            return parent;
        } else {
            return null;
        }
    }

    public void clear() {
        Type = EntryType.Empty;
    }
}
