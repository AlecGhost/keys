package de.ghost;

import org.lwjgl.glfw.GLFW;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.input.KeyEvent;
import net.minecraft.network.chat.Component;

public class KeysSettingScreen extends Screen {
	int originY = 40;
	int buttonHeight = 20;
	String profileName = "Profile Name";
	EditBox profileNameTextField;
	Button profileNameButton;
	Button duplicateButton;
	Button deleteButton;

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
		int space1 = 10;
		int space2 = space1 * 2;
		int centerX = this.width / 2;
		int boxWidth = this.width / 3;
		int boxNameWidth = this.width * 2 / 9;
		int buttonWidth = this.width / 12;

		int originX = (this.width - boxWidth) / 2 + space1;
		int nameBoxWidth = boxNameWidth;

		int duplicateButtonX = centerX + buttonWidth + space1;
		int duplicateButtonWidth = buttonWidth - space2;

		int deleteButtonX = centerX + buttonWidth * 2 + space1;
		int deleteButtonWidth = buttonWidth - space2;

		profileNameTextField = new EditBox(this.font, originX, originY, nameBoxWidth, buttonHeight,
				Component.literal(profileName));
		this.addRenderableWidget(profileNameTextField);
		profileNameTextField.visible = false;
		profileNameTextField.setValue(profileName);

		profileNameButton = Button.builder(Component.literal(profileName), (btn) -> {
			profileNameTextField.visible = true;
			btn.visible = false;
		}).bounds(originX, originY, nameBoxWidth, buttonHeight).build();
		this.addRenderableWidget(profileNameButton);

		duplicateButton = Button.builder(Component.literal("Duplicate"), (btn) -> {
		}).bounds(duplicateButtonX, originY, duplicateButtonWidth, buttonHeight).build();
		this.addRenderableWidget(duplicateButton);

		deleteButton = Button.builder(Component.literal("Delete"), (btn) -> {
		}).bounds(deleteButtonX, originY, deleteButtonWidth, buttonHeight).build();
		this.addRenderableWidget(deleteButton);
	}

	@Override
	public boolean keyPressed(KeyEvent keyEvent) {
		if (keyEvent.key() == GLFW.GLFW_KEY_ENTER && profileNameTextField.visible) {
			String newProfileName = profileNameTextField.getValue();
			if (!newProfileName.isEmpty()) {
				profileName = newProfileName;
				profileNameButton.setMessage(Component.literal(profileName));
			}
			profileNameTextField.visible = false;
			profileNameButton.visible = true;
		}
		return super.keyPressed(keyEvent);
	}
}