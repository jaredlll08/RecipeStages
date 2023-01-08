package com.blamejared.recipestages;

import net.minecraftforge.fml.util.thread.EffectiveSide;

import javax.annotation.Nullable;
import java.util.concurrent.Callable;
import java.util.function.Supplier;

public class RecipeStagesUtil {
    
    @SuppressWarnings("unchecked")
    public static <T> T cast(Object o) {
        
        return (T) o;
    }
    
    @Nullable
    public static <T> T callForSide(@Nullable Supplier<Callable<T>> client, @Nullable Supplier<Callable<T>> server) {
        
        try {
            
            if(EffectiveSide.get().isClient() && client != null) {
                
                return client.get().call();
            } else if(EffectiveSide.get().isServer() && server != null) {
                
                return server.get().call();
            }
        } catch(final Exception e) {
            
            throw new RuntimeException(e);
        }
        
        return null;
    }
    
}
