package dev.zontreck.registry;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class BlankEntry extends Entry {
	public BlankEntry(String name) {
		super(EntryType.Empty, name);
		EncodeDescription = false;
	}

	@Override
	public void readValue(DataInputStream stream) throws IOException {

	}

	@Override
	public void Write(DataOutputStream stream) throws IOException {
		super.Write(stream);
	}
}
