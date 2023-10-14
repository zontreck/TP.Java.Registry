package dev.zontreck.registry;

import dev.zontreck.registry.v3.Tag;

import java.io.*;

public class NbtIO {
	public static void Save(String filename, Tag tag) {
		// proceed!

		try {
			DataOutputStream dos = new DataOutputStream(new FileOutputStream(filename));
			tag.Write(dos);
			tag.WriteValue(dos);

			dos.close();
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static Tag Load(String file) {
		try {
			DataInputStream dis = new DataInputStream(new FileInputStream(file));
			Tag ret = Tag.Read(dis);
			ret.ReadValue(dis);

			return ret;
		} catch (IOException e) {
			throw new RuntimeException();
		}
	}
}
