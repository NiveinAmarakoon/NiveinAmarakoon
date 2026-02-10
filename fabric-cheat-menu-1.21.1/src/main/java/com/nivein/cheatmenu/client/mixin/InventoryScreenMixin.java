package com.nivein.cheatmenu.client.mixin;

import com.nivein.cheatmenu.client.CheatMenuScreen;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InventoryScreen.class)
public class InventoryScreenMixin {
    @Inject(method = "init", at = @At("TAIL"))
    private void cheatmenu$addButton(CallbackInfo info) {
        InventoryScreen screen = (InventoryScreen) (Object) this;
        int x = screen.width / 2 + 70;
        int y = screen.height / 2 - 80;
        ButtonWidget button = ButtonWidget.builder(Text.literal("Cheat Menu"), b -> {
            MinecraftClient.getInstance().setScreen(new CheatMenuScreen(screen));
        }).dimensions(x, y, 90, 20).build();
        screen.addDrawableChild(button);
    }
}
