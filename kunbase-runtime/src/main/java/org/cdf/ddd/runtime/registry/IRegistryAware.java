/*
 * Copyright kunbase-framework Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.cdf.ddd.runtime.registry;

import javax.validation.constraints.NotNull;

interface IRegistryAware {

    void registerBean(@NotNull Object bean);
}
