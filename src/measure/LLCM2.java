package measure;

import java.util.BitSet;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;

import util.FileUtil;
import util.Util;
import feature.BinaryFeature;
import feature.Feature;

public class LLCM2 {

	private static int N;

	public static int getN() {
		return N;
	}

	public static void setN(int n) {
		N = n;
	}

	public static ConfusionMatrix computeConfusionMatrix(
			Collection<Feature> model1, Collection<Feature> model2, int n) {
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
	private static Collection<Feature> getMatchingFeatures(
			Collection<Feature> model, int x, int y) {
		Collection<Feature> result = new HashSet<Feature>();
		for (Feature f : model) {
			if (f.hasVar(x) && f.hasVar(y)) {
				result.add(f);
			}
		}
		return result;
	}

	private static Collection<Feature> falseNegatives(Collection<Feature> F,
			Collection<Feature> G) {
		Collection<Feature> result = new HashSet<Feature>();
		for (Feature f : F) {
			result.addAll(featureMinusFeatureSet(f, G));
		}
		return partition(result);
	}

	public static Collection<Feature> truePositives(Collection<Feature> F,
			Collection<Feature> G) {
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
	public static Collection<Feature> featureMinusFeatureSet(Feature f,
			Collection<Feature> G) {

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
	 * Intersection: Features that match both f and g.
	 */
	public static Collection<Feature> featureDifference(Feature f, Feature g,
			String partition) {
		Collection<Feature> result = null;
		//Collection<Feature> difference = new HashSet<Feature>(); // Difference
		//Collection<Feature> intersection = new HashSet<Feature>(); // Intersection
		Collection<Feature> XS_f = new HashSet<Feature>(); // 
		Collection<Feature> XS_g = new HashSet<Feature>();

		//BitSet S = (BitSet) f.getVar().clone();
		BitSet S = new BitSet(f.getNumberOfDomainVariables());
		S.or(f.getVar());
		S.or(g.getVar());
		
		XS_f = generateReducedContextsFromFeature(S,f,f.getNumberOfDomainVariables());
		XS_g = generateReducedContextsFromFeature(S,g,g.getNumberOfDomainVariables());

		//intersection = XS_f;
		//difference = XS_f;
						
		switch (partition) {
		case "difference":
			Collection<Feature> difference = new HashSet<Feature>(XS_f);
			difference.removeAll(XS_g);
			result = difference;
			break;
		case "intersection":
			Collection<Feature> intersection = new HashSet<Feature>(XS_f); // Intersection
			intersection.retainAll(XS_g);
			result = intersection;
		}

		return result;
	}
	
	
	
    //TODO maybe put this method below in the Util class together with 
	// BF's generateContextsFromFeatureSet
	
	/*
	 * TODO update description
	 * Generates the canonical assignments (contexts) represented by the
	 * features in the model, that is, this method returns all the contexts
	 * whose associated canonical graph in the model given contains at least one
	 * edge.
	 */
	public static Collection<Feature> generateReducedContextsFromFeature(
			BitSet S, Feature f, int N) {
		Collection<Feature> result = new HashSet<Feature>();
		Feature g;
		int k, c, b_k;
		BitSet b, aux;

			//aux = (BitSet) f.getVar().clone();			
			aux = new BitSet(f.getNumberOfDomainVariables());
			aux.or(f.getVar());
			aux.xor(S);
			//aux.flip(0, N); // indicator of unassigned variables in f
			// System.out.println("aux: "+aux.toString());
			g = new BinaryFeature(N);
			k = S.length() - f.getLength(); // number of unassigned variables
			c = (int) Math.pow(2, k); // number of contexts represented by f

			// Generate all features represented by f
			for (int i = 0; i < c; i++) {
				g = f.clone();
				b = Util.convert(i, k);
				// System.out.println("b : "+b.toString());
				b_k = 0;

				// Set values of each context
				for (int j = aux.nextSetBit(0); j >= 0; j = aux
						.nextSetBit(j + 1)) {
					g.addVal(j, b.get(b_k) ? 1 : 0);
					b_k++;
				}

				// System.out.println("adding: "+g.toString());
				result.add(g);

			}
		

		return result;
	}

	

}
