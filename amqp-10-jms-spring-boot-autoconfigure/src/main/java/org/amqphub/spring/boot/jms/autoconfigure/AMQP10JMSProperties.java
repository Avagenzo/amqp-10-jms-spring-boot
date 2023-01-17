/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.amqphub.spring.boot.jms.autoconfigure;

import java.time.Duration;
import java.util.List;

import org.springframework.boot.autoconfigure.jms.JmsPoolConnectionFactoryProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

import jakarta.jms.JMSContext;

/**
 * Configuration properties for the AMQP 1.0 JMS client
 */
@ConfigurationProperties(prefix = "amqphub.amqp10jms")
public class AMQP10JMSProperties {

    /**
     * The URL of the remote peer the AMQP client should connect to.
     */
    private String remoteUrl = "amqp://localhost:5672";

    /**
     * AMQP broker user name.
     */
    private String username;

    /**
     * Login password of the AMQP broker.
     */
    private String password;

    /**
     * JMS clientID to use for connections. A clientID can only be used by one Connection at a time, so setting it
     * will restrict the ConnectionFactory to creating a single open Connection at a time.
     */
    private String clientId;

    /**
     * Whether the client only checks its local message buffer when using receive calls with a timeout.
     */
    private boolean receiveLocalOnly = false;

    /**
     * Whether the client only checks its local message buffer when using receiveNoWait calls.
     */
    private boolean receiveNoWaitLocalOnly = false;

    @NestedConfigurationProperty
    private final DeserializationPolicy deserializationPolicy = new DeserializationPolicy();

    @NestedConfigurationProperty
    private Pool pool = new Pool();

    public String getRemoteUrl() {
        return remoteUrl;
    }

