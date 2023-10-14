package dev.zontreck.registry.v3;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class LongTag extends Tag
{
	public long value;
	public LongTag()
	{
		value = 0;
	}
	public LongTag(long val)
	{
		value = val;
	}

	@Override
	public String getCanonicalName() {
		return "TAG_Long";
	}

	@Override
	public void WriteValue(DataOutputStream dos) throws IOException {
		dos.writeLong(value);
	}

	@Override
	public void ReadValue(DataInputStream dis) throws IOException {
		value = dis.readLong();
	}

	@Override
	public String PrettyPrint(int indent)
	{
		String builder = super.PrettyPrint(indent);
		builder += ": " + value;

		return builder;
	}
}
