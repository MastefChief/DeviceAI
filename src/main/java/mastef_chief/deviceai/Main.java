package mastef_chief.deviceai;


import com.mrcrayfish.device.api.ApplicationManager;
import mastef_chief.deviceai.app.AIApp;
import mastef_chief.deviceai.proxy.CommonProxy;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;

@Mod(modid = Reference.MOD_ID, name = Reference.NAME, version = Reference.VERSION, acceptedMinecraftVersions = Reference.ACCEPTED_VERSIONS, dependencies = Reference.DEPENDS)
public class Main {

    private static Logger logger;

    @Mod.Instance
    public static Main instance;

    @SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.SERVER_PROXY_CLASS)
    public static CommonProxy proxy;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event){

        logger = event.getModLog();

    }

    @Mod.EventHandler
    public void Init(FMLInitializationEvent event){

        ApplicationManager.registerApplication(new ResourceLocation(Reference.MOD_ID, "ai_app"), AIApp.class);

    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event){

    }

}
