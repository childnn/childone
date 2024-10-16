/*
 * Copyright 1999-2001,2004 The Apache Software Foundation.
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


package org.apache.catalina.authenticator;


import org.apache.catalina.*;
import org.apache.catalina.util.LifecycleSupport;
import org.apache.catalina.util.StringManager;
import org.apache.catalina.valves.ValveBase;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;
import java.util.HashMap;


/**
 * A <strong>Valve</strong> that supports a "single sign on" user experience,
 * where the security identity of a user who successfully authenticates to one
 * web application is propogated to other web applications in the same
 * security domain.  For successful use, the following requirements must
 * be met:
 * <ul>
 * <li>This Valve must be configured on the Container that represents a
 *     virtual host (typically an implementation of <code>Host</code>).</li>
 * <li>The <code>Realm</code> that contains the shared user and role
 *     information must be configured on the same Container (or a higher
 *     one), and not overridden at the web application level.</li>
 * <li>The web applications themselves must use one of the standard
 *     Authenticators found in the
 *     <code>org.apache.catalina.authenticator</code> package.</li>
 * </ul>
 *
 * @author Craig R. McClanahan
 * @version $Revision: 1.15 $ $Date: 2004/08/26 21:27:39 $
 */

public class SingleSignOn
        extends ValveBase
        implements Lifecycle, SessionListener {


    // ----------------------------------------------------- Instance Variables


    /**
     * The string manager for this package.
     */
    protected final static StringManager sm =
            StringManager.getManager(Constants.Package);
    /**
     * Descriptive information about this Valve implementation.
     */
    protected static String info =
            "org.apache.catalina.authenticator.SingleSignOn";
    /**
     * The cache of SingleSignOnEntry instances for authenticated Principals,
     * keyed by the cookie value that is used to select them.
     */
    protected HashMap cache = new HashMap();
    /**
     * The debugging detail level for this component.
     */
    protected int debug = 0;
    /**
     * The lifecycle event support for this component.
     */
    protected LifecycleSupport lifecycle = new LifecycleSupport(this);
    /**
     * The cache of single sign on identifiers, keyed by the Session that is
     * associated with them.
     */
    protected HashMap reverse = new HashMap();
    /**
     * Component started flag.
     */
    protected boolean started = false;
    /**
     * Indicates whether this valve should require a downstream Authenticator to
     * reauthenticate each request, or if it itself can bind a UserPrincipal
     * and AuthType object to the request.
     */
    private boolean requireReauthentication = false;


    // ------------------------------------------------------------- Properties

    /**
     * Return the debugging detail level.
     */
    public int getDebug() {

        return (this.debug);

    }


    /**
     * Set the debugging detail level.
     *
     * @param debug The new debugging detail level
     */
    public void setDebug(int debug) {

        this.debug = debug;

    }


    /**
     * Gets whether each request needs to be reauthenticated (by an
     * Authenticator downstream in the pipeline) to the security
     * <code>Realm</code>, or if this Valve can itself bind security info
     * to the request based on the presence of a valid SSO entry without
     * rechecking with the <code>Realm</code..
     *
     * @return <code>true</code> if it is required that a downstream
     * Authenticator reauthenticate each request before calls to
     * <code>HttpServletRequest.setUserPrincipal()</code>
     * and <code>HttpServletRequest.setAuthType()</code> are made;
     * <code>false</code> if the <code>Valve</code> can itself make
     * those calls relying on the presence of a valid SingleSignOn
     * entry associated with the request.
     * @see #setRequireReauthentication
     */
    public boolean getRequireReauthentication() {
        return requireReauthentication;
    }


    /**
     * Sets whether each request needs to be reauthenticated (by an
     * Authenticator downstream in the pipeline) to the security
     * <code>Realm</code>, or if this Valve can itself bind security info
     * to the request, based on the presence of a valid SSO entry, without
     * rechecking with the <code>Realm</code.
     * <p>
     * If this property is <code>false</code> (the default), this
     * <code>Valve</code> will bind a UserPrincipal and AuthType to the request
     * if a valid SSO entry is associated with the request.  It will not notify
     * the security <code>Realm</code> of the incoming request.
     * <p>
     * This property should be set to <code>true</code> if the overall server
     * configuration requires that the <code>Realm</code> reauthenticate each
     * request thread.  An example of such a configuration would be one where
     * the <code>Realm</code> implementation provides security for both a
     * web tier and an associated EJB tier, and needs to set security
     * credentials on each request thread in order to support EJB access.
     * <p>
     * If this property is set to <code>true</code>, this Valve will set flags
     * on the request notifying the downstream Authenticator that the request
     * is associated with an SSO session.  The Authenticator will then call its
     * {@link AuthenticatorBase#reauthenticateFromSSO reauthenticateFromSSO}
     * method to attempt to reauthenticate the request to the
     * <code>Realm</code>, using any credentials that were cached with this
     * Valve.
     * <p>
     * The default value of this property is <code>false</code>, in order
     * to maintain backward compatibility with previous versions of Tomcat.
     *
     * @param required <code>true</code> if it is required that a downstream
     *                 Authenticator reauthenticate each request before calls
     *                 to  <code>HttpServletRequest.setUserPrincipal()</code>
     *                 and <code>HttpServletRequest.setAuthType()</code> are
     *                 made; <code>false</code> if the <code>Valve</code> can
     *                 itself make those calls relying on the presence of a
     *                 valid SingleSignOn entry associated with the request.
     * @see AuthenticatorBase#reauthenticateFromSSO
     */
    public void setRequireReauthentication(boolean required) {
        this.requireReauthentication = required;
    }


    // ------------------------------------------------------ Lifecycle Methods


    /**
     * Add a lifecycle event listener to this component.
     *
     * @param listener The listener to add
     */
    public void addLifecycleListener(LifecycleListener listener) {

        lifecycle.addLifecycleListener(listener);

    }


    /**
     * Get the lifecycle listeners associated with this lifecycle. If this
     * Lifecycle has no listeners registered, a zero-length array is returned.
     */
    public LifecycleListener[] findLifecycleListeners() {

        return lifecycle.findLifecycleListeners();

    }


    /**
     * Remove a lifecycle event listener from this component.
     *
     * @param listener The listener to remove
     */
    public void removeLifecycleListener(LifecycleListener listener) {

        lifecycle.removeLifecycleListener(listener);

    }


    /**
     * Prepare for the beginning of active use of the public methods of this
     * component.  This method should be called after <code>configure()</code>,
     * and before any of the public methods of the component are utilized.
     *
     * @throws LifecycleException if this component detects a fatal error
     *                            that prevents this component from being used
     */
    public void start() throws LifecycleException {

        // Validate and update our current component state
        if (started)
            throw new LifecycleException
                    (sm.getString("authenticator.alreadyStarted"));
        lifecycle.fireLifecycleEvent(START_EVENT, null);
        started = true;

        if (debug >= 1)
            log("Started");

    }


    /**
     * Gracefully terminate the active use of the public methods of this
     * component.  This method should be the last one called on a given
     * instance of this component.
     *
     * @throws LifecycleException if this component detects a fatal error
     *                            that needs to be reported
     */
    public void stop() throws LifecycleException {

        // Validate and update our current component state
        if (!started)
            throw new LifecycleException
                    (sm.getString("authenticator.notStarted"));
        lifecycle.fireLifecycleEvent(STOP_EVENT, null);
        started = false;

        if (debug >= 1)
            log("Stopped");

    }


    // ------------------------------------------------ SessionListener Methods


    /**
     * Acknowledge the occurrence of the specified event.
     *
     * @param event SessionEvent that has occurred
     */
    public void sessionEvent(SessionEvent event) {

        // We only care about session destroyed events
        if (!Session.SESSION_DESTROYED_EVENT.equals(event.getType()))
            return;

        // Look up the single session id associated with this session (if any)
        Session session = event.getSession();
        if (debug >= 1)
            log("Process session destroyed on " + session);
        String ssoId = null;
        synchronized (reverse) {
            ssoId = (String) reverse.get(session);
        }
        if (ssoId == null)
            return;


        /*
         *  Was the session destroyed as the result of a timeout?
         *  If so, we'll just remove the expired session from the
         *  SSO.  If the session was logged out, we'll log out
         *  of all session associated with the SSO.
         */
        if ((session.getMaxInactiveInterval() > 0)
                && (System.currentTimeMillis() - session.getLastAccessedTime() >=
                session.getMaxInactiveInterval() * 1000)) {

            removeSession(ssoId, session);
        } else {
            // The session was logged out.
            // Deregister this single session id, invalidating associated sessions
            deregister(ssoId);
        }

    }


    // ---------------------------------------------------------- Valve Methods


    /**
     * Return descriptive information about this Valve implementation.
     */
    public String getInfo() {

        return (info);

    }


    /**
     * Perform single-sign-on support processing for this request.
     *
     * @param request  The servlet request we are processing
     * @param response The servlet response we are creating
     * @param context  The valve context used to invoke the next valve
     *                 in the current processing pipeline
     * @throws IOException      if an input/output error occurs
     * @throws ServletException if a servlet error occurs
     */
    public void invoke(Request request, Response response,
                       ValveContext context)
            throws IOException, ServletException {

        // If this is not an HTTP request and response, just pass them on
        if (!(request instanceof HttpRequest) ||
                !(response instanceof HttpResponse)) {
            context.invokeNext(request, response);
            return;
        }
        HttpServletRequest hreq =
                (HttpServletRequest) request.getRequest();
        HttpServletResponse hres =
                (HttpServletResponse) response.getResponse();
        request.removeNote(Constants.REQ_SSOID_NOTE);

        // Has a valid user already been authenticated?
        if (debug >= 1)
            log("Process request for '" + hreq.getRequestURI() + "'");
        if (hreq.getUserPrincipal() != null) {
            if (debug >= 1)
                log(" Principal '" + hreq.getUserPrincipal().getName() +
                        "' has already been authenticated");
            context.invokeNext(request, response);
            return;
        }

        // Check for the single sign on cookie
        if (debug >= 1)
            log(" Checking for SSO cookie");
        Cookie cookie = null;
        Cookie cookies[] = hreq.getCookies();
        if (cookies == null)
            cookies = new Cookie[0];
        for (int i = 0; i < cookies.length; i++) {
            if (Constants.SINGLE_SIGN_ON_COOKIE.equals(cookies[i].getName())) {
                cookie = cookies[i];
                break;
            }
        }
        if (cookie == null) {
            if (debug >= 1)
                log(" SSO cookie is not present");
            context.invokeNext(request, response);
            return;
        }

        // Look up the cached Principal associated with this cookie value
        if (debug >= 1)
            log(" Checking for cached principal for " + cookie.getValue());
        SingleSignOnEntry entry = lookup(cookie.getValue());
        if (entry != null) {
            if (debug >= 1)
                log(" Found cached principal '" +
                        entry.getPrincipal().getName() + "' with auth type '" +
                        entry.getAuthType() + "'");
            request.setNote(Constants.REQ_SSOID_NOTE, cookie.getValue());
            // Only set security elements if reauthentication is not required
            if (!getRequireReauthentication()) {
                ((HttpRequest) request).setAuthType(entry.getAuthType());
                ((HttpRequest) request).setUserPrincipal(entry.getPrincipal());
            }
        } else {
            if (debug >= 1)
                log(" No cached principal found, erasing SSO cookie");
            cookie.setMaxAge(0);
            hres.addCookie(cookie);
        }

        // Invoke the next Valve in our pipeline
        context.invokeNext(request, response);

    }


    // --------------------------------------------------------- Public Methods


    /**
     * Return a String rendering of this object.
     */
    public String toString() {

        StringBuffer sb = new StringBuffer("SingleSignOn[");
        if (container == null)
            sb.append("Container is null");
        else
            sb.append(container.getName());
        sb.append("]");
        return (sb.toString());

    }


    // -------------------------------------------------------- Package Methods


    /**
     * Associate the specified single sign on identifier with the
     * specified Session.
     *
     * @param ssoId   Single sign on identifier
     * @param session Session to be associated
     */
    void associate(String ssoId, Session session) {

        if (debug >= 1)
            log("Associate sso id " + ssoId + " with session " + session);

        SingleSignOnEntry sso = lookup(ssoId);
        if (sso != null)
            sso.addSession(this, session);
        synchronized (reverse) {
            reverse.put(session, ssoId);
        }

    }


    /**
     * Deregister the specified single sign on identifier, and invalidate
     * any associated sessions.
     *
     * @param ssoId Single sign on identifier to deregister
     */
    void deregister(String ssoId) {

        if (debug >= 1)
            log("Deregistering sso id '" + ssoId + "'");

        // Look up and remove the corresponding SingleSignOnEntry
        SingleSignOnEntry sso = null;
        synchronized (cache) {
            sso = (SingleSignOnEntry) cache.remove(ssoId);
        }
        if (sso == null)
            return;

        // Expire any associated sessions
        Session sessions[] = sso.findSessions();
        for (int i = 0; i < sessions.length; i++) {
            if (debug >= 2)
                log(" Invalidating session " + sessions[i]);
            // Remove from reverse cache first to avoid recursion
            synchronized (reverse) {
                reverse.remove(sessions[i]);
            }
            // Invalidate this session
            sessions[i].expire();
        }

        // NOTE:  Clients may still possess the old single sign on cookie,
        // but it will be removed on the next request since it is no longer
        // in the cache

    }


    /**
     * Register the specified Principal as being associated with the specified
     * value for the single sign on identifier.
     *
     * @param ssoId     Single sign on identifier to register
     * @param principal Associated user principal that is identified
     * @param authType  Authentication type used to authenticate this
     *                  user principal
     * @param username  Username used to authenticate this user
     * @param password  Password used to authenticate this user
     */
    void register(String ssoId, Principal principal, String authType,
                  String username, String password) {

        if (debug >= 1)
            log("Registering sso id '" + ssoId + "' for user '" +
                    principal.getName() + "' with auth type '" + authType + "'");

        synchronized (cache) {
            cache.put(ssoId, new SingleSignOnEntry(principal, authType,
                    username, password));
        }

    }


    /**
     * Remove a single Session from a SingleSignOn.  Called when
     * a session is timed out and no longer active.
     *
     * @param ssoId   Single sign on identifier from which to remove the session.
     * @param session the session to be removed.
     */
    void removeSession(String ssoId, Session session) {

        if (debug >= 1)
            log("Removing session " + session.toString() + " from sso id " +
                    ssoId);

        // Get a reference to the SingleSignOn
        SingleSignOnEntry entry = lookup(ssoId);
        if (entry == null)
            return;

        // Remove the inactive session from SingleSignOnEntry
        entry.removeSession(session);

        // Remove the inactive session from the 'reverse' Map.
        synchronized (reverse) {
            reverse.remove(session);
        }

        // If there are not sessions left in the SingleSignOnEntry,
        // deregister the entry.
        if (entry.findSessions().length == 0) {
            deregister(ssoId);
        }
    }


    /**
     * Updates any <code>SingleSignOnEntry</code> found under key
     * <code>ssoId</code> with the given authentication data.
     * <p>
     * The purpose of this method is to allow an SSO entry that was
     * established without a username/password combination (i.e. established
     * following DIGEST or CLIENT-CERT authentication) to be updated with
     * a username and password if one becomes available through a subsequent
     * BASIC or FORM authentication.  The SSO entry will then be usable for
     * reauthentication.
     * <p>
     * <b>NOTE:</b> Only updates the SSO entry if a call to
     * <code>SingleSignOnEntry.getCanReauthenticate()</code> returns
     * <code>false</code>; otherwise, it is assumed that the SSO entry already
     * has sufficient information to allow reauthentication and that no update
     * is needed.
     *
     * @param ssoId     identifier of Single sign to be updated
     * @param principal the <code>Principal</code> returned by the latest
     *                  call to <code>Realm.authenticate</code>.
     * @param authType  the type of authenticator used (BASIC, CLIENT-CERT,
     *                  DIGEST or FORM)
     * @param username  the username (if any) used for the authentication
     * @param password  the password (if any) used for the authentication
     */
    void update(String ssoId, Principal principal, String authType,
                String username, String password) {

        SingleSignOnEntry sso = lookup(ssoId);
        if (sso != null && !sso.getCanReauthenticate()) {
            if (debug >= 1)
                log("Update sso id " + ssoId + " to auth type " + authType);

            synchronized (sso) {
                sso.updateCredentials(principal, authType, username, password);
            }

        }
    }


    // ------------------------------------------------------ Protected Methods


    /**
     * Log a message on the Logger associated with our Container (if any).
     *
     * @param message Message to be logged
     */
    protected void log(String message) {

        Logger logger = container.getLogger();
        if (logger != null)
            logger.log(this.toString() + ": " + message);
        else
            System.out.println(this.toString() + ": " + message);

    }


    /**
     * Log a message on the Logger associated with our Container (if any).
     *
     * @param message   Message to be logged
     * @param throwable Associated exception
     */
    protected void log(String message, Throwable throwable) {

        Logger logger = container.getLogger();
        if (logger != null)
            logger.log(this.toString() + ": " + message, throwable);
        else {
            System.out.println(this.toString() + ": " + message);
            throwable.printStackTrace(System.out);
        }

    }


    /**
     * Look up and return the cached SingleSignOn entry associated with this
     * sso id value, if there is one; otherwise return <code>null</code>.
     *
     * @param ssoId Single sign on identifier to look up
     */
    protected SingleSignOnEntry lookup(String ssoId) {

        synchronized (cache) {
            return ((SingleSignOnEntry) cache.get(ssoId));
        }

    }

}
