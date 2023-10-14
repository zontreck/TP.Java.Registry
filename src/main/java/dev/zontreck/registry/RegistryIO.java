package dev.zontreck.registry;


import dev.zontreck.eventsbus.Bus;

import java.io.*;
import java.nio.file.Path;
import java.time.Instant;
import java.util.stream.Stream;

public class RegistryIO
{
	public static final byte Version = 3;
	public static final byte Version2 = 0;

	public static String GetRegistryGlobalPath(String name, String database)
	{
		String filename = name;

		String dataFolder = System.getProperty("user.home");
		Path pth = Path.of(dataFolder);
		pth = pth.resolve("." + Constants.HSRDGlobalStorage);

		if(!pth.toFile().exists())
		{
			pth.toFile().mkdir();
		}
		pth = pth.resolve(database);
		if(!pth.toFile().exists())
		{
			pth.toFile().mkdir();
		}

		filename = pth.resolve(filename).toAbsolutePath() + "." + HSRDExtension;

		return filename;
	}

	/// <summary>
	/// Saves the entire Registry to disk
	///
	/// This uses the Entry.ROOT object. To use another registry, see the saveHive function.
	/// </summary>
	public static void save(String database)
	{
		String filename = GetRegistryGlobalPath(RootHSRD, database);

		System.out.println("Saving Registry : \n\nByte Count: " + getBytes(Entry.ROOT).length + "\n\n" + Entry.ROOT.PrettyPrint(0));



		// Reset the file to zero bytes if it exists
		resetFile(filename);
		try {
			DataOutputStream dos = new DataOutputStream(new FileOutputStream(filename));
			writeHeaderV2(dos);

			Entry.ROOT.setRoot(Entry.ROOT);
			Entry.ROOT.Write(dos);
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		System.out.println("Registry Saved");
	}

	/// <summary>
	/// Saves the specified Root Hive to disk
	///
	/// This requires a file name!
	/// </summary>
	/// <param name="root"></param>
	public static void saveHive(Key root, String name, String database)
	{
		String filename = GetRegistryGlobalPath(name, database);


		if(!Bus.debug)
			System.out.println("Saving Registry "+name+" : \n\nByte Count: " + getBytes(Entry.ROOT).length + "\n\n" + Entry.ROOT.PrettyPrint(0));


		// Reset the file to zero bytes if it exists
		resetFile(filename);

		try {
			DataOutputStream dos = new DataOutputStream(new FileOutputStream(filename));
			writeHeaderV2(dos);

			root.setRoot(root);
			root.Write(dos);
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		if(!Bus.debug)
			System.out.println("Registry Saved");
	}

	public static void saveHive(Key root, OutputStream stream)
	{

		if (!Bus.debug)
			System.out.println("Saving Registry : \n\nByte Count: " + getBytes(Entry.ROOT).length + "\n\n" + Entry.ROOT.PrettyPrint(0));


		try {
			DataOutputStream dos = new DataOutputStream(stream);
			writeHeaderV2(dos);

			root.setRoot(root);
			root.Write(dos);
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		if (!Bus.debug)
			System.out.println("Registry Saved");
	}

	public static byte[] saveWithoutHeader (Key root)
	{
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(baos);
		try {
			root.Write(dos);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}


		return baos.toByteArray();
	}

	public static Key loadWithoutHeader(byte[] bytes)
	{
		ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
		DataInputStream dis = new DataInputStream(bais);
		try {
			return Entry.Read(dis, null, false, null, false).Key();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

	}

	public static byte[] getBytes(Key root)
	{
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream();
		try {
			writeHeaderV2(dos);
			root.Write(dos);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}


		return baos.toByteArray();

	}

	/// <summary>
	/// Write the header version 1 to the file
	///
	/// Header v1 is 31 bytes total, but can vary depending on the byte count of Creator
	/// </summary>
	/// <param name="bw"></param>
	private static void writeHeaderV1(DataOutputStream bw) throws IOException {
		// Write out header!
		bw.writeByte((byte)1);
		bw.writeByte(2); // 2 was the last patch version of v1
		bw.writeUTF(Constants.Creator);
		bw.write(new byte[16]);

		// 16 bytes of padding for minor version changes. Potential bitmasks may be added.
		// Minor version upgrades should make padding changes when necessary during an upgrade to maintain compatibility when a major update arrives.
	}

	/// <summary>
	/// Writes header version 2 to the file
	///
	/// Header v2 is 128 bytes total
	/// Unlike v1, this is fixed in length.
	/// </summary>
	/// <param name="bw"></param>
	private static void writeHeaderV2(DataOutputStream bw)
	{
		ByteArrayOutputStream baos = new ByteArrayOutputStream(128);
		DataOutputStream dos = new DataOutputStream(baos);

		try{
			dos.writeByte(2);
			dos.writeByte(4); // The last patch version of v2.
			dos.writeUTF(Constants.Extension);
			dos.writeUTF(Constants.Creator);

			dos.writeInt(Math.toIntExact(Instant.now().getEpochSecond()));
		}catch(Exception e){
			e.printStackTrace();
		}

		try {
			bw.write(baos.toByteArray());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

	}

	/**
	 * V3 of the Registry data format
	 *
	 * This revision breaks a few things. So conversion of data types is necessary in the loader from v2.
	 *
	 * Old v2 data types are in the package: v2
	 *
	 * v3 Data types now have no knowledge of other tags unless they are a container.
	 * @param bw The final stream to write the header to
	 */
	private static void writeHeaderV3(DataOutputStream bw){
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(baos);

		try{
			dos.writeByte(3);
			dos.writeByte(Version2);
			dos.writeUTF(Constants.Extension);
			dos.writeUTF(Constants.Creator);

			dos.writeLong(Instant.now().getEpochSecond());
		}catch (Exception e){
			e.printStackTrace();
		}

		try{
			bw.write(baos.toByteArray());
		}catch (Exception e){
			e.printStackTrace();
		}
	}

	/// <summary>
	/// Loads the Root Hive into memory
	/// </summary>
	public static void load(String database)
	{
		String filename = "";

		filename = GetRegistryGlobalPath(RootHSRD, database);


		Entry.ROOT.Type = EntryType.Root;
		boolean exists = Path.of(filename).toFile().exists();
		if (exists)
		{
			try {
				DataInputStream dis = new DataInputStream(new FileInputStream(Path.of(filename).toFile()));

				byte ver1 = dis.readByte();
				switch(ver1)
				{
					case 1:
					{
						readHeaderV1(dis);
						break;
					}
					case 2:
					{
						readHeaderV2(dis);
						break;
					}
				}

				Entry.ROOT.replaceEntries(Entry.Read(dis, null, false, null, false));
			} catch (FileNotFoundException e) {
				throw new RuntimeException(e);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
		Entry.ROOT.setRoot(Entry.ROOT);


		System.out.println("Registry Loaded.");
	}

	private static void readHeaderV1(DataInputStream br)
	{
		try {
			if(br.readByte() != 2)
			{
				// Legacy behavior just prints out a warning
			}
			br.readUTF(); // Read the creator's signature

			br.readNBytes(16);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

	}

	private static boolean skipEncodeDescription = false;
	private static void readHeaderV2(DataInputStream br)
	{
		byte[] header = br.ReadBytes(127); // First byte was read already.
		using (MemoryStream ms = new MemoryStream(header))
		{
			using (BinaryReader reader = new BinaryReader(ms))
			{
				byte v2 = header[0];
				switch (v2)
				{
					case 0:
					case 1:
					case 2:
					{
						// V2.3 wont skip the encoded description once the version is appropriately bumped.
						skipEncodeDescription = true;
						break;
					}
					default:
					{
						break;
					}
				}
			}
		}
	}

	/// <summary>
	/// Loads the specified custom Hive into memory
	/// </summary>
	public static Key loadHive(string name, string database = "%nset")
	{

		string filename = Path.Combine(DataFolder, name);
		filename = Path.ChangeExtension(filename, HSRDExtension);

		if (File.Exists(filename))
		{
			File.Move(filename, GetRegistryGlobalPath(name, database));
		}
		filename = GetRegistryGlobalPath(name, database);


		Key x = new Key("root");
		x.Type = EntryType.Root;
		if (File.Exists(filename))
		{

			using (FileStream fs = new FileStream(filename, FileMode.Open))
			{
				using (BinaryReader br = new BinaryReader(fs))
				{
					byte ver1 = br.ReadByte();

					switch (ver1)
					{
						case 1:
						{
							readHeaderV1(br);
							break;
						}
						case 2:
						{
							readHeaderV2(br);
							break;
						}
					}

					x.replaceEntries(Entry.Read(br, null, skipEncDesc : (skipEncodeDescription ? true : false)));
				}
			}
		}

		x.setRoot(x);
		skipEncodeDescription = false;

		return x;
	}


	/// <summary>
	/// Loads the specified custom Hive into memory
	/// </summary>
	public static Key loadHive(Stream hsrd)
	{

		Key x = new Key("root");
		x.Type = EntryType.Root;

		using (BinaryReader br = new BinaryReader(hsrd))
		{
			byte ver1 = br.ReadByte();

			switch (ver1)
			{
				case 1:
				{
					readHeaderV1(br);
					break;
				}
				case 2:
				{
					readHeaderV2(br);
					break;
				}
			}

			x.replaceEntries(Entry.Read(br, null, skipEncDesc: (skipEncodeDescription ? true : false)));
		}

		x.setRoot(x);
		skipEncodeDescription = false;

		return x;
	}

	private static void ensureFolder()
	{
		if(!Directory.Exists(DataFolder))
		{
			Directory.CreateDirectory(DataFolder);
		}
	}

	private static void resetFile(string filename)
	{
		File.WriteAllBytes(filename, new byte[0] { });
	}

	// HSRD
	// Harbinger Serialized Registry Data

	public static string RootHSRD = Embed.DefaultFileName;
	public static string DataFolder = Embed.RegistryFolder;

	public static string HSRDExtension = Embed.Extension;


        [Subscribe(Priority.Severe)]
	public static void onStartup(StartupEvent ev)
	{
		Console.WriteLine("Loading Registry...");
		load();


		EventBus.Broadcast(new RegistryLoadedEvent(Entry.ROOT));
	}



        [Subscribe(Priority.Low)]
	public static void onShutdown(ShutdownEvent ev)
	{
		Console.WriteLine("Flushing Registry...");
		if(!EventBus.Broadcast(new RegistrySavedEvent(Entry.ROOT, RootHSRD)))
			save();
	}

}
