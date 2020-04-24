package net.earthcomputer.multiconnect.mixin;

import com.mojang.authlib.GameProfile;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.packet.s2c.login.LoginSuccessS2CPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.util.UUID;

@Mixin(LoginSuccessS2CPacket.class)
public class MixinLoginSuccessS2CPacket {

    @Shadow
    private GameProfile profile;

    /**
     * @reason This is utterly fucking retarded.
     * @author Mojang
     */
    @Overwrite
    public void read(PacketByteBuf buf) {
        String string = buf.readString(36);
        String string2 = buf.readString(16);
        UUID uUID = UUID.fromString(string);
        this.profile = new GameProfile(uUID, string2);
    }

}
