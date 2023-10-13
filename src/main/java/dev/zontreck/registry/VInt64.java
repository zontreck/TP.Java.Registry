package dev.zontreck.registry;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class VInt64 extends Entry {
	public VInt64(String name, long value) {
		super(EntryType.Int64, name);
		Parent = null;
		Value = value;
	}

	public long Value;

	@Override
	public void readValue(DataInputStream stream) throws IOException {
		Value = stream.readLong();
	}

	@Override
	public void Write(DataOutputStream stream) throws IOException {
		super.Write(stream);
		stream.writeLong(Value);
	}

	@Override
	public String PrettyPrint(int indent) {
		return super.PrettyPrint(indent) + " [" + Value + "]";
	}

	public VInt64 setInt64(long value) {
		Value = value;
		return this;
	}
}
