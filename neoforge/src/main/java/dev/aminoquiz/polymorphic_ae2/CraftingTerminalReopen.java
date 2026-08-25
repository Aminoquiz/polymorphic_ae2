package dev.aminoquiz.polymorphic_ae2;

import appeng.menu.me.items.CraftingTermMenu;
import dev.aminoquiz.polymorphic_ae2.mixin.CraftingTermMenuAccessor;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.player.PlayerContainerEvent;

/**
 * Restores the selector button when a crafting terminal is reopened on a grid that already
 * holds a conflicting recipe.
 *
 * <p>{@code CraftingTermMenu} resolves its recipe from its own constructor. Polymorph only
 * forwards a conflict list to the player whose {@code containerMenu} is the menu that asked
 * for it, and during the constructor that field still points at the previous menu, so the
 * list was computed and then dropped. Nothing recomputed it afterwards either, since AE2
 * only re-resolves when a slot changes, which is why editing the grid brought the button
 * back. Re-running the resolution once the menu is actually installed fixes it at the source
 * rather than loosening Polymorph's guard, which exists to stop one menu's conflict list
 * leaking into another.
 *
 * <p>The pattern terminal is unaffected: it resolves client-side from
 * {@code initializeContents}, which runs after the menu is open.
 */
public final class CraftingTerminalReopen {

    private CraftingTerminalReopen() {
    }

    @SubscribeEvent
    public static void openContainer(final PlayerContainerEvent.Open evt) {

        if (evt.getEntity() instanceof ServerPlayer player
                && evt.getContainer() instanceof CraftingTermMenu menu
                && player.level() instanceof ServerLevel level) {
            ((CraftingTermMenuAccessor) menu).callUpdateCurrentRecipeAndOutput(level, true);
        }
    }
}
