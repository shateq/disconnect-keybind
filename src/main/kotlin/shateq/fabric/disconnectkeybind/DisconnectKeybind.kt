package shateq.fabric.disconnectkeybind

import com.mojang.blaze3d.platform.InputConstants
import net.fabricmc.api.ClientModInitializer
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper
import net.minecraft.client.KeyMapping
import net.minecraft.client.gui.screens.GenericDirtMessageScreen
import net.minecraft.client.gui.screens.TitleScreen
import net.minecraft.client.gui.screens.multiplayer.JoinMultiplayerScreen
import net.minecraft.network.chat.Component
import org.lwjgl.glfw.GLFW

class DisconnectKeybind : ClientModInitializer {
    private val id = "disconnectkeybind"

    override fun onInitializeClient() {
        val disconnect = KeyBindingHelper.registerKeyBinding(
            KeyMapping(
                "key.$id",
                InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_BACKSPACE,
                "key.categories.misc",
            )
        )

        ClientTickEvents.END_CLIENT_TICK.register {
            while (disconnect.consumeClick()) {
                val localServer = it.isLocalServer
                // Disconnects
                it.level?.disconnect()

                if (localServer) {
                    it.clearLevel(GenericDirtMessageScreen(Component.translatable("menu.savingLevel")))
                }

                val titleScreen = TitleScreen()
                if (localServer) {
                    it.setScreen(titleScreen)
                } else {
                    it.setScreen(JoinMultiplayerScreen(titleScreen))
                }

            }
        }
    }
}