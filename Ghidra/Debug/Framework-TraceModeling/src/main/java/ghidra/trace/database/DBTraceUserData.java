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
package ghidra.trace.database;

import java.io.IOException;

import db.DBHandle;
import ghidra.framework.data.DomainObjectAdapterDB;
import ghidra.trace.model.TraceUserData;
import ghidra.util.task.TaskMonitor;

public class DBTraceUserData extends DomainObjectAdapterDB implements TraceUserData {

	private static String getName(DBTrace trace) {
		return trace.getName() + "_UserData";
	}

	protected DBTraceUserData(DBTrace trace) throws IOException {
		super(new DBHandle(), getName(trace), 500, trace);
		// TODO: Create database and such
	}

	public DBTraceUserData(DBHandle dbh, DBTrace trace, TaskMonitor monitor) {
		super(dbh, getName(trace), 500, trace);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean isChangeable() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return null;
	}

}
