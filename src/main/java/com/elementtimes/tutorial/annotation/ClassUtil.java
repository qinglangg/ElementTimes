package com.elementtimes.tutorial.annotation;

import java.io.File;
import java.lang.reflect.Field;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class ClassUtil {

    public static void init() {
        try {
            Set<Class<?>> classes = getClasses("com.lq2007.mymod");
            for (Class<?> aClass : classes) {
                tryPut(aClass,
                        ModBlock.class, ModBucket.class, ModCapability.class,
                        ModCommand.class, ModEnchantment.class, ModEntity.class,
                        ModFluid.class, ModItem.class, ModMultiBlock.class,
                        ModNetwork.class, ModPotion.class, ModTileEntity.class);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void tryPut(Class aClass, Class... clazz) {
        for (Class annotation : clazz) {
            if (aClass.getAnnotation(annotation) != null){
                Elements.addElement(annotation, aClass);
                break;
            }
        }
        for (Field field : aClass.getDeclaredFields()) {
            field.setAccessible(true);
            for (Class annotation : clazz) {
                if (field.getAnnotation(annotation) != null){
                    Elements.addElement(annotation, field);
                    break;
                }
            }
        }
    }

    /**
     * 从包package中获取所有的Class
     * 代码来自 http://guoliangqi.iteye.com/blog/644876
     */
    private static Set<Class<?>> getClasses(String pack) throws Exception {
        Set<Class<?>> classes = new LinkedHashSet<>();
        boolean recursive = true;
        String packageName = pack;
        String packageDirName = packageName.replace('.', '/');
        Enumeration<URL> dirs;
        dirs = Thread.currentThread().getContextClassLoader().getResources(packageDirName);
        while (dirs.hasMoreElements()) {
            URL url = dirs.nextElement();
            String protocol = url.getProtocol();
            if ("file".equals(protocol)) {
                System.err.println("file类型的扫描");
                String filePath = URLDecoder.decode(url.getFile(), "UTF-8");
                findAndAddClassesInPackageByFile(packageName, filePath, recursive, classes);
            } else if ("jar".equals(protocol)) {
                System.err.println("jar类型的扫描");
                JarFile jar = ((JarURLConnection) url.openConnection()).getJarFile();
                Enumeration<JarEntry> entries = jar.entries();
                while (entries.hasMoreElements()) {
                    JarEntry entry = entries.nextElement();
                    String name = entry.getName();
                    if (name.charAt(0) == '/') name = name.substring(1);
                    if (name.startsWith(packageDirName)) {
                        int idx = name.lastIndexOf('/');
                        if (idx != -1) packageName = name.substring(0, idx).replace('/', '.');
                        if ((idx != -1) || recursive) {
                            if (name.endsWith(".class") && !entry.isDirectory()) {
                                String className = name.substring(packageName.length() + 1, name.length() - 6);
                                classes.add(Class.forName(packageName + '.' + className));
                            }
                        }
                    }
                }
            }
        }

        return classes;
    }

    /**
     * 以文件的形式来获取包下的所有Class
     */
    private static void findAndAddClassesInPackageByFile(String packageName, String packagePath, final boolean recursive, Set<Class<?>> classes) throws Exception {
        File dir = new File(packagePath);
        if (!dir.exists() || !dir.isDirectory()) {
            return;
        }
        File[] dirfiles = dir.listFiles(file -> (recursive && file.isDirectory())
                || (file.getName().endsWith(".class")));
        for (File file : dirfiles) {
            if (file.isDirectory()) {
                findAndAddClassesInPackageByFile(packageName + "." + file.getName(), file.getAbsolutePath(), recursive, classes);
            } else {
                String className = file.getName().substring(0, file.getName().length() - 6);
                classes.add(Thread.currentThread().getContextClassLoader().loadClass(packageName + '.' + className));
            }
        }
    }
}
