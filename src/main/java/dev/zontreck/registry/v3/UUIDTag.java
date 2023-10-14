package dev.zontreck.registry.v3;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.UUID;

public class UUIDTag extends Tag {
	long lsb;
	long msb;

	public UUID value() {
		return new UUID(msb, lsb);
	}

	public UUIDTag() {
		UUID id = UUID.randomUUID();
		lsb = id.getLeastSignificantBits();
		msb = id.getMostSignificantBits();
	}

	public UUIDTag(UUID ID) {
		lsb = ID.getLeastSignificantBits();
		msb = ID.getMostSignificantBits();
	}

	public UUIDTag withLeastSignificantBits(long val) {
		lsb = val;
		return this;
	}

	public UUIDTag withMostSignificantBits(long val) {
		msb = val;
		return this;
	}

	@Override
	public Type getType() {
		return Type.UUID;
	}

	@Override
	public String getCanonicalName() {
		return "TAG_UUID";
	}

	@Override
	public void WriteValue(DataOutputStream dos) throws IOException {
		dos.writeLong(msb);
		dos.writeLong(lsb);
	}

	@Override
	public void ReadValue(DataInputStream dis) throws IOException {
		msb = dis.readLong();
		lsb = dis.readLong();
	}

	@Override
	public String PrettyPrint(int indent, String name) {
		String builder = super.PrettyPrint(indent, name);
		builder += ": " + new UUID(msb, lsb);
		return builder;
	}
}
