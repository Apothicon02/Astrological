package com.Apothic0n.Inversia.core.objects;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.portal.PortalShape;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.Optional;

public abstract class InversiaBaseFireBlock extends Block {
    private static final int SECONDS_ON_FIRE = 8;
    private final float fireDamage;
    protected static final float AABB_OFFSET = 1.0F;
    protected static final VoxelShape DOWN_AABB = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 1.0D, 16.0D);

    public InversiaBaseFireBlock(BlockBehaviour.Properties p_49241_, float p_49242_) {
        super(p_49241_);
        this.fireDamage = p_49242_;
    }

    public BlockState getStateForPlacement(BlockPlaceContext p_49244_) {
        return getState(p_49244_.getLevel(), p_49244_.getClickedPos());
    }

    public static BlockState getState(BlockGetter p_49246_, BlockPos p_49247_) {
        BlockPos blockpos = p_49247_.below();
        BlockState blockstate = p_49246_.getBlockState(blockpos);
        return blockstate.equals(InversiaBlocks.SLEEP) ? InversiaBlocks.SLEEP_FIRE.get().defaultBlockState() : ((SleepFireBlock)InversiaBlocks.SLEEP_FIRE.get()).getStateForPlacement(p_49246_, p_49247_);
    }

    public VoxelShape getShape(BlockState p_49274_, BlockGetter p_49275_, BlockPos p_49276_, CollisionContext p_49277_) {
        return DOWN_AABB;
    }

    public void animateTick(BlockState p_220763_, Level p_220764_, BlockPos p_220765_, RandomSource p_220766_) {
        if (p_220766_.nextInt(24) == 0) {
            p_220764_.playLocalSound((double)p_220765_.getX() + 0.5D, (double)p_220765_.getY() + 0.5D, (double)p_220765_.getZ() + 0.5D, SoundEvents.GLOW_SQUID_AMBIENT, SoundSource.BLOCKS, 0.4F + p_220766_.nextFloat(), p_220766_.nextFloat() * 0.7F + 0.3F, false);
        }

        BlockPos blockpos = p_220765_.below();
        BlockState blockstate = p_220764_.getBlockState(blockpos);
        int randomNumber = (int)(Math.random()*(3)+1);
        double x = 0.1;
        if (randomNumber < 2) {
            x = -x;
        } else if (randomNumber < 3) {
            x = 0;
        }
        int randomNumber2 = (int)(Math.random()*(3)+1);
        double z = 0.1;
        if (randomNumber2 < 2) {
            z = -z;
        } else if (randomNumber2 < 3) {
            z = 0;
        }
        if (!this.canBurn(blockstate) && !blockstate.isFaceSturdy(p_220764_, blockpos, Direction.UP) && !blockstate.is(InversiaBlocks.SLEEP.get())) {
            if (this.canBurn(p_220764_.getBlockState(p_220765_.west()))) {
                for(int j = 0; j < 2; ++j) {
                    double d3 = (double)p_220765_.getX() + p_220766_.nextDouble() * (double)0.1F;
                    double d8 = (double)p_220765_.getY() + p_220766_.nextDouble();
                    double d13 = (double)p_220765_.getZ() + p_220766_.nextDouble();
                    p_220764_.addParticle(ParticleTypes.DRAGON_BREATH, d3, d8, d13, x, 0.05D, z);
                }
            }

            if (this.canBurn(p_220764_.getBlockState(p_220765_.east()))) {
                for(int k = 0; k < 2; ++k) {
                    double d4 = (double)(p_220765_.getX() + 1) - p_220766_.nextDouble() * (double)0.1F;
                    double d9 = (double)p_220765_.getY() + p_220766_.nextDouble();
                    double d14 = (double)p_220765_.getZ() + p_220766_.nextDouble();
                    p_220764_.addParticle(ParticleTypes.DRAGON_BREATH, d4, d9, d14, x, 0.05D, z);
                }
            }

            if (this.canBurn(p_220764_.getBlockState(p_220765_.north()))) {
                for(int l = 0; l < 2; ++l) {
                    double d5 = (double)p_220765_.getX() + p_220766_.nextDouble();
                    double d10 = (double)p_220765_.getY() + p_220766_.nextDouble();
                    double d15 = (double)p_220765_.getZ() + p_220766_.nextDouble() * (double)0.1F;
                    p_220764_.addParticle(ParticleTypes.DRAGON_BREATH, d5, d10, d15, x, 0.05D, z);
                }
            }

            if (this.canBurn(p_220764_.getBlockState(p_220765_.south()))) {
                for(int i1 = 0; i1 < 2; ++i1) {
                    double d6 = (double)p_220765_.getX() + p_220766_.nextDouble();
                    double d11 = (double)p_220765_.getY() + p_220766_.nextDouble();
                    double d16 = (double)(p_220765_.getZ() + 1) - p_220766_.nextDouble() * (double)0.1F;
                    p_220764_.addParticle(ParticleTypes.DRAGON_BREATH, d6, d11, d16, x, 0.05D, z);
                }
            }

            if (this.canBurn(p_220764_.getBlockState(p_220765_.above()))) {
                for(int j1 = 0; j1 < 2; ++j1) {
                    double d7 = (double)p_220765_.getX() + p_220766_.nextDouble();
                    double d12 = (double)(p_220765_.getY() + 1) - p_220766_.nextDouble() * (double)0.1F;
                    double d17 = (double)p_220765_.getZ() + p_220766_.nextDouble();
                    p_220764_.addParticle(ParticleTypes.DRAGON_BREATH, d7, d12, d17, x, 0.05D, z);
                }
            }
        } else {
            for(int i = 0; i < 3; ++i) {
                double d0 = (double)p_220765_.getX() + p_220766_.nextDouble();
                double d1 = (double)p_220765_.getY() + p_220766_.nextDouble() * 0.5D + 0.5D;
                double d2 = (double)p_220765_.getZ() + p_220766_.nextDouble();
                p_220764_.addParticle(ParticleTypes.DRAGON_BREATH, d0, d1, d2, x, 0.05D, z);
            }
        }

    }

    protected abstract boolean canBurn(BlockState p_49284_);

    public void entityInside(BlockState blockState, Level level, BlockPos blockPos, Entity entity) {
        if (!(entity instanceof LivingEntity) || entity.getFeetBlockState().is(this)) {
            entity.makeStuckInBlock(blockState, new Vec3((double)0.9F, 1.5D, (double)0.9F));
            if (level.isClientSide) {
                RandomSource randomsource = level.getRandom();
                boolean flag = entity.xOld != entity.getX() || entity.zOld != entity.getZ();
                if (flag && randomsource.nextBoolean()) {
                    level.addParticle(ParticleTypes.SNOWFLAKE, entity.getX(), (double)(blockPos.getY() + 1), entity.getZ(), (double)(Mth.randomBetween(randomsource, -1.0F, 1.0F) * 0.083333336F), (double)0.05F, (double)(Mth.randomBetween(randomsource, -1.0F, 1.0F) * 0.083333336F));
                }
            }
        }

        entity.setIsInPowderSnow(true);
        if (!level.isClientSide) {
            if (entity.isOnFire() && (level.getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING) || entity instanceof Player) && entity.mayInteract(level, blockPos)) {
                level.destroyBlock(blockPos, false);
            }

            entity.setSharedFlagOnFire(false);
        }
    }

    public void onPlace(BlockState p_49279_, Level p_49280_, BlockPos p_49281_, BlockState p_49282_, boolean p_49283_) {
        if (!p_49282_.is(p_49279_.getBlock())) {
            if (inPortalDimension(p_49280_)) {
                Optional<PortalShape> optional = PortalShape.findEmptyPortalShape(p_49280_, p_49281_, Direction.Axis.X);
                optional = net.minecraftforge.event.ForgeEventFactory.onTrySpawnPortal(p_49280_, p_49281_, optional);
                if (optional.isPresent()) {
                    optional.get().createPortalBlocks();
                    return;
                }
            }

            if (!p_49279_.canSurvive(p_49280_, p_49281_)) {
                p_49280_.removeBlock(p_49281_, false);
            }

        }
    }

    private static boolean inPortalDimension(Level p_49249_) {
        return p_49249_.dimension() == Level.OVERWORLD || p_49249_.dimension() == Level.NETHER;
    }

    protected void spawnDestroyParticles(Level p_152139_, Player p_152140_, BlockPos p_152141_, BlockState p_152142_) {
    }

    public void playerWillDestroy(Level p_49251_, BlockPos p_49252_, BlockState p_49253_, Player p_49254_) {
        if (!p_49251_.isClientSide()) {
            p_49251_.levelEvent((Player)null, 1009, p_49252_, 0);
        }

        super.playerWillDestroy(p_49251_, p_49252_, p_49253_, p_49254_);
    }

    public static boolean canBePlacedAt(Level p_49256_, BlockPos p_49257_, Direction p_49258_) {
        BlockState blockstate = p_49256_.getBlockState(p_49257_);
        if (!blockstate.isAir()) {
            return false;
        } else {
            return getState(p_49256_, p_49257_).canSurvive(p_49256_, p_49257_) || isPortal(p_49256_, p_49257_, p_49258_);
        }
    }

    private static boolean isPortal(Level p_49270_, BlockPos p_49271_, Direction p_49272_) {
        if (!inPortalDimension(p_49270_)) {
            return false;
        } else {
            BlockPos.MutableBlockPos blockpos$mutableblockpos = p_49271_.mutable();
            boolean flag = false;

            for(Direction direction : Direction.values()) {
                if (p_49270_.getBlockState(blockpos$mutableblockpos.set(p_49271_).move(direction)).isPortalFrame(p_49270_, blockpos$mutableblockpos)) {
                    flag = true;
                    break;
                }
            }

            if (!flag) {
                return false;
            } else {
                Direction.Axis direction$axis = p_49272_.getAxis().isHorizontal() ? p_49272_.getCounterClockWise().getAxis() : Direction.Plane.HORIZONTAL.getRandomAxis(p_49270_.random);
                return PortalShape.findEmptyPortalShape(p_49270_, p_49271_, direction$axis).isPresent();
            }
        }
    }
}
