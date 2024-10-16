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

package tomcat4.org.apache.catalina.ssi;


import java.io.PrintWriter;

/**
 * Implements the Server-side #set command
 *
 * @author Paul Speed 
 * @author Dan Sandberg
 * @version $Revision: 1.4 $, $Date: 2004/08/26 21:40:03 $
 */
public class SSISet implements SSICommand {
    /**
     * @see SSICommand
     */
    public void process( SSIMediator ssiMediator,
			 String commandName,
			 String[] paramNames,
			 String[] paramValues,
			 PrintWriter writer) throws SSIStopProcessingException {

	String errorMessage = ssiMediator.getConfigErrMsg();
	String variableName = null;
	for ( int i=0; i < paramNames.length; i++ ) {
	    String paramName = paramNames[i];
	    String paramValue = paramValues[i];

	    if ( paramName.equalsIgnoreCase("var") ) {
		variableName = paramValue;
	    } else if ( paramName.equalsIgnoreCase("value") ) {
		if ( variableName != null ) {
		    String substitutedValue = ssiMediator.substituteVariables( paramValue );
		    ssiMediator.setVariableValue( variableName, substitutedValue );
		} else {
		    ssiMediator.log("#set--no variable specified");
		    writer.write( errorMessage );
		    throw new SSIStopProcessingException();
		}
	    } else {
		ssiMediator.log("#set--Invalid attribute: " + paramName );
		writer.write( errorMessage );
		throw new SSIStopProcessingException();
	    }
	}
    }
}
