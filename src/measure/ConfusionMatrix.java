package measure;

public class ConfusionMatrix {
	private int truePositives, trueNegatives, falsePositives, falseNegatives,
			numberOfComparisons;

	public ConfusionMatrix() {
		truePositives = trueNegatives = falseNegatives = falsePositives = 0;
	}

	public ConfusionMatrix(int truePositives, int trueNegatives,
			int falsePositives, int falseNegatives, int numberOfComparisons) {
		super();
		this.truePositives = truePositives;
		this.trueNegatives = trueNegatives;
		this.falsePositives = falsePositives;
		this.falseNegatives = falseNegatives;
		this.numberOfComparisons = numberOfComparisons;
	}

	public ConfusionMatrix(int truePositives, int trueNegatives,
			int falsePositives, int falseNegatives) {
		super();
		this.truePositives = truePositives;
		this.trueNegatives = trueNegatives;
		this.falsePositives = falsePositives;
		this.falseNegatives = falseNegatives;
	}

	public int getTruePositives() {
		return truePositives;
	}

	public void setTruePositives(int truePositives) {
		this.truePositives = truePositives;
	}

	public int getTrueNegatives() {
		return trueNegatives;
	}

	public void setTrueNegatives(int trueNegatives) {
		this.trueNegatives = trueNegatives;
	}

	public int getFalsePositives() {
		return falsePositives;
	}

	public void setFalsePositives(int falsePositives) {
		this.falsePositives = falsePositives;
	}

	public int getFalseNegatives() {
		return falseNegatives;
	}

	public void setFalseNegatives(int falseNegatives) {
		this.falseNegatives = falseNegatives;
	}

	public int getNumberOfComparisons() {
		return this.numberOfComparisons;
	}

	public void setNumberOfComparisons(int numberOfComparisons) {
		this.numberOfComparisons = numberOfComparisons;
	}

	public int getNumberOfErrors() {
		return this.falseNegatives + this.falsePositives;
	}

	public String toString() {
		return "[TP: " + this.getTruePositives() + ", FN: "
				+ this.getFalseNegatives() + "; FP: "
				+ this.getFalsePositives() + ", TN: " + this.getTrueNegatives()
				+ "]";
	}
}
