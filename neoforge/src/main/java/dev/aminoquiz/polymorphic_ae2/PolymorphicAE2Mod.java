package dev.aminoquiz.polymorphic_ae2;

import appeng.client.gui.me.items.CraftingTermScreen;
import appeng.client.gui.me.items.PatternEncodingTermScreen;
import com.illusivesoulworks.polymorph.api.client.PolymorphWidgets;
import dev.aminoquiz.polymorphic_ae2.widget.CraftingTerminalWidget;
import dev.aminoquiz.polymorphic_ae2.widget.PatternTerminalWidget;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.common.NeoForge;

@Mod(PolymorphicAE2.MOD_ID)
public class PolymorphicAE2Mod {

    public PolymorphicAE2Mod() {
        NeoForge.EVENT_BUS.register(CraftingTerminalReopen.class);

        if (FMLEnvironment.getDist().isClient()) {
            ClientSetup.init();
        }
    }

    private static final class ClientSetup {

        static void init() {
            PolymorphWidgets.getInstance().registerWidget(screen -> {
                if (screen instanceof CraftingTermScreen<?> craftingTerminal) {
                    return new CraftingTerminalWidget<>(craftingTerminal);
                }

                if (screen instanceof PatternEncodingTermScreen<?> patternTerminal) {
                    return new PatternTerminalWidget<>(patternTerminal);
                }
                return null;
            });
        }
    }
}
