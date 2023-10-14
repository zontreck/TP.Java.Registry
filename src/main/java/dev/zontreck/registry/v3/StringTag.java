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

	@Override
	public Type getType() {
		return Type.String;
	}

	@Override
	public String getCanonicalName() {
		return "TAG_String";
	}

	@Override
	public String PrettyPrint(int indent) {
		String builder = super.PrettyPrint(indent);
		builder += ": \"" + value + "\"";

		return builder;
	}

	@Override
	public void WriteValue(DataOutputStream dos) throws IOException {
		dos.writeUTF(value);
	}

	@Override
	public void ReadValue(DataInputStream dis) throws IOException {
		value = dis.readUTF();
	}
}
