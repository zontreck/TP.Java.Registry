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
	public String PrettyPrint(int indent) {
		String builder = super.PrettyPrint(indent);
		builder += ": " + value;

		return builder;
	}
}
