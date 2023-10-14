package dev.zontreck.registry.v3;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public abstract class Tag {
	public abstract Type getType();

	public static String MakeIndent(int indent) {
		String builder = "";
		for (int i = 0; i < indent; i++) {
			builder += "    ";
		}

		return builder;
	}

	public abstract String getCanonicalName();

	public String PrettyPrint(int indent) {
		String builder = "";
		builder += MakeIndent(indent) + getCanonicalName();

		return builder;
	}

	public void Write(DataOutputStream dos) throws IOException {
		dos.writeByte(getType().value);
	}

	public abstract void WriteValue(DataOutputStream dos) throws IOException;

	/**
	 * Reads the tag type (1 byte) from the stream
	 * <p>
	 * Then, initializes an instance of the new tag and returns it.
	 * <p>
	 * NOTE: Value is not read by this method. The separate ReadValue method must be
	 * called after reading a name, or other data as necessary
	 * 
	 * @param dis
	 * @return
	 * @throws IOException
	 */
	public static Tag Read(DataInputStream dis) throws IOException {
		Type type = Type.valueOf(dis.readByte());
		return TagTypeRegistry.getInstanceOf(type);
	}

	public abstract void ReadValue(DataInputStream dis) throws IOException;

	public StringTag asString() {
		if (this instanceof StringTag st)
			return st;
		else
			return new StringTag();
	}

	public ShortTag asShort() {
		if (this instanceof ShortTag st)
			return st;
		else
			return new ShortTag();
	}

	public ByteTag asByte() {
		if (this instanceof ByteTag bt)
			return bt;
		else
			return new ByteTag();
	}

	public IntTag asInt() {
		if (this instanceof IntTag it)
			return it;
		else
			return new IntTag();
	}

	public LongTag asLong() {
		if (this instanceof LongTag lt)
			return lt;
		else
			return new LongTag();
	}

	public FloatTag asFloat() {
		if (this instanceof FloatTag ft)
			return ft;
		else
			return new FloatTag();
	}

	public DoubleTag asDouble() {
		if (this instanceof DoubleTag dt)
			return dt;
		else
			return new DoubleTag();
	}

	public ByteArrayTag asByteArray() {
		if (this instanceof ByteArrayTag bat)
			return bat;
		else
			return new ByteArrayTag();
	}

}
