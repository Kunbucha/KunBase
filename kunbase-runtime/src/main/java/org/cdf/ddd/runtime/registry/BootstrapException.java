/*
 * Copyright kunbase-framework Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.cdf.ddd.runtime.registry;

/**
 * DDD框启动过程中进行注册阶段产生的异常.
 */
class BootstrapException extends RuntimeException {

    private BootstrapException(String message) {
        super(message);
    }

    public static BootstrapException ofMessage(String... message) {
        StringBuilder sb = new StringBuilder(100);
        for (String s : message) {
            sb.append(s);
        }

        return new BootstrapException(sb.toString());
    }
}
