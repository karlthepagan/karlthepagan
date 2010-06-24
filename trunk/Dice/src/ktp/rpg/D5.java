package ktp.rpg;

/** 
 * d5 - > 4 recover none, > 5 recover LSB
 * 000
 * 001
 * 010
 * 011
 * 100
 * 101 X
 * 110 X recover LSB
 * 111 X
 * 
 * encode used in 5 MSB (5 needed)
 */
final class D5 extends Die {
	public int massStart(int minCount) {
		return minCount;
	}

	public int massValue(int v) {
		return v & 0x07;
	}

	public int r(int rand, int bits) {
		int wrand = rand;
		int used = 3;
		int r = wrand & 0x07;
		
		while(r > 4) {
			if(r > 5) {
				wrand = (wrand & 0x01) ^ wrand >>> 2;
				used += 2;
			} else {
				wrand >>>= 3;
				used += 3;
			}
			
			if( used > bits ) return -1;
			
			r = wrand & 0x07;
		}
		
		return r + (used << 3);
	}

	public int value(int v) {
		return 1 + (v & 0x07);
	}

	public int roll(BitDice r) {
		int v;
		do {
			v = r(r.nextInt(), 32);
		} while(v == -1);
		return value(v);
	}
	
	public void mass(Accumulator a, BitDice r) {
		a.add(a.minCount());
		
		int rand = r.nextInt();
		int bits = 32;
		int v;
		
		while(!a.isDone()) {
			if(bits < 3) {
				rand = r.nextInt();
				bits = 32;
			}
			
			v = rand & 0x07;
			
			if(v > 5) {
				// recover LSB
				rand = (rand & 0x01) ^ rand >>> 2;
				bits -= 2;
				continue;
			}
			
			// consumed all
			rand >>>= 3;
			bits -= 3;
			
			if(v > 4) // recover none
				continue;
			
			a.result(v & 0x07);
		}
	}

	public int bits(int i) {
		return i >>> 3;
	}

	public boolean regen(int bits) {
		return bits < 3;
	}
}