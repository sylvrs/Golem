package net.golem.block;

import lombok.ToString;

@ToString
public class BlockType {

	private int id;

	private int meta;

	public BlockType(int id, int meta) {
		this.id = id;
		this.meta = meta;
	}

	public int getId() {
		return id;
	}

	public int getMeta() {
		return meta;
	}

}
