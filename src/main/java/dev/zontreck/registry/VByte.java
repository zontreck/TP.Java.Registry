package dev.zontreck.registry;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class VByte extends Entry {
	public VByte(String name, byte value) {
		super(EntryType.Byte, name);
		Parent = null;
		Value = value;
	}

	public byte Value;

	@Override
	public void readValue(DataInputStream stream) throws IOException {
		Value = stream.readByte();
	}

	@Override
	public void Write(DataOutputStream stream) throws IOException {
		super.Write(stream);
		stream.writeByte(Value);
	}

	@Override
	public String PrettyPrint(int indent) {
		return super.PrettyPrint(indent) + " [" + Value + "]";
	}

	public VByte setByte(byte value) {
		Value = value;
		return this;
	}
}
