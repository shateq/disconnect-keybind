package shateq.disconnectkeybind.fabric

import com.mojang.blaze3d.platform.InputConstants
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper
import net.minecraft.client.KeyMapping
import net.minecraft.client.gui.screens.TitleScreen
import net.minecraft.client.gui.screens.multiplayer.JoinMultiplayerScreen
import org.lwjgl.glfw.GLFW

@Suppress("unused")
fun init() {
    val id = "disconnectkeybind"

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
            it.level?.disconnect() //Disconnects

            if (localServer) {
                it.clearLevel()
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
