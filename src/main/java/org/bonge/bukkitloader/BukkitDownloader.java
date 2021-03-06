package org.bonge.bukkitloader;

import java.io.*;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.jar.JarFile;

public class BukkitDownloader {

    public static final BukkitVersion BUKKIT_1_13_2 = new BukkitVersion("https://hub.spigotmc.org/nexus/service/local/repositories/snapshots/content/org/bukkit/bukkit/1.13.2-R0.1-SNAPSHOT/bukkit-1.13.2-R0.1-20190423.030918-214.jar", 134392663, BukkitType.BUKKIT, 1, 13, 2);
    public static final BukkitVersion BUKKIT_1_14_4 = new BukkitVersion("https://hub.spigotmc.org/nexus/service/local/repositories/snapshots/content/org/bukkit/bukkit/1.14.4-R0.1-SNAPSHOT/bukkit-1.14.4-R0.1-20191210.224213-92.jar", 1184424, BukkitType.BUKKIT, 1, 14, 4);
    public static final BukkitVersion BUKKIT_1_15_2 = new BukkitVersion("https://hub.spigotmc.org/nexus/service/local/repositories/snapshots/content/org/bukkit/bukkit/1.15.2-R0.1-SNAPSHOT/bukkit-1.15.2-R0.1-20200624.000052-123.jar", 1184424, BukkitType.BUKKIT, 1, 15, 2);


    public static final BukkitVersion DEFAULT_VERSION = BUKKIT_1_15_2;
    public static final File DEFAULT_OUTPUT = new File("config/bonge/API/");

    private final File exactOutput;
    private final BukkitVersion version;

    public BukkitDownloader(BukkitVersion version, File output, boolean useExact){
        this.version = version;
        this.exactOutput = (useExact ? output : new File(output, version.getTargetVersion()[0] + "_" + version.getTargetVersion()[1] + "_" + version.getTargetVersion()[2] + "/" + version.getType().name().toLowerCase() + ".jar"));
    }

    public File getOutput(){
        return this.exactOutput;
    }

    public File getExactOutput() {
        return this.exactOutput;
    }

    public BukkitVersion getVersion() {
        return this.version;
    }

    public void download(BiConsumer<Long, Long> consumer) throws IOException {
        Optional<URL> opURL = this.version.getDownload();
        if(!opURL.isPresent()){
            throw new IOException("Can not download, no URL provided");
        }
        if(!this.exactOutput.exists()){
            this.exactOutput.getParentFile().mkdirs();
            this.exactOutput.createNewFile();
        }
        InputStream stream = opURL.get().openStream();
        final long totalSize = this.version.getSizeInBytes();
        System.out.println("Size: " + totalSize);
        long size = 0;
        BufferedInputStream in = new BufferedInputStream(stream);
        FileOutputStream fos = new FileOutputStream(this.exactOutput);
        int bytesRead;
        while((bytesRead = in.read()) != -1){
            size += bytesRead;
            fos.write(bytesRead);
            if(totalSize != 0) {
                consumer.accept(size, totalSize);
            }
        }
        fos.flush();
        fos.close();
        in.close();

        Files.copy(opURL.get().openStream(), this.exactOutput.toPath(), StandardCopyOption.REPLACE_EXISTING);
    }

    public Set<Class<?>> load() throws IOException {
        Set<Class<?>> set = new HashSet<>();
        URLClassLoader loader = new URLClassLoader(new URL[]{this.exactOutput.toURI().toURL()});
        JarFile jar = new JarFile(this.exactOutput);
        jar.stream().filter(e -> e.getName().endsWith(".class")).forEach(e -> {
            String name = e.getName();
            name = name.substring(0, name.length() - 6).replace("/", ".");
            try {
                Class<?> class1 = loader.loadClass(name);
                set.add(class1);
            }catch (ClassNotFoundException | NoClassDefFoundError ignore){
            }
        });
        return set;
    }

}
