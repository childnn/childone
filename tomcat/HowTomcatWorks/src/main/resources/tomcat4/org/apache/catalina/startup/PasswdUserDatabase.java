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


package tomcat4.org.apache.catalina.startup;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Enumeration;


/**
 * Concrete implementation of the <strong>UserDatabase</code> interface
 * that processes the <code>/etc/passwd</code> file on a Unix system.
 *
 * @author Craig R. McClanahan
 * @version $Revision: 1.3 $ $Date: 2004/08/26 21:41:41 $
 */

public final class PasswdUserDatabase
    implements UserDatabase {


    // --------------------------------------------------------- Constructors


    /**
     * Initialize a new instance of this user database component.
     */
    public PasswdUserDatabase() {

        super();

    }


    // --------------------------------------------------- Instance Variables


    /**
     * The pathname of the Unix password file.
     */
    private static final String PASSWORD_FILE = "/etc/passwd";


    /**
     * The set of home directories for all defined users, keyed by username.
     */
    private Hashtable homes = new Hashtable();


    /**
     * The UserConfig listener with which we are associated.
     */
    private UserConfig userConfig = null;


    // ----------------------------------------------------------- Properties


    /**
     * Return the UserConfig listener with which we are associated.
     */
    public UserConfig getUserConfig() {

        return (this.userConfig);

    }


    /**
     * Set the UserConfig listener with which we are associated.
     *
     * @param userConfig The new UserConfig listener
     */
    public void setUserConfig(UserConfig userConfig) {

        this.userConfig = userConfig;
        init();

    }


    // ------------------------------------------------------- Public Methods


    /**
     * Return an absolute pathname to the home directory for the specified user.
     *
     * @param user User for which a home directory should be retrieved
     */
    public String getHome(String user) {

        return ((String) homes.get(user));

    }


    /**
     * Return an enumeration of the usernames defined on this server.
     */
    public Enumeration getUsers() {

        return (homes.keys());

    }


    // ------------------------------------------------------ Private Methods


    /**
     * Initialize our set of users and home directories.
     */
    private void init() {

        BufferedReader reader = null;
        try {

            reader = new BufferedReader(new FileReader(PASSWORD_FILE));

            while (true) {

                // Accumulate the next line
                StringBuffer buffer = new StringBuffer();
                while (true) {
                    int ch = reader.read();
                    if ((ch < 0) || (ch == '\n'))
                        break;
                    buffer.append((char) ch);
                }
                String line = buffer.toString();
                if (line.length() < 1)
                    break;

                // Parse the line into constituent elements
                int n = 0;
                String tokens[] = new String[7];
                for (int i = 0; i < tokens.length; i++)
                    tokens[i] = null;
                while (n < tokens.length) {
                    String token = null;
                    int colon = line.indexOf(':');
                    if (colon >= 0) {
                        token = line.substring(0, colon);
                        line = line.substring(colon + 1);
                    } else {
                        token = line;
                        line = "";
                    }
                    tokens[n++] = token;
                }

                // Add this user and corresponding directory
                if ((tokens[0] != null) && (tokens[5] != null))
                    homes.put(tokens[0], tokens[5]);

            }

            reader.close();
            reader = null;

        } catch (Exception e) {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException f) {
                    ;
                }
                reader = null;
            }
        }

    }


}
