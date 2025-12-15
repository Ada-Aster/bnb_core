package net.dusty_dusty.bnb_core;

import com.mojang.datafixers.util.Either;
import com.mojang.logging.LogUtils;
import com.momosoftworks.coldsweat.api.util.Temperature;
import com.momosoftworks.coldsweat.util.world.WorldHelper;
import net.dusty_dusty.bnb_core.cold_crops.ColdCrops;
import net.dusty_dusty.bnb_core.cold_crops.data.CropData;
import net.dusty_dusty.bnb_core.cold_crops.data.CropsNSeedsData;
import net.dusty_dusty.bnb_core.cold_crops.network.PacketChannel;
import net.dusty_dusty.bnb_core.cold_crops.network.SyncDataPacket;
import net.dusty_dusty.bnb_core.cold_crops.tooltip.ClientTempTooltipComponent;
import net.dusty_dusty.bnb_core.cold_crops.tooltip.TempTooltipComponent;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterClientTooltipComponentFactoriesEvent;
import net.minecraftforge.client.event.RenderTooltipEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.event.level.SaplingGrowTreeEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.registries.ForgeRegistries;
import org.slf4j.Logger;

import net.minecraftforge.eventbus.api.SubscribeEvent;


// The value here should match an entry in the META-INF/mods.toml file
@Mod(BnbCore.MODID)
public class BnbCore
{
    public static final String MODID = "bnb_core";
    public static final Logger LOGGER = LogUtils.getLogger();

    public BnbCore(FMLJavaModLoadingContext context )
    {

        IEventBus modEventBus = context.getModEventBus();
        modEventBus.addListener(this::commonSetup);

        IEventBus ForgeEventBus = MinecraftForge.EVENT_BUS;
        ForgeEventBus.register(this);

        new ColdCrops().initialize( context );
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        PacketChannel.register();
    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
//    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
//    public static class ClientModEvents {
//
//        @SubscribeEvent
//        public static void onClientSetup(FMLClientSetupEvent event) {
//            // Some client setup code
//            LOGGER.info("HELLO FROM CLIENT SETUP");
//            LOGGER.info("MINECRAFT NAME >> {}", Minecraft.getInstance().getUser().getName());
//        }
//
//    }



    @SubscribeEvent
    public void jsonReading(AddReloadListenerEvent event) {
        event.addListener(CropsNSeedsData.instance);
    }
}
