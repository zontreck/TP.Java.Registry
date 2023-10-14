package dev.zontreck.registry.v3;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class EndTag extends Tag
{

	@Override
	public Type getType() {
		return Type.End;
	}

	@Override
	public String getCanonicalName() {
		return "TAG_End";
	}

	@Override
	public void WriteValue(DataOutputStream dos) throws IOException {

	}

	@Override
	public void ReadValue(DataInputStream dis) throws IOException {

	}
}
