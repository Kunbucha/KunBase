/*
 * Copyright kunbase-framework Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.cdf.ddd.runtime;

/**
 * 系统启动的回调.
 */
public interface IStartupListener {

    /**
     * 启动时执行.
     * <p>
     * <p>触发时机：Spring加载完毕后</p>
     */
    void onStartComplete();
}
