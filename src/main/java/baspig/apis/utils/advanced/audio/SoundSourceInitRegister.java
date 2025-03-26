package baspig.apis.utils.advanced.audio;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

public class SoundSourceInitRegister {

    private static final Map<String, SourceData> data = new HashMap<>();

    protected static void soundTicker(){
        AtomicBoolean check = new AtomicBoolean(false);
        ServerTickEvents.END_WORLD_TICK.register(serverWorld -> {

            for(Map.Entry<String, SourceData> entry : data.entrySet()){
                String name = entry.getKey();
                SoundSourceInitRegister.SourceData data = entry.getValue();

                if(!check.get()){
                    new SoundSourceRun(data.getModId(), name, data.getEncryptKey());
                    check.set(true);
                }
            }
        });
    }

    protected static void register(String name, String MOD_ID, String encryptKey){
        data.put(name, new SourceData(MOD_ID, encryptKey));
    }

    private static class SourceData{
        private final String modId;
        private final String encryptKey;

        private SourceData(String MOD_ID, String encryptKey){
            modId = MOD_ID;
            this.encryptKey = encryptKey;
        }

        public String getModId() {
            return modId;
        }

        public String getEncryptKey() {
            return encryptKey;
        }
    }
}