package baspig.apis.utils.advanced.audio;

@Deprecated
@SuppressWarnings(value = "all")
public class IsRegAudio {
    public IsRegAudio(){
        SourceObjectLoader.initiate();
        SoundSourceInitRegister.soundTicker();
    }
}