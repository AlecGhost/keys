package de.ghost;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.toasts.SystemToast;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class KeysSettingScreen extends Screen {
	int headerY = 40;
	public KeysSettingScreen(Component title) {
		super(title);
	}

	@Override
	public void render(GuiGraphics graphics, int mouseX, int mouseY, float delta) {
		Component header = Component.literal("Key Bind Profiles");
		super.render(graphics, mouseX, mouseY, delta);
		graphics.drawString(this.font, header, this.width / 2 - this.font.width(header) / 2, this.headerY - this.font.lineHeight - 10, 0xFFFFFFFF, true);
	}

	@Override
	protected void init() {
		EditBox editBox = new EditBox(this.font, 40, 40 + 30, 200, 20, Component.literal("Type here"));
		this.addRenderableWidget(editBox);

		Button buttonWidget = Button.builder(Component.literal("Hello World"), (btn) -> {
			this.minecraft.getToastManager().addToast(
					SystemToast.multiline(this.minecraft, SystemToast.SystemToastId.NARRATOR_TOGGLE,
							Component.nullToEmpty("Hello World!"), Component.nullToEmpty("This is a toast.")));
		}).bounds(40, 40, 120, 20).build();
		this.addRenderableWidget(buttonWidget);

	}

}