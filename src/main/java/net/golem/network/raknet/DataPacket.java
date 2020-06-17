package net.golem.network.raknet;

import io.netty.buffer.ByteBuf;
import lombok.extern.log4j.Log4j2;
import net.golem.network.raknet.codec.PacketDecoder;
import net.golem.network.raknet.codec.PacketEncoder;

/**
 * Packet used for interfacing between RakNet
 */
@Log4j2
public abstract class DataPacket {

	protected int id;

	public DataPacket(int id) {
		this.id = id;
	}

	/**
	 * Returns the packet ID
	 *
	 * @return int
	 */
	public int getId() {
		return this.id;
	}

	public void encodeHeader(PacketEncoder encoder) {
		encoder.writeByte((byte) getId());
	}

	public abstract void encode(PacketEncoder encoder);

	public void decodeHeader(PacketDecoder decoder) {
		this.id = decoder.readByte();
	}

	public abstract void decode(PacketDecoder decoder);

	public ByteBuf write(PacketEncoder encoder) {
		encodeHeader(encoder);
		encode(encoder);
		return encoder.getBuffer();
	}

}
