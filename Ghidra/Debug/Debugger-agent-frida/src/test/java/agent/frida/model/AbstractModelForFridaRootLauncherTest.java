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
package agent.frida.model;

import static org.junit.Assert.*;

import java.util.List;
import java.util.Map;

import org.junit.Ignore;
import org.junit.Test;

import ghidra.dbg.DebugModelConventions.AsyncState;
import ghidra.dbg.target.*;
import ghidra.dbg.target.TargetExecutionStateful.TargetExecutionState;
import ghidra.dbg.target.TargetMethod.ParameterDescription;
import ghidra.dbg.target.TargetMethod.TargetParameterMap;
import ghidra.dbg.test.AbstractDebuggerModelLauncherTest;
import ghidra.dbg.util.PathUtils;

public abstract class AbstractModelForFridaRootLauncherTest
		extends AbstractDebuggerModelLauncherTest {

	@Override
	public List<String> getExpectedLauncherPath() {
		return PathUtils.parse("");
	}

	@Override
	public DebuggerTestSpecimen getLaunchSpecimen() {
		return FridaLinuxSpecimen.PRINT;
	}

	@Override
	public TargetParameterMap getExpectedLauncherParameters() {
		return TargetParameterMap.copyOf(Map.ofEntries(
			Map.entry("args", ParameterDescription.create(String.class, "args", true, "",
				"Command Line", "space-separated command-line arguments"))));
	}

	@Override
	public void assertEnvironment(TargetEnvironment environment) {
		assertEquals("x64", environment.getArchitecture());
		//assertTrue(environment.getOperatingSystem().startsWith("macos"));
		assertTrue(environment.getDebugger().toLowerCase().contains("frida"));
	}

	@Override
	protected void runTestDetach(DebuggerTestSpecimen specimen)
			throws Throwable {
		TargetProcess process = retryForProcessRunning(specimen, this);
		TargetDetachable detachable = m.suitable(TargetDetachable.class, process.getPath());
		waitAcc(detachable);
		waitOn(detachable.detach());
	}

	@Override
	protected void runTestResumeTerminates(DebuggerTestSpecimen specimen) throws Throwable {
		TargetProcess process = retryForProcessRunning(specimen, this);
		TargetResumable resumable = m.suitable(TargetResumable.class, process.getPath());
		AsyncState state =
			new AsyncState(m.suitable(TargetExecutionStateful.class, process.getPath()));
		TargetExecutionState st = waitOn(state.waitUntil(s -> s == TargetExecutionState.STOPPED));
		assertTrue(st.isAlive());
		waitOn(resumable.resume());
	}

	@Override
	@Ignore
	@Test
	public void testLauncherIsWhereExpected() throws Throwable {
		// Disabled as of 220609
	}

	@Override
	@Ignore
	@Test
	public void testLaunchParameters() throws Throwable {
		// Disabled as of 220609		
	}

	@Override
	@Ignore
	@Test
	public void testLaunch() throws Throwable {
		// Disabled as of 220609
	}

	@Override
	@Ignore
	@Test
	public void testLaunchThenDetach() throws Throwable {
		// Disabled as of 220609
	}

	@Override
	@Ignore
	@Test
	public void testLaunchThenKill() throws Throwable {
		// Disabled as of 220609
	}

	@Override
	@Ignore
	@Test
	public void testLaunchThenResume() throws Throwable {
		// Disabled as of 220609
	}

	@Override
	@Ignore
	@Test
	public void testLaunchShowsInProcessContainer() throws Throwable {
		// Disabled as of 220609
	}

}
