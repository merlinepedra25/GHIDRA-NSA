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

import ghidra.program.model.address.AddressSpace;

/**
 * An executor state decorator which transforms the offset type
 *
 * @param <A> the offset type of the decorator
 * @param <B> the offset type of the delegate
 * @param <T> the type of values
 */
public abstract class AbstractOffsetTransformedPcodeExecutorState<A, B, T>
		implements PcodeExecutorStatePiece<A, T> {

	private final PcodeExecutorStatePiece<B, T> state;

	/**
	 * Construct a decorator around the given delegate
	 * 
	 * @param state the delegate
	 */
	public AbstractOffsetTransformedPcodeExecutorState(PcodeExecutorStatePiece<B, T> state) {
		this.state = state;
	}

	/**
	 * Transform an offset of type {@code A} to type {@code B}
	 * 
	 * @param offset the offset as an {@code A}
	 * @return the offset as a {@code B}
	 */
	protected abstract B transformOffset(A offset);

	@Override
	public void setVar(AddressSpace space, A offset, int size, boolean truncateAddressableUnit,
			T val) {
		state.setVar(space, transformOffset(offset), size, truncateAddressableUnit, val);
	}

	@Override
	public void setVar(AddressSpace space, long offset, int size, boolean truncateAddressableUnit,
			T val) {
		state.setVar(space, offset, size, truncateAddressableUnit, val);
	}

	@Override
	public T getVar(AddressSpace space, A offset, int size, boolean truncateAddressableUnit) {
		return state.getVar(space, transformOffset(offset), size, truncateAddressableUnit);
	}

	@Override
	public T getVar(AddressSpace space, long offset, int size, boolean truncateAddressableUnit) {
		return state.getVar(space, offset, size, truncateAddressableUnit);
	}
}
