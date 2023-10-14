package dev.zontreck.registry.v3;

public enum Type {
	End(0),
	Byte(1),
	Short(2),
	Int(3),
	Long(4),
	Float(5),
	Double(6),
	ByteArray(7),
	String(8),
	List(9),
	Key(10),
	IntArray(11),
	LongArray(12);

	byte value;

	Type(int val) {
		value = (byte) val;
	}

	public static void PerformRegister() {
		TagTypeRegistry.RegisterType(End, EndTag.class);
		TagTypeRegistry.RegisterType(String, StringTag.class);
		TagTypeRegistry.RegisterType(Byte, ByteTag.class);
		TagTypeRegistry.RegisterType(Short, ShortTag.class);
		TagTypeRegistry.RegisterType(Int, IntTag.class);
		TagTypeRegistry.RegisterType(Long, LongTag.class);
		TagTypeRegistry.RegisterType(Float, FloatTag.class);
		TagTypeRegistry.RegisterType(Double, DoubleTag.class);
		TagTypeRegistry.RegisterType(ByteArray, ByteArrayTag.class);
		TagTypeRegistry.RegisterType(List, ListTag.class);
		TagTypeRegistry.RegisterType(Key, CompoundTag.class);
	}

	public static Type valueOf(byte b) {
		for (Type T : values()) {
			if (T.value == b)
				return T;
		}
		return null;
	}
}
