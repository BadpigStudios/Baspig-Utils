package baspig.apis.utils.client;

import net.minecraft.client.MinecraftClient;

import java.util.Objects;
import java.util.UUID;

@SuppressWarnings("unused")
public class ClientInfo {

    public static MinecraftClient client = MinecraftClient.getInstance();

    public static String getUserName(){
        return client.getSession().getUsername();
    }

    public static boolean isUser(String user){
        return client.getSession() != null && client.getSession().getUsername().equals(user);
    }

    private static boolean issUUID(String uuid){
        return Objects.equals(String.valueOf(client.getSession().getUuidOrNull()), uuid);
    }

    public static boolean isUUID(String uuid){
        return client.uuidEquals(UUID.fromString(uuid));
    }
}
