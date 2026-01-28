package de.ghost;

import net.fabricmc.api.ClientModInitializer;
import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.blaze3d.platform.InputConstants.Key;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.lwjgl.glfw.GLFW;

import net.minecraft.client.KeyMapping;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Options;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;

public class KeysClient implements ClientModInitializer {
	 Options options = Minecraft.getInstance().options;
List<KeyMapping> keys = new ArrayList<>(Arrays.asList(
		options.keyUp,
		options.keyDown,	
		options.keyLeft,
		options.keyRight,
		options.keyJump,
		options.keySprint,
		options.keyInventory,
		options.keySwapOffhand,
		options.keyDrop,
		options.keyUse,
		options.keyAttack,
		options.keyPickItem,
		options.keyChat,
		options.keyCommand,
		options.keyScreenshot,
		options.keyTogglePerspective,
		options.keySmoothCamera,
		options.keyFullscreen,
		options.keySpectatorOutlines,
		options.keyAdvancements
	));

	@Override
	public void onInitializeClient() {
		keys.addAll(List.of(options.keyMappings));

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
				switchProfiles();
			}

			while (binding2.consumeClick()) {
				client.player.displayClientMessage(Component.literal("Key 2 was pressed!"), false);
				setProfile2();
			}
		});
	}

	public void switchProfiles() {
		var mappings = "";
		for (KeyMapping key : keys) {
			var keyValue = key.saveString();
			var keyName = key.getName();
			mappings += keyName + "=" + keyValue + "\n";
		}
		try {
			Files.writeString(Path.of("~/mappings.txt"), mappings);
		} catch (IOException e) {
			e.printStackTrace();
		}
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
