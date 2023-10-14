package dev.zontreck.registry.v3;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class ByteTag extends Tag {
	public byte value;

	public ByteTag() {
		value = 0;
	}

	public ByteTag(byte val) {
		value = val;
	}

	@Override
	public Type getType() {
		return Type.Byte;
	}

	@Override
	public String getCanonicalName() {
		return "TAG_Byte";
	}

	@Override
	public void WriteValue(DataOutputStream dos) throws IOException {
		dos.write(value);
	}

	@Override
	public void ReadValue(DataInputStream dis) throws IOException {
		value = dis.readByte();
	}

	@Override
	public String PrettyPrint(int indent, String name) {
		String builder = super.PrettyPrint(indent, name);
		builder += ": " + value;

		return builder;
	}
}
