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
package ghidra.framework.main.logviewer.event;

import java.util.Observable;
import java.util.Observer;

/**
 * Extension of the Java {@link Observer} class that allows clients to send {@link FVEvent}
 * messages to subscribers.
 *
 * <p>Note: this 'listener' class serves as an event 'hub', where clients can push events to this
 * class and register to receive events from this class.   The events given to this listener are
 * heterogeneous and serve as a general message passing system for this API.   This class should
 * be replaced by simple object communication by using normal method calls.
 */
public class FVEventListener extends Observable {

	/**
	 * Fires off the given {@link FVEvent} using the appropriate {@link Observer} methods.
	 * 
	 * @param evt
	 */
	public void send(FVEvent evt) {
		setChanged();
		notifyObservers(evt);
	}

}
