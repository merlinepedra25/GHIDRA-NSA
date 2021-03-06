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
package ghidra.pcode.exec;

import java.util.HashMap;
import java.util.Map;

import ghidra.pcode.utils.Utils;
import ghidra.program.model.address.Address;
import ghidra.program.model.address.AddressSpace;
import ghidra.program.model.mem.MemBuffer;

/**
 * A rider state piece that reports the address of the control value
 * 
 * <p>
 * This is intended for use as the right side of a {@link PairedPcodeExecutorState} or
 * {@link PairedPcodeExecutorStatePiece}. Except for unique spaces, sets are ignored, and gets
 * simply echo back the address of the requested read. In unique spaces, the "address of" is treated
 * as the value, so that values transiting unique space can correctly have their source addresses
 * reported.
 */
public class AddressOfPcodeExecutorState
		implements PcodeExecutorStatePiece<byte[], Address> {
	private final boolean isBigEndian;
	private Map<Long, Address> unique = new HashMap<>();

	/**
	 * Construct an "address of" state piece
	 * 
	 * @param isBigEndian true if the control language is big endian
	 */
	public AddressOfPcodeExecutorState(boolean isBigEndian) {
		this.isBigEndian = isBigEndian;
	}

	@Override
	public byte[] longToOffset(AddressSpace space, long l) {
		return Utils.longToBytes(l, space.getPointerSize(), isBigEndian);
	}

	@Override
	public void setVar(AddressSpace space, byte[] offset, int size,
			boolean truncateAddressableUnit, Address val) {
		if (!space.isUniqueSpace()) {
			return;
		}
		long off = Utils.bytesToLong(offset, offset.length, isBigEndian);
		unique.put(off, val);
	}

	@Override
	public Address getVar(AddressSpace space, byte[] offset, int size,
			boolean truncateAddressableUnit) {
		long off = Utils.bytesToLong(offset, offset.length, isBigEndian);
		if (!space.isUniqueSpace()) {
			return space.getAddress(off);
		}
		return unique.get(off);
	}

	@Override
	public MemBuffer getConcreteBuffer(Address address) {
		throw new AssertionError("Cannot make 'address of' concrete buffers");
	}
}
