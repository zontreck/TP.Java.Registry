package dev.zontreck.registry;

import java.io.*;

public class HeaderV2 extends Header {

	public byte curPatch = 4;

	public String FormatSig = Constants.Extension;
	public String CreatorSig = Constants.Creator;

	public int LastSavedAt;


	@Override
	public void Read(DataInputStream dis) throws IOException {
		byte[] remain = dis.readNBytes(127);
		ByteArrayInputStream bais = new ByteArrayInputStream(remain);
		DataInputStream dr = new DataInputStream(bais);

		curPatch = dr.readByte();
		FormatSig = dr.readUTF();
		CreatorSig = dr.readUTF();
		LastSavedAt = dr.readInt();
	}

	@Override
	public void Write(DataOutputStream dos) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream(128);
		DataOutputStream dw = new DataOutputStream(baos);
		dw.writeByte(Version());
		dw.writeByte(getPatch());
		dw.writeUTF(FormatSig);
		dw.writeUTF(CreatorSig);
		dw.writeInt(LastSavedAt);

		dos.write(baos.toByteArray());
	}

	@Override
	public byte Version() {
		return 2;
	}

	@Override
	public byte getPatch() {
		return curPatch;
	}
}
