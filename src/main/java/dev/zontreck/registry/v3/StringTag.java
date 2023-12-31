package dev.zontreck.registry.v3;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class StringTag extends Tag {
	public String value;

	public StringTag() {
		value = "";
	}

	public StringTag(String val) {
		value = val;
	}

	public StringTag withValue(String val) {
		value = val;
		return this;
	}

	@Override
	public Type getType() {
		return Type.String;
	}

	@Override
	public String getCanonicalName() {
		return "TAG_String";
	}

	@Override
	public String PrettyPrint(int indent, String name) {
		String builder = super.PrettyPrint(indent, name);
		builder += ": \"" + value + "\"";

		return builder;
	}

	@Override
	public void WriteValue(DataOutputStream dos) throws IOException {
		dos.writeShort(value.length());
		dos.write(value.getBytes());
	}

	@Override
	public void ReadValue(DataInputStream dis) throws IOException {
		short len = dis.readShort();
		value = new String(dis.readNBytes(len));
	}
}
