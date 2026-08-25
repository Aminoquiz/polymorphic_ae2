package dev.aminoquiz.polymorphic_ae2.widget;

import appeng.client.gui.me.common.MEStorageScreen;
import appeng.menu.me.common.MEStorageMenu;
import com.illusivesoulworks.polymorph.api.client.widgets.PlayerRecipesWidget;
import dev.aminoquiz.polymorphic_ae2.PolymorphicAE2;
import dev.aminoquiz.polymorphic_ae2.mixin.AEBaseMenuAccessor;
import net.minecraft.resources.Identifier;
import net.minecraft.world.inventory.Slot;

public abstract class BaseTerminalWidget<M extends MEStorageMenu, S extends MEStorageScreen<M>>
        extends PlayerRecipesWidget {

    protected final M menu;

    protected BaseTerminalWidget(S screen, Slot outputSlot) {
        super(screen, outputSlot);
        this.menu = screen.getMenu();
    }

    @Override
    public void selectRecipe(Identifier id) {
        super.selectRecipe(id);
        // Polymorph stores the choice, AE2 still has to recompute its own output for it to show up.
        ((AEBaseMenuAccessor) this.menu).callSendClientAction(PolymorphicAE2.SELECT_RECIPE);
    }
}
