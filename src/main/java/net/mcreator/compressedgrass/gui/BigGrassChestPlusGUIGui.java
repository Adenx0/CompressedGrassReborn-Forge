
package net.mcreator.compressedgrass.gui;

import net.minecraftforge.items.SlotItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.fml.network.IContainerFactory;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.DeferredWorkQueue;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.api.distmarker.Dist;

import net.minecraft.world.World;
import net.minecraft.util.math.BlockPos;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.item.ItemStack;
import net.minecraft.inventory.container.Slot;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.Container;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.client.gui.ScreenManager;

import net.mcreator.compressedgrass.CompressedGrassModElements;
import net.mcreator.compressedgrass.CompressedGrassMod;

import java.util.function.Supplier;
import java.util.Map;
import java.util.HashMap;

@CompressedGrassModElements.ModElement.Tag
public class BigGrassChestPlusGUIGui extends CompressedGrassModElements.ModElement {
	public static HashMap guistate = new HashMap();
	private static ContainerType<GuiContainerMod> containerType = null;

	public BigGrassChestPlusGUIGui(CompressedGrassModElements instance) {
		super(instance, 312);
		elements.addNetworkMessage(ButtonPressedMessage.class, ButtonPressedMessage::buffer, ButtonPressedMessage::new,
				ButtonPressedMessage::handler);
		elements.addNetworkMessage(GUISlotChangedMessage.class, GUISlotChangedMessage::buffer, GUISlotChangedMessage::new,
				GUISlotChangedMessage::handler);
		containerType = new ContainerType<>(new GuiContainerModFactory());
		FMLJavaModLoadingContext.get().getModEventBus().register(new ContainerRegisterHandler());
	}

	private static class ContainerRegisterHandler {
		@SubscribeEvent
		public void registerContainer(RegistryEvent.Register<ContainerType<?>> event) {
			event.getRegistry().register(containerType.setRegistryName("big_grass_chest_plus_gui"));
		}
	}

	@OnlyIn(Dist.CLIENT)
	public void initElements() {
		DeferredWorkQueue.runLater(() -> ScreenManager.registerFactory(containerType, BigGrassChestPlusGUIGuiWindow::new));
	}

	public static class GuiContainerModFactory implements IContainerFactory {
		public GuiContainerMod create(int id, PlayerInventory inv, PacketBuffer extraData) {
			return new GuiContainerMod(id, inv, extraData);
		}
	}

	public static class GuiContainerMod extends Container implements Supplier<Map<Integer, Slot>> {
		World world;
		PlayerEntity entity;
		int x, y, z;
		private IItemHandler internal;
		private Map<Integer, Slot> customSlots = new HashMap<>();
		private boolean bound = false;

