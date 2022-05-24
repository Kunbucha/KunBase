package org.ddd.cp.ddd.runtime.registry;

import javax.validation.constraints.NotNull;

interface IRegistryAware {

    void registerBean(@NotNull Object bean);
}
