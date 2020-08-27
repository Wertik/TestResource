package space.devport.wertik.testresource.commands.subcommands;

import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import space.devport.utils.commands.SubCommand;
import space.devport.utils.commands.struct.ArgumentRange;
import space.devport.utils.commands.struct.CommandResult;
import space.devport.utils.commands.struct.Preconditions;
import space.devport.utils.text.StringUtil;
import space.devport.wertik.testresource.TestResourcePlugin;
import space.devport.wertik.testresource.test.struct.impl.CustomEntityTest;

public class CustomEntitySubCommand extends SubCommand {

    private final TestResourcePlugin plugin;

    public CustomEntitySubCommand(TestResourcePlugin plugin) {
        super("customentity");
        this.plugin = plugin;
        this.preconditions = new Preconditions()
                .playerOnly();
    }

    @Override
    protected CommandResult perform(CommandSender sender, String label, String[] args) {

        Player player = (Player) sender;
        Location location = player.getLocation();

        int amount = 1;
        if (args.length > 0)
            try {
                amount = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                sender.sendMessage(StringUtil.color("&cNot a number."));
                return CommandResult.FAILURE;
            }

        CustomEntityTest test = (CustomEntityTest) plugin.getTestManager().getTest("customentity");

        if (test == null) {
            sender.sendMessage(StringUtil.color("&cTest is not loaded."));
            return CommandResult.FAILURE;
        }

        test.spawnCustomEntities(location, "&dCute Zombie &8[&e%level%&8]", 10, amount);
        sender.sendMessage(StringUtil.color("&aSpawned."));
        return CommandResult.SUCCESS;
    }

    @Override
    public @NotNull String getDefaultUsage() {
        return "/%label% customentity (amount)";
    }

    @Override
    public @NotNull String getDefaultDescription() {
        return "Spawn some custom entities.";
    }

    @Override
    public @NotNull ArgumentRange getRange() {
        return new ArgumentRange(0, 1);
    }
}