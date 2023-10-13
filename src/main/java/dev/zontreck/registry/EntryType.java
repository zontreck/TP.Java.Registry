package dev.zontreck.registry;

public enum EntryType
{
    Word,
    Int16,
    Int32,
    Int64,
    Bool,
    Byte,
    Empty, // Undefined value type

    Key,    // Contains children, similar to a Dictionary
    Root,    // Contains Children - Is key but with nullable parent
    Array,   // Non-Named Entry List. This is not a Key. (DEPRECATED)

    WordArray,
    ByteArray

}

