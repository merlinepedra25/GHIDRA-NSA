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
package ghidra.app.util.bin.format.pe.debug;

import java.io.IOException;
import java.util.ArrayList;

import ghidra.app.util.bin.BinaryReader;
import ghidra.app.util.bin.format.pe.OffsetValidator;
import ghidra.util.Msg;

/**
 * A possible implementation of the FIXUP debug directory. 
 * It may be inaccurate and/or incomplete.
 */
public class DebugFixup {
	private DebugFixupElement[] elements;

	/**
	 * Constructor
	 * @param reader the binary reader
	 * @param debugDir the debug directory associated to this FIXUP
	 * @param ntHeader 
	 */
	DebugFixup(BinaryReader reader, DebugDirectory debugDir, OffsetValidator validator)
			throws IOException {
		int ptr = debugDir.getPointerToRawData();
		if (!validator.checkPointer(ptr)) {
			Msg.error(this, "Invalid pointer " + Long.toHexString(ptr));
			return;
		}
		int size = debugDir.getSizeOfData();

		ArrayList<DebugFixupElement> list = new ArrayList<DebugFixupElement>();

		while (size > 0) {
			list.add(new DebugFixupElement(reader, ptr));
			ptr += DebugFixupElement.SIZEOF;
			size -= DebugFixupElement.SIZEOF;
		}

		elements = new DebugFixupElement[list.size()];
		list.toArray(elements);
	}

	/**
	 * Returns the array of FIXUP elements associated with this fixup debug directory.
	 * @return the array of FIXUP elements associated with this fixup debug directory
	 */
	public DebugFixupElement[] getDebugFixupElements() {
		return elements;
	}
}
