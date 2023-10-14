package dev.zontreck.registry;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.time.Instant;

public class RegistryIO {
	public static Path getGlobalPath() {
		Path P = Path.of(System.getProperty("user.home"));
		return P.resolve("." + Constants.HSRDGlobalStorage);
	}

	public static File getFileFor(String name) {
		return getGlobalPath().resolve(name + "." + Constants.Extension).toFile();
	}


	public static File getNBTFor(String name) {
		return getGlobalPath().resolve(name + ".dat").toFile();
	}

	public static final byte Version = 3;
	public static final byte Patch = 0;

	public static void WriteHeader(DataOutputStream dos, int numOfTags) {
		switch (Version) {
			case 1: {
				try {
					HeaderV1 v1 = new HeaderV1();
					v1.Write(dos);
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
				break;
			}
			case 2: {
				try {
					HeaderV2 v2 = new HeaderV2();
					v2.LastSavedAt = (int) Instant.now().getEpochSecond();

					v2.Write(dos);
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
				break;
			}
			case 3: {
				try {
					HeaderV3 v3 = new HeaderV3();
					v3.curPatch = Patch;
					v3.NumOfTags = numOfTags;
					v3.LastSavedAt = Instant.now().getEpochSecond();

					v3.Write(dos);
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
				break;
			}
		}
	}

	public static Header ReadHeader(DataInputStream dis) throws IOException {
		byte ver = dis.readByte();
		switch (ver) {
			case 1: {
				HeaderV1 v1 = new HeaderV1();
				v1.Read(dis);

				return v1;
			}
			case 2: {
				HeaderV2 v2 = new HeaderV2();
				v2.Read(dis);

				return v2;
			}
			case 3: {
				HeaderV3 v3 = new HeaderV3();
				v3.Read(dis);

				return v3;
			}
		}

		return null;
	}


}
