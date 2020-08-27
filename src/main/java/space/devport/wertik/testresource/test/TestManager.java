package space.devport.wertik.testresource.test;

import org.jetbrains.annotations.Nullable;
import space.devport.wertik.testresource.TestResourcePlugin;
import space.devport.wertik.testresource.test.struct.TestProvider;

import java.util.HashMap;
import java.util.Map;

public class TestManager {

    private final TestResourcePlugin plugin;

    private final Map<String, TestProvider> providers = new HashMap<>();

    public TestManager(TestResourcePlugin plugin) {
        this.plugin = plugin;
    }

    public void registerProvider(String name, TestProvider provider) {
        this.providers.put(name, provider);
        provider.initialize(plugin);
        plugin.getConsoleOutput().info("Initialized test " + name);
    }

    public void unregisterProvider(String name) {
        this.providers.remove(name);
    }

    @Nullable
    public TestProvider getTest(String name) {
        return this.providers.getOrDefault(name, null);
    }

    public void reloadAll() {
        this.providers.values().forEach(TestProvider::reload);
    }

    public void disableAll() {
        this.providers.values().forEach(TestProvider::disable);
    }
}