		public GuiContainerMod(int id, PlayerInventory inv, PacketBuffer extraData) {
			super(containerType, id);
			this.entity = inv.player;
			this.world = inv.player.world;
			this.internal = new ItemStackHandler(166);
			BlockPos pos = null;
			if (extraData != null) {
				pos = extraData.readBlockPos();
				this.x = pos.getX();
				this.y = pos.getY();
				this.z = pos.getZ();
			}
			if (pos != null) {
				if (extraData.readableBytes() == 1) { // bound to item
					byte hand = extraData.readByte();
					ItemStack itemstack;
					if (hand == 0)
						itemstack = this.entity.getHeldItemMainhand();
					else
						itemstack = this.entity.getHeldItemOffhand();
					itemstack.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null).ifPresent(capability -> {
						this.internal = capability;
						this.bound = true;
					});
				} else if (extraData.readableBytes() > 1) {
					extraData.readByte(); // drop padding
					Entity entity = world.getEntityByID(extraData.readVarInt());
					if (entity != null)
						entity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null).ifPresent(capability -> {
							this.internal = capability;
							this.bound = true;
						});
				} else { // might be bound to block
					TileEntity ent = inv.player != null ? inv.player.world.getTileEntity(pos) : null;
					if (ent != null) {
						ent.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null).ifPresent(capability -> {
							this.internal = capability;
							this.bound = true;
						});
					}
				}
			}
			this.customSlots.put(0, this.addSlot(new SlotItemHandler(internal, 0, 5, 6) {
			}));
			this.customSlots.put(1, this.addSlot(new SlotItemHandler(internal, 1, 23, 6) {
			}));
			this.customSlots.put(2, this.addSlot(new SlotItemHandler(internal, 2, 41, 6) {
			}));
			this.customSlots.put(3, this.addSlot(new SlotItemHandler(internal, 3, 59, 6) {
			}));
			this.customSlots.put(4, this.addSlot(new SlotItemHandler(internal, 4, 77, 6) {
			}));
			this.customSlots.put(5, this.addSlot(new SlotItemHandler(internal, 5, 95, 6) {
			}));
			this.customSlots.put(6, this.addSlot(new SlotItemHandler(internal, 6, 113, 6) {
			}));
			this.customSlots.put(7, this.addSlot(new SlotItemHandler(internal, 7, 131, 6) {
			}));
			this.customSlots.put(8, this.addSlot(new SlotItemHandler(internal, 8, 149, 6) {
			}));
			this.customSlots.put(9, this.addSlot(new SlotItemHandler(internal, 9, 167, 6) {
			}));
			this.customSlots.put(10, this.addSlot(new SlotItemHandler(internal, 10, 185, 6) {
			}));
			this.customSlots.put(11, this.addSlot(new SlotItemHandler(internal, 11, 203, 6) {
			}));
			this.customSlots.put(12, this.addSlot(new SlotItemHandler(internal, 12, 221, 6) {
			}));
			this.customSlots.put(13, this.addSlot(new SlotItemHandler(internal, 13, 239, 6) {
			}));
			this.customSlots.put(14, this.addSlot(new SlotItemHandler(internal, 14, 257, 6) {
			}));
			this.customSlots.put(15, this.addSlot(new SlotItemHandler(internal, 15, 275, 6) {
			}));
			this.customSlots.put(16, this.addSlot(new SlotItemHandler(internal, 16, 293, 6) {
			}));
			this.customSlots.put(17, this.addSlot(new SlotItemHandler(internal, 17, 311, 6) {
			}));
			this.customSlots.put(18, this.addSlot(new SlotItemHandler(internal, 18, 329, 6) {
			}));
			this.customSlots.put(19, this.addSlot(new SlotItemHandler(internal, 19, 347, 6) {
			}));
			this.customSlots.put(20, this.addSlot(new SlotItemHandler(internal, 20, 365, 6) {
			}));
			this.customSlots.put(21, this.addSlot(new SlotItemHandler(internal, 21, 5, 24) {
			}));
			this.customSlots.put(22, this.addSlot(new SlotItemHandler(internal, 22, 23, 24) {
			}));
			this.customSlots.put(23, this.addSlot(new SlotItemHandler(internal, 23, 41, 24) {
			}));
			this.customSlots.put(24, this.addSlot(new SlotItemHandler(internal, 24, 59, 24) {
			}));
			this.customSlots.put(25, this.addSlot(new SlotItemHandler(internal, 25, 77, 24) {
			}));
			this.customSlots.put(26, this.addSlot(new SlotItemHandler(internal, 26, 95, 24) {
			}));
			this.customSlots.put(27, this.addSlot(new SlotItemHandler(internal, 27, 113, 24) {
			}));
			this.customSlots.put(28, this.addSlot(new SlotItemHandler(internal, 28, 131, 24) {
			}));
			this.customSlots.put(29, this.addSlot(new SlotItemHandler(internal, 29, 149, 24) {
			}));
			this.customSlots.put(30, this.addSlot(new SlotItemHandler(internal, 30, 167, 24) {
			}));
			this.customSlots.put(31, this.addSlot(new SlotItemHandler(internal, 31, 185, 24) {
			}));
			this.customSlots.put(32, this.addSlot(new SlotItemHandler(internal, 32, 203, 24) {
			}));
			this.customSlots.put(33, this.addSlot(new SlotItemHandler(internal, 33, 221, 24) {
			}));
			this.customSlots.put(34, this.addSlot(new SlotItemHandler(internal, 34, 239, 24) {
			}));
			this.customSlots.put(35, this.addSlot(new SlotItemHandler(internal, 35, 257, 24) {
			}));
			this.customSlots.put(36, this.addSlot(new SlotItemHandler(internal, 36, 275, 24) {
			}));
			this.customSlots.put(37, this.addSlot(new SlotItemHandler(internal, 37, 293, 24) {
			}));
			this.customSlots.put(38, this.addSlot(new SlotItemHandler(internal, 38, 311, 24) {
			}));
			this.customSlots.put(39, this.addSlot(new SlotItemHandler(internal, 39, 329, 24) {
			}));
			this.customSlots.put(40, this.addSlot(new SlotItemHandler(internal, 40, 347, 24) {
			}));
			this.customSlots.put(41, this.addSlot(new SlotItemHandler(internal, 41, 365, 24) {
			}));
			this.customSlots.put(42, this.addSlot(new SlotItemHandler(internal, 42, 5, 42) {
			}));
			this.customSlots.put(43, this.addSlot(new SlotItemHandler(internal, 43, 23, 42) {
			}));
			this.customSlots.put(44, this.addSlot(new SlotItemHandler(internal, 44, 41, 42) {
			}));
			this.customSlots.put(45, this.addSlot(new SlotItemHandler(internal, 45, 59, 42) {
			}));
			this.customSlots.put(46, this.addSlot(new SlotItemHandler(internal, 46, 77, 42) {
			}));
			this.customSlots.put(47, this.addSlot(new SlotItemHandler(internal, 47, 95, 42) {
			}));
			this.customSlots.put(48, this.addSlot(new SlotItemHandler(internal, 48, 113, 42) {
			}));
			this.customSlots.put(49, this.addSlot(new SlotItemHandler(internal, 49, 131, 42) {
			}));
			this.customSlots.put(50, this.addSlot(new SlotItemHandler(internal, 50, 149, 42) {
			}));
			this.customSlots.put(51, this.addSlot(new SlotItemHandler(internal, 51, 167, 42) {
			}));
			this.customSlots.put(52, this.addSlot(new SlotItemHandler(internal, 52, 185, 42) {
			}));
			this.customSlots.put(53, this.addSlot(new SlotItemHandler(internal, 53, 203, 42) {
			}));
			this.customSlots.put(54, this.addSlot(new SlotItemHandler(internal, 54, 221, 42) {
			}));
			this.customSlots.put(55, this.addSlot(new SlotItemHandler(internal, 55, 239, 42) {
			}));
			this.customSlots.put(56, this.addSlot(new SlotItemHandler(internal, 56, 257, 42) {
			}));
			this.customSlots.put(57, this.addSlot(new SlotItemHandler(internal, 57, 275, 42) {
			}));
			this.customSlots.put(58, this.addSlot(new SlotItemHandler(internal, 58, 293, 42) {
			}));
			this.customSlots.put(59, this.addSlot(new SlotItemHandler(internal, 59, 311, 42) {
			}));
			this.customSlots.put(60, this.addSlot(new SlotItemHandler(internal, 60, 329, 42) {
			}));
			this.customSlots.put(61, this.addSlot(new SlotItemHandler(internal, 61, 347, 42) {
			}));
			this.customSlots.put(62, this.addSlot(new SlotItemHandler(internal, 62, 365, 42) {
			}));
			this.customSlots.put(63, this.addSlot(new SlotItemHandler(internal, 63, 5, 60) {
			}));
			this.customSlots.put(64, this.addSlot(new SlotItemHandler(internal, 64, 23, 60) {
			}));
			this.customSlots.put(65, this.addSlot(new SlotItemHandler(internal, 65, 41, 60) {
			}));
			this.customSlots.put(66, this.addSlot(new SlotItemHandler(internal, 66, 59, 60) {
			}));
			this.customSlots.put(67, this.addSlot(new SlotItemHandler(internal, 67, 77, 60) {
			}));
			this.customSlots.put(68, this.addSlot(new SlotItemHandler(internal, 68, 95, 60) {
			}));
			this.customSlots.put(69, this.addSlot(new SlotItemHandler(internal, 69, 113, 60) {
			}));
			this.customSlots.put(70, this.addSlot(new SlotItemHandler(internal, 70, 131, 60) {
			}));
			this.customSlots.put(71, this.addSlot(new SlotItemHandler(internal, 71, 149, 60) {
			}));
			this.customSlots.put(72, this.addSlot(new SlotItemHandler(internal, 72, 167, 60) {
			}));
			this.customSlots.put(73, this.addSlot(new SlotItemHandler(internal, 73, 185, 60) {
			}));
			this.customSlots.put(74, this.addSlot(new SlotItemHandler(internal, 74, 203, 60) {
			}));
			this.customSlots.put(75, this.addSlot(new SlotItemHandler(internal, 75, 221, 60) {
			}));
			this.customSlots.put(76, this.addSlot(new SlotItemHandler(internal, 76, 239, 60) {
			}));
			this.customSlots.put(77, this.addSlot(new SlotItemHandler(internal, 77, 257, 60) {
			}));
			this.customSlots.put(78, this.addSlot(new SlotItemHandler(internal, 78, 275, 60) {
			}));
			this.customSlots.put(79, this.addSlot(new SlotItemHandler(internal, 79, 293, 60) {
			}));
			this.customSlots.put(80, this.addSlot(new SlotItemHandler(internal, 80, 311, 60) {
			}));
			this.customSlots.put(81, this.addSlot(new SlotItemHandler(internal, 81, 329, 60) {
			}));
			this.customSlots.put(82, this.addSlot(new SlotItemHandler(internal, 82, 347, 60) {
			}));
			this.customSlots.put(83, this.addSlot(new SlotItemHandler(internal, 83, 365, 60) {
			}));
			this.customSlots.put(84, this.addSlot(new SlotItemHandler(internal, 84, 5, 78) {
			}));
			this.customSlots.put(85, this.addSlot(new SlotItemHandler(internal, 85, 23, 78) {
			}));
			this.customSlots.put(86, this.addSlot(new SlotItemHandler(internal, 86, 41, 78) {
			}));
			this.customSlots.put(87, this.addSlot(new SlotItemHandler(internal, 87, 59, 78) {
			}));
			this.customSlots.put(88, this.addSlot(new SlotItemHandler(internal, 88, 77, 78) {
			}));
			this.customSlots.put(89, this.addSlot(new SlotItemHandler(internal, 89, 95, 78) {
			}));
			this.customSlots.put(90, this.addSlot(new SlotItemHandler(internal, 90, 113, 78) {
			}));
			this.customSlots.put(91, this.addSlot(new SlotItemHandler(internal, 91, 131, 78) {
			}));
			this.customSlots.put(92, this.addSlot(new SlotItemHandler(internal, 92, 149, 78) {
			}));
			this.customSlots.put(93, this.addSlot(new SlotItemHandler(internal, 93, 167, 78) {
			}));
			this.customSlots.put(94, this.addSlot(new SlotItemHandler(internal, 94, 185, 78) {
			}));
			this.customSlots.put(95, this.addSlot(new SlotItemHandler(internal, 95, 203, 78) {
			}));
			this.customSlots.put(96, this.addSlot(new SlotItemHandler(internal, 96, 221, 78) {
			}));
			this.customSlots.put(97, this.addSlot(new SlotItemHandler(internal, 97, 239, 78) {
			}));
			this.customSlots.put(98, this.addSlot(new SlotItemHandler(internal, 98, 257, 78) {
			}));
			this.customSlots.put(99, this.addSlot(new SlotItemHandler(internal, 99, 275, 78) {
			}));
			this.customSlots.put(100, this.addSlot(new SlotItemHandler(internal, 100, 293, 78) {
			}));
			this.customSlots.put(101, this.addSlot(new SlotItemHandler(internal, 101, 311, 78) {
			}));
			this.customSlots.put(102, this.addSlot(new SlotItemHandler(internal, 102, 329, 78) {
			}));
			this.customSlots.put(103, this.addSlot(new SlotItemHandler(internal, 103, 347, 78) {
			}));
			this.customSlots.put(104, this.addSlot(new SlotItemHandler(internal, 104, 365, 78) {
			}));
			this.customSlots.put(105, this.addSlot(new SlotItemHandler(internal, 105, 5, 96) {
			}));
			this.customSlots.put(106, this.addSlot(new SlotItemHandler(internal, 106, 23, 96) {
			}));
			this.customSlots.put(107, this.addSlot(new SlotItemHandler(internal, 107, 41, 96) {
			}));
			this.customSlots.put(108, this.addSlot(new SlotItemHandler(internal, 108, 59, 96) {
			}));
			this.customSlots.put(109, this.addSlot(new SlotItemHandler(internal, 109, 77, 96) {
			}));
			this.customSlots.put(110, this.addSlot(new SlotItemHandler(internal, 110, 95, 96) {
			}));
			this.customSlots.put(111, this.addSlot(new SlotItemHandler(internal, 111, 113, 96) {
			}));
			this.customSlots.put(112, this.addSlot(new SlotItemHandler(internal, 112, 131, 96) {
			}));
			this.customSlots.put(113, this.addSlot(new SlotItemHandler(internal, 113, 149, 96) {
			}));
			this.customSlots.put(114, this.addSlot(new SlotItemHandler(internal, 114, 167, 96) {
			}));
			this.customSlots.put(115, this.addSlot(new SlotItemHandler(internal, 115, 185, 96) {
			}));
			this.customSlots.put(116, this.addSlot(new SlotItemHandler(internal, 116, 203, 96) {
			}));
			this.customSlots.put(117, this.addSlot(new SlotItemHandler(internal, 117, 221, 96) {
			}));
			this.customSlots.put(118, this.addSlot(new SlotItemHandler(internal, 118, 239, 96) {
			}));
			this.customSlots.put(119, this.addSlot(new SlotItemHandler(internal, 119, 257, 96) {
			}));
			this.customSlots.put(120, this.addSlot(new SlotItemHandler(internal, 120, 275, 96) {
			}));
			this.customSlots.put(121, this.addSlot(new SlotItemHandler(internal, 121, 293, 96) {
			}));
			this.customSlots.put(122, this.addSlot(new SlotItemHandler(internal, 122, 311, 96) {
			}));
			this.customSlots.put(123, this.addSlot(new SlotItemHandler(internal, 123, 329, 96) {
			}));
			this.customSlots.put(124, this.addSlot(new SlotItemHandler(internal, 124, 347, 96) {
			}));
			this.customSlots.put(125, this.addSlot(new SlotItemHandler(internal, 125, 365, 96) {
			}));
			this.customSlots.put(126, this.addSlot(new SlotItemHandler(internal, 126, 5, 114) {
			}));
			this.customSlots.put(127, this.addSlot(new SlotItemHandler(internal, 127, 23, 114) {
			}));
			this.customSlots.put(128, this.addSlot(new SlotItemHandler(internal, 128, 41, 114) {
			}));
			this.customSlots.put(129, this.addSlot(new SlotItemHandler(internal, 129, 59, 114) {
			}));
			this.customSlots.put(130, this.addSlot(new SlotItemHandler(internal, 130, 77, 114) {
			}));
			this.customSlots.put(131, this.addSlot(new SlotItemHandler(internal, 131, 293, 114) {
			}));
			this.customSlots.put(132, this.addSlot(new SlotItemHandler(internal, 132, 311, 114) {
			}));
			this.customSlots.put(133, this.addSlot(new SlotItemHandler(internal, 133, 329, 114) {
			}));
			this.customSlots.put(134, this.addSlot(new SlotItemHandler(internal, 134, 347, 114) {
			}));
			this.customSlots.put(135, this.addSlot(new SlotItemHandler(internal, 135, 365, 114) {
			}));
			this.customSlots.put(136, this.addSlot(new SlotItemHandler(internal, 136, 5, 132) {
			}));
			this.customSlots.put(137, this.addSlot(new SlotItemHandler(internal, 137, 23, 132) {
			}));
			this.customSlots.put(138, this.addSlot(new SlotItemHandler(internal, 138, 41, 132) {
			}));
			this.customSlots.put(139, this.addSlot(new SlotItemHandler(internal, 139, 59, 132) {
			}));
			this.customSlots.put(140, this.addSlot(new SlotItemHandler(internal, 140, 77, 132) {
			}));
			this.customSlots.put(141, this.addSlot(new SlotItemHandler(internal, 141, 293, 132) {
			}));
			this.customSlots.put(142, this.addSlot(new SlotItemHandler(internal, 142, 311, 132) {
			}));
			this.customSlots.put(143, this.addSlot(new SlotItemHandler(internal, 143, 329, 132) {
			}));
			this.customSlots.put(144, this.addSlot(new SlotItemHandler(internal, 144, 347, 132) {
			}));
			this.customSlots.put(145, this.addSlot(new SlotItemHandler(internal, 145, 365, 132) {
			}));
			this.customSlots.put(146, this.addSlot(new SlotItemHandler(internal, 146, 5, 150) {
			}));
			this.customSlots.put(147, this.addSlot(new SlotItemHandler(internal, 147, 23, 150) {
			}));
			this.customSlots.put(148, this.addSlot(new SlotItemHandler(internal, 148, 41, 150) {
			}));
			this.customSlots.put(149, this.addSlot(new SlotItemHandler(internal, 149, 59, 150) {
			}));
			this.customSlots.put(150, this.addSlot(new SlotItemHandler(internal, 150, 77, 150) {
			}));
			this.customSlots.put(151, this.addSlot(new SlotItemHandler(internal, 151, 293, 150) {
			}));
			this.customSlots.put(152, this.addSlot(new SlotItemHandler(internal, 152, 311, 150) {
			}));
			this.customSlots.put(153, this.addSlot(new SlotItemHandler(internal, 153, 329, 150) {
			}));
			this.customSlots.put(154, this.addSlot(new SlotItemHandler(internal, 154, 347, 150) {
			}));
			this.customSlots.put(155, this.addSlot(new SlotItemHandler(internal, 155, 365, 150) {
			}));
			this.customSlots.put(156, this.addSlot(new SlotItemHandler(internal, 156, 5, 168) {
			}));
			this.customSlots.put(157, this.addSlot(new SlotItemHandler(internal, 157, 23, 168) {
			}));
			this.customSlots.put(158, this.addSlot(new SlotItemHandler(internal, 158, 41, 168) {
			}));
			this.customSlots.put(159, this.addSlot(new SlotItemHandler(internal, 159, 59, 168) {
			}));
			this.customSlots.put(160, this.addSlot(new SlotItemHandler(internal, 160, 77, 168) {
			}));
			this.customSlots.put(161, this.addSlot(new SlotItemHandler(internal, 161, 293, 168) {
			}));
			this.customSlots.put(162, this.addSlot(new SlotItemHandler(internal, 162, 311, 168) {
			}));
			this.customSlots.put(163, this.addSlot(new SlotItemHandler(internal, 163, 329, 168) {
			}));
			this.customSlots.put(164, this.addSlot(new SlotItemHandler(internal, 164, 347, 168) {
			}));
			this.customSlots.put(165, this.addSlot(new SlotItemHandler(internal, 165, 365, 168) {
			}));
			int si;
			int sj;
			for (si = 0; si < 3; ++si)
				for (sj = 0; sj < 9; ++sj)
					this.addSlot(new Slot(inv, sj + (si + 1) * 9, 106 + 8 + sj * 18, 31 + 84 + si * 18));
			for (si = 0; si < 9; ++si)
				this.addSlot(new Slot(inv, si, 106 + 8 + si * 18, 31 + 142));
		}

		public Map<Integer, Slot> get() {
			return customSlots;
		}

		@Override
		public boolean canInteractWith(PlayerEntity player) {
			return true;
		}

		@Override
		public ItemStack transferStackInSlot(PlayerEntity playerIn, int index) {
			ItemStack itemstack = ItemStack.EMPTY;
			Slot slot = (Slot) this.inventorySlots.get(index);
			if (slot != null && slot.getHasStack()) {
				ItemStack itemstack1 = slot.getStack();
				itemstack = itemstack1.copy();
				if (index < 166) {
					if (!this.mergeItemStack(itemstack1, 166, this.inventorySlots.size(), true)) {
						return ItemStack.EMPTY;
					}
					slot.onSlotChange(itemstack1, itemstack);
				} else if (!this.mergeItemStack(itemstack1, 0, 166, false)) {
					if (index < 166 + 27) {
						if (!this.mergeItemStack(itemstack1, 166 + 27, this.inventorySlots.size(), true)) {
							return ItemStack.EMPTY;
						}
					} else {
						if (!this.mergeItemStack(itemstack1, 166, 166 + 27, false)) {
							return ItemStack.EMPTY;
						}
					}
					return ItemStack.EMPTY;
				}
				if (itemstack1.getCount() == 0) {
					slot.putStack(ItemStack.EMPTY);
				} else {
					slot.onSlotChanged();
				}
				if (itemstack1.getCount() == itemstack.getCount()) {
					return ItemStack.EMPTY;
				}
				slot.onTake(playerIn, itemstack1);
			}
			return itemstack;
		}

		@Override /** 
					* Merges provided ItemStack with the first avaliable one in the container/player inventor between minIndex (included) and maxIndex (excluded). Args : stack, minIndex, maxIndex, negativDirection. /!\ the Container implementation do not check if the item is valid for the slot
					*/
		protected boolean mergeItemStack(ItemStack stack, int startIndex, int endIndex, boolean reverseDirection) {
			boolean flag = false;
			int i = startIndex;
			if (reverseDirection) {
				i = endIndex - 1;
			}
			if (stack.isStackable()) {
				while (!stack.isEmpty()) {
					if (reverseDirection) {
						if (i < startIndex) {
							break;
						}
					} else if (i >= endIndex) {
						break;
					}
					Slot slot = this.inventorySlots.get(i);
					ItemStack itemstack = slot.getStack();
					if (slot.isItemValid(itemstack) && !itemstack.isEmpty() && areItemsAndTagsEqual(stack, itemstack)) {
						int j = itemstack.getCount() + stack.getCount();
						int maxSize = Math.min(slot.getSlotStackLimit(), stack.getMaxStackSize());
						if (j <= maxSize) {
							stack.setCount(0);
							itemstack.setCount(j);
							slot.putStack(itemstack);
							flag = true;
						} else if (itemstack.getCount() < maxSize) {
							stack.shrink(maxSize - itemstack.getCount());
							itemstack.setCount(maxSize);
							slot.putStack(itemstack);
							flag = true;
						}
					}
					if (reverseDirection) {
						--i;
					} else {
						++i;
					}
				}
			}
			if (!stack.isEmpty()) {
				if (reverseDirection) {
					i = endIndex - 1;
				} else {
					i = startIndex;
				}
				while (true) {
					if (reverseDirection) {
						if (i < startIndex) {
							break;
						}
					} else if (i >= endIndex) {
						break;
					}
					Slot slot1 = this.inventorySlots.get(i);
					ItemStack itemstack1 = slot1.getStack();
					if (itemstack1.isEmpty() && slot1.isItemValid(stack)) {
						if (stack.getCount() > slot1.getSlotStackLimit()) {
							slot1.putStack(stack.split(slot1.getSlotStackLimit()));
						} else {
							slot1.putStack(stack.split(stack.getCount()));
						}
						slot1.onSlotChanged();
						flag = true;
						break;
					}
					if (reverseDirection) {
						--i;
					} else {
						++i;
					}
				}
			}
			return flag;
		}

		@Override
		public void onContainerClosed(PlayerEntity playerIn) {
			super.onContainerClosed(playerIn);
			if (!bound && (playerIn instanceof ServerPlayerEntity)) {
				if (!playerIn.isAlive() || playerIn instanceof ServerPlayerEntity && ((ServerPlayerEntity) playerIn).hasDisconnected()) {
					for (int j = 0; j < internal.getSlots(); ++j) {
						playerIn.dropItem(internal.extractItem(j, internal.getStackInSlot(j).getCount(), false), false);
					}
				} else {
					for (int i = 0; i < internal.getSlots(); ++i) {
						playerIn.inventory.placeItemBackInInventory(playerIn.world,
								internal.extractItem(i, internal.getStackInSlot(i).getCount(), false));
					}
				}
			}
		}

		private void slotChanged(int slotid, int ctype, int meta) {
			if (this.world != null && this.world.isRemote()) {
				CompressedGrassMod.PACKET_HANDLER.sendToServer(new GUISlotChangedMessage(slotid, x, y, z, ctype, meta));
				handleSlotAction(entity, slotid, ctype, meta, x, y, z);
			}
		}
	}

	public static class ButtonPressedMessage {
		int buttonID, x, y, z;

		public ButtonPressedMessage(PacketBuffer buffer) {
			this.buttonID = buffer.readInt();
			this.x = buffer.readInt();
			this.y = buffer.readInt();
			this.z = buffer.readInt();
		}

		public ButtonPressedMessage(int buttonID, int x, int y, int z) {
			this.buttonID = buttonID;
			this.x = x;
			this.y = y;
			this.z = z;
		}

		public static void buffer(ButtonPressedMessage message, PacketBuffer buffer) {
			buffer.writeInt(message.buttonID);
			buffer.writeInt(message.x);
			buffer.writeInt(message.y);
			buffer.writeInt(message.z);
		}

		public static void handler(ButtonPressedMessage message, Supplier<NetworkEvent.Context> contextSupplier) {
			NetworkEvent.Context context = contextSupplier.get();
			context.enqueueWork(() -> {
				PlayerEntity entity = context.getSender();
				int buttonID = message.buttonID;
				int x = message.x;
				int y = message.y;
				int z = message.z;
				handleButtonAction(entity, buttonID, x, y, z);
			});
			context.setPacketHandled(true);
		}
	}

	public static class GUISlotChangedMessage {
		int slotID, x, y, z, changeType, meta;

		public GUISlotChangedMessage(int slotID, int x, int y, int z, int changeType, int meta) {
			this.slotID = slotID;
			this.x = x;
			this.y = y;
			this.z = z;
			this.changeType = changeType;
			this.meta = meta;
		}

		public GUISlotChangedMessage(PacketBuffer buffer) {
			this.slotID = buffer.readInt();
			this.x = buffer.readInt();
			this.y = buffer.readInt();
			this.z = buffer.readInt();
			this.changeType = buffer.readInt();
			this.meta = buffer.readInt();
		}

		public static void buffer(GUISlotChangedMessage message, PacketBuffer buffer) {
			buffer.writeInt(message.slotID);
			buffer.writeInt(message.x);
			buffer.writeInt(message.y);
			buffer.writeInt(message.z);
			buffer.writeInt(message.changeType);
			buffer.writeInt(message.meta);
		}

		public static void handler(GUISlotChangedMessage message, Supplier<NetworkEvent.Context> contextSupplier) {
			NetworkEvent.Context context = contextSupplier.get();
			context.enqueueWork(() -> {
				PlayerEntity entity = context.getSender();
				int slotID = message.slotID;
				int changeType = message.changeType;
				int meta = message.meta;
				int x = message.x;
				int y = message.y;
				int z = message.z;
				handleSlotAction(entity, slotID, changeType, meta, x, y, z);
			});
			context.setPacketHandled(true);
		}
	}

	static void handleButtonAction(PlayerEntity entity, int buttonID, int x, int y, int z) {
		World world = entity.world;
		// security measure to prevent arbitrary chunk generation
		if (!world.isBlockLoaded(new BlockPos(x, y, z)))
			return;
	}

	private static void handleSlotAction(PlayerEntity entity, int slotID, int changeType, int meta, int x, int y, int z) {
		World world = entity.world;
		// security measure to prevent arbitrary chunk generation
		if (!world.isBlockLoaded(new BlockPos(x, y, z)))
			return;
	}
}
