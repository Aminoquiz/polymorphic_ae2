package dev.aminoquiz.polymorphic_ae2.mixin;

import appeng.menu.me.items.CraftingTermMenu;
import net.minecraft.server.level.ServerLevel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

/**
 * Lets the reopen fix re-run AE2's own recipe resolution from outside the menu.
 */
@Mixin(CraftingTermMenu.class)
public interface CraftingTermMenuAccessor {

    @Invoker("updateCurrentRecipeAndOutput")
    void callUpdateCurrentRecipeAndOutput(ServerLevel level, boolean forceUpdate);
}
