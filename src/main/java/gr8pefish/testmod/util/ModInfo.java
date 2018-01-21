package gr8pefish.testmod.util;

/**
 * All the basic information about the mod (that shouldn't ever change)
 */
public class ModInfo {

    /** Basic, constant info */
    public static final String VERSION = "@VERSION@";
    public static final String MODID = "testmod"; //internal use
    public static final String MOD_NAME = "Test Mod"; //displayed name

    /** The location of the proxy packages */
    public static final String COMMON_PROXY = "gr8pefish.testmod.proxy.CommonProxy";
    public static final String CLIENT_PROXY = "gr8pefish.testmod.proxy.ClientProxy";

}
