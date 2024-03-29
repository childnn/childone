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

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.RequestDispatcher;

/**
 * Implements the Server-side #include command
 *
 * @author Bip Thelin
 * @author Paul Speed
 * @author Dan Sandberg
 * @version $Revision: 1.4 $, $Date: 2004/08/26 21:40:03 $
 */
public final class SSIInclude implements SSICommand {
    /**
     * @see SSICommand
     */
    public void process(SSIMediator ssiMediator,
			String commandName,
			String[] paramNames,
			String[] paramValues,
			PrintWriter writer) {

	String configErrMsg = ssiMediator.getConfigErrMsg();

	for ( int i=0; i < paramNames.length; i++ ) {
	    String paramName = paramNames[i];
	    String paramValue = paramValues[i];
	    String substitutedValue = ssiMediator.substituteVariables( paramValue );

	    try {
		if ( paramName.equalsIgnoreCase("file") ||
		     paramName.equalsIgnoreCase("virtual") ) {
		    boolean virtual = paramName.equalsIgnoreCase("virtual");
		    String text = ssiMediator.getFileText( substitutedValue, virtual );
		    writer.write( text );
		} else {
		    ssiMediator.log("#include--Invalid attribute: " + paramName );
		    writer.write( configErrMsg );
		}
	    } catch ( IOException e ) {
		ssiMediator.log("#include--Couldn't include file: " + substitutedValue, e );
		writer.write( configErrMsg );
	    }
	}
    }
}

