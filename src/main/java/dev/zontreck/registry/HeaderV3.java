package dev.zontreck.registry;

import java.io.*;

public class HeaderV3 extends Header {

	public byte curPatch = 0;

	public String FormatSig = Constants.Extension;
	public String CreatorSig = Constants.Creator;

	public long LastSavedAt;
	public int NumOfTags;


	@Override
	public void Read(DataInputStream dis) throws IOException {
		byte[] remain = dis.readNBytes(127);
		ByteArrayInputStream bais = new ByteArrayInputStream(remain);
		DataInputStream dr = new DataInputStream(bais);

		curPatch = dr.readByte();
		FormatSig = dr.readUTF();
		CreatorSig = dr.readUTF();
		LastSavedAt = dr.readLong();
		NumOfTags = dr.readInt();
	}

	@Override
	public void Write(DataOutputStream dos) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream(128);
		DataOutputStream dw = new DataOutputStream(baos);
		dw.writeByte(Version());
		dw.writeByte(getPatch());
		dw.writeUTF(FormatSig);
		dw.writeUTF(CreatorSig);
		dw.writeLong(LastSavedAt);
		dw.writeInt(NumOfTags);

		dos.write(baos.toByteArray());
	}

	@Override
	public byte Version() {
		return 3;
	}

	@Override
	public byte getPatch() {
		return curPatch;
	}
}
