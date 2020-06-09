package net.golem.network.raknet.handler.packet;

import io.netty.channel.ChannelHandlerContext;
import lombok.extern.log4j.Log4j2;
import net.golem.Server;
import net.golem.network.raknet.RakNetAddressedEnvelope;
import net.golem.network.raknet.RakNetServer;
import net.golem.network.raknet.handler.RakNetInboundPacketHandler;
import net.golem.network.raknet.protocol.connection.request.OpenConnectionRequest2Packet;
import net.golem.network.raknet.protocol.connection.response.OpenConnectionReply2Packet;

@Log4j2
public class OpenConnectionReply2Handler extends RakNetInboundPacketHandler<OpenConnectionRequest2Packet> {

	public OpenConnectionReply2Handler(RakNetServer rakNet) {
		super(rakNet, OpenConnectionRequest2Packet.class);
	}


	@Override
	protected void handlePacket(ChannelHandlerContext context, RakNetAddressedEnvelope<OpenConnectionRequest2Packet> message) {
		OpenConnectionRequest2Packet request = message.content();
		OpenConnectionReply2Packet response = new OpenConnectionReply2Packet();

		log.info("Second request packet received!");

		response.serverId = Server.getInstance().getGlobalUniqueId().getMostSignificantBits();
		response.maximumTransferUnits = request.maximumTransferUnits;

		response.clientAddress = message.recipient();

		log.info(String.format("MTU Size: %s", response.maximumTransferUnits));
		this.sendPacket(context, response, message.recipient());
	}
}
