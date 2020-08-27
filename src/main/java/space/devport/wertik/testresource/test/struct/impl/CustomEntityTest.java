package space.devport.wertik.testresource.test.struct.impl;

import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_16_R2.entity.CraftZombie;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import space.devport.utils.text.StringUtil;
import space.devport.utils.utility.LocationUtil;
import space.devport.wertik.testresource.NumberUtil;
import space.devport.wertik.testresource.TestResourcePlugin;
import space.devport.wertik.testresource.test.struct.TestProvider;

import java.util.Random;

public class CustomEntityTest implements TestProvider, Listener {

    private TestResourcePlugin plugin;

    private final Random random = new Random();

    public void spawnCustomEntities(Location location, String name, double health, int amount) {
        CustomEntity entity = new CustomEntity(name, location.getWorld(), 1, health);

        for (int i = 0; i < amount; i++) {
            CustomEntity loopEntity = new CustomEntity(entity);

            loopEntity.setLevel(random.nextInt(10) + 1);
            loopEntity.updateAttributes();

            loopEntity.spawn(location);

            plugin.getConsoleOutput().info("Spawned custom entity on location " + LocationUtil.locationToString(location) + " with level " + loopEntity.getLevel());
        }
    }

    @Override
    public void initialize(TestResourcePlugin plugin) {
        this.plugin = plugin;
        this.plugin.getPluginManager().registerEvents(this, plugin);
    }

    @Override
    public void reload() {
    }

    @Override
    public void disable() {
    }

    @EventHandler
    public void onHit(EntityDamageByEntityEvent event) {

        Entity entity = event.getEntity();
        Entity damager = event.getDamager();

        if (!(damager instanceof Player) || !(entity instanceof CraftZombie)) return;

        CraftZombie craftZombie = (CraftZombie) entity;

        Player player = (Player) damager;

        if (!(craftZombie.getHandle() instanceof CustomEntity)) return;

        CustomEntity customEntity = (CustomEntity) craftZombie.getHandle();

        player.sendMessage(StringUtil.color("&7You hit a custom entity with level &f%level%! &7Hp: &c%health%&7/&4%maxHealth%"
                .replaceAll("(?i)%level%", String.valueOf(customEntity.getLevel()))
                .replaceAll("(?i)%health%", String.valueOf(NumberUtil.round(customEntity.getHealth(), 2)))
                .replaceAll("(?i)%maxHealth%", String.valueOf(customEntity.getMaxHealth()))));
    }
}