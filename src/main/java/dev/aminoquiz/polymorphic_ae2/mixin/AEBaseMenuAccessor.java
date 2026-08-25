package dev.aminoquiz.polymorphic_ae2.mixin;

import appeng.menu.AEBaseMenu;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

/**
 * {@code sendClientAction} is protected in AE2, but the widget needs to call it from outside the
 * menu hierarchy.
 */
@Mixin(AEBaseMenu.class)
public interface AEBaseMenuAccessor {

    @Invoker("sendClientAction")
    void callSendClientAction(String action);
}
