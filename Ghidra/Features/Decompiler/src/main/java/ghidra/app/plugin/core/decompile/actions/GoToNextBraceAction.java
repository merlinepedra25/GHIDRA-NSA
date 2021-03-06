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
package ghidra.app.plugin.core.decompile.actions;

import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import docking.action.KeyBindingData;
import ghidra.app.decompiler.ClangSyntaxToken;
import ghidra.app.decompiler.ClangToken;
import ghidra.app.decompiler.component.DecompilerPanel;
import ghidra.app.decompiler.component.DecompilerUtils;
import ghidra.app.plugin.core.decompile.DecompilerActionContext;
import ghidra.app.util.HelpTopics;
import ghidra.util.HelpLocation;

/**
 * Go to the next enclosing closing brace in the forward direction.
 */
public class GoToNextBraceAction extends AbstractDecompilerAction {

	public GoToNextBraceAction() {
		super("Go To Next Brace");

		setHelpLocation(new HelpLocation(HelpTopics.DECOMPILER, "GoToBrace"));
		setKeyBindingData(
			new KeyBindingData(KeyEvent.VK_CLOSE_BRACKET, InputEvent.SHIFT_DOWN_MASK));
	}

	@Override
	protected boolean isEnabledForDecompilerContext(DecompilerActionContext context) {
		return true;
	}

	@Override
	protected void decompilerActionPerformed(DecompilerActionContext context) {

		ClangToken token = context.getTokenAtCursor();
		ClangSyntaxToken brace = DecompilerUtils.getNextBrace(token, true);
		if (brace != null) {
			DecompilerPanel panel = context.getDecompilerPanel();
			panel.goToToken(brace);
		}
	}

}
