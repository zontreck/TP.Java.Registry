package dev.zontreck.registry;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public abstract class Header {
	public abstract byte Version();

	public abstract byte getPatch();

	public abstract void Read(DataInputStream dis) throws IOException;

	public abstract void Write(DataOutputStream dos) throws IOException;
}
