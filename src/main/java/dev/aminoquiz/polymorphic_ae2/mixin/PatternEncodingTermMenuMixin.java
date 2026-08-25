package dev.aminoquiz.polymorphic_ae2.mixin;

import appeng.api.storage.ITerminalHost;
import appeng.helpers.IPatternTerminalMenuHost;
import appeng.menu.me.common.MEStorageMenu;
import appeng.menu.me.items.PatternEncodingTermMenu;
import com.illusivesoulworks.polymorph.api.PolymorphApi;
import dev.aminoquiz.polymorphic_ae2.PolymorphicAE2;
import java.util.Optional;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingRecipe;
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

@Mixin(PatternEncodingTermMenu.class)
public abstract class PatternEncodingTermMenuMixin extends MEStorageMenu {

    @Shadow
    private RecipeHolder<CraftingRecipe> currentRecipe;

    protected PatternEncodingTermMenuMixin(MenuType<?> menuType, int id, Inventory ip, ITerminalHost host) {
        super(menuType, id, ip, host);
    }

    @Shadow
    private ItemStack getAndUpdateOutput() {
        throw new AssertionError();
    }

    @Inject(
        method = "<init>(Lnet/minecraft/world/inventory/MenuType;ILnet/minecraft/world/entity/player/Inventory;Lappeng/helpers/IPatternTerminalMenuHost;Z)V",
        at = @At("RETURN"))
    private void polymorphicAe2$registerAction(MenuType<?> menuType, int id, Inventory ip,
                                               IPatternTerminalMenuHost host, boolean bindInventory,
                                               CallbackInfo ci) {
        registerClientAction(PolymorphicAE2.SELECT_RECIPE, () -> {
            this.currentRecipe = null;
            getAndUpdateOutput();
        });
    }

    /**
     * Only the crafting-mode lookup in {@code getAndUpdateOutput} is redirected; the smithing lookup
     * elsewhere in the class is left alone.
     */
    @Redirect(
        method = "getAndUpdateOutput",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/item/crafting/RecipeManager;getRecipeFor(Lnet/minecraft/world/item/crafting/RecipeType;Lnet/minecraft/world/item/crafting/RecipeInput;Lnet/minecraft/world/level/Level;)Ljava/util/Optional;"))
    private <I extends RecipeInput, R extends Recipe<I>> Optional<RecipeHolder<R>> polymorphicAe2$getRecipe(
            RecipeManager manager, RecipeType<R> type, I input, Level level) {
        return PolymorphApi.getInstance().getRecipeManager()
            .getPlayerRecipe(this, type, input, level, getPlayer());
    }

    /**
     * Without this the cached recipe survives a grid edit and the terminal keeps showing a result
     * that no longer matches the new ingredients.
     */
    @Inject(method = "setItem", at = @At("HEAD"))
    private void polymorphicAe2$resetRecipe(int slotID, int stateId, ItemStack stack, CallbackInfo ci) {
        this.currentRecipe = null;
    }
}
