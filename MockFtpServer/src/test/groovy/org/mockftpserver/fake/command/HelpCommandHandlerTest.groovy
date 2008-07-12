/*
 * Copyright 2008 the original author or authors.
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
package org.mockftpserver.fake.command

import org.mockftpserver.core.command.Command
import org.mockftpserver.core.command.CommandHandler
import org.mockftpserver.core.command.CommandNames
import org.mockftpserver.core.command.ReplyCodes

/**
 * Tests for HelpCommandHandler
 *
 * @version $Revision$ - $Date$
 *
 * @author Chris Mair
 */
class HelpCommandHandlerTest extends AbstractFakeCommandHandlerTest {

    void testHandleCommand_Arg() {
        serverConfiguration.helpText = [abc: '_abc']
        commandHandler.handleCommand(createCommand(['abc']), session)
        assertSessionReply(ReplyCodes.HELP_OK, '_abc')
    }

    void testHandleCommand_NoArg_UseDefault() {
        serverConfiguration.helpText = ['': 'default']
        commandHandler.handleCommand(createCommand([]), session)
        assertSessionReply(ReplyCodes.HELP_OK, 'default')
    }

    void testHandleCommand_Unrecognized() {
        serverConfiguration.setTextForKey('noHelpTextDefined', 'xxx {0}')
        serverConfiguration.helpText = ['': 'default']
        commandHandler.handleCommand(createCommand(['unrecognized']), session)

        // Reply text includes the message text and the passed-in command as a message parameter 
        assertSessionReply(ReplyCodes.HELP_OK, 'xxx')
        assertSessionReply(ReplyCodes.HELP_OK, 'unrecognized')
    }

    //-------------------------------------------------------------------------
    // Helper Methods
    //-------------------------------------------------------------------------

    CommandHandler createCommandHandler() {
        new HelpCommandHandler()
    }

    Command createValidCommand() {
        return new Command(CommandNames.HELP, [])
    }

    void setUp() {
        super.setUp()
    }

}