    public void setRemoteUrl(String remoteUrl) {
        this.remoteUrl = remoteUrl;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public boolean isReceiveLocalOnly() {
        return receiveLocalOnly;
    }

    public void setReceiveLocalOnly(boolean receiveLocalOnly) {
        this.receiveLocalOnly = receiveLocalOnly;
    }

    public boolean isReceiveNoWaitLocalOnly() {
        return receiveNoWaitLocalOnly;
    }

    public void setReceiveNoWaitLocalOnly(boolean receiveNoWaitLocalOnly) {
        this.receiveNoWaitLocalOnly = receiveNoWaitLocalOnly;
    }

    public DeserializationPolicy getDeserializationPolicy() {
        return deserializationPolicy;
    }

    public Pool getPool() {
        return pool;
    }

    public static class DeserializationPolicy {

        /**
         * Allow-list of classes or packages.
         */
        private List<String> allowList;

        /**
         * Deny-list of classes or packages. Deny-List overrides the Allow-list, entries that could match both are
         * counted as denied.
         */
        private List<String> denyList;

        public List<String> getAllowList() {
            return this.allowList;
        }

        public void setAllowList(List<String> allowList) {
            this.allowList = allowList;
        }

        @Deprecated
        public void setWhiteList(List<String> allowList) {
            this.allowList = allowList;
        }

        public List<String> getDenyList() {
            return this.denyList;
        }

        public void setDenyList(List<String> denyList) {
            this.denyList = denyList;
        }

        @Deprecated
        public void setBlackList(List<String> denyList) {
            this.denyList = denyList;
        }
    }

    public static class Pool extends JmsPoolConnectionFactoryProperties {

        /**
         * When the useAnonymousProducers option is disabled this option controls whether a
         * pooled session will cache some number of explicit JMS producers in an LRUCache.
         */
        private int explicitProducerCacheSize = 0;

        /**
         * Controls whether the pool will use a generic JMSContext that wraps a pooled Connection or
         * uses the provider JMS ConnectionFactory to directly create JMSContext instances.  The generic
         * JMSContext object cannot fully implement all methods of the simplified API and must be disabled
         * in the case where those methods are needed.
         */
        private boolean useProviderJMSContext = false;

        /**
         * @return if the session pool blocks when full.
         */
        public boolean isBlockIfSessionPoolIsFull() {
            return isBlockIfFull();
        }

        /**
         * Whether to block when a Session is requested and the Session pool is full. Set it to
         * false to throw a "JMSException" instead.
         *
         * @param blockIfSessionPoolIsFull
         *      If true the session API will block if it is full.
         */
        public void setBlockIfSessionPoolIsFull(boolean blockIfSessionPoolIsFull) {
            this.setBlockIfFull(blockIfSessionPoolIsFull);
        }

        /**
         * @return the amount of time a session pool operation blocks if configured to do so.
         */
        public Duration getBlockIfSessionPoolIsFullTimeout() {
            return getBlockIfFullTimeout();
        }

        /**
         * Blocking period before throwing an exception if the Session pool is still full and the
         * blockIfSessionPoolIsFull configuration option is set to 'true'.
         *
         * @param blockIfSessionPoolIsFullTimeout
         *      The duration to wait before throwing an error due to session pool being full.
         */
        public void setBlockIfSessionPoolIsFullTimeout(long blockIfSessionPoolIsFullTimeout) {
            setBlockIfFullTimeout(Duration.ofMillis(blockIfSessionPoolIsFullTimeout));
        }

        /**
         * Blocking period before throwing an exception if the Session pool is still full and the
         * blockIfSessionPoolIsFull configuration option is set to 'true'.
         *
         * @param blockIfSessionPoolIsFullTimeout
         *      The duration to wait before throwing an error due to session pool being full.
         */
        public void setBlockIfSessionPoolIsFullTimeout(Duration blockIfSessionPoolIsFullTimeout) {
            setBlockIfFullTimeout(blockIfSessionPoolIsFullTimeout);
        }

        /**
         * @return the time a connection must be idle before being a candidate for eviction.
         */
        public Duration getConnectionIdleTimeout() {
            return getIdleTimeout();
        }

        /**
         * Connection idle timeout for connections that are not currently in use.
         */
        public void setConnectionIdleTimeout(long connectionIdleTimeout) {
            setIdleTimeout(Duration.ofMillis(connectionIdleTimeout));
        }

        /**
         * Connection idle timeout for connections that are not currently in use.
         */
        public void setConnectionIdleTimeout(Duration connectionIdleTimeout) {
            setIdleTimeout(connectionIdleTimeout);
        }

        /**
         * @return The time to sleep between runs of the connection check thread which will only
         * run if the configuration value is non-negative.
         *
         * @see #getIdleTimeout()
         */
        public Duration getConnectionCheckInterval() {
            return getTimeBetweenExpirationCheck();
        }

        /**
         * The time to sleep between runs of the connection check thread which will only
         * run if the configuration value is non-negative.
         *
         * @param connectionCheckInterval
         *      Time to sleep between connection idle checks (negative value disables the check).
         *
         * @see #setIdleTimeout(Duration)
         */
        public void setConnectionCheckInterval(long connectionCheckInterval) {
            setTimeBetweenExpirationCheck(Duration.ofMillis(connectionCheckInterval));
        }

        /**
         * The time to sleep between runs of the connection check thread which will only
         * run if the configuration value is non-negative.
         *
         * @param connectionCheckInterval
         *      Time to sleep between connection idle checks (negative value disables the check).
         *
         * @see #setIdleTimeout(Duration)
         */
        public void setConnectionCheckInterval(Duration connectionCheckInterval) {
            setTimeBetweenExpirationCheck(connectionCheckInterval);
        }

        /**
         * @return if the pooled {@link JMSContext} is used or a provider specific instance.
         */
        public boolean isUseProviderJMSContext() {
            return useProviderJMSContext;
        }

        /**
         * Controls whether the pool will use a generic JMSContext that wraps a pooled Connection or
         * uses the provider JMS ConnectionFactory to directly create JMSContext instances.  The generic
         * JMSContext object cannot fully implement all methods of the simplified API and must be disabled
         * in the case where those methods are needed. The default is to use the generic pooling JMS context
         * but if the unsupported features are needed (e.g. acknowledgement from the context) then this value
         * can be set to true but this disabled pooling for {@link JMSContext} instances.
         *
         * @param useProviderJMSContext
         *      Sets if the pooled {@link JMSContext} is used or a provider specific instance.
         */
        public void setUseProviderJMSContext(boolean useProviderJMSContext) {
            this.useProviderJMSContext = useProviderJMSContext;
        }

        /**
         * @return the configured number of explicit destination JMS producers to cache.
         */
        public int getExplicitProducerCacheSize() {
            return explicitProducerCacheSize;
        }

        /**
         * When the useAnonymousProducers option is disabled this option controls whether a
         * pooled session will cache some number of explicit JMS producers in an LRUCache.
         * If enabled the session would cache up to the configured number of explicit JMS
         * producers and discard the oldest cached value if the limit is exceeded.
         * <p>
         * This caching would only be done when the {@link #setUseAnonymousProducers(boolean)}
         * configuration option is disabled.
         *
         * @param explicitProducerCacheSize
         *      The total number of explicit destination JMS producers to cache
         */
        public void setExplicitProducerCacheSize(int explicitProducerCacheSize) {
            this.explicitProducerCacheSize = explicitProducerCacheSize;
        }
    }
}
