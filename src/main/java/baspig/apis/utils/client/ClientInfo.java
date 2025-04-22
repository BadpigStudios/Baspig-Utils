package baspig.apis.utils.client;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.DownloadingTerrainScreen;
import net.minecraft.client.gui.screen.GameMenuScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;

import java.util.Objects;
import java.util.UUID;

@SuppressWarnings("unused")
public class ClientInfo {

    private final MinecraftClient firstToCompare;
    private MinecraftClient client;

    public ClientInfo(){
        client = MinecraftClient.getInstance();
        firstToCompare = MinecraftClient.getInstance();
    }

    public MinecraftClient getFirstTimeDeclaredClient(){
        return firstToCompare;
    }

    public void refreshData(){
        client = MinecraftClient.getInstance();
    }

    public String getUserName(){
        return client.getSession().getUsername();
    }

    public boolean isUser(String user){
        return client.getSession() != null && client.getSession().getUsername().equals(user);
    }

    private boolean isUUID_2(String uuid){
        return Objects.equals(String.valueOf(client.getSession().getUuidOrNull()), uuid);
    }

    public boolean isUUID(String uuid){
        return client.uuidEquals(UUID.fromString(uuid));
    }

    /**
     * This is a nested class that give some actions into the client instance.
     * @author Baspig_
     */
    public class Do{
        public void disconnect(){
            client.disconnect();
        }

        public void disconnect(Screen setScreen){
            client.disconnect(setScreen != null ? setScreen : new GameMenuScreen(true));
        }

        public void joinWorld(ClientWorld worldToEnter, DownloadingTerrainScreen.WorldEntryReason worldEntryReason){
            client.joinWorld(worldToEnter, worldEntryReason);
        }

        public void changeCameraTo(Entity cameraToEntity){
            client.setCameraEntity(cameraToEntity);
        }
    }

    public int getWindowX(){
        return client.getWindow().getX();
    }

    public int getWindowY(){
        return client.getWindow().getY();
    }

    public int getHeight(){
        return client.getWindow().getHeight();
    }

    public int getWidth(){
        return client.getWindow().getWidth();
    }

    public int getRefreshRate(){
        return client.getWindow().getRefreshRate();
    }

    public int getFPS(){
        return client.getCurrentFps();
    }

    public boolean finishedLoading(){
        return client.isFinishedLoading();
    }

    public boolean isSingleplayer() {
        return client.isInSingleplayer();
    }

}
