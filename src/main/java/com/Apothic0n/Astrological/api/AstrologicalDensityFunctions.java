package com.Apothic0n.Astrological.api;

import com.Apothic0n.Astrological.Astrological;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.util.KeyDispatchDataCodec;
import net.minecraft.world.level.levelgen.DensityFunction;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.jetbrains.annotations.NotNull;
import org.joml.SimplexNoise;

public final class AstrologicalDensityFunctions {
    public static final DeferredRegister<MapCodec<? extends DensityFunction>> DENSITY_FUNCTION_TYPES = DeferredRegister.create(Registries.DENSITY_FUNCTION_TYPE, Astrological.MODID);

    public static final DeferredHolder<MapCodec<? extends DensityFunction>, ?> LOWER_ISLANDS_DENSITY_FUNCTION_TYPE = DENSITY_FUNCTION_TYPES.register("lower_islands", LowerIslands.CODEC::codec);

    public static void register(IEventBus eventBus) {
        DENSITY_FUNCTION_TYPES.register(eventBus);
    }

    protected record LowerIslands(DensityFunction input, boolean hollow) implements DensityFunction {
        private static final MapCodec<LowerIslands> DATA_CODEC = RecordCodecBuilder.mapCodec((data) -> {
            return data.group(DensityFunction.HOLDER_HELPER_CODEC.fieldOf("input").forGetter(LowerIslands::input), Codec.BOOL.fieldOf("hollow").forGetter(LowerIslands::hollow)).apply(data, LowerIslands::new);
        });
        public static final KeyDispatchDataCodec<LowerIslands> CODEC = AstrologicalDensityFunctions.makeCodec(DATA_CODEC);

        @Override
        public double compute(@NotNull FunctionContext context) {
            double floatingIsland;
            int x = context.blockX();
            int y = context.blockY();
            int z = context.blockZ();
            int mainOffset = (int) (SimplexNoise.noise(x * 0.005F, z * 0.005F)*25);
            int offset = -300+mainOffset;
            int offset2 = -400+mainOffset;
            if (y < 0) {
                double airPart = Math.abs(SimplexNoise.noise(x * 0.02F, (y+mainOffset) * 0.005F, z * 0.02F))*-1;
                double solidPart = Math.abs(SimplexNoise.noise(x * 0.0024F, (y+mainOffset) * 0.0016F, z * 0.0024F));
                if (y > -155) {
                    floatingIsland = Math.min(0.5 - (airPart + AstrologicalMath.gradient(y, 164 + offset, 292 + offset, -1, 1.5F)),
                            solidPart + (AstrologicalMath.gradient(y, 228 + offset, 356 + offset, 0.75F, 0.5F) - (2 * (0.1 + AstrologicalMath.gradient(y, 169 + offset, 259 + offset, 0.76F, 0F)))));
                } else {
                    floatingIsland = Math.min(0.5 - (airPart + AstrologicalMath.gradient(y, 164 + offset2, 292 + offset2, -1, 1.5F)),
                            solidPart + (AstrologicalMath.gradient(y, 228 + offset2, 356 + offset2, 0.75F, 0.5F) - (2 * (0.1 + AstrologicalMath.gradient(y, 169 + offset2, 259 + offset2, 0.76F, 0F)))));
                }
                double caves = 0;
                if (hollow()) {
                    caves = Math.min(0, ((floatingIsland - 0.2) * -5));
                }
                return caves + floatingIsland + input().compute(context);
            } else {
                return input().compute(context);
            }
        }

        @Override
        public void fillArray(double @NotNull [] densities, ContextProvider context) {
            context.fillAllDirectly(densities, this);
        }

        @Override
        public @NotNull DensityFunction mapAll(Visitor visitor) {
            return visitor.apply(new LowerIslands(this.input().mapAll(visitor), hollow()));
        }

        @Override
        public double minValue() {
            return -1875000d;
        }

        @Override
        public double maxValue() {
            return 1875000d;
        }

        @Override
        public KeyDispatchDataCodec<? extends DensityFunction> codec() {
            return CODEC;
        }
    }

    static <O> KeyDispatchDataCodec<O> makeCodec(MapCodec<O> codec) {
        return KeyDispatchDataCodec.of(codec);
    }
}
