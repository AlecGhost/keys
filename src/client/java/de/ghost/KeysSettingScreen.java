package de.ghost;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.toasts.SystemToast;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class KeysSettingScreen extends Screen {
	public KeysSettingScreen(Component title) {
		super(title);
	}

	@Override
	protected void init() {
		Button buttonWidget = Button.builder(Component.literal("Hello World"), (btn) -> {
			// When the button is clicked, we can display a toast to the screen.
			this.minecraft.getToastManager().addToast(
					SystemToast.multiline(this.minecraft, SystemToast.SystemToastId.NARRATOR_TOGGLE,
							Component.nullToEmpty("Hello World!"), Component.nullToEmpty("This is a toast.")));
		}).bounds(40, 40, 120, 20).build();
		this.addRenderableWidget(buttonWidget);

	}

	@Override
	public void render(GuiGraphics graphics, int mouseX, int mouseY, float delta) {
		super.render(graphics, mouseX, mouseY, delta);
		graphics.drawString(this.font, "Special Button", 40, 40 - this.font.lineHeight - 10, 0xFFFFFFFF, true);
	}
}