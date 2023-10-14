package dev.zontreck.registry.v3;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class FloatTag extends Tag
{
	public float value;
	public FloatTag()
	{
		value = 0.0f;
	}

	public FloatTag(float val)
	{
		value = val;
	}


	@Override
	public String getCanonicalName() {
		return "TAG_Float";
	}

	@Override
	public void WriteValue(DataOutputStream dos) throws IOException {
		dos.writeFloat(value);
	}

	@Override
	public void ReadValue(DataInputStream dis) throws IOException {
		value = dis.readFloat();
	}
}