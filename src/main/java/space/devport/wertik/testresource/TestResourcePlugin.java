package space.devport.wertik.testresource;

import lombok.Getter;
import space.devport.utils.DevportPlugin;
import space.devport.utils.UsageFlag;
import space.devport.wertik.testresource.commands.TestResourceCommand;
import space.devport.wertik.testresource.commands.subcommands.CustomEntitySubCommand;
import space.devport.wertik.testresource.test.TestManager;
import space.devport.wertik.testresource.test.struct.impl.CustomEntityTest;

public class TestResourcePlugin extends DevportPlugin {

    @Getter
    private TestManager testManager;

    @Override
    public void onPluginEnable() {
        this.testManager = new TestManager(this);

        this.testManager.registerProvider("customentity", new CustomEntityTest());

        consoleOutput.setDebug(true);

        addMainCommand(new TestResourceCommand())
                .addSubCommand(new CustomEntitySubCommand(this));
    }

    @Override
    public void onPluginDisable() {
        this.testManager.disableAll();
    }

    @Override
    public void onReload() {
        this.testManager.reloadAll();
    }

    @Override
    public UsageFlag[] usageFlags() {
        return new UsageFlag[]{UsageFlag.COMMANDS};
    }
}
