package de.ghost;

import net.fabricmc.api.ClientModInitializer;
import com.mojang.blaze3d.platform.InputConstants;

import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;

import org.lwjgl.glfw.GLFW;

import net.minecraft.client.KeyMapping;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.client.Minecraft;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.loader.api.FabricLoader;

public class KeysClient implements ClientModInitializer {
	List<String> profiles = List.of("profile1", "profile2", "profile3");
	int currentProfilesIndex = 0;
	static final String PROFILES_DIR = "keys/profiles/";
	static final String CONFIG_DIR = "keys/";

	@Override
	public void onInitializeClient() {
		loadConfig();
		KeyMapping.Category category1 = KeyMapping.Category
				.register(Identifier.fromNamespaceAndPath("keys", "profiles"));

		KeyMapping next = KeyBindingHelper
				.registerKeyBinding(new KeyMapping("Next profile",
						InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_P, category1));
		KeyMapping prev = KeyBindingHelper
				.registerKeyBinding(new KeyMapping("Previous profile",
						InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_U, category1));

		ClientTickEvents.END_CLIENT_TICK.register(client -> {
			if (client.player == null)
				return;

			while (next.consumeClick()) {
				var currentProfile = this.profiles.get(currentProfilesIndex);
				saveCurrentProfile(currentProfile);
				var nextIndex = (currentProfilesIndex + 1) % profiles.size();
				var nextProfile = this.profiles.get(nextIndex);
				loadProfile(nextProfile);
				client.player.displayClientMessage(Component.literal("Loaded " + nextProfile), false);
				currentProfilesIndex = nextIndex;
				saveConfig();
			}

			while (prev.consumeClick()) {
				var currentProfile = this.profiles.get(currentProfilesIndex);
				saveCurrentProfile(currentProfile);
				var nextIndex = (currentProfilesIndex + profiles.size() - 1) % profiles.size();
				var nextProfile = this.profiles.get(nextIndex);
				loadProfile(nextProfile);
				client.player.displayClientMessage(Component.literal("Loaded " + nextProfile), false);
				currentProfilesIndex = nextIndex;
				saveConfig();
			}
		});
	}

	public void saveCurrentProfile(String name) {
		var keys = Minecraft.getInstance().options.keyMappings;
		var mappings = "";
		for (KeyMapping key : keys) {
			var keyName = key.getName();
			var keyValue = key.saveString();
			mappings += keyName + "=" + keyValue + "\n";
		}
		try {
			var file = FabricLoader.getInstance().getConfigDir().resolve(PROFILES_DIR + name + ".txt");
			Files.createDirectories(file.getParent());
			Files.writeString(file, mappings);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void loadProfile(String name) {
		var mappings = new HashMap<String, InputConstants.Key>();
		try {
			var file = FabricLoader.getInstance().getConfigDir().resolve(PROFILES_DIR + name + ".txt");
			var content = Files.readString(file);
			for (var line : content.split("\n")) {
				var items = line.split("=");
				assert items.length == 2;
				mappings.put(items[0], InputConstants.getKey(items[1]));
			}
		} catch (IOException e) {
			// Ignore for now
		}
		var options = Minecraft.getInstance().options;
		var keys = options.keyMappings;
		for (var key : keys) {
			var newKey = mappings.get(key.getName());
			if (newKey != null) {
				key.setKey(newKey);
			}
		}

		options.save();
		options.load();
	}

	public void loadConfig() {
		try {
			var file = FabricLoader.getInstance().getConfigDir().resolve(CONFIG_DIR + "config.txt");
			var content = Files.readString(file);
			for (var line : content.split("\n")) {
				var items = line.split("=");
				assert items.length == 2;
				if (items[0].equals("currentProfileIndex")) {
					currentProfilesIndex = Integer.parseInt(items[1]);
				}
			}
		} catch (IOException e) {
			// Ignore for now
		}
	}

	public void saveConfig() {
		var config = "currentProfileIndex=" + currentProfilesIndex + "\n";
		try {
			var file = FabricLoader.getInstance().getConfigDir().resolve(CONFIG_DIR + "config.txt");
			Files.createDirectories(file.getParent());
			Files.writeString(file, config);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
