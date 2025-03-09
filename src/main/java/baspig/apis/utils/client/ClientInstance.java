package baspig.apis.utils.client;

import net.minecraft.client.MinecraftClient;

import java.util.Objects;

@SuppressWarnings("unused")
public class ClientInstance {

    public static boolean isUser(String user){
        MinecraftClient client = MinecraftClient.getInstance();
        return client.getSession() != null && client.getSession().getUsername().equals(user);
    }

    public static boolean isUUID(String uuid){
        MinecraftClient client = MinecraftClient.getInstance();
        return Objects.equals(String.valueOf(client.getSession().getUuidOrNull()), uuid);
    }
}
