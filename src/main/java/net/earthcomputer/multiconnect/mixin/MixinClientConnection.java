package net.earthcomputer.multiconnect.mixin;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.TimeoutException;
import net.minecraft.network.ClientConnection;
import net.minecraft.network.PacketEncoderException;
import org.apache.logging.log4j.LogManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientConnection.class)
public class MixinClientConnection {

    @Shadow private Channel channel;

    @Inject(method = "exceptionCaught", at = @At("HEAD"))
    public void onExceptionCaught(ChannelHandlerContext context, Throwable t, CallbackInfo ci) {
        if (!(t instanceof PacketEncoderException) && !(t instanceof TimeoutException) && channel.isOpen()) {
            LogManager.getLogger("multiconnect").error("Unexpectedly disconnected from server!", t);
        }
    }

}
