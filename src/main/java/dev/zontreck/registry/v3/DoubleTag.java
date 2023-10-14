package dev.zontreck.registry.v3;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class DoubleTag extends Tag {
	public double value;

	public DoubleTag() {
		value = 0;
	}

	public DoubleTag(double val) {
		value = val;
	}

	public DoubleTag withValue(double val) {
		value = val;
		return this;
	}

	@Override
	public Type getType() {
		return Type.Double;
	}

	@Override
	public String getCanonicalName() {
		return "TAG_Double";
	}

	@Override
	public void WriteValue(DataOutputStream dos) throws IOException {
		dos.writeDouble(value);
	}

	@Override
	public void ReadValue(DataInputStream dis) throws IOException {
		value = dis.readDouble();
	}

	@Override
	public String PrettyPrint(int indent, String name) {
		String builder = super.PrettyPrint(indent, name);
		builder += ": " + value;

		return builder;
	}
}
