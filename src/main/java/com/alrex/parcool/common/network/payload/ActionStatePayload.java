package com.alrex.parcool.common.network.payload;

import com.alrex.parcool.ParCool;
import com.alrex.parcool.api.unstable.action.ParCoolActionEvent;
import com.alrex.parcool.common.action.Action;
import com.alrex.parcool.common.action.Actions;
import com.alrex.parcool.common.attachment.common.Parkourability;
import io.netty.buffer.ByteBuf;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

import org.jetbrains.annotations.NotNull;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public record ActionStatePayload(UUID playerID, List<Entry> states) implements CustomPacketPayload {
    public static final Type<ActionStatePayload> TYPE
            = new Type<>(ResourceLocation.fromNamespaceAndPath(ParCool.MOD_ID, "payload.action_state"));
    public static final StreamCodec<ByteBuf, ActionStatePayload> CODEC = StreamCodec.of(
            ActionStatePayload::encode,
            ActionStatePayload::decode
    );

    @NotNull
    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    private static void encode(ByteBuf buf, ActionStatePayload payload) {
        buf.writeLong(payload.playerID().getMostSignificantBits());
        buf.writeLong(payload.playerID().getLeastSignificantBits());
        buf.writeInt(payload.states().size());
        for (var s : payload.states()) {
            s.encode(buf);
        }
    }

    private static ActionStatePayload decode(ByteBuf buf) {
        var id = new UUID(buf.readLong(), buf.readLong());
        int size = buf.readInt();
        ArrayList<Entry> entries = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            entries.addLast(Entry.decode(buf));
        }
        return new ActionStatePayload(id, entries);
    }

    @Environment(EnvType.CLIENT)
    public static void handleClient(ActionStatePayload payload, ClientPlayNetworking.Context context) {
        Level world = context.player().level();
        context.client().execute(() -> {
            Player player;
            player = world.getPlayerByUUID(payload.playerID());
            if (player == null || player.isLocalPlayer()) return;

            Parkourability parkourability = Parkourability.get(player);
            if (parkourability == null) return;

            for (var state : payload.states()) {
                Action action = parkourability.get(state.action());
                switch (state.type()) {
                    case Start:
                        action.setDoing(true);
                        var buf = state.getDataAsBuffer();
                        action.onStart(player, parkourability, buf);
                        buf.rewind();
                        action.onStartInOtherClient(player, parkourability, buf);
                        (new ParCoolActionEvent.StartEvent(player, action)).sendEvent();
                        break;
                    case Finish:
                        action.setDoing(false);
                        action.onStopInOtherClient(player);
                        action.onStop(player);
                        (new ParCoolActionEvent.StopEvent(player, action)).sendEvent();
                        break;
                    case Normal:
                        action.restoreSynchronizedState(state.getDataAsBuffer());
                        break;
                }
            }
        });
    }

    public static void handleServer(ActionStatePayload payload, ServerPlayNetworking.Context context) {
        context.server().execute(() -> {
            Player player = context.player();
            for (ServerPlayer serverPlayer : PlayerLookup.all(context.server())) {
                ServerPlayNetworking.send(serverPlayer, payload);
            }

            Parkourability parkourability = Parkourability.get(player);
            if (parkourability == null) return;

            for (var state : payload.states()) {
                Action action = parkourability.get(state.action());
                switch (state.type()) {
                    case Start:
                        action.setDoing(true);
                        var buf = state.getDataAsBuffer();
                        action.onStart(player, parkourability, buf);
                        buf.rewind();
                        action.onStartInServer(player, parkourability, buf);
                        (new ParCoolActionEvent.StartEvent(player, action)).sendEvent();
                        break;
                    case Finish:
                        action.setDoing(false);
                        action.onStopInServer(player);
                        action.onStop(player);
                        (new ParCoolActionEvent.StopEvent(player, action)).sendEvent();
                        break;
                    case Normal:
                        action.restoreSynchronizedState(state.getDataAsBuffer());
                        break;
                }
            }
        });
    }

    public record Entry(Class<? extends Action> action, Type type, byte[] data) {
        public enum Type {
            Normal, Start, Finish;
        }

        private void encode(ByteBuf buf) {
            buf
                    .writeShort(Actions.getIndexOf(action))
                    .writeByte(type().ordinal())
                    .writeInt(data().length)
                    .writeBytes(data);
        }

        public ByteBuffer getDataAsBuffer() {
            return ByteBuffer.wrap(data);
        }

        private static Entry decode(ByteBuf buf) {
            var action = Actions.getByIndex(buf.readShort());
            Type type = Type.values()[buf.readByte()];
            int remaining = buf.readInt();
            var data = new byte[remaining];
            buf.readBytes(data);
            return new Entry(action, type, data);
        }
    }
}
