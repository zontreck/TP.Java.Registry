package dev.zontreck.registry;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.time.Instant;

public class RegistryIO
{
	public static Path getGlobalPath()
	{
		Path P = Path.of(System.getProperty("user.home"));
		return P.resolve("." + Constants.HSRDGlobalStorage);
	}

	public static File getFileFor(String name)
	{
		return getGlobalPath().resolve(name + "." + Constants.Extension).toFile();
	}


	public static File getNBTFor(String name)
	{
		return getGlobalPath().resolve(name + ".dat").toFile();
	}

	public static final byte Version = 3;
	public static final byte Patch = 0;

	public static void writeHeaderV3(DataOutputStream dos) throws IOException
	{
		ByteArrayOutputStream baos = new ByteArrayOutputStream(128);
		DataOutputStream dos2 = new DataOutputStream(baos);

		dos2.writeByte(Version);
		dos2.writeByte(Patch);

		dos2.writeUTF(Constants.Extension);
		dos2.writeUTF(Constants.Creator);

		dos2.writeLong(Instant.now().getEpochSecond());

		dos.write(baos.toByteArray());
	}
}
