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


package org.apache.catalina;


/**
 * Common interface for component life cycle methods.  Catalina components
 * may, but are not required to, implement this interface (as well as the
 * appropriate interface(s) for the functionality they support) in order to
 * provide a consistent mechanism to start and stop the component.
 *
 * @author Craig R. McClanahan
 * @version $Revision: 1.7 $ $Date: 2004/08/26 22:05:20 $
 * @see LifecycleEvent
 * @see LifecycleListener
 * @see org.apache.catalina.util.LifecycleSupport
 */
public interface Lifecycle {


    /*
     Catalina 由多个组件组成，当 Catalina 启动的时候，这些组件也会启动。当
        Catalina 停止的时候，这些组件也必须有机会被清除。例如，当一个容器停止
        工作的时候，它必须唤醒所有加载的 servlet 的 destroy 方法，而 session 管理
        器要保存 session 到二级存储器中。保持组件启动和停止一致的的机制通过实现
        org.apache.catalina.Lifecycle 接口来实现。
        一个实现了 Lifecycle 接口的组件同是会触发一个或多个下列事件：
        BEFORE_START_EVENT, START_EVENT, AFTER_START_EVENT, BEFORE_STOP_EVENT,
        STOP_EVENT, and AFTER_STOP_EVENT。当组件被启动的时候前三个事件会被触发，
        而组件停止的时候会触发后边三个事件。另外，如果一个组件可以触发事件，那
        么必须存在相应的监听器来对触发的事件作出回应。监听器使用
        org.apache.catalina.LifecycleListener 来表示。
     */


    // ----------------------------------------------------- Manifest Constants


    /**
     * The LifecycleEvent type for the "component start" event.
     */
    public static final String START_EVENT = "start";


    /**
     * The LifecycleEvent type for the "component before start" event.
     */
    public static final String BEFORE_START_EVENT = "before_start";


    /**
     * The LifecycleEvent type for the "component after start" event.
     */
    public static final String AFTER_START_EVENT = "after_start";


    /**
     * The LifecycleEvent type for the "component stop" event.
     */
    public static final String STOP_EVENT = "stop";


    /**
     * The LifecycleEvent type for the "component before stop" event.
     */
    public static final String BEFORE_STOP_EVENT = "before_stop";


    /**
     * The LifecycleEvent type for the "component after stop" event.
     */
    public static final String AFTER_STOP_EVENT = "after_stop";


    // --------------------------------------------------------- Public Methods


    /**
     * Add a LifecycleEvent listener to this component.
     *
     * @param listener The listener to add
     */
    public void addLifecycleListener(LifecycleListener listener);


    /**
     * Get the lifecycle listeners associated with this lifecycle. If this
     * Lifecycle has no listeners registered, a zero-length array is returned.
     */
    public LifecycleListener[] findLifecycleListeners();


    /**
     * Remove a LifecycleEvent listener from this component.
     *
     * @param listener The listener to remove
     */
    public void removeLifecycleListener(LifecycleListener listener);


    /**
     * Prepare for the beginning of active use of the public methods of this
     * component.  This method should be called before any of the public
     * methods of this component are utilized.  It should also send a
     * LifecycleEvent of type START_EVENT to any registered listeners.
     *
     * @throws LifecycleException if this component detects a fatal error
     *                            that prevents this component from being used
     */
    public void start() throws LifecycleException;


    /**
     * Gracefully terminate the active use of the public methods of this
     * component.  This method should be the last one called on a given
     * instance of this component.  It should also send a LifecycleEvent
     * of type STOP_EVENT to any registered listeners.
     *
     * @throws LifecycleException if this component detects a fatal error
     *                            that needs to be reported
     */
    public void stop() throws LifecycleException;


}
