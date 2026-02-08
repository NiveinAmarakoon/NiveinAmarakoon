package com.nivein.cheatmenu.client;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.text.Text;

public class CheatMenuScreen extends Screen {
    private static final int MAX_RESULTS = 8;

    private final Screen parent;
    private TextFieldWidget searchField;
    private final List<ButtonWidget> resultButtons = new ArrayList<>();

    public CheatMenuScreen(Screen parent) {
        super(Text.literal("Cheat Menu"));
        this.parent = parent;
    }

    @Override
    protected void init() {
        int centerX = this.width / 2;
        int top = this.height / 4;

        this.searchField = new TextFieldWidget(this.textRenderer, centerX - 120, top, 240, 20, Text.literal("Search items"));
        this.searchField.setMaxLength(64);
        this.searchField.setChangedListener(value -> refreshResults());
        this.addSelectableChild(this.searchField);
        this.setInitialFocus(this.searchField);

        refreshResults();

        this.addDrawableChild(ButtonWidget.builder(Text.literal("Back"), button -> close())
            .dimensions(centerX - 40, this.height - 28, 80, 20)
            .build());
    }

    private void refreshResults() {
        for (ButtonWidget button : resultButtons) {
            this.remove(button);
        }
        resultButtons.clear();

        String query = searchField == null ? "" : searchField.getText().trim().toLowerCase();
        List<Item> matches = new ArrayList<>();
        for (Item item : Registries.ITEM) {
            String name = item.getName().getString().toLowerCase();
            if (query.isEmpty() || name.contains(query)) {
                matches.add(item);
            }
            if (matches.size() >= MAX_RESULTS) {
                break;
            }
        }

        int centerX = this.width / 2;
        int startY = this.height / 4 + 30;
        int index = 0;
        for (Item item : matches) {
            int y = startY + index * 22;
            Text label = Text.literal(item.getName().getString());
            ButtonWidget button = ButtonWidget.builder(label, b -> giveItem(item))
                .dimensions(centerX - 120, y, 240, 20)
                .build();
            resultButtons.add(button);
            this.addDrawableChild(button);
            index++;
        }
    }

    private void giveItem(Item item) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player == null) {
            return;
        }
        ItemStack stack = new ItemStack(item, 1);
        client.player.getInventory().insertStack(stack);
    }

    private void close() {
        MinecraftClient.getInstance().setScreen(parent);
    }

    @Override
    public boolean shouldPause() {
        return false;
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        this.renderBackground(context);
        super.render(context, mouseX, mouseY, delta);
        context.drawTextWithShadow(this.textRenderer, this.title, (this.width - this.textRenderer.getWidth(this.title)) / 2, this.height / 4 - 20, 0xFFFFFF);
    }
}
