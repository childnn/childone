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


import org.apache.catalina.LifecycleException;
import org.apache.catalina.util.StringManager;

import java.security.Principal;
import java.sql.*;
import java.util.ArrayList;
import java.util.Properties;


/**
 * Implmentation of <b>Realm</b> that works with any JDBC supported database.
 * See the JDBCRealm.howto for more details on how to set up the database and
 * for configuration options.
 *
 * <p><strong>TODO</strong> - Support connection pooling (including message
 * format objects) so that <code>authenticate()</code> does not have to be
 * synchronized and would fix the ugly connection logic. </p>
 *
 * @author Craig R. McClanahan
 * @author Carson McDonald
 * @author Ignacio Ortega
 * @version $Revision: 1.24 $ $Date: 2004/08/26 21:37:21 $
 */

public class JDBCRealm
        extends RealmBase {


    // ----------------------------------------------------- Instance Variables


    /**
     * Descriptive information about this Realm implementation.
     */
    protected static final String info =
            "org.apache.catalina.realm.JDBCRealm/1.0";
    /**
     * Descriptive information about this Realm implementation.
     */
    protected static final String name = "JDBCRealm";
    /**
     * The string manager for this package.
     */
    protected static final StringManager sm =
            StringManager.getManager(Constants.Package);
    /**
     * The connection username to use when trying to connect to the database.
     */
    protected String connectionName = null;
    /**
     * The connection URL to use when trying to connect to the database.
     */
    protected String connectionPassword = null;
    /**
     * The connection URL to use when trying to connect to the database.
     */
    protected String connectionURL = null;
    /**
     * The connection to the database.
     */
    protected Connection dbConnection = null;
    /**
     * Instance of the JDBC Driver class we use as a connection factory.
     */
    protected Driver driver = null;
    /**
     * The JDBC driver to use.
     */
    protected String driverName = null;
    /**
     * The PreparedStatement to use for authenticating users.
     */
    protected PreparedStatement preparedCredentials = null;
    /**
     * The PreparedStatement to use for identifying the roles for
     * a specified user.
     */
    protected PreparedStatement preparedRoles = null;
    /**
     * The column in the user role table that names a role
     */
    protected String roleNameCol = null;
    /**
     * The column in the user table that holds the user's credintials
     */
    protected String userCredCol = null;


    /**
     * The column in the user table that holds the user's name
     */
    protected String userNameCol = null;


    /**
     * The table that holds the relation between user's and roles
     */
    protected String userRoleTable = null;


    /**
     * The table that holds user data.
     */
    protected String userTable = null;


    // ------------------------------------------------------------- Properties

    /**
     * Return the username to use to connect to the database.
     */
    public String getConnectionName() {
        return connectionName;
    }

    /**
     * Set the username to use to connect to the database.
     *
     * @param connectionName Username
     */
    public void setConnectionName(String connectionName) {
        this.connectionName = connectionName;
    }

    /**
     * Return the password to use to connect to the database.
     */
    public String getConnectionPassword() {
        return connectionPassword;
    }

    /**
     * Set the password to use to connect to the database.
     *
     * @param connectionPassword User password
     */
    public void setConnectionPassword(String connectionPassword) {
        this.connectionPassword = connectionPassword;
    }

    /**
     * Return the URL to use to connect to the database.
     */
    public String getConnectionURL() {
        return connectionURL;
    }

    /**
     * Set the URL to use to connect to the database.
     *
     * @param connectionURL The new connection URL
     */
    public void setConnectionURL(String connectionURL) {
        this.connectionURL = connectionURL;
    }

    /**
     * Return the JDBC driver that will be used.
     */
    public String getDriverName() {
        return driverName;
    }

    /**
     * Set the JDBC driver that will be used.
     *
     * @param driverName The driver name
     */
    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    /**
     * Return the column in the user role table that names a role.
     */
    public String getRoleNameCol() {
        return roleNameCol;
    }

    /**
     * Set the column in the user role table that names a role.
     *
     * @param roleNameCol The column name
     */
    public void setRoleNameCol(String roleNameCol) {
        this.roleNameCol = roleNameCol;
    }

    /**
     * Return the column in the user table that holds the user's credentials.
     */
    public String getUserCredCol() {
        return userCredCol;
    }

    /**
     * Set the column in the user table that holds the user's credentials.
     *
     * @param userCredCol The column name
     */
    public void setUserCredCol(String userCredCol) {
        this.userCredCol = userCredCol;
    }

    /**
     * Return the column in the user table that holds the user's name.
     */
    public String getUserNameCol() {
        return userNameCol;
    }

    /**
     * Set the column in the user table that holds the user's name.
     *
     * @param userNameCol The column name
     */
    public void setUserNameCol(String userNameCol) {
        this.userNameCol = userNameCol;
    }

    /**
     * Return the table that holds the relation between user's and roles.
     */
    public String getUserRoleTable() {
        return userRoleTable;
    }

    /**
     * Set the table that holds the relation between user's and roles.
     *
     * @param userRoleTable The table name
     */
    public void setUserRoleTable(String userRoleTable) {
        this.userRoleTable = userRoleTable;
    }

    /**
     * Return the table that holds user data..
     */
    public String getUserTable() {
        return userTable;
    }

    /**
     * Set the table that holds user data.
     *
     * @param userTable The table name
     */
    public void setUserTable(String userTable) {
        this.userTable = userTable;
    }


    // --------------------------------------------------------- Public Methods


    /**
     * Return the Principal associated with the specified username and
     * credentials, if there is one; otherwise return <code>null</code>.
     * <p>
     * If there are any errors with the JDBC connection, executing
     * the query or anything we return null (don't authenticate). This
     * event is also logged, and the connection will be closed so that
     * a subsequent request will automatically re-open it.
     *
     * @param username    Username of the Principal to look up
     * @param credentials Password or other credentials to use in
     *                    authenticating this username
     */
    public synchronized Principal authenticate(String username, String credentials) {

        // Number of tries is the numebr of attempts to connect to the database
        // during this login attempt (if we need to open the database)
        // This needs rewritten wuth better pooling support, the existing code
        // needs signature changes since the Prepared statements needs cached
        // with the connections.
        // The code below will try twice if there is a SQLException so the
        // connection may try to be opened again. On normal conditions (including
        // invalid login - the above is only used once.
        int numberOfTries = 2;
        while (numberOfTries > 0) {
            try {

                // Ensure that we have an open database connection
                open();

                // Acquire a Principal object for this user
                Principal principal = authenticate(dbConnection,
                        username, credentials);


                // Return the Principal (if any)
                return (principal);

            } catch (SQLException e) {

                // Log the problem for posterity
                log(sm.getString("jdbcRealm.exception"), e);

                // Close the connection so that it gets reopened next time
                if (dbConnection != null)
                    close(dbConnection);

            }

            numberOfTries--;
        }

        // Worst case scenario
        return null;

    }


    // -------------------------------------------------------- Package Methods


    // ------------------------------------------------------ Protected Methods


    /**
     * Return the Principal associated with the specified username and
     * credentials, if there is one; otherwise return <code>null</code>.
     *
     * @param dbConnection The database connection to be used
     * @param username     Username of the Principal to look up
     * @param credentials  Password or other credentials to use in
     *                     authenticating this username
     * @throws SQLException if a database error occurs
     */
    public synchronized Principal authenticate(Connection dbConnection,
                                               String username,
                                               String credentials)
            throws SQLException {

        // Look up the user's credentials
        String dbCredentials = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            stmt = credentials(dbConnection, username);
            rs = stmt.executeQuery();

            if (rs.next()) {
                dbCredentials = rs.getString(1);
            }
            rs.close();
            rs = null;
            if (dbCredentials == null) {
                return (null);
            }

            dbCredentials = dbCredentials.trim();


            // Validate the user's credentials
            boolean validated = false;
            if (hasMessageDigest()) {
                // Hex hashes should be compared case-insensitive
                validated = (digest(credentials).equalsIgnoreCase(dbCredentials));
            } else {
                validated = (digest(credentials).equals(dbCredentials));
            }

            if (validated) {
                if (debug >= 2)
                    log(sm.getString("jdbcRealm.authenticateSuccess",
                            username));
            } else {
                if (debug >= 2)
                    log(sm.getString("jdbcRealm.authenticateFailure",
                            username));
                return (null);
            }

            // Accumulate the user's roles
            ArrayList roleList = new ArrayList();
            stmt = roles(dbConnection, username);
            rs = stmt.executeQuery();
            while (rs.next()) {
                String role = rs.getString(1);
                if (null != role) {
                    roleList.add(role.trim());
                }
            }
            rs.close();
            rs = null;

            // Create and return a suitable Principal for this user
            return (new GenericPrincipal(this, username, credentials, roleList));
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    log(sm.getString("jdbcRealm.abnormalCloseResultSet"));
                }
            }
            dbConnection.commit();
        }

    }


    /**
     * Close the specified database connection.
     *
     * @param dbConnection The connection to be closed
     */
    protected void close(Connection dbConnection) {

        // Do nothing if the database connection is already closed
        if (dbConnection == null)
            return;

        // Close our prepared statements (if any)
        try {
            preparedCredentials.close();
        } catch (Throwable f) {
            ;
        }
        this.preparedCredentials = null;


        try {
            preparedRoles.close();
        } catch (Throwable f) {
            ;
        }
        this.preparedRoles = null;


        // Close this database connection, and log any errors
        try {
            dbConnection.close();
        } catch (SQLException e) {
            log(sm.getString("jdbcRealm.close"), e); // Just log it here
        } finally {
            this.dbConnection = null;
        }

    }


    /**
     * Return a PreparedStatement configured to perform the SELECT required
     * to retrieve user credentials for the specified username.
     *
     * @param dbConnection The database connection to be used
     * @param username     Username for which credentials should be retrieved
     * @throws SQLException if a database error occurs
     */
    protected PreparedStatement credentials(Connection dbConnection,
                                            String username)
            throws SQLException {

        if (preparedCredentials == null) {
            StringBuffer sb = new StringBuffer("SELECT ");
            sb.append(userCredCol);
            sb.append(" FROM ");
            sb.append(userTable);
            sb.append(" WHERE ");
            sb.append(userNameCol);
            sb.append(" = ?");
            preparedCredentials =
                    dbConnection.prepareStatement(sb.toString());
        }

        if (username == null) {
            preparedCredentials.setNull(1, java.sql.Types.VARCHAR);
        } else {
            preparedCredentials.setString(1, username);
        }

        return (preparedCredentials);
    }


    /**
     * Return a short name for this Realm implementation.
     */
    protected String getName() {

        return (this.name);

    }


    /**
     * Return the password associated with the given principal's user name.
     */
    protected String getPassword(String username) {

        return (null);

    }


    /**
     * Return the Principal associated with the given user name.
     */
    protected Principal getPrincipal(String username) {

        return (null);

    }


    /**
     * Open (if necessary) and return a database connection for use by
     * this Realm.
     *
     * @throws SQLException if a database error occurs
     */
    protected Connection open() throws SQLException {

        // Do nothing if there is a database connection already open
        if (dbConnection != null)
            return (dbConnection);

        // Instantiate our database driver if necessary
        if (driver == null) {
            try {
                Class clazz = Class.forName(driverName);
                driver = (Driver) clazz.newInstance();
            } catch (Throwable e) {
                throw new SQLException(e.getMessage());
            }
        }

        // Open a new connection
        Properties props = new Properties();
        if (connectionName != null)
            props.put("user", connectionName);
        if (connectionPassword != null)
            props.put("password", connectionPassword);
        dbConnection = driver.connect(connectionURL, props);
        dbConnection.setAutoCommit(false);
        return (dbConnection);

    }


    /**
     * Release our use of this connection so that it can be recycled.
     *
     * @param dbConnnection The connection to be released
     */
    protected void release(Connection dbConnection) {

        ; // NO-OP since we are not pooling anything

    }


    /**
     * Return a PreparedStatement configured to perform the SELECT required
     * to retrieve user roles for the specified username.
     *
     * @param dbConnection The database connection to be used
     * @param username     Username for which roles should be retrieved
     * @throws SQLException if a database error occurs
     */
    protected PreparedStatement roles(Connection dbConnection, String username)
            throws SQLException {

        if (preparedRoles == null) {
            StringBuffer sb = new StringBuffer("SELECT ");
            sb.append(roleNameCol);
            sb.append(" FROM ");
            sb.append(userRoleTable);
            sb.append(" WHERE ");
            sb.append(userNameCol);
            sb.append(" = ?");
            preparedRoles =
                    dbConnection.prepareStatement(sb.toString());
        }

        preparedRoles.setString(1, username);
        return (preparedRoles);

    }


    // ------------------------------------------------------ Lifecycle Methods


    /**
     * Prepare for active use of the public methods of this Component.
     *
     * @throws LifecycleException if this component detects a fatal error
     *                            that prevents it from being started
     */
    public void start() throws LifecycleException {

        // Validate that we can open our connection - but let tomcat
        // startup in case the database is temporarily unavailable
        try {
            open();
        } catch (SQLException e) {
            log(sm.getString("jdbcRealm.open"), e);
        }

        // Perform normal superclass initialization
        super.start();

    }


    /**
     * Gracefully shut down active use of the public methods of this Component.
     *
     * @throws LifecycleException if this component detects a fatal error
     *                            that needs to be reported
     */
    public void stop() throws LifecycleException {

        // Perform normal superclass finalization
        super.stop();

        // Close any open DB connection
        close(this.dbConnection);

    }


}

