package dev.aminoquiz.polymorphic_ae2.mixin;

import appeng.api.storage.ITerminalHost;
import appeng.menu.me.common.MEStorageMenu;
import appeng.menu.me.items.CraftingTermMenu;
import com.illusivesoulworks.polymorph.api.PolymorphApi;
import dev.aminoquiz.polymorphic_ae2.PolymorphicAE2;
import it.unimi.dsi.fastutil.shorts.ShortSet;
import java.util.Optional;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeInput;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CraftingTermMenu.class)
public abstract class CraftingTermMenuMixin extends MEStorageMenu {

    protected CraftingTermMenuMixin(MenuType<?> menuType, int id, Inventory ip, ITerminalHost host) {
        super(menuType, id, ip, host);
    }

    @Shadow
    private void updateCurrentRecipeAndOutput(boolean forceUpdate) {
        throw new AssertionError();
    }

    @Inject(
        method = "<init>(Lnet/minecraft/world/inventory/MenuType;ILnet/minecraft/world/entity/player/Inventory;Lappeng/api/storage/ITerminalHost;Z)V",
        at = @At("RETURN"))
    private void polymorphicAe2$registerAction(MenuType<?> menuType, int id, Inventory ip, ITerminalHost host,
                                               boolean bindInventory, CallbackInfo ci) {
        registerClientAction(PolymorphicAE2.SELECT_RECIPE, () -> updateCurrentRecipeAndOutput(true));
    }

    @Redirect(
        method = "updateCurrentRecipeAndOutput(Z)V",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/item/crafting/RecipeManager;getRecipeFor(Lnet/minecraft/world/item/crafting/RecipeType;Lnet/minecraft/world/item/crafting/RecipeInput;Lnet/minecraft/world/level/Level;)Ljava/util/Optional;"))
    private <I extends RecipeInput, R extends Recipe<I>> Optional<RecipeHolder<R>> polymorphicAe2$getRecipe(
            RecipeManager manager, RecipeType<R> type, I input, Level level) {
        return PolymorphApi.getInstance().getRecipeManager()
            .getPlayerRecipe(this, type, input, level, getPlayer());
    }

    /**
     * The grid contents arrive from the server after the menu is built, so the output has to be
     * recomputed once the sync lands or the selector shows a stale result.
     */
    @Override
    public void onServerDataSync(ShortSet updatedFields) {
        super.onServerDataSync(updatedFields);
        updateCurrentRecipeAndOutput(true);
    }
}
