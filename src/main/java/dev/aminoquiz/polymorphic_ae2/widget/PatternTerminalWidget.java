package dev.aminoquiz.polymorphic_ae2.widget;

import appeng.client.gui.me.items.PatternEncodingTermScreen;
import appeng.menu.SlotSemantics;
import appeng.menu.me.items.PatternEncodingTermMenu;
import appeng.parts.encoding.EncodingMode;
import net.minecraft.client.gui.GuiGraphics;

public class PatternTerminalWidget<M extends PatternEncodingTermMenu>
        extends BaseTerminalWidget<M, PatternEncodingTermScreen<M>> {

    public PatternTerminalWidget(PatternEncodingTermScreen<M> screen) {
        super(screen, screen.getMenu().getSlots(SlotSemantics.CRAFTING_RESULT).getFirst());
    }

    // Processing, smithing and stonecutting patterns have no crafting-grid conflict to resolve.
    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float renderPartialTicks) {
        if (this.menu.mode == EncodingMode.CRAFTING) {
            super.render(guiGraphics, mouseX, mouseY, renderPartialTicks);
        }
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        return this.menu.mode == EncodingMode.CRAFTING && super.mouseClicked(mouseX, mouseY, button);
    }
}
