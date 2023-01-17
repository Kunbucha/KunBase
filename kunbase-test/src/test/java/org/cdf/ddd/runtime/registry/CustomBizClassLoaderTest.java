package org.cdf.ddd.runtime.registry;

import lombok.extern.slf4j.Slf4j;
import org.cdf.ddd.runtime.DDD;
import org.junit.Test;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.List;

import static org.junit.Assert.*;

@Slf4j
public class CustomBizClassLoaderTest {

    @Test
    public void loadClass() throws Exception {
        CustomBizClassLoader loader = new CustomBizClassLoader(new URL[]{new File("").toURI().toURL()});
        Class clazz = loader.loadClass(CustomBizClassLoaderTest.class.getCanonicalName(), false);
        assertTrue(clazz.equals(CustomBizClassLoaderTest.class));
    }

    @Test
    public void jdkClassLoader() {
        ClassLoader classLoader = CustomBizClassLoader.jdkClassLoader();
        assertNotNull(classLoader);
        assertTrue(classLoader instanceof URLClassLoader);
    }

    @Test
    public void containerClassLoader() {
        ClassLoader classLoader = CustomBizClassLoader.containerClassLoader();
        assertNotNull(classLoader);
        assertEquals("sun.misc.Launcher.AppClassLoader", classLoader.getClass().getCanonicalName());
    }

    @Test
    public void containerFirstClass() throws MalformedURLException {
        CustomBizClassLoader loader = new CustomBizClassLoader(new URL[]{new File("").toURI().toURL()});
        assertTrue(loader.containerFirstClass(DDD.class.getName()));
        assertFalse(loader.containerFirstClass(List.class.getName()));
        assertFalse(loader.containerFirstClass("com.jdl.bp.oms.doo.j.extension.JAntiConcurrentLockExt"));
    }

}
