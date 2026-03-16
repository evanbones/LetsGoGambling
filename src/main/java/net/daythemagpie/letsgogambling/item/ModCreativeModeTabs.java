package net.daythemagpie.letsgogambling.item;

import net.daythemagpie.letsgogambling.ChanceGamesMod;
import net.daythemagpie.letsgogambling.block.ModBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModCreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, ChanceGamesMod.MOD_ID);

    public static final RegistryObject<CreativeModeTab> CASINO_MACHINES_TAB = CREATIVE_MODE_TABS.register("casino_machines_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModBlocks.SLOT_MACHINE.get()))
                    .title(Component.translatable("creativetab.casino_machines_tab"))
                    .displayItems((itemDisplayParameters, output) -> {
                        output.accept(ModBlocks.SLOT_MACHINE.get());
                    })
                    .build());

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
