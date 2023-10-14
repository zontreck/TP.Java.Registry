package dev.zontreck.registry;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class HeaderV1 extends Header {
	public byte curPatch = 2;
	public String Creator = Constants.Creator;

	@Override
	public void Read(DataInputStream dis) throws IOException {
		curPatch = (byte) dis.readInt();
		Creator = dis.readUTF();

		dis.readNBytes(16);
	}

	@Override
	public void Write(DataOutputStream dos) throws IOException {
		HeaderV1 v1 = new HeaderV1();
		dos.writeInt(v1.Version());
		dos.writeInt(v1.getPatch());
		dos.writeUTF(v1.Creator);
		dos.write(new byte[16]);
	}


	@Override
	public byte Version() {
		return 1;
	}

	@Override
	public byte getPatch() {
		return curPatch;
	}
}
