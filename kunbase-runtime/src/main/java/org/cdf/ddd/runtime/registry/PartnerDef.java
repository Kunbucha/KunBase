/*
 * Copyright kunbase-framework Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.cdf.ddd.runtime.registry;

import org.cdf.ddd.annotation.Partner;
import org.cdf.ddd.ext.IDomainExtension;
import org.cdf.ddd.ext.IIdentityResolver;
import org.cdf.ddd.model.IDomainModel;
import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Map;

@ToString
class PartnerDef implements IRegistryAware, IIdentityResolver {

    @Getter
    private String code;

    @Getter
    private String name;

    @Getter
    private IIdentityResolver partnerBean;

    private Map<Class<? extends IDomainExtension>, ExtensionDef> extensionDefMap = new HashMap<>();

    @Override
    public void registerBean(@NotNull Object bean) {
        Partner partner = CoreAopUtils.getAnnotation(bean, Partner.class);
        this.code = partner.code();
        this.name = partner.name();

        if (!(bean instanceof IIdentityResolver)) {
            throw BootstrapException.ofMessage(bean.getClass().getCanonicalName(), " MUST implements IIdentityResolver");
        }
        this.partnerBean = (IIdentityResolver) bean;

        InternalIndexer.indexPartner(this);
    }

    void registerExtensionDef(ExtensionDef extensionDef) {
        Class<? extends IDomainExtension> extClazz = extensionDef.getExtClazz();
        if (extensionDefMap.containsKey(extClazz)) {
            throw BootstrapException.ofMessage("Partner(code=", code, ") can hold ONLY one instance on ", extClazz.getCanonicalName(),
                    ", existing ", extensionDefMap.get(extClazz).toString(),
                    ", illegal ", extensionDef.toString());
        }

        extensionDefMap.put(extClazz, extensionDef);
    }

    ExtensionDef getExtension(Class<? extends IDomainExtension> extClazz) {
        return extensionDefMap.get(extClazz);
    }

    @Override
    public boolean match(@NotNull IDomainModel model) {
        return partnerBean.match(model);
    }

}
