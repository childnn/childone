/*
 * Copyright 1999,2004 The Apache Software Foundation.
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

package org.apache.catalina.ssi;

import org.apache.catalina.util.IOTools;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

/**
 * Implements the Server-side #exec command
 *
 * @author Bip Thelin
 * @author Amy Roh
 * @author Paul Speed
 * @author Dan Sandberg
 * @version $Revision: 1.4 $, $Date: 2004/08/26 21:39:40 $
 */
public class SSIExec implements SSICommand {
    protected final static int BUFFER_SIZE = 1024;
    protected SSIInclude ssiInclude = new SSIInclude();

    /**
     * @see SSICommand
     */
    public void process(SSIMediator ssiMediator,
                        String commandName,
                        String[] paramNames,
                        String[] paramValues,
                        PrintWriter writer) {

        String configErrMsg = ssiMediator.getConfigErrMsg();
        String paramName = paramNames[0];
        String paramValue = paramValues[0];
        String substitutedValue = ssiMediator.substituteVariables(paramValue);

        if (paramName.equalsIgnoreCase("cgi")) {
            ssiInclude.process(ssiMediator, "include", new String[]{"virtual"}, new String[]{substitutedValue}, writer);
        } else if (paramName.equalsIgnoreCase("cmd")) {
            boolean foundProgram = false;
            try {
                Runtime rt = Runtime.getRuntime();
                Process proc = rt.exec(substitutedValue);
                foundProgram = true;

                BufferedReader stdOutReader = new BufferedReader(new InputStreamReader(proc.getInputStream()));
                BufferedReader stdErrReader = new BufferedReader(new InputStreamReader(proc.getErrorStream()));

                char[] buf = new char[BUFFER_SIZE];
                IOTools.flow(stdErrReader, writer, buf);
                IOTools.flow(stdOutReader, writer, buf);
                proc.waitFor();
            } catch (InterruptedException e) {
                ssiMediator.log("Couldn't exec file: " + substitutedValue, e);
                writer.write(configErrMsg);
            } catch (IOException e) {
                if (!foundProgram) {
                    //apache doesn't output an error message if it can't find a program
                }
                ssiMediator.log("Couldn't exec file: " + substitutedValue, e);
            }
        }
    }
}
