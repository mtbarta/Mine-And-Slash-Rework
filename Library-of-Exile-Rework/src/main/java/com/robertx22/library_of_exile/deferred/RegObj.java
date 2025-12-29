package com.robertx22.library_of_exile.deferred;

import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class RegObj<T> {

    public RegObj(DeferredHolder<? super T, T> obj) {
        this.obj = obj;
    }

    private final DeferredHolder<? super T, T> obj;

    //DO NOT CALL THIS BEFORE THE FORGE'S REGISTRY EVENT FOR THIS TYPE HAS BEEN CALLED
    public T get() {
        return obj.get();
    }

    public DeferredHolder<? super T, T> getRegistryObject() {
        return obj;
    }

    public static <I> RegObj<I> register(String id, Supplier<I> sup, DeferredRegister<I> register) {
        DeferredHolder<I, I> reg = register.register(id, sup);
        RegObj<I> wrapper = new RegObj<I>(reg);
        return wrapper;
    }

}
