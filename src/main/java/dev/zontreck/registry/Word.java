package dev.zontreck.registry;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Word extends Entry {
    public Word(String name, String value) {
        super(EntryType.Word, name);
        Parent = null;
        Value = value;
    }

    public String Value = "";

    @Override
    public void Write(DataOutputStream stream) throws IOException {
        super.Write(stream);
        stream.writeUTF(Value);
    }

    @Override
    public void readValue(DataInputStream stream) throws IOException {
        Value = stream.readUTF();
    }

    @Override
    public String PrettyPrint(int indent) {
        return super.PrettyPrint(indent) + " [" + Value + "]";
    }

    @Override
    public void setValue(Object value) {
        super.setValue(value);

        if (value instanceof String str) {
            Value = str;
        }
    }

    public Word setWord(String value) {
        Value = value;
        return this;
    }
}
