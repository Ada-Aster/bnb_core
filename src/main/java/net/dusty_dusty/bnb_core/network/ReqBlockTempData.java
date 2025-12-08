package net.dusty_dusty.bnb_core.network;

import com.momosoftworks.coldsweat.util.world.WorldHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ReqBlockTempData {
    public BlockPos cropPos;

    public ReqBlockTempData(BlockPos cropPos) {
        this.cropPos = cropPos;
    }

    public ReqBlockTempData(FriendlyByteBuf buf) {
        this.cropPos = buf.readBlockPos();
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeBlockPos(this.cropPos);
    }


    //TODO this packet
    @SuppressWarnings({"UnusedReturnValue", "unused"})
    public boolean handle(Supplier<NetworkEvent.Context> contextSupplier) {

        NetworkEvent.Context context = contextSupplier.get();
        if (context.getSender() == null) return true;

        context.enqueueWork(() -> {
            double temperature = WorldHelper.getTemperatureAt(context.getSender().level(), this.cropPos);
            PacketChannel.sendToClient(new RecBlockTempData(cropPos, temperature), context.getSender());
        });

        return true;
    }
}

