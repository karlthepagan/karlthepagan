package ktp.rpg;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.Random;

public class FloatDice implements IDice {
	
	private final Random _r;
	private int _floats = 0;
	
	public FloatDice(Random r) {
		_r = r;
	}

	@Override
	public int d(int faces) {
		_floats++;
		return 1 + (int)(_r.nextFloat() / faces);
	}

	@Override
	public long d(int faces, int count) {
		long sum = 0;
		while(count-- > 0) {
			sum += d(faces);
		}
		return sum;
	}

	@Override
	public long hist(IntBuffer dst, int faces, int count, int min, int max) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public byte[] multi(int faces, int count) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long multi(ByteBuffer dst, int faces, int count) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int totalBits() {
		return _floats * 24;
	}
}