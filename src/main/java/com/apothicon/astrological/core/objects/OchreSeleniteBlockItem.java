package com.apothicon.astrological.core.objects;

import net.minecraft.ChatFormatting;
import net.minecraft.util.Util;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.level.block.Block;

import java.util.function.Consumer;

public class OchreSeleniteBlockItem extends BlockItem {

    public OchreSeleniteBlockItem(Block p_40565_, Properties p_40566_) {
        super(p_40565_, p_40566_);
    }
    
    @Override
    public void appendHoverText(ItemStack itemStack, TooltipContext context, TooltipDisplay display, Consumer<Component> builder, TooltipFlag tooltipFlag) {
        super.appendHoverText(itemStack, context, display, builder, tooltipFlag);
        builder.accept( Component.translatable(Util.makeDescriptionId("item", Identifier.parse("astrological:ochre_selenite"))).withStyle(ChatFormatting.YELLOW).withStyle(ChatFormatting.ITALIC));
    }
}
