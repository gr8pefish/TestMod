package gr8pefish.testmod.api;

import com.google.common.base.Preconditions;
import net.minecraft.util.IStringSerializable;

import javax.annotation.Nonnull;
import java.util.Locale;

public enum BackpackVariantEnum implements IStringSerializable {
    BASIC,
    IRON,
    GOLD,
    DIAMOND;

    /**
     * Gets the name of the specialty.
     *
     * @return - a String representation of the specialty.
     */
    @Override
    public String getName() {
        return name().toLowerCase(Locale.ENGLISH);
    }


    @Nonnull
    public static BackpackVariantEnum getVariantEnum(@Nonnull String name) {
        Preconditions.checkNotNull(name, "Name cannot be null");

        //Loop through possible specialties
        for (BackpackVariantEnum backpackVariantEnum : BackpackVariantEnum.values())
            //Check if it is the one we want
            if (backpackVariantEnum.getName().equalsIgnoreCase(name))
                //if so, return it
                return backpackVariantEnum;

        //No specialty found, return BASIC (should be unreachable)
        return BASIC;
    }
}
