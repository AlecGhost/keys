package de.ghost;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class KeysSettingScreen extends Screen {
	int originY = 40;
	int buttonHeight = 20;

	public KeysSettingScreen(Component title) {
		super(title);
	}

	@Override
	public void render(GuiGraphics graphics, int mouseX, int mouseY, float delta) {
		Component headerText = Component.literal("Key Bind Profiles");
		super.render(graphics, mouseX, mouseY, delta);
		graphics.drawString(this.font, headerText, this.width / 2 - this.font.width(headerText) / 2,
				this.originY - this.font.lineHeight - 10, 0xFFFFFFFF, true);
	}

	@Override
	protected void init() {
		int boxWidth = this.width / 2;
		int originX = (this.width - boxWidth) / 2;
		int buttonWidth = boxWidth / 2;
		String profileName = "Profile Name";

		EditBox profileNameTextField = new EditBox(this.font, originX, originY, buttonWidth, buttonHeight,
				Component.literal(profileName));
		this.addRenderableWidget(profileNameTextField);
		profileNameTextField.visible = false;

		Button buttonWidget = Button.builder(Component.literal(profileName), (btn) -> {
			profileNameTextField.visible = true;
			btn.visible = false;
		}).bounds(originX, originY, buttonWidth, buttonHeight).build();
		this.addRenderableWidget(buttonWidget);
	}

}