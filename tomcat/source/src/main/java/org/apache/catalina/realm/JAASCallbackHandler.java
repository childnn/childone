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


package org.apache.catalina.realm;


import javax.security.auth.callback.*;
import java.io.IOException;


/**
 * <p>Implementation of the JAAS <strong>CallbackHandler</code> interface,
 * used to negotiate delivery of the username and credentials that were
 * specified to our constructor.  No interaction with the user is required
 * (or possible).</p>
 *
 * @author Craig R. McClanahan
 * @version $Revision: 1.2 $ $Date: 2004/08/26 21:37:21 $
 */

public class JAASCallbackHandler implements CallbackHandler {


    // ------------------------------------------------------------ Constructor


    /**
     * The password to be authenticated with.
     */
    protected String password = null;


    // ----------------------------------------------------- Instance Variables
    /**
     * The associated <code>JAASRealm</code> instance.
     */
    protected JAASRealm realm = null;
    /**
     * The username to be authenticated with.
     */
    protected String username = null;


    /**
     * Construct a callback handler configured with the specified values.
     *
     * @param realm    Our associated JAASRealm instance
     * @param username Username to be authenticated with
     * @param password Password to be authenticated with
     */
    public JAASCallbackHandler(JAASRealm realm, String username,
                               String password) {

        super();
        this.realm = realm;
        this.username = username;
        this.password = password;

    }


    // --------------------------------------------------------- Public Methods

    /**
     * Retrieve the information requested in the provided Callbacks.  This
     * implementation only recognizes <code>NameCallback</code> and
     * <code>PasswordCallback</code> instances.
     *
     * @param callbacks The set of callbacks to be processed
     * @throws IOException                  if an input/output error occurs
     * @throws UnsupportedCallbackException if the login method requests
     *                                      an unsupported callback type
     */
    public void handle(Callback callbacks[])
            throws IOException, UnsupportedCallbackException {

        for (int i = 0; i < callbacks.length; i++) {

            if (callbacks[i] instanceof NameCallback) {
                if (realm.getDebug() >= 3)
                    realm.log("Returning username " + username);
                ((NameCallback) callbacks[i]).setName(username);
            } else if (callbacks[i] instanceof PasswordCallback) {
                if (realm.getDebug() >= 3)
                    realm.log("Returning password " + password);
                ((PasswordCallback) callbacks[i]).setPassword
                        (password.toCharArray());
            } else {
                throw new UnsupportedCallbackException(callbacks[i]);
            }


        }

    }


}
