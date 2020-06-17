package net.golem.network.raknet.protocol;

import io.netty.buffer.ByteBuf;
import lombok.extern.log4j.Log4j2;
import net.golem.network.GamePacketIds;
import net.golem.network.protocol.PacketBatch;
import net.golem.network.raknet.DataPacket;
import net.golem.network.raknet.codec.PacketDecoder;
import net.golem.network.raknet.protocol.connection.ConnectionRequestPacket;
import net.golem.network.raknet.protocol.connection.IncompatibleProtocolPacket;
import net.golem.network.raknet.protocol.connection.connected.ConnectedPingPacket;
import net.golem.network.raknet.protocol.connection.request.OpenConnectionRequest1Packet;
import net.golem.network.raknet.protocol.connection.request.OpenConnectionRequest2Packet;
import net.golem.network.raknet.BitFlags;
import net.golem.network.raknet.protocol.unconnected.UnconnectedPingPacket;
import net.golem.network.raknet.protocol.unconnected.UnconnectedPongPacket;

@Log4j2
public class RakNetPacketFactory {

	public static DataPacket from(ByteBuf buffer) {
		PacketDecoder decoder = new PacketDecoder(buffer);

		int id = decoder.readUnsignedByte();
		DataPacket packet = null;
		if((id & BitFlags.VALID.getId()) != 0) {
			if((id & BitFlags.ACK.getId()) != 0) {
				packet = AcknowledgePacket.createACK();
			} else if((id & BitFlags.NAK.getId()) != 0){
				packet = AcknowledgePacket.createNAK();
			} else {
				packet = new RakNetDatagram();
			}
		} else {
			switch(id) {
				case RakNetPacketIds.UNCONNECTED_PING:
					packet = new UnconnectedPingPacket();
					break;
				case RakNetPacketIds.OPEN_CONNECTION_REQUEST_1:
					packet = new OpenConnectionRequest1Packet();
					break;
				case RakNetPacketIds.OPEN_CONNECTION_REQUEST_2:
					packet = new OpenConnectionRequest2Packet();
					break;
				case RakNetPacketIds.INCOMPATIBLE_PROTOCOL_VERSION:
					packet = new IncompatibleProtocolPacket();
					break;
				case RakNetPacketIds.UNCONNECTED_PONG:
					packet = new UnconnectedPongPacket();
					break;
				case RakNetPacketIds.CONNECTION_REQUEST:
					packet = new ConnectionRequestPacket();
					break;
				case RakNetPacketIds.DISCONNECTION_REQUEST:
					packet = new DisconnectionNotificationPacket();
					break;
				case RakNetPacketIds.CONNECTED_PING:
					packet = new ConnectedPingPacket();
					break;
			}
		}
		if(packet == null) {
			packet = new RawRakNetPacket(id);
		}
		packet.decode(decoder);
		return packet;
	}

}