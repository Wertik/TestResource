package space.devport.wertik.testresource.test.struct;

import space.devport.wertik.testresource.TestResourcePlugin;

public interface TestProvider {

    void initialize(TestResourcePlugin plugin);

    void reload();

    void disable();
}