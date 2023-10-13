package dev.zontreck.registry;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class VBool extends Entry {
	public VBool(String name, boolean value) {
		super(EntryType.Bool, name);
		Parent = null;
		Value = value;
	}

	public boolean Value;

	@Override
	public void readValue(DataInputStream stream) throws IOException {
		Value = stream.readBoolean();
	}

	@Override
	public void Write(DataOutputStream stream) throws IOException {
		super.Write(stream);
		stream.writeBoolean(Value);
	}

	@Override
	public String PrettyPrint(int indent) {
		return super.PrettyPrint(indent) + " [" + Value + "]";
	}

	public VBool setBool(boolean value) {
		Value = value;
		return this;
	}
}
