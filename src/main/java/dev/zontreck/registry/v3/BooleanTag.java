package dev.zontreck.registry.v3;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class BooleanTag extends Tag {
	public boolean value;

	public BooleanTag() {
		value = false;
	}

	public BooleanTag(boolean val) {
		value = val;
	}

	public BooleanTag withValue(boolean val) {
		value = val;
		return this;
	}

	public IntTag toNBT() {
		return new IntTag((value ? 1 : 0));
	}

	@Override
	public Type getType() {
		return Type.Boolean;
	}

	@Override
	public String getCanonicalName() {
		return "TAG_Boolean";
	}

	@Override
	public void WriteValue(DataOutputStream dos) throws IOException {
		dos.writeBoolean(value);
	}

	@Override
	public void ReadValue(DataInputStream dis) throws IOException {
		value = dis.readBoolean();
	}

	@Override
	public String PrettyPrint(int indent, String name) {
		String builder = super.PrettyPrint(indent, name);
		builder += ": " + (value ? "true" : "false");

		return builder;
	}
}
