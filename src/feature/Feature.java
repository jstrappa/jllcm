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
 * This abstract class represents a feature, which is a conjunction of
 * variable assignments.
 */
public abstract class Feature {
    private final BitSet _var;
    private final int numberOfDomainVariables;

    public Feature(int n) {
        this.numberOfDomainVariables = n;
        this._var = new BitSet(n);
    }

    protected Feature(int n, BitSet var) {
        this.numberOfDomainVariables = n;
        this._var = (BitSet)var.clone();
    }

    public BitSet getVar() {
        return this._var;
    }

    public void addVar(int var) {
        this._var.set(var);
    }

    public void rmVar(int var) {
        this._var.clear(var);
    }

    public int getNumberOfDomainVariables() {
        return this.numberOfDomainVariables;
    }

    public abstract boolean isSatisfied(Feature otherFeature);

    public abstract void addVal(int var, Integer val);

    public abstract Integer getVal(int var);

    public abstract int getLength();
    
    public abstract boolean hasVar(int x);

    @Override
    public abstract Feature clone();

    @Override
    public abstract boolean equals(Object object);

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 47 * hash + ( this._var == null ? 0 : this._var.hashCode());

        return hash;
    }

	
}