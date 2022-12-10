/*
 * Copyright kunbase-framework Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.cdf.ddd.plugin;

/**
 * 插件的启动、关闭监听器，是插件包加载卸载的入口.
 */
public interface IPluginListener {

    /**
     * 加载插件包时触发.
     *
     * @param context 容器上下文
     * @throws Exception
     */
    void onLoad(IContainerContext context) throws Exception;

    /**
     * 卸载插件包时触发.
     *
     * @param context 容器上下文
     * @throws Exception
     */
    void onUnload(IContainerContext context) throws Exception;
}
