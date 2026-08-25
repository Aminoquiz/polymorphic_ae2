package dev.aminoquiz.polymorphic_ae2.mixin;

import appeng.client.gui.me.common.MEStorageScreen;
import com.illusivesoulworks.polymorph.api.client.PolymorphWidgets;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Switching terminal style resizes the screen, so the selector button has to be moved with it.
 */
@Mixin(MEStorageScreen.class)
public abstract class MEStorageScreenMixin {

    @Inject(method = "toggleTerminalStyle(Lappeng/client/gui/widgets/SettingToggleButton;Z)V", at = @At("RETURN"))
    private void polymorphicAe2$moveWidget(CallbackInfo ci) {
        if (PolymorphWidgets.getInstance().getCurrentWidget() instanceof AbstractRecipesWidgetAccessor widget) {
            widget.callResetWidgetOffsets();
        }
    }
}
