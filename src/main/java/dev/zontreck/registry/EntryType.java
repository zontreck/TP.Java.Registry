package dev.zontreck.registry;

public enum EntryType {
    Word(0),
    Int16(1),
    Int32(2),
    Int64(3),
    Bool(4),
    Byte(5),
    Empty(6), // Undefined value type

    Key(7), // Contains children, similar to a Dictionary
    Root(8), // Contains Children - Is key but with nullable parent
    Array(9), // Non-Named Entry List. This is not a Key. (DEPRECATED)

    WordArray(10),
    ByteArray(11);

    private final byte value;

    private EntryType(int val) {
        value = (byte) val;
    }

    public byte byteValue() {
        return value;
    }
}
