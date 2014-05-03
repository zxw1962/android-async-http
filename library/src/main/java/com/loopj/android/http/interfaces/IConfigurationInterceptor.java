package com.loopj.android.http.interfaces;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.jetbrains.annotations.NotNull;

public interface IConfigurationInterceptor {

    public void modifyRequestConfigBuilder(@NotNull RequestConfig.Builder builder);

    public void modifyHttpClientBuilder(@NotNull HttpClientBuilder httpClientBuilder);

    public void modifyConnectionManager(@NotNull PoolingHttpClientConnectionManager connectionManager);

}
