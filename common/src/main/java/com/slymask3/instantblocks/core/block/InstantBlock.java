package com.slymask3.instantblocks.core.block;

import com.slymask3.instantblocks.core.Core;
import com.slymask3.instantblocks.core.builder.Builder;
import com.slymask3.instantblocks.core.util.ClientHelper;
import com.slymask3.instantblocks.core.util.Helper;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;

public abstract class InstantBlock extends Block {
	public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;

	protected String createMessage, errorMessage, createVariable, errorVariable;
	private boolean isDirectional = false;
	private boolean has_screen = false;
	
	protected InstantBlock(Properties properties) {
		super(properties);
		this.createMessage = this.errorMessage = this.createVariable = this.errorVariable = "";
	}

	public void setCreateMessage(String message) {
		this.setCreateMessage(message,"");
	}

	public void setCreateMessage(String message, String variable) {
		this.createMessage = message;
		this.createVariable = variable.isEmpty() ? "" : ChatFormatting.GREEN + variable;
	}

	public void setErrorMessage(String message) {
		this.setErrorMessage(message,"");
	}

	public void setErrorMessage(String message, String variable) {
		this.errorMessage = message;
		this.errorVariable = variable.isEmpty() ? "" : ChatFormatting.RED + variable;
	}

	public void setDirectional(boolean directional) {
		this.isDirectional = directional;
		if(directional) {
			this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.EAST));
		}
	}

	public void hasScreen() {
		this.has_screen = true;
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(FACING);
	}

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		return this.isDirectional ? this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite()) : super.getStateForPlacement(context);
	}

	public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult result) {
		if(Builder.inProgress(world,pos)) {
			return InteractionResult.SUCCESS;
		}
		Core.CONFIG.reload();
		return !this.has_screen ? onActivate(world,pos,player,hand) : onActivateGui(world,pos,player,hand);
	}

	public boolean canActivate(Level world, BlockPos pos, Player player) {
		return true;
	}

	public boolean isEnabled() {
		return true;
	}

	private boolean isDisabled(Player player) {
		if(!this.isEnabled()) {
			Helper.sendMessage(player,Core.Strings.ERROR_DISABLED);
			return true;
		}
		return false;
	}

	private InteractionResult onActivate(Level world, BlockPos pos, Player player, InteractionHand hand) {
		if(Helper.isServer(world)) {
			if(hand == InteractionHand.OFF_HAND) {
				return InteractionResult.FAIL;
			}

			if(isDisabled(player)) {
				return InteractionResult.FAIL;
			}

			ItemStack is = player.getItemInHand(hand);
			if(Core.CONFIG.USE_WANDS()) {
				if(!Helper.isWand(is)) {
					Helper.sendMessage(player, Core.Strings.ERROR_WAND);
					return InteractionResult.FAIL;
				}
			}

			if(!canActivate(world,pos,player)) {
				return InteractionResult.FAIL;
			}

			return activate(world,pos,player);
		}
		return InteractionResult.FAIL;
	}

	private InteractionResult onActivateGui(Level world, BlockPos pos, Player player, InteractionHand hand) {
		if(hand == InteractionHand.OFF_HAND) {
			return InteractionResult.FAIL;
		}

		if(isDisabled(player)) {
			return InteractionResult.FAIL;
		}

		if(!canActivate(world,pos,player)) {
			return InteractionResult.FAIL;
		}

		ItemStack is = player.getItemInHand(hand);
		if(Core.CONFIG.USE_WANDS()) {
			if(!Helper.isWand(is)) {
				Helper.sendMessage(player, Core.Strings.ERROR_WAND);
				return InteractionResult.FAIL;
			}
		}

		if(Helper.isClient(player.getLevel()) && this.has_screen) {
			this.openScreen(player,pos);
		}

		return InteractionResult.SUCCESS;
	}

	public void openScreen(Player player, BlockPos pos) {}

	public InteractionResult activate(Level world, BlockPos pos, Player player) {
		if(build(world, pos.getX(), pos.getY(), pos.getZ(), player)) {
			postBuild(world, pos, player);
			return InteractionResult.SUCCESS;
		} else {
			return InteractionResult.FAIL;
		}
	}

	public boolean build(Level world, int x, int y, int z, Player player) {
		//build structure
		return true;
	}

	private void postBuild(Level world, BlockPos pos, Player player) {
		Helper.sendMessage(player,this.createMessage,this.createVariable);
		Helper.showParticles(world, pos, ClientHelper.Particles.GENERATE);
		Helper.giveExp(world, player, Core.CONFIG.XP_AMOUNT());
	}
}