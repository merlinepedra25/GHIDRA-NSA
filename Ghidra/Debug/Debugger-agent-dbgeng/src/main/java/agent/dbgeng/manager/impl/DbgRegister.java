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
package agent.dbgeng.manager.impl;

import agent.dbgeng.dbgeng.DebugRegisters.DebugRegisterDescription;

public class DbgRegister implements Comparable<DbgRegister> {
	private final String name;
	private final int number;
	private final int size;
	private DebugRegisterDescription desc;

	/**
	 * Construct a new register descriptor
	 * 
	 * @param name the register's name
	 * @param number the dbgeng-assigned register number
	 * @param size the size in bytes
	 */
	public DbgRegister(String name, int number, int size) {
		this.name = name;
		this.number = number;
		this.size = size;
	}

	public DbgRegister(DebugRegisterDescription desc) {
		this.name = desc.name;
		this.number = desc.index;
		this.size = desc.type.byteLength;
		this.desc = desc;
	}

	/**
	 * Get the register's name
	 * 
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Get the register's Dbg-assigned number
	 * 
	 * @return the number
	 */
	public int getNumber() {
		return number;
	}

	/**
	 * Get the register's size in bytes
	 * 
	 * @return the size
	 */
	public int getSize() {
		return size;
	}

	@Override
	public int compareTo(DbgRegister that) {
		return this.number - that.number;
	}

	@Override
	public String toString() {
		return "<" + getClass().getSimpleName() + " " + name + " (" + number + ")>";
	}

	public boolean isBaseRegister() {
		if (desc == null) {
			return true;
		}
		return desc.subregMask == 0;
	}
}
