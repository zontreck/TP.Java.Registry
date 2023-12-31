package dev.zontreck.registry.v3;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class IntTag extends Tag {
	public int value;

	public IntTag() {
		value = 0;
	}

	public IntTag(int val) {
		value = val;
	}

	public IntTag withValue(int val) {
		value = val;
		return this;
	}

	@Override
	public Type getType() {
		return Type.Int;
	}

	@Override
	public String getCanonicalName() {
		return "TAG_Int";
	}

	@Override
	public void WriteValue(DataOutputStream dos) throws IOException {
		dos.writeInt(value);
	}

	@Override
	public void ReadValue(DataInputStream dis) throws IOException {
		value = dis.readInt();
	}

	@Override
	public String PrettyPrint(int indent, String name) {
		String builder = super.PrettyPrint(indent, name);
		builder += ": " + value;

		return builder;
	}
}
