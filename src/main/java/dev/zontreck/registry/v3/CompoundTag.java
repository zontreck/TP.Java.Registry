package dev.zontreck.registry.v3;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class CompoundTag extends Tag implements Map<String, Tag> {
	Map<String, Tag> tags;

	public CompoundTag() {
		tags = new HashMap<>();
	}

	@Override
	public Type getType() {
		return Type.Key;
	}

	@Override
	public String getCanonicalName() {
		return "TAG_Compound";
	}

	@Override
	public void WriteValue(DataOutputStream dos) throws IOException {
		for (Map.Entry<String, Tag> E : tags.entrySet()) {
			E.getValue().Write(dos);
			dos.writeUTF(E.getKey());
			E.getValue().WriteValue(dos);
		}

		dos.writeByte(Type.End.value);
	}

	@Override
	public void ReadValue(DataInputStream dis) throws IOException {
		Type current = Type.Byte;
		while (current != Type.End) {
			Tag tag = Tag.Read(dis);
			if (tag.getType() != Type.End) {
				String name = dis.readUTF();
				tag.ReadValue(dis);

				put(name, tag);
			}

			current = tag.getType();
		}
	}

	@Override
	public String PrettyPrint(int indent, String name) {
		String builder = super.PrettyPrint(indent, name);
		builder += ": {\n";

		for (Map.Entry<String, Tag> entry : entrySet()) {
			builder += entry.getValue().PrettyPrint(indent + 1, entry.getKey()) + ",\n";
		}

		builder += MakeIndent(indent) + "}";

		return builder;
	}

	@Override
	public int size() {
		return tags.size();
	}

	@Override
	public boolean isEmpty() {
		return tags.isEmpty();
	}

	@Override
	public boolean containsKey(Object o) {
		return tags.containsKey(o);
	}

	@Override
	public boolean containsValue(Object o) {
		return tags.containsValue(o);
	}

	@Override
	public Tag get(Object o) {
		return tags.get(o);
	}

	@Override
	public Tag put(String s, Tag tag) {
		return tags.put(s, tag);
	}

	@Override
	public Tag remove(Object o) {
		return tags.remove(o);
	}

	@Override
	public void putAll(Map<? extends String, ? extends Tag> map) {
		tags.putAll(map);
	}

	@Override
	public void clear() {
		tags.clear();
	}

	@Override
	public Set<String> keySet() {
		return tags.keySet();
	}

	@Override
	public Collection<Tag> values() {
		return tags.values();
	}

	@Override
	public Set<Entry<String, Tag>> entrySet() {
		return tags.entrySet();
	}
}
