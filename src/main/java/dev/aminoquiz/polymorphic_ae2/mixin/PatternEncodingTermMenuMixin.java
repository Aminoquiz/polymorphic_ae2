package dev.aminoquiz.polymorphic_ae2.mixin;

import appeng.api.storage.ITerminalHost;
import appeng.crafting.RecipeAccess;
import appeng.helpers.IPatternTerminalMenuHost;
import appeng.menu.me.common.MEStorageMenu;
import appeng.menu.me.items.PatternEncodingTermMenu;
import com.illusivesoulworks.polymorph.api.PolymorphApi;
import dev.aminoquiz.polymorphic_ae2.PolymorphicAE2;
import java.util.List;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeInput;
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
     * Unlike the crafting terminal, the pattern terminal goes through AE2's own {@code RecipeAccess}
     * helper, which never touches the vanilla {@code RecipeManager}. Only the crafting-mode lookup in
     * {@code getAndUpdateOutput} is redirected; the smithing lookup elsewhere in the class is left alone.
     *
     * <p>{@code getAndUpdateOutput} is driven from {@code setItem}, {@code initializeContents} and
     * {@code onServerDataSync}, so it runs on the CLIENT, where MC 26.x keeps no recipes at all and
     * Polymorph's own lookup comes back empty. AE2 does sync its {@code RecipeMap} though, so the
     * candidate list is handed over explicitly instead of letting Polymorph search for it. Without
     * this the pattern terminal reported "no conflict" and hid the selector button.
     */
    @Redirect(
        method = "getAndUpdateOutput",
        at = @At(
            value = "INVOKE",
            target = "Lappeng/crafting/RecipeAccess;getRecipeFor(Lnet/minecraft/world/level/Level;Lnet/minecraft/world/item/crafting/RecipeType;Lnet/minecraft/world/item/crafting/RecipeInput;)Lnet/minecraft/world/item/crafting/RecipeHolder;"))
    private <I extends RecipeInput, R extends Recipe<I>> RecipeHolder<R> polymorphicAe2$getRecipe(
            Level level, RecipeType<R> type, I input) {
        List<RecipeHolder<R>> candidates = RecipeAccess.getRecipesFor(level, type, input).toList();
        return PolymorphApi.getInstance().getRecipeManager()
            .getPlayerRecipe(this, type, input, level, getPlayer(), candidates)
            .orElse(null);
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
