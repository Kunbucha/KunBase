/*
 * Copyright kunbase-framework Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.cdf.ddd.runtime.registry;

import lombok.extern.slf4j.Slf4j;
import org.cdf.ddd.annotation.Extension;
import org.cdf.ddd.annotation.Partner;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.context.support.AbstractRefreshableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.core.env.EnvironmentCapable;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.util.Assert;

import javax.validation.constraints.NotNull;
import java.io.File;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 业务前台jar包热加载器.
 * <p>
 * <ul>限制条件:
 * <li>统一使用中台的spring xml</li>
 * <li>jar里只能使用中台spring bean，不能自己定义依赖</li>
 * <li>不支持卸载，不支持hot swap</li>
 * </ul>
 * <p>如果使用本动态加载，就不要maven里静态引入业务前台jar包依赖了.</p>
 */
@Slf4j
class PartnerLoader {

    /**
     * 加载业务前台模块.
     *
     * @param jarPath     业务前台的jarPath
     * @param basePackage Spring component-scan base-package值，但不支持逗号分隔. if null, will not scan Spring
     * @throws Exception
     */
    public void load(@NotNull String jarPath, String basePackage) throws Exception {
        long t0 = System.nanoTime();

        List<Class<? extends Annotation>> annotations = new ArrayList<>(2);
        // 只加载业务前台的扩展点和Partner注解类，通过java ClassLoader的全盘负责机制自动加载相关引用类
        annotations.add(Partner.class);
        annotations.add(Extension.class);

        PartnerClassLoader.getInstance().addUrl(new File(jarPath).toURI().toURL());

        if (basePackage != null) {
            // TODO 10个bean，扫描到第8个出现异常，需要把PartnerClassLoader里已经addUrl的摘除
            // 先扫spring，然后初始化所有的basePackage bean，包括已经在中台里加载完的bean
            log.info("Spring scan...");
            springScanComponent(DDDBootstrap.applicationContext(), PartnerClassLoader.getInstance(), basePackage);
        }

        log.info("loading extensions...");
        Map<Class<? extends Annotation>, List<Class>> resultMap = JarUtils.loadClassWithAnnotations(jarPath,
                annotations, null, PartnerClassLoader.getInstance());

        // 实例化该业务前台的所有扩展点，并注册到索引
        log.info("register and index extensions...");
        List<Class> partners = resultMap.get(Partner.class);
        if (partners != null && !partners.isEmpty()) {
            this.registerPartner(partners.get(0), DDDBootstrap.applicationContext());
        }
        this.registerExtensions(resultMap.get(Extension.class), DDDBootstrap.applicationContext());

        log.warn("loaded ok, cost {}ms", (System.nanoTime() - t0) / 1000_000);
    }

    private void registerExtensions(List<Class> extensions, ApplicationContext applicationContext) {
        if (extensions == null || extensions.isEmpty()) {
            return;
        }

        for (Class extensionClazz : extensions) {
            // 扩展点实例，Spring里获取的
            RegistryFactory.lazyRegister(Extension.class, applicationContext.getBean(extensionClazz));
        }
    }

    private void registerPartner(@NotNull Class partner, ApplicationContext applicationContext) throws Exception {
        RegistryFactory.lazyRegister(Partner.class, applicationContext.getBean(partner));
    }

    // manual <context:component-scan>
    private void springScanComponent(@NotNull ApplicationContext context, @NotNull ClassLoader classLoader, @NotNull String... basePackages) throws Exception {
        AbstractRefreshableApplicationContext realContext;
        if (context instanceof ClassPathXmlApplicationContext) {
            realContext = (ClassPathXmlApplicationContext) context;
        } else {
            realContext = (FileSystemXmlApplicationContext) context;
        }
        realContext.getBeanFactory().setBeanClassLoader(classLoader);

        BeanDefinitionRegistry beanDefinitionRegistry = (BeanDefinitionRegistry) realContext.getBeanFactory();
        new ClassPathBeanDefinitionScanner(
                beanDefinitionRegistry,
                true,
                getOrCreateEnvironment(beanDefinitionRegistry),
                new PathMatchingResourcePatternResolver(new DefaultResourceLoader(PartnerClassLoader.getInstance()))
        ).scan(basePackages);
    }

    private static Environment getOrCreateEnvironment(BeanDefinitionRegistry registry) {
        Assert.notNull(registry, "BeanDefinitionRegistry must not be null");
        if (registry instanceof EnvironmentCapable) {
            return ((EnvironmentCapable) registry).getEnvironment();
        }

        return new StandardEnvironment();
    }

}
