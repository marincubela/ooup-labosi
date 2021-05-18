package editor.plugins;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PluginFactory {
    public static List<Plugin> getPlugins() {
        List<Plugin> plugins = new ArrayList<>();
        for(String name : getNames()) {
            plugins.add(newInstance(name));
        }
        return plugins;
    }

    public static Plugin newInstance(String name) {
        Class<Plugin> clazz;
        Constructor<?> ctr;
        Plugin plugin = null;
        try {
            clazz = (Class<Plugin>) Class.forName("editor.plugins." + name);
            ctr = clazz.getConstructor();
            plugin = (Plugin) ctr.newInstance();
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return plugin;
    }

    public static List<String> getNames() {
        List<String> names = new ArrayList<>();
        Path dir = Paths.get("src/editor/plugins");
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir)) {
            for (Path entry: stream) {
                names.add(entry.getFileName().toString());
            }
        } catch (DirectoryIteratorException | IOException e) {
            e.printStackTrace();
        }
        names.remove("Plugin.java");
        names.remove("PluginFactory.java");
        return names.stream().map(s -> s.substring(0, s.length() - 5)).collect(Collectors.toList());
    }
}
