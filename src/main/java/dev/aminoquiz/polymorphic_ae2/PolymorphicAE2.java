package dev.aminoquiz.polymorphic_ae2;

import appeng.menu.guisync.ClientActionKey;

public final class PolymorphicAE2 {

    public static final String MOD_ID = "polymorphic_ae2";

    /**
     * Sent by the recipe selector widget after the player picks a result, so the menu recomputes
     * its output with the new selection. On 26.x the recipe update runs server-side only, so this
     * round trip is what makes the terminal reflect the choice.
     */
    public static final ClientActionKey<Void> SELECT_RECIPE = new ClientActionKey<>(MOD_ID + "$selectRecipe");

    private PolymorphicAE2() {
    }
}
