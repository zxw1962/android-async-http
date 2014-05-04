package com.loopj.android.http.interfaces;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.jetbrains.annotations.NotNull;

/**
 * Interface to be implemented in case the default configuration of library is not sufficient in
 * given conditions
 * <p/>
 * Instance of this interface is provided over {@link com.loopj.android.http.interfaces.IAsyncHttpClientOptions}
 * instance to library constructor and can modify all settings
 *
 * @see com.loopj.android.http.interfaces.IAsyncHttpClientOptions#setConfigurationInterceptor(IConfigurationInterceptor)
 */
public interface IConfigurationInterceptor {

    /**
     * Can disable default configuration of components by library if false is returned
     *
     * @return false if default library configuration should be ommited
     * @see com.loopj.android.http.interfaces.IAsyncHttpClientOptions#buildHttpClient(org.apache.http.impl.client.HttpClientBuilder)
     */
    public boolean useDefaultConfig();

    /**
     * Will be given non-null instances of objects to configure, settings won't be further
     * validated, full responsibility thus lays on developer
     *
     * @param builder           instance to be configured
     * @param connectionManager instance to be configured
     * @param httpClientBuilder instance to be configured
     * @see org.apache.http.client.config.RequestConfig.Builder
     * @see org.apache.http.impl.client.HttpClientBuilder
     * @see org.apache.http.impl.conn.PoolingHttpClientConnectionManager
     * @see com.loopj.android.http.interfaces.IAsyncHttpClientOptions
     */
    public void configureComponents(
            @NotNull RequestConfig.Builder builder,
            @NotNull HttpClientBuilder httpClientBuilder,
            @NotNull PoolingHttpClientConnectionManager connectionManager
    );

}
