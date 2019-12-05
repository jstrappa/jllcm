package measure;

import java.util.Collection;
import java.util.HashSet;

import feature.BinaryFeature;
import feature.Feature;

public class LLCM {

	private static int N;

	public static int getN() {
		return N;
	}

	public static void setN(int n) {
		N = n;
	}

	public static ConfusionMatrix computeConfusionMatrix(Collection<Feature> model1, Collection<Feature> model2,
			int n) {
		N = n;

		// Auxiliary sets
		Collection<Feature> ijF = new HashSet<Feature>();
		Collection<Feature> ijG = new HashSet<Feature>();

		// Initialise output values
		int TP, TN, FP, FN;
		TP = TN = FP = FN = 0;

		for (int x = 0; x < N - 1; x++) {
			for (int y = x + 1; y < N; y++) {
				ijF = getMatchingFeatures(model1, x, y);
				ijG = getMatchingFeatures(model2, x, y);

				TP += cardinality(truePositives(ijF, ijG));
				FN += cardinality(falseNegatives(ijF, ijG));
				FP += cardinality(falseNegatives(ijG, ijF));
			}
		}

		TN = (int) (Math.pow(2, n) * n * (n - 1) / 2) - TP - FN - FP;

		ConfusionMatrix cm = new ConfusionMatrix(TP, TN, FP, FN);

		return cm;
	}

	/*
	 * Computes the number of contexts represented by a partitioned set of
	 * binary features.
	 */
	private static int cardinality(Collection<Feature> model) {
		int result = 0;
		for (Feature f : model) {
			result += (int) Math.pow(2, N - f.getLength());
		}
		return result;
	}

	/*
	 * Compute the set matchingFeatures of all features in 'model' that have an
	 * edge (X,Y).
	 */
	private static Collection<Feature> getMatchingFeatures(Collection<Feature> model, int x, int y) {
		Collection<Feature> result = new HashSet<Feature>();
		for (Feature f : model) {
			if (f.hasVar(x) && f.hasVar(y)) {
				result.add(f);
			}
		}
		return result;
	}

	private static Collection<Feature> falseNegatives(Collection<Feature> F, Collection<Feature> G) {
		Collection<Feature> result = new HashSet<Feature>();
		for (Feature f : F) {
			result.addAll(featureMinusFeatureSet(f, G));
		}
		return partition(result);
	}

	public static Collection<Feature> truePositives(Collection<Feature> F, Collection<Feature> G) {
		Collection<Feature> result = new HashSet<Feature>();
		for (Feature f : F) {
			for (Feature g : G) {
				result.addAll(featureDifference(f, g, "intersection"));
			}
		}
		return partition(result);
	}

	/*
	 * Returns a set of features representing the same canonical contexts as
	 * those represented by f minus those represented by each g in G.
	 */
	public static Collection<Feature> featureMinusFeatureSet(Feature f, Collection<Feature> G) {

		Collection<Feature> result = new HashSet<Feature>(); // H

		Collection<Feature> aux; // H'

		result.add(f);

		for (Feature g : G) {
			aux = new HashSet<Feature>();
			for (Feature h : result) {
				aux.addAll(featureDifference(h, g, "difference"));
			}
			result = aux;
		}
		return result;
	}

	/*
	 * Given a set of features H, returns a set of non-overlapping features P.
	 */
	public static Collection<Feature> partition(Collection<Feature> H) {
		Collection<Feature> p = new HashSet<Feature>();
		for (Feature h : H) {
			p.addAll(featureMinusFeatureSet(h, p));
		}
		return p;
	}

	/*
	 * Given two features f and g, this method computes three sets of features:
	 * 
	 * Difference: f \ g, Features that match all canonical contexts that match
	 * f but not g.
	 * 
	 * Complement: g \ f, Features that do not match f but match g.
	 * 
	 * Intersection: Features that match both f and g.
	 */
	public static Collection<Feature> featureDifference(Feature ff, Feature gg, String partition) {
		Collection<Feature> result = new HashSet<Feature>();
		Collection<Feature> difference = new HashSet<Feature>(); // Difference

		// Auxiliary variables
//		Feature I_g = gg;
		Feature I_f = ff;
		Feature d, i;

		for (int x = 0; x < N; x++) {
			//if (I_f != null && I_g != null) { // unnecessary when using break
				if (ff.hasVar(x) && gg.hasVar(x)) {
					if (ff.getVal(x) != gg.getVal(x)) {
						// X has a different value in each feature
						difference.add(I_f);
						I_f = null;
//						I_g = null;
						break;
					}
				} else if (gg.hasVar(x)) {
					// X is assigned in g but not in f
					// i.e., g(X) is included in f(X)
					d = I_f.clone();
					i = I_f.clone();
					i.addVal(x, gg.getVal(x));
					// hard-coded for binary variables:
					d.addVal(x, gg.getVal(x) == 0 ? 1 : 0);
					difference.add(d);
					I_f = i;
//				} else if (ff.hasVar(x)) {
//					// X is assigned in f but not in g
//					// i.e., f(X) is included in g(X)
//					i = I_g.clone();
//					i.addVal(x, ff.getVal(x));
//					I_g = i;
				}
			//}
		}
		// At this point, I_f and I_g should be equal

		switch (partition) {
		case "difference":
			result = difference;
			break;
		case "intersection":			
			if (I_f != null)
				result.add(I_f);
		}

		return result;
	}

}
