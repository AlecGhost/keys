package de.ghost;

import net.fabricmc.api.ClientModInitializer;
import com.mojang.blaze3d.platform.InputConstants;


import org.lwjgl.glfw.GLFW;

import net.minecraft.client.KeyMapping;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.client.Minecraft;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;

public class KeysClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		KeyMapping.Category category1 = KeyMapping.Category
				.register(Identifier.fromNamespaceAndPath("keys", "profiles"));

		KeyMapping binding1 = KeyBindingHelper
				.registerKeyBinding(new KeyMapping("Set Keys Profile 1",
						InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_P, category1));
		KeyMapping binding2 = KeyBindingHelper
				.registerKeyBinding(new KeyMapping("Set Keys Profile 2",
						InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_U, category1));

		ClientTickEvents.END_CLIENT_TICK.register(client -> {
			if (client.player == null)
				return;

			while (binding1.consumeClick()) {
				client.player.displayClientMessage(Component.literal("Key 1 was pressed!"), false);
				setProfile1();
			}

			while (binding2.consumeClick()) {
				client.player.displayClientMessage(Component.literal("Key 2 was pressed!"), false);
				setProfile2();
			}
		});
	}

	public void setProfile1() {
		var options = Minecraft.getInstance().options;
		var upKey = options.keyUp;
		var downKey = options.keyDown;
		upKey.setKey(downKey.getDefaultKey());
		downKey.setKey(upKey.getDefaultKey());
		options.save();
		options.load();
	}

	public void setProfile2() {
		var options = Minecraft.getInstance().options;
		var downKey = options.keyDown;
		var upKey = options.keyUp;
		upKey.setKey(upKey.getDefaultKey());
		downKey.setKey(downKey.getDefaultKey());
		options.save();
		options.load();
	}
}
