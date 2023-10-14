package dev.zontreck.registry;

import dev.zontreck.registry.v3.Tag;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class RegistryFile {
	public Header RegistryHeader;
	public Map<String, Tag> Tags = new HashMap<>();


	public void MakeHeader(byte version) {
		switch (version) {
			case 1: {
				RegistryHeader = new HeaderV1();
				break;
			}
			case 2: {
				RegistryHeader = new HeaderV2();
				break;
			}
			case 3: {
				HeaderV3 v3 = new HeaderV3();
				v3.NumOfTags = Tags.size();
				RegistryHeader = v3;
				break;
			}
		}
	}

	public void SaveToFile(String filename) {
		try {
			DataOutputStream dos = new DataOutputStream(new FileOutputStream(RegistryIO.getFileFor(filename)));

			RegistryHeader.Write(dos);
			for (Map.Entry<String, Tag> entry :
				Tags.entrySet()) {
				dos.writeUTF(entry.getKey());
				entry.getValue().Write(dos);
				entry.getValue().WriteValue(dos);
			}
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static RegistryFile LoadFromFile(String filename) {
		try {
			DataInputStream dis = new DataInputStream(new FileInputStream(RegistryIO.getFileFor(filename)));

			RegistryFile file = new RegistryFile();
			file.RegistryHeader = RegistryIO.ReadHeader(dis);

			if (file.RegistryHeader.Version() >= 3) {
				// This contains more than one tag!
				if (file.RegistryHeader instanceof HeaderV3 v3) {
					for (int i = 0; i < v3.NumOfTags; i++) {
						String name = dis.readUTF();
						Tag tag = Tag.Read(dis);
						tag.ReadValue(dis);

						file.Tags.put(name, tag);
					}
				}
			} else {
				// This contains one tag only, read it, append it to the map with name 'root'
				Tag tag = Tag.Read(dis);
				tag.ReadValue(dis);

				file.Tags.put("root", tag);
			}


			return file;
		} catch (IOException e) {
			throw new RuntimeException();
		}
	}
}
