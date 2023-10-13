package dev.zontreck.registry;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class VInt16 extends Entry {
    public VInt16(String name, short value) {
        super(EntryType.Int16, name);
        Parent = null;
        Value = value;
    }

    public short Value;

    @Override
    public void readValue(DataInputStream stream) throws IOException {
        Value = stream.readShort();
    }

    @Override
    public void Write(DataOutputStream stream) throws IOException {
        super.Write(stream);
        stream.writeShort(Value);
    }

    @Override
    public String PrettyPrint(int indent) {
        return super.PrettyPrint(indent) + " [" + Value + "]";
    }

    public VInt16 setInt16(short value) {
        Value = value;
        return this;
    }
}
