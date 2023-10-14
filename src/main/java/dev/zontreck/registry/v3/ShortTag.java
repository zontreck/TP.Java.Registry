package dev.zontreck.registry.v3;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class ShortTag extends Tag {
	public short value;

	public ShortTag() {
		value = 0;
	}

	public ShortTag(short val) {
		value = val;
	}

	public ShortTag withValue(short val) {
		value = val;
		return this;
	}

	@Override
	public Type getType() {
		return Type.Short;
	}

	@Override
	public String getCanonicalName() {
		return "TAG_Short";
	}

	@Override
	public String PrettyPrint(int indent, String name) {
		String builder = super.PrettyPrint(indent, name);
		builder += ": " + value;
		return builder;
	}

	@Override
	public void WriteValue(DataOutputStream dos) throws IOException {
		dos.writeShort(value);
	}

	@Override
	public void ReadValue(DataInputStream dis) throws IOException {
		value = dis.readShort();
	}
}
