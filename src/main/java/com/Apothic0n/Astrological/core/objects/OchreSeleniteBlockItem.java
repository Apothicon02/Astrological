package com.Apothic0n.Astrological.core.objects;

import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

import javax.annotation.Nullable;
import java.util.List;

public class OchreSeleniteBlockItem extends BlockItem {

    public OchreSeleniteBlockItem(Block p_40565_, Properties p_40566_) {
        super(p_40565_, p_40566_);
    }
    
    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> text, TooltipFlag flag) {
        super.appendHoverText(itemStack, level, text, flag);
        text.add(1, Component.translatable(Util.makeDescriptionId("item", new ResourceLocation("astrological:ochre_selenite"))).withStyle(ChatFormatting.YELLOW).withStyle(ChatFormatting.ITALIC));
    }
}
