package baspig.apis.utils.advanced.audio;

import baspig.apis.utils.ModID;
import baspig.apis.utils.util.BP;
import baspig.apis.utils.util.LogColors;
import org.jetbrains.annotations.ApiStatus;

import java.util.Objects;

@ApiStatus.NonExtendable
public class IsRegAudio {
    public IsRegAudio(ModID id){
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        if (stackTrace.length > 3) {
            String clasWhoCallThis = stackTrace[3].getClassName();
            if(!clasWhoCallThis.equals("baspig.apis.utils.Baspig_utils") && !Objects.equals(id.toString(), "baspig_utils")){
                String r = LogColors.RESET;
                String red = LogColors.RED;
                String yellow = LogColors.YELLOW;
                String lightBlue = LogColors.BLUE_BRIGHT;

                BP.fancyLog("BaspigUtils.IsRegAudio."+yellow+"class"+r, lightBlue+"IsRegAudio"+r,
                        "        If you see this message "+yellow+"twice"+r+", it is a " +red+ "problem" +r+
                                   "\n        Other/s mod are calling it. \n It's being called by method: " +yellow+ clasWhoCallThis);
            }
        }
        SourceObjectLoader.initiate();
        SoundSourceInitRegister.soundTicker();
    }
}