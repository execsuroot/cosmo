package tech.execsuroot.cosmo;

import lombok.Getter;
import lombok.NonNull;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.cacheddata.CachedMetaData;
import net.minecraft.network.message.MessageType;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.Pair;
import org.apache.logging.log4j.LogManager;
import tech.execsuroot.cosmo.util.Log;
import java.util.Base64;

public class CosmoMod implements ModInitializer {

    public static final RegistryKey<MessageType> MESSAGE_TYPE_ID = RegistryKey.of(RegistryKeys.MESSAGE_TYPE, Identifier.of("cosmo", "message_type"));
    @Getter
    private static MessageType messageType;

    public CosmoMod() {
        Log.setLogger(LogManager.getLogger("Cosmo"));
    }

    @Override
    public void onInitialize() {
        ServerLifecycleEvents.SERVER_STARTING.register(server -> {
            messageType = server.getRegistryManager().get(RegistryKeys.MESSAGE_TYPE).getOrThrow(MESSAGE_TYPE_ID);
        });
        Log.info("Cosmo integration with LuckPerms has been initialized.");
    }

    public static @NonNull MessageType.Parameters getParams(@NonNull ServerPlayerEntity sender, @NonNull Text content) {
        return getMessageType().params(formatMessage(sender, content));
    }

    public static @NonNull Text formatMessage(@NonNull ServerPlayerEntity sender, @NonNull Text content) {
        Pair<Text, Text> prefixAndSuffix = getPrefixAndSuffixForPlayer(sender);
        Text prefix = prefixAndSuffix.getLeft();
        Text suffix = prefixAndSuffix.getRight();
        Text senderName = Text.literal(sender.getGameProfile().getName()).setStyle(Style.EMPTY.withColor(Formatting.GREEN));
        Text senderDisplayName = Text.empty().append(prefix).append(senderName).append(suffix);
        return Text.empty().append(senderDisplayName).append(" > ").append(content);
    }

    public static @NonNull Pair<@NonNull Text, @NonNull Text> getPrefixAndSuffixForPlayer(@NonNull ServerPlayerEntity sender) {
        Text prefix = Text.empty();
        Text suffix = Text.empty();
        CachedMetaData metaData = LuckPermsProvider.get().getPlayerAdapter(ServerPlayerEntity.class).getMetaData(sender);
        String prefixString = metaData.getPrefix();
        if (prefixString != null) {
            try {
                Text deserialized = Text.Serializer.fromJson(new String(Base64.getDecoder().decode(prefixString)));
                if (deserialized != null) {
                    prefix = deserialized;
                }
            } catch (Exception e) {
                Log.warn("Failed to decode prefix for player " + sender.getGameProfile().getName() + ".", e);
            }
        }
        String suffixString = metaData.getSuffix();
        if (suffixString != null) {
            try {
                Text deserialized = Text.Serializer.fromJson(new String(Base64.getDecoder().decode(suffixString)));
                if (deserialized != null) {
                    suffix = deserialized;
                }
            } catch (Exception e) {
                Log.warn("Failed to decode suffix for player " + sender.getGameProfile().getName() + ".", e);
            }
        }
        return new Pair<>(prefix, suffix);
    }
}