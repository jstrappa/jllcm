/* 
 * TODO update license
 * 
  This file is part of CSGS algorithm.

  CSGS algorithm is free software: you can redistribute it and/or modify it
  under the terms of the GNU Lesser General Public License as published by the
  Free Software Foundation, either version 3 of the License, or (at your
  option) any later version.

  CSGS algorithm is distributed in the hope that it will be useful, but
  WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
  or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public
  License for more details.

  You should have received a copy of the GNU Lesser General Public License
  along with CSGS algorithm.  If not, see <http://www.gnu.org/licenses/>.
 */

package feature;

import java.util.BitSet;

/**
 * A binary feature is represented as an array of boolean.
 * 
 **/
public class BinaryFeature extends Feature {
	private final BitSet _val;

	public BinaryFeature(int n) {
		super(n);
		this._val = new BitSet(n);
	}

	public BinaryFeature(BinaryFeature f) {
		super(f.getNumberOfDomainVariables(), f.getVar());
		this._val = new BitSet(f.getNumberOfDomainVariables());

		for (int i = f._val.nextSetBit(0); i >= 0; i = f._val.nextSetBit(i + 1))
			this.addVal(i, 1);
	}

	public BinaryFeature(int n, BitSet vars) {
		super(n,vars);
		this._val = new BitSet(n);
	}

	public BinaryFeature(BitSet vars, BitSet values) {
		super(vars.size(),vars);
		this._val = values;
	}
	
	/*
	 * Returns true if and only if all the variables in the scope of this
	 * feature are in the scope of otherFeature and have the same value in both features.
	 * 
	 * @see feature.Feature#isSatisfied(feature.Feature)
	 */
	@Override
	public boolean isSatisfied(Feature otherFeature) {
		for (int i = this.getVar().nextSetBit(0); i >= 0; i = this.getVar()
				.nextSetBit(i + 1)) {
			if (!otherFeature.getVar().get(i))
				return false;

			if (this.getVal(i) != otherFeature.getVal(i))
				return false;
		}

		return true;
	}

	@Override
	public void addVal(int var, Integer val) {
		if (val == null) {
			this._val.set(var, false);
			this.rmVar(var);
			return;
		}

		this.addVar(var);

		if (val == 0)
			this._val.set(var, false);
		else
			this._val.set(var, true);
	}

	@Override
	public Integer getVal(int var) {
		return this._val.get(var) ? 1 : 0;
	}

	@Override
	public int getLength() {
		return this.getVar().cardinality();
	}

	@Override
	public BinaryFeature clone() {
		return new BinaryFeature(this);
	}

	@Override
	public int hashCode() {
		int hash = super.hashCode();
		hash = 23 * hash + (this._val == null ? 0 : this._val.hashCode());
		return hash;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}
		final BinaryFeature other = (BinaryFeature) obj;
		return this.getVar().equals(other.getVar())
				&& this._val.equals(other._val);
	}

	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();

		for (int i = this.getVar().nextSetBit(0); i >= 0; i = this.getVar()
				.nextSetBit(i + 1)) {
			stringBuilder.append("+v" + i + "_" + getVal(i) + " ");
		}

		return stringBuilder.toString().trim();
	}

	public BitSet getValues() {
		return this._val;
	}

	@Override
	public boolean hasVar(int x) {
		return this.getVar().get(x);
	}


}
