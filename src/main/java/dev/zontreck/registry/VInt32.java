package dev.zontreck.registry;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class VInt32 extends Entry {
        public VInt32(String name, int value) {
                super(EntryType.Int32, name);
                Parent = null;
                Value = value;
        }

        public int Value;

        @Override
        public void readValue(DataInputStream stream) throws IOException {
                Value = stream.readInt();
        }

        @Override
        public void Write(DataOutputStream stream) throws IOException {
                super.Write(stream);
                stream.writeInt(Value);
        }

        @Override
        public String PrettyPrint(int indent) {
                return super.PrettyPrint(indent) + " [" + Value + "]";
        }

        public VInt32 setInt32(int value) {
                Value = value;
                return this;
        }
}
