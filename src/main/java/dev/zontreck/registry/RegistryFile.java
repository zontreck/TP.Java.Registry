package dev.zontreck.registry;

import dev.zontreck.registry.v3.Tag;

import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
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
}
