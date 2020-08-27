package space.devport.wertik.testresource.test.struct.impl;

import lombok.Getter;
import lombok.Setter;
import net.minecraft.server.v1_16_R2.ChatComponentText;
import net.minecraft.server.v1_16_R2.EntityTypes;
import net.minecraft.server.v1_16_R2.EntityZombie;
import net.minecraft.server.v1_16_R2.GenericAttributes;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_16_R2.CraftWorld;
import space.devport.utils.text.StringUtil;

public class CustomEntity extends EntityZombie {

    @Getter
    @Setter
    private String customDisplayName;

    @Getter
    @Setter
    private double customHealth;

    @Getter
    @Setter
    private int level;

    /**
     * Copy constructor.
     */
    public CustomEntity(CustomEntity entity) {
        super(EntityTypes.ZOMBIE, entity.getWorld());

        this.customDisplayName = entity.getCustomDisplayName();
        this.level = entity.getLevel();
        this.customHealth = entity.getCustomHealth();

        updateAttributes();
    }

    public CustomEntity(String name, org.bukkit.World world, int level, double health) {
        super(EntityTypes.ZOMBIE, ((CraftWorld) world).getHandle());

        this.customDisplayName = name;
        this.level = level;
        this.customHealth = health;

        updateAttributes();
    }

    public void updateAttributes() {

        // Parse %level% placeholder.
        String customName = this.customDisplayName.replaceAll("(?i)%level%", String.valueOf(this.level));

        // Set the name
        this.setCustomName(new ChatComponentText(StringUtil.color(customName)));
        this.setCustomNameVisible(true);

        // Update some other attributes.
        this.getAttributeInstance(GenericAttributes.FOLLOW_RANGE).setValue(11);
        this.getAttributeInstance(GenericAttributes.MAX_HEALTH).setValue(this.customHealth);
        this.setHealth((float) this.customHealth);
    }

    /**
     * Spawn a single entity at location.
     */
    public void spawn(Location location) {
        this.setPosition(location.getX(), location.getY(), location.getZ());
        world.addEntity(this);
    }
}