/* ###
 * IP: GHIDRA
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ghidra.file.formats.android.dex.format;

import ghidra.app.util.bin.BinaryReader;
import ghidra.app.util.bin.StructConverter;
import ghidra.program.model.data.*;
import ghidra.util.exception.DuplicateNameException;

import java.io.IOException;

public class PackedSwitchPayload implements StructConverter {

	public final static short MAGIC = 0x0100;

	private short ident;
	private short size;
	private int firstKey;
	private int[] targets;

	public PackedSwitchPayload(BinaryReader reader) throws IOException {
		ident = reader.readNextShort();
		size = reader.readNextShort();
		firstKey = reader.readNextInt();
		targets = reader.readNextIntArray(size & 0xffff);
	}

	public short getIdent() {
		return ident;
	}

	public short getSize() {
		return size;
	}

	public int getFirstKey() {
		return firstKey;
	}

	public int[] getTargets() {
		return targets;
	}

	@Override
	public DataType toDataType() throws DuplicateNameException, IOException {
		Structure structure = new StructureDataType("packed_switch_payload_" + size, 0);
		structure.add(WORD, "ident", null);
		structure.add(WORD, "size", null);
		structure.add(DWORD, "first_key", null);
		structure.add(new ArrayDataType(DWORD, size & 0xffff, DWORD.getLength()), "targets", null);
		structure.setCategoryPath(new CategoryPath("/dex/packed_switch_payload"));
		return structure;
	}

}
