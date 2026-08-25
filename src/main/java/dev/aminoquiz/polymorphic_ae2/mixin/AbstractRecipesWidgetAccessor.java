package dev.aminoquiz.polymorphic_ae2.mixin;

import com.illusivesoulworks.polymorph.api.client.base.AbstractRecipesWidget;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(AbstractRecipesWidget.class)
public interface AbstractRecipesWidgetAccessor {

    @Invoker("resetWidgetOffsets")
    void callResetWidgetOffsets();
}
