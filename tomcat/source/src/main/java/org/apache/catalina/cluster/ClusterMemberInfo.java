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

package org.apache.catalina.cluster;

import org.apache.catalina.util.ServerInfo;

import java.io.Serializable;

/**
 * Class that represents a member in a Cluster, keeps information
 * that can be used when implementing Classes thats utilizing a Cluster.
 *
 * @author Bip Thelin
 * @version $Revision: 1.5 $
 */

public final class ClusterMemberInfo implements Serializable {

    // ----------------------------------------------------- Instance Variables

    private static String clusterName = null;

    private static String hostName = null;

    private static String clusterInfo = null;

    // ------------------------------------------------------------- Properties

    public String getClusterName() {
        return (this.clusterName);
    }

    public void setClusterName(String clusterName) {
        this.clusterName = clusterName;
    }

    public String getHostName() {
        return (this.hostName);
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public String getServerVersion() {
        return (ServerInfo.getServerInfo());
    }

    public String getClusterInfo() {
        return (this.clusterInfo);
    }

    public void setClusterInfo(String clusterInfo) {
        this.clusterInfo = clusterInfo;
    }
}
