package baspig.apis.utils.client;

import net.minecraft.client.MinecraftClient;

public class ClientInstance {

    public static boolean isUser(String user){
        MinecraftClient client = MinecraftClient.getInstance();
        return client.getSession() != null && client.getSession().getUsername().equals(user);
    }
}